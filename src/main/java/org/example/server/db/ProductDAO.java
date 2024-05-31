package org.example.server.db;

import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.model.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProductDAO extends DAO {
    public ProductDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }
    public long addProduct(ProductDTO productDTO,String userLogin) throws SQLException {
        Integer coordinatesId = addCoordinates(productDTO.getCoordinates());
        int manufacturerId = addManufacturer(productDTO.getManufacturer());

        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO products (" +
                "name, coordinates_id, creationdate, price, partnumber, unitofmeasure, manufacturer_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id");

        ps.setString(1, productDTO.getName());
        ps.setInt(2, coordinatesId);
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setInt(4, productDTO.getPrice());
        ps.setString(5, productDTO.getPartNumber());
        ps.setString(6, productDTO.getUnitOfMeasure().toString());
        ps.setInt(7, manufacturerId);

        long id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getLong(1);
        }
        return id;

    }

    public int addAddress(Address address) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO addresses (street) VALUES (?) RETURNING id");
        ps.setString(1, address.getStreet());
        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    public int addCoordinates(Coordinates coordinates) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id");
        ps.setInt(1, coordinates.getX());
        ps.setFloat(2, coordinates.getY());
        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;

    }

    public int addManufacturer(Organization organization) throws SQLException {
        int addressId = addAddress(organization.getOfficialAddress());
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO organizations " +
                "(name, employeescount, type, address_id)" +
                "VALUES (?, ?, ?, ?) RETURNING ID");

        ps.setString(1, organization.getName());
        ps.setLong(2,organization.getEmployeesCount());
        ps.setString(3, organization.getType().toString());
        ps.setInt(4, addressId);

        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }
    public void removeById(long id) throws SQLException {
        String deleteProductSql = "DELETE FROM products WHERE id = ?";
        String deleteCoordinatesSql = "DELETE FROM Coordinates  WHERE id = (SELECT coordinates_id FROM Products WHERE id = ?)";
        String deleteOrganizationSql = "DELETE FROM Organizations  WHERE id = (SELECT manufacturer_id FROM Products WHERE id = ?)";
        String deleteAddressSql = "DELETE FROM Addresses WHERE id = (SELECT address_id FROM Organizations WHERE id = (SELECT manufacturer_id FROM Products WHERE id = ?))";
        connection.setAutoCommit(false);
        try (PreparedStatement psProduct = connection.prepareStatement(deleteProductSql);
             PreparedStatement psCoordinates = connection.prepareStatement(deleteCoordinatesSql);
             PreparedStatement psOrganization = connection.prepareStatement(deleteOrganizationSql);
             PreparedStatement psAddress = connection.prepareStatement(deleteAddressSql)) {

            psCoordinates.setLong(1, id);
            psCoordinates.executeUpdate();

            psAddress.setLong(1, id);
            psAddress.executeUpdate();

            psOrganization.setLong(1, id);
            psOrganization.executeUpdate();

            psProduct.setLong(1, id);
            psProduct.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

    }
    public void updateProduct(long id, ProductDTO productDTO) throws SQLException{
        String updateProductSql = "UPDATE Products SET name = ?, price = ?, partNumber = ?, unitOfMeasure = ? WHERE id = ?";
        String updateCoordinatesSql = "UPDATE Coordinates SET x = ?, y = ? WHERE id = (SELECT coordinates_id FROM Products WHERE id = ?)";
        String updateOrganizationSql = "UPDATE Organizations SET name = ?, employeesCount = ?, type = ? WHERE id = (SELECT manufacturer_id FROM Products WHERE id = ?)";
        String updateAddressSql = "UPDATE Addresses SET street = ? WHERE id = (SELECT address_id FROM Organizations WHERE id = (SELECT manufacturer_id FROM Products WHERE id = ?))";
        connection.setAutoCommit(false);
        try (PreparedStatement psProduct = connection.prepareStatement(updateProductSql);
             PreparedStatement psCoordinates = connection.prepareStatement(updateCoordinatesSql);
             PreparedStatement psOrganization = connection.prepareStatement(updateOrganizationSql);
             PreparedStatement psAddress = connection.prepareStatement(updateAddressSql)) {

            psProduct.setString(1, productDTO.getName());
            psProduct.setInt(2, productDTO.getPrice());
            psProduct.setString(3, productDTO.getPartNumber());
            psProduct.setString(4, productDTO.getUnitOfMeasure().toString());
            psProduct.setLong(5, id);
            psProduct.executeUpdate();


            psCoordinates.setInt(1, productDTO.getCoordinates().getX());
            psCoordinates.setFloat(2, productDTO.getCoordinates().getY());
            psCoordinates.setLong(3, id);
            psCoordinates.executeUpdate();


            psOrganization.setString(1, productDTO.getManufacturer().getName());
            psOrganization.setLong(2, productDTO.getManufacturer().getEmployeesCount());
            psOrganization.setString(3, productDTO.getManufacturer().getType().toString());
            psOrganization.setLong(4, id);
            psOrganization.executeUpdate();


            psAddress.setString(1, productDTO.getManufacturer().getOfficialAddress().getStreet());
            psAddress.setLong(2, id);
            psAddress.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

    }
    public Set<Product> getAllProducts() throws SQLException {
        Set<Product> result = new LinkedHashSet<>();
        String statement = "SELECT " +
                "p.id, p.name, p.creationDate, p.price, p.partNumber, p.unitOfMeasure, " +
                "c.x AS coordinate_x, c.y AS coordinate_y, " +
                "o.id AS organization_id, o.name AS organization_name, o.employeesCount, o.type AS organization_type, " +
                "a.street AS organization_street " +
                "FROM Products p " +
                "JOIN Coordinates c ON p.coordinates_id = c.id " +
                "JOIN Organizations o ON p.manufacturer_id = o.id " +
                "LEFT JOIN Addresses a ON o.address_id = a.id";;
        PreparedStatement ps = this.connection.prepareStatement(statement);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Product product  = resultSetToProduct(resultSet);
            result.add(product);
        }
        return result;

    }
    private Product resultSetToProduct(ResultSet resultSet) throws SQLException {
        long id  = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Integer x = resultSet.getInt("coordinate_x");
        Float y = resultSet.getFloat("coordinate_y");
        Coordinates coordinates = new Coordinates(x, y);
        LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
        int price = resultSet.getInt("price");
        String partNumber = resultSet.getString("partnumber");
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(resultSet.getString("unitofmeasure"));
        long organizationId = resultSet.getLong("organization_id");
        String organizationName = resultSet.getString("organization_name");
        Long employeesCount = resultSet.getLong("employeesCount");
        OrganizationType organizationType = OrganizationType.valueOf(resultSet.getString("organization_type"));
        String street = resultSet.getString("organization_street");
        Address address = new Address(street);
        Organization organization = new Organization(organizationId, organizationName, employeesCount, organizationType, address);
        return new Product(id, name, coordinates, creationDate, price, partNumber, unitOfMeasure, organization);

    }


}
