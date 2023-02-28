package com.codecool.buyourstuff.dao.implementation.databse;

public class DaoDbConnectionData {
    protected static final String DB_TYPE = "jdbc:postgresql";

    protected static final String ADDRESS = "localhost";
    protected static final int PORT = 5432;
    protected static final String DB_NAME = "codecoolshop";

    protected static final String DB_URL = DB_TYPE + "://" + ADDRESS + ":" + PORT + "/" + DB_NAME;

    protected static final String userName = System.getenv("USER_NAME");
    protected static final String pass = System.getenv("PASS");



}
