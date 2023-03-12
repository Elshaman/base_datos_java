package org.example;

import org.example.modelo.Categoria;
import org.example.modelo.Producto;
import org.example.repositorio.ProductoRepositorioImp;
import org.example.repositorio.Repositorio;

import java.sql.Date;

public class EjemploJdbcUpdate {

    public static void main(String[] args) {




            Repositorio<Producto> repo = new ProductoRepositorioImp();
            System.out.println("========== Listar ================");
            repo.listar().forEach(System.out::println);
            System.out.println("========== obtener por id ================");
            System.out.println(repo.porId(1L));
            System.out.println("========== editar ================");
            Producto p = new Producto();
            p.setId(4L);
            p.setNombre("TEclado corsair mecanico");
            p.setPrecio(600);
            Categoria categoria = new Categoria();
            categoria.setId(1L);
            p.setCategoria(categoria);
            p.setFechaRegistro(new Date(2023 , 3 , 3));
            repo.guardar(p);
            System.out.println("Producto editado con exito");
            repo.listar().forEach(System.out::println);

    }
}
