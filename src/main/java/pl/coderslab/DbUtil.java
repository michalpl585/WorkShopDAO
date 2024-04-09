package pl.coderslab;
/*
create database if not exists workshop2
character set utf8mb4
collate utf8mb4_unicode_ci

-- TABELA Z UŻYTKOWNIKAMI
create table if not exists users
(
    id int primary key auto_increment not null,
    email varchar(255) unique not null,
    username varchar(255) not null,
    password varchar(60) not null
);
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
