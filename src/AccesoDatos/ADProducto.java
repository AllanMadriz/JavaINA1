package AccesoDatos;

import Config.Config;
import Entidades.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADProducto {

    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }
    public ADProducto() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Producto producto) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Productos (NombreProducto, FechaElaboracion, FechaVencimiento, Stock, Precio, Descripcion, IDCategoriaProducto, IDProveedor)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombreProducto());
            ps.setDate(2, (Date) producto.getFechaElaboracion());
            ps.setDate(3, (Date) producto.getFechaVencimiento());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getPrecio());
            ps.setString(6, producto.getDescripcion());
            ps.setInt(7, producto.getIdCategoriaProducto());
            ps.setInt(8, producto.getIdProveedor());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Producto ingresado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }
    
    public List<Producto> ListarProductos(String condicion) throws SQLException {
        ResultSet rs;
        List<Producto> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idproducto, nombreproducto, fechaelaboracion, fechavencimiento, stock, precio, descripcion, idcategoriaproducto, idproveedor, enventa"
                    + " from Productos";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Producto(rs.getInt("idproducto"), rs.getString("nombreproducto"), rs.getDate("fechaelaboracion"),
                        rs.getDate("fechavencimiento"), rs.getInt("stock"), rs.getInt("precio"), rs.getString("descripcion"), rs.getInt("idcategoriaproducto"),
                        rs.getInt("idproveedor"), rs.getBoolean("enventa")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }
    
    public Producto ObtenerProducto(String condicion) throws SQLException {
        Producto producto = new Producto();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idproducto, nombreproducto, fechaelaboracion, fechavencimiento, stock, precio, descripcion, idcategoriaproducto, idproveedor, enventa"
                    + " from Productos";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                producto.setIdProducto(rs.getInt(1));
                producto.setNombreProducto(rs.getString(2));
                producto.setFechaElaboracion(rs.getDate(3));
                producto.setFechaVencimiento(rs.getDate(4));
                producto.setStock(rs.getInt(5));
                producto.setPrecio(rs.getInt(6));
                producto.setDescripcion(rs.getString(7));
                producto.setIdCategoriaProducto(rs.getInt(8));
                producto.setIdProveedor(rs.getInt(9));
                producto.setEnVenta(rs.getBoolean(10));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return producto;
    }

    public int Modificar(Producto producto) throws Exception {
        int resultado;
        String sentencia = "update Productos set NombreProducto=?, FechaElaboracion=?, FechaVencimiento=?, Stock=?, Precio=?, Descripcion=?, IDCategoriaProducto=?, IDProveedor=? "
                + "where IDProducto=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setString(1, producto.getNombreProducto());
            ps.setDate(2, (Date) producto.getFechaElaboracion());
            ps.setDate(3, (Date) producto.getFechaVencimiento());
            ps.setInt(4, producto.getStock());
            ps.setInt(5, producto.getPrecio());
            ps.setString(6, producto.getDescripcion());
            ps.setInt(7, producto.getIdCategoriaProducto());
            ps.setInt(8, producto.getIdProveedor());
            ps.setInt(9, producto.getIdProducto());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Producto actualizado satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Producto producto) throws Exception {
        int resultado;
        String sentencia = "update Productos set EnVenta = 0 where IDProducto=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, producto.getIdProducto());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Producto borrado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
