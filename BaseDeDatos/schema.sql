CREATE DATABASE IF NOT EXISTS recycling_db;
USE recycling_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_date VARCHAR(20),
    points INT DEFAULT 0,
    glass INT DEFAULT 0,
    plastic INT DEFAULT 0,
    metal INT DEFAULT 0,
    hard_to_recycle INT DEFAULT 0
);