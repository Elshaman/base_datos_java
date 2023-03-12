package org.example.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionPool {
    private static String url = "jdbc:mysql://localhost:3306/java_curso";
    private static String username="root";
    private static String password="";
    private static BasicDataSource pool;

    //este metodo devuelve un pool de conexiones no una conexion
    public static BasicDataSource getInstance() throws SQLException {
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            //numero de conexiones abierta para empezar
            pool.setInitialSize(3);
            //minimo de conexiones abiertas INUTILIZADAS esperando para conectarse
            pool.setMinIdle(3);
            //maximo de conexiones abiertas INUTILIZADAS esperando a conectarse
            pool.setMaxIdle(10);
            //conexiones totales activas e inactivas
            pool.setMaxTotal(10);

        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
