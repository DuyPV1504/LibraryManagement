USE library_management;

DROP TABLE IF EXISTS Loans;
DROP TABLE IF EXISTS Admins;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Books;

CREATE TABLE IF NOT EXISTS Users
(
    id           INT auto_increment PRIMARY KEY,
    surname      VARCHAR(50)            NOT NULL,
    lastname     VARCHAR(50)            NOT NULL,
    dateOfBirth  DATE                   NOT NULL,
    gender       ENUM ('Male', 'Female', 'Other'),
    email        VARCHAR(50) UNIQUE     NOT NULL,
    userName     VARCHAR(50) UNIQUE     NOT NULL,
    userAccount  VARCHAR(50) UNIQUE     NOT NULL,
    userPassword varchar(50)            NOT NULL,
    roles        ENUM ('User', 'Admin') NOT NULL,
    warning      TINYINT(1)             NOT NULL DEFAULT 0 -- 1 - yes, 0 - no
);

ALTER TABLE Users
    ADD INDEX idx_users_roles (roles);

CREATE TABLE IF NOT EXISTS Admins
(
    id          int auto_increment PRIMARY KEY,
    userAccount VARCHAR(50) UNIQUE NOT NULL,
    roles       ENUM ('Admin')     NOT NULL,
    FOREIGN KEY (id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Books
(
    id             int auto_increment PRIMARY KEY,
    bookName       VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    publisher      VARCHAR(255),
    publishYear    year,
    availableBooks INT default 0,
    totalBooks     INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Loans
(
    transaction_id int auto_increment PRIMARY KEY,
    userAccount    VARCHAR(50),
    book_id        int ,
    borrowDate     DATE        NOT NULL,
    endDate        DATE        NOT NULL,
    returnDate     DATE,
    status         VARCHAR(50) NOT NULL,
    FOREIGN KEY (userAccount) REFERENCES Users (userAccount) ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES Books (id) ON UPDATE CASCADE
);

INSERT INTO Users (surname, lastname, dateOfBirth, gender, email, userName, userAccount, userPassword, roles, warning)
VALUES ('Nguyen', 'Van A', '1990-01-15', 'Male', 'nguyenvana@example.com', 'nguyenvana', 'user1', 'ehem', 'User', 0),
       ('Tran', 'Thi B', '1995-05-20', 'Female', 'tranthib@example.com', 'tranthib', 'user2', 'ehem', 'Admin', 1),
       ('Hang', 'Van C', '1999-05-26', 'Male', 'hoangvanc@example.com', 'hoangvanc', 'user3', 'ehem', 'Admin', 1);

INSERT INTO Admins (id, userAccount)
VALUES (2, 'user2'),
       (3, 'user3');

INSERT INTO Books (bookName, author, publisher, publishYear, availableBooks, totalBooks)
VALUES ('To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.',
        1960, 5, 10),
       ('1984', 'George Orwell', 'Secker & Warburg', 1949,
        3, 7);

INSERT INTO Loans (userAccount, book_id, borrowDate, endDate, returnDate, status)
VALUES ('user1', 1, '2024-10-01', '2024-10-15', NULL, 'Borrowed'),
       ('user2', 2, '2024-10-05', '2024-10-20', NULL, 'Borrowed');

