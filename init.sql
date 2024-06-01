CREATE TABLE Coordinates (
                             id SERIAL PRIMARY KEY,
                             x INTEGER NOT NULL CHECK (x > -497),
                             y FLOAT NOT NULL CHECK (y <= 745)
);

CREATE TABLE Addresses (
                           id SERIAL PRIMARY KEY,
                           street VARCHAR(255)
);

CREATE TABLE Organizations (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL CHECK (name <> ''),
                               employeesCount BIGINT CHECK (employeesCount > 0),
                               type VARCHAR(50) NOT NULL CHECK (type IN ('GOVERNMENT', 'PRIVATE_LIMITED_COMPANY', 'OPEN_JOINT_STOCK_COMPANY')),
                               address_id INTEGER,
                               FOREIGN KEY (address_id) REFERENCES Addresses(id) ON DELETE  CASCADE
);

CREATE TABLE Products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL CHECK (name <> ''),
                          coordinates_id INTEGER NOT NULL,
                          creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          price INTEGER NOT NULL CHECK (price > 0),
                          partNumber VARCHAR(74) NOT NULL CHECK (partNumber <> ''),
                          unitOfMeasure VARCHAR(255) CHECK (unitOfMeasure IN ('KILOGRAMS', 'PCS', 'MILLIGRAMS')),
                          manufacturer_id INTEGER NOT NULL,
                          FOREIGN KEY (coordinates_id) REFERENCES Coordinates(id) ON DELETE CASCADE,
                          FOREIGN KEY (manufacturer_id) REFERENCES Organizations(id) ON DELETE CASCADE
);

CREATE TABLE Users (
                       login VARCHAR(255) PRIMARY KEY,
                       password VARCHAR(96) NOT NULL
);

CREATE TABLE Product_User(
                             product_id BIGINT REFERENCES Products(id) ON DELETE CASCADE,
                             user_login VARCHAR(255) REFERENCES Users(login) ON DELETE CASCADE,
                             PRIMARY KEY(product_id, user_login)

);