package org.example;

import org.example.modelo.Categoria;
import org.example.modelo.Producto;
import org.example.repositorio.ProductoRepositorioImp;
import org.example.repositorio.Repositorio;

import java.sql.Date;

public class EjemploJdbc {

    public static void main(String[] args) {




            Repositorio<Producto> repo = new ProductoRepositorioImp();
            System.out.println("========== Listar ================");
            repo.listar().forEach(System.out::println);
            System.out.println("========== obtener por id ================");
            System.out.println(repo.porId(1L));
            System.out.println("========== insertar ================");
            Producto p = new Producto();
            p.setNombre("tecla red dragon  mecanico");
            p.setPrecio(500);
            p.setFechaRegistro(new Date(2023 , 3 , 3));
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            p.setCategoria(categoria);
            repo.guardar(p);
            System.out.println("Producto guardado con exito");
            repo.listar().forEach(System.out::println);

    }
}
