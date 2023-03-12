package org.example;

import org.example.modelo.Producto;
import org.example.repositorio.ProductoRepositorioImp;
import org.example.repositorio.Repositorio;
import org.example.util.ConexionSingleton;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class EjemploJdbcDelete {

    public static void main(String[] args) {




            Repositorio<Producto> repo = new ProductoRepositorioImp();
            System.out.println("========== Listar ================");
            repo.listar().forEach(System.out::println);
            System.out.println("========== obtener por id ================");
            System.out.println(repo.porId(1L));
            System.out.println("========== editar ================");
            repo.eliminar(4L);
            System.out.println("Producto eliminado con exito");
            repo.listar().forEach(System.out::println);

    }
}
