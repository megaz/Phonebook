package com.zahir.dao.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
public class DAOHelper {
        private static DAOHelper instance = new DAOHelper();
        private final EntityManager mgr;
        private DAOHelper(){
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager");
                mgr = factory.createEntityManager();
        }

        public EntityManager getEntityManager(){
                return mgr;
        }

        public static DAOHelper getInstance(){
                return instance;
        }
}
