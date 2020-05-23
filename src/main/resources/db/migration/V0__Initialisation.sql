CREATE TABLE Departments(
    name varchar(64),
    phoneNumber varchar (16),
    PRIMARY KEY (name)
);

CREATE TABLE Employees(
    name varchar(64),
    department varchar(64) NOT NULL,
    externalNumber varchar(16),
    internalNumber char(3),
    homeNumber varchar(16),
    PRIMARY KEY (name),
    FOREIGN KEY (department) REFERENCES Departments(name) ON UPDATE CASCADE
);