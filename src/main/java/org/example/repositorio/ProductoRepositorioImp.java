package org.example.repositorio;

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
        try(Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from productos")){

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
        try(PreparedStatement stmt = getConnection()
                                    .prepareStatement("select * from productos where id = ?")) {
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
        return p;
    }

    @Override
    public void guardar(Producto producto) {
        String sql = null;
        if (producto.getId() !=null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre=?, precio =? WHERE id=?";
        }else{
            sql = "INSERT INTO PRODUCTOS(nombre, precio, fecha_registro) values(?,?,?)";
        }
        try(PreparedStatement s = getConnection().prepareStatement(sql)){
            s.setString(1, producto.getNombre());
            s.setLong(2, producto.getPrecio());
            if(producto.getId() !=null && producto.getId()>0){
                s.setLong(3, producto.getId());
            }else{

            }
            s.setDate(3, new Date(producto.getFechaRegistro().getTime()));
            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try(PreparedStatement s= getConnection().prepareStatement("DELETE FROM PRODUCTOS WHERE id=?")) {
            s.setLong(1, id);
            s.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
