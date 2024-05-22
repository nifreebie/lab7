package org.example.server.utils;

import org.example.contract.responses.Response;
import org.example.contract.utils.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TCPserver {
    private static final int BUFFER_SIZE = 4096;
    private ByteBuffer buffer;

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private CommandManager commandManager;
    private RequestHandler requestHandler;
    private Logger logger;

    private final ExecutorService requestProcessPool = Executors.newFixedThreadPool(5);
    private final ExecutorService responseWritePool = Executors.newCachedThreadPool();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public TCPserver(CommandManager commandManager, RequestHandler requestHandler, Logger logger) {
        this.commandManager = commandManager;
        this.requestHandler = requestHandler;
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.logger = logger;
    }
public void openConnection() throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    this.serverSocketChannel.configureBlocking(false);
    InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
    this.serverSocketChannel.bind(inetSocketAddress);
    this.selector = initSelector();
}

    public void run() {
        try {
            while (true) {
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = takeKey(selectedKeys);
                    handleKey(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SelectionKey takeKey(Iterator<SelectionKey> selectionKeyIterator) {
        SelectionKey key = selectionKeyIterator.next();
        selectionKeyIterator.remove();
        return key;
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();
        this.serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
        return socketSelector;
    }

    private void handleKey(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                accept(key);
            } else if (key.isReadable()) {
                //read(key);
                new Thread(() -> {
                    try {
                        read(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (key.isWritable()) {
                write(key);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        System.out.println("Подключено: " + socketChannel.getRemoteAddress());
        logger.log("Подключено: " + socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer localBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead;
        try {
            bytesRead = socketChannel.read(localBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (bytesRead == -1) {
            key.cancel();
            return;
        }
        localBuffer.flip();

//        new Thread(() -> {
//            int bytesRead;
//            try {
//                try {
//                    bytesRead = socketChannel.read(localBuffer);
//                } catch (IOException e) {
//                    key.cancel();
//                    socketChannel.close();
//                    return;
//                }
//
//                if (bytesRead == -1) {
//                    key.cancel();
//                    return;
//                }
//                localBuffer.flip();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();


        requestProcessPool.submit(() -> {
            readWriteLock.readLock().lock();
            try {
                Response response = requestHandler.handleRequest(localBuffer);
                System.out.println(response);
                logger.log("Отправлено: " + response.toString());
                socketChannel.register(selector, SelectionKey.OP_WRITE, response);
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        });
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();
        responseWritePool.execute(() -> {
            ByteBuffer writeBuffer = null;
            try {
                writeBuffer = Serializer.serializeObject(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeBuffer.flip();
            while (writeBuffer.hasRemaining()) {
                try {
                    socketChannel.write(writeBuffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public void close() throws IOException {
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        requestProcessPool.shutdown();
        responseWritePool.shutdown();
    }
}
