package org.example.contract.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedLineReader {
    private final BufferedInputStream bufferedInputStream;

    private Integer cachedCharCode = null;

    public BufferedLineReader(InputStream inputStream) {
        bufferedInputStream = new BufferedInputStream(inputStream);
    }


    public String nextLine() {
        StringBuilder builder = new StringBuilder();
        if (cachedCharCode != null) {
            builder.append((char) cachedCharCode.intValue());
            cachedCharCode = null;
        }
        while (true) {
            int charCode = readCharCode();
            if (charCode == -1) {
                break;
            } else if (charCode == '\n') {
                break;
            } else if (charCode == '\r') {
                readCharCode();
                break;
            }
            builder.append((char) charCode);
        }
        return builder.toString();
    }

    public boolean hasNextLine() {
        if (cachedCharCode != null) {
            return true;
        }
        int charCode = readCharCode();
        if (charCode == -1) {
            return false;
        }
        cachedCharCode = charCode;
        return true;
    }

    private int readCharCode() {
        int charCode;
        try {
            charCode = bufferedInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return charCode;
    }
}
