package AccesoDatos;

import Config.Config;
import Entidades.DetalleFactura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADDetalleFactura {

    private Connection _cnn;

    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADDetalleFactura() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(DetalleFactura detalleFactura) throws Exception {
        int idUser = 1;
        String sentencia = "insert into DetalleFacturas (IDFactura, IDProducto, CantidadFactura, PrecioUnitario, Total) values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, detalleFactura.getIdFactura());
            ps.setInt(2, detalleFactura.getIdProducto());
            ps.setInt(3, detalleFactura.getCantidadFacturada());
            ps.setInt(4, detalleFactura.getPrecioUnidad());
            ps.setInt(5, detalleFactura.getTotal());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Detalle creado";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<DetalleFactura> ListarDetallesFactura(String condicion) throws SQLException {
        ResultSet rs;
        List<DetalleFactura> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idfactura, idproducto, cantidadfactura, preciounitario, total from DetalleFacturas";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new DetalleFactura(rs.getInt("idfactura"), rs.getInt("idproducto"), rs.getInt("cantidadfactura"),
                        rs.getInt("preciounitario"), rs.getInt("total")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    public DetalleFactura ObtenerDetalle(String condicion) throws SQLException {
        DetalleFactura detalleFactura = new DetalleFactura();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select IDFactura, IDProducto, CantidadFactura, PrecioUnitario, Total from DetalleFacturas";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                detalleFactura.setIdFactura(rs.getInt(1));
                detalleFactura.setIdProducto(rs.getInt(2));
                detalleFactura.setCantidadFacturada(rs.getInt(3));
                detalleFactura.setPrecioUnidad(rs.getInt(4));
                detalleFactura.setTotal(rs.getInt(5));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return detalleFactura;
    }

    public int Eliminar(DetalleFactura detalleFactura) throws Exception {
        int resultado;
        String sentencia = "delete from DetalleFacturas where IDFactura=? and IDProducto=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, detalleFactura.getIdFactura());
            ps.setInt(2, detalleFactura.getIdProducto());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Detalle eliminado";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

}
