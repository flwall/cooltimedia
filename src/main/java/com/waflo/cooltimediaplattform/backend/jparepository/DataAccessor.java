package com.waflo.cooltimediaplattform.backend.jparepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataAccessor {
    private static final String env = "dev";

    public DataAccessor() {
        if(env.equalsIgnoreCase("prod")){
            factory=Persistence.createEntityManagerFactory("prod");
        }else
            factory= Persistence.createEntityManagerFactory("dev");



    }

    public static DataAccessor getInstance() {
        if (instance == null) {
            instance = new DataAccessor();
        }

        return instance;
    }

    private static DataAccessor instance;

    protected EntityManagerFactory factory;//= Persistence.createEntityManagerFactory("dev");
}
