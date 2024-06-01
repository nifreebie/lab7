package org.example.server.collection;

import lombok.Getter;
import lombok.Setter;
import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.model.Product;
import org.example.contract.model.ProductDTO;
import org.example.server.db.ProductDAO;
import org.example.server.db.ProductUserReferenceDAO;
import org.example.server.db.UserDAO;
import org.example.server.utils.ProductComparator;

import java.sql.SQLException;
import java.util.*;
@Getter
@Setter
public class CollectionManager {
    private Set<Product> products;
    private long lastId = 0;
    private ProductDAO productDAO;
    private UserDAO userDAO;
    private ProductUserReferenceDAO productUserReferenceDAO;

    public CollectionManager(Set<Product> productCollection) {
        this.products = productCollection;
    }

    public void add(ProductDTO productDTO, String ownerLogin) throws SQLException {
        long id = productDAO.addProduct(productDTO,ownerLogin);
        productUserReferenceDAO.addRelationship(id, ownerLogin);
        loadCollectionFromDB();
    }
    public void loadCollectionFromDB() throws SQLException {
        this.products = this.productDAO.getAllProducts();
    }

    public void removeById(long id, String login) throws SQLException, UserIsNotOwnerException {
        if (!productUserReferenceDAO.isOwner(id, login)) {
            throw new UserIsNotOwnerException();
        }
        System.out.println("1");
        productDAO.removeById(id);
        loadCollectionFromDB();
    }

    public void updateById(long id, ProductDTO productDTO, String login) throws SQLException {
        if (!productUserReferenceDAO.isOwner(id, login)) {
            throw new UserIsNotOwnerException();
        }
        this.productDAO.updateProduct(id, productDTO);
        loadCollectionFromDB();
    }

    public long generateId() {
        return ++lastId;
    }

    public int getSize() {
        return products.size();
    }

    public void sort(Comparator<Product> comparator) {
        List<Product> list = new ArrayList<>();
        for(Product p: products){
            list.add(p);
        }
        list.sort(comparator);
        products = new LinkedHashSet<>(list);

    }

    public boolean isIdExists(long idToFind) {
        boolean flag = false;
        for (Product p : products) {
            if (p.getId() == idToFind) flag = true;
        }
        return flag;

    }

    public boolean checkIfMin(ProductDTO productDTO) {
        ProductComparator productComparator = new ProductComparator();
        sort(productComparator);
        Product p = new Product(generateId(), productDTO);
        List<Product> list = new ArrayList<>(products);
        if (productComparator.compare(p, list.get(0)) < 0) {
            return true;
        } else {
            return false;
        }

    }

    public void removeGreater(ProductDTO productDTO) {
        Product p = new Product(generateId(), productDTO);
        ProductComparator productComparator = new ProductComparator();
        for (Product product : products) {
            if (productComparator.compare(product, p) > 0) {
                products.remove(product);
            }

        }

    }

    public void removeLower(ProductDTO productDTO) {
        Product p = new Product(generateId(), productDTO);
        ProductComparator productComparator = new ProductComparator();
        for (Product product : products) {
            if (productComparator.compare(product, p) < 0) {
                products.remove(product);
            }

        }

    }

    public void clear(String owner) throws SQLException {
        for(long id: productUserReferenceDAO.getUserProducts(owner)){
            productDAO.removeById(id);
        }
        loadCollectionFromDB();
    }

    public void save() {
        Storage.save(products,
                "collection");

    }
}
