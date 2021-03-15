package com.waflo.cooltimediaplattform.backend.jparepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;


public class DataAccessor {
    private static final String env="dev";

    public DataAccessor(){
        if(env.equalsIgnoreCase("prod")){
            factory=Persistence.createEntityManagerFactory("prod");
        }else
            factory=Persistence.createEntityManagerFactory("dev");

    }
    public static DataAccessor getInstance(){
        if(instance==null){
            instance=new DataAccessor();
        }

        return instance;
    }
    private static DataAccessor instance;
    protected final EntityManagerFactory factory;//= Persistence.createEntityManagerFactory("dev");
}
