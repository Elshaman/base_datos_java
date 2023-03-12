package org.example.repositorio;

import org.example.modelo.Categoria;
import org.example.modelo.Producto;
import org.example.util.ConexionSingleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImp implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return ConexionSingleton.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try(
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select p.*,c.nombre as categoria from productos as p" +
                        " inner join categorias as c ON(p.categoria_id = c.id) ")) {

                        while (rs.next()){
                            Producto p = crearProducto(rs);
                            productos.add(p);
                        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) {
        Producto producto = null;
        try(     Connection conn = getConnection();
                 PreparedStatement stmt = conn
                                    .prepareStatement("select p.*,c.nombre as categoria from productos as p " +
                                                          "inner join categorias as c ON(p.categoria_id = c.id) where p.id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                producto = crearProducto(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    private static Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        p.setCategoria(categoria);
        return p;
    }

    @Override
    public void guardar(Producto producto) {
        String sql = null;
        if (producto.getId() !=null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre=?, precio =?, categoria_id=? WHERE id=?";
        }else{
            sql = "INSERT INTO PRODUCTOS(nombre, precio, categoria_id, fecha_registro) values(?,?,?,?)";
        }
        try(
                Connection conn = getConnection();
                PreparedStatement s = conn.prepareStatement(sql)){

                    s.setString(1, producto.getNombre());
                    s.setLong(2, producto.getPrecio());
                    s.setLong(3, producto.getCategoria().getId());

            if(producto.getId() !=null && producto.getId()>0){
                s.setLong(4, producto.getId());
            }else{
                s.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }
            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try(
                Connection conn = getConnection();
                PreparedStatement s= conn.prepareStatement("DELETE FROM PRODUCTOS WHERE id=?")) {
                    s.setLong(1, id);
                    s.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
