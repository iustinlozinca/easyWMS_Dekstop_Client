CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(255),
    tipulDeUtilizator VARCHAR(100)
);

CREATE TABLE Produs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ean VARCHAR(50),
    nume VARCHAR(255),
    codIntern VARCHAR(100),
    unitate INT,
    stoc INT
);

CREATE TABLE Comanda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dataComanda DATETIME,
    status VARCHAR(100),
    valoareTotala DECIMAL(10,2),
    observatii TEXT
);

CREATE TABLE Produs_Comanda (
    produsId INT,
    comandaId INT,
    cantitateComandata INT,
    cantitateScanata INT,
    FOREIGN KEY (produsId) REFERENCES Produs(id),
    FOREIGN KEY (comandaId) REFERENCES Comanda(id)
);

CREATE TABLE User_Comanda (
    userId INT,
    comandaId INT,
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (comandaId) REFERENCES Comanda(id)
);

CREATE TABLE Comentariu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    comandaId INT NULL,
    data DATETIME,
    comentariu TEXT,
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (comandaId) REFERENCES Comanda(id)
);
