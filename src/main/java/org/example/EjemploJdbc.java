package org.example;

import org.example.modelo.Producto;
import org.example.repositorio.ProductoRepositorioImp;
import org.example.repositorio.Repositorio;
import org.example.util.ConexionSingleton;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbc {

    public static void main(String[] args) {


        try (Connection conn = ConexionSingleton.getInstance()){

            Repositorio<Producto> repo = new ProductoRepositorioImp();
            repo.listar().forEach(System.out::println);
            System.out.println(repo.porId(1L));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
