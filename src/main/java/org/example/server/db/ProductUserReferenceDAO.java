package org.example.server.db;

import org.example.contract.model.ProductDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductUserReferenceDAO extends DAO{
    public ProductUserReferenceDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }
    public void addRelationship(long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO product_user (" +
                "product_id, user_login) VALUES (?,?) ");
        ps.setLong(1,id);
        ps.setString(2,ownerLogin);

        ps.executeUpdate();
    }
    public boolean isOwner(long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("SELECT user_login FROM product_user WHERE product_id = ?");
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            String realOwner = resultSet.getString("user_login");
            return realOwner.equals(ownerLogin);
        }
        return false;
    }

    public ArrayList<Long> getUserProducts(String ownerLogin) throws SQLException {
        ArrayList<Long> ids = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement("SELECT product_id FROM product_user WHERE user_login = ?");
        ps.setString(1,ownerLogin);

        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            ids.add(resultSet.getLong("product_id"));
        }
        return ids;
    }
}
