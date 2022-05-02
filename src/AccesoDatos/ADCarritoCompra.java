package AccesoDatos;

import Config.Config;
import Entidades.CarritoCompra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//No se comentaran todas las clases de Acceso a Datos ya que son muy similares

public class ADCarritoCompra {
    //Creamos el objeto de conexion y el mensaje a devolver
    private Connection _cnn;
    
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    //Creamos un constructor
    public ADCarritoCompra() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    //Metodo para insertar 
    public int Insertar(CarritoCompra carritoCompra) throws Exception {
        int idUser = 1;
        String sentencia = "insert into CarritoCompras (IDProducto, IDCliente, Cantidad) values (?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, carritoCompra.getIdProducto());
            ps.setInt(2, carritoCompra.getIdCliente());
            ps.setInt(3, carritoCompra.getCantidad());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Producto agregado al carrito satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    //Metodo para listar el carrito de compra
    public List<CarritoCompra> ListarCarrito(String condicion) throws SQLException {
        ResultSet rs;
        List<CarritoCompra> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select P.IDProducto, NombreProducto, idcliente, cantidad from CarritoCompras inner join Productos P on P.IDProducto = CarritoCompras.IDProducto";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new CarritoCompra(rs.getInt("P.IDProducto"), rs.getInt("idcliente"), rs.getInt("cantidad"), rs.getString("NombreProducto") ));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    //Metodo para modificar 
    public int Modificar(CarritoCompra carritoCompra) throws Exception {
        int resultado;
        String sentencia = "update CarritoCompras set cantidad=? where IDProducto = ? and IDCliente = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, carritoCompra.getCantidad());
            ps.setInt(2, carritoCompra.getIdProducto());
            ps.setInt(2, carritoCompra.getIdCliente());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = ":)";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    //Metodo para eliminar
    public int Eliminar(CarritoCompra carritoCompra) throws Exception {
        int resultado;
        String sentencia = "delete from CarritoCompras where IDProducto = ? and IDCliente = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, carritoCompra.getIdProducto());
            ps.setInt(2, carritoCompra.getIdCliente());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Borrado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
