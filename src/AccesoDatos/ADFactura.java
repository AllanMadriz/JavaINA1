package AccesoDatos;

import Config.Config;
import Entidades.Factura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADFactura {

    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADFactura() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Factura factura) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Facturas (fechafactura, tipofactura) \n"
                + "values (?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, (Date) factura.getFechaFactura());
            ps.setString(2, factura.getTipoFactura());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Factura creada satisfactoriamente";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<Factura> ListarFacturas(String condicion) throws SQLException {
        ResultSet rs;
        List<Factura> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idfactura, idcliente, idempleado, fechafactura, totalmonto, descuento, montopagar, tipofactura, cancelada from Facturas";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Factura(rs.getInt("idfactura"), rs.getInt("idcliente"), rs.getInt("idempleado"), rs.getDate("fechafactura"),
                        rs.getInt("totalmonto"), rs.getInt("descuento"), rs.getInt("montopagar"),
                        rs.getString("tipofactura"), rs.getBoolean("cancelada")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    public Factura ObtenerFactura(String condicion) throws SQLException {
        Factura factura = new Factura();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idfactura, idcliente, idempleado, fechafactura, totalmonto, descuento, montopagar, tipofactura, cancelada from Facturas";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                factura.setIdFactura(rs.getInt(1));
                factura.setIdCliente(rs.getInt(2));
                factura.setIdEmpleado(rs.getInt(3));
                factura.setFechaFactura(rs.getDate(4));
                factura.setTotalMonto(rs.getInt(5));
                factura.setDescuento(rs.getInt(6));
                factura.setMontoPagar(rs.getInt(7));
                factura.setTipoFactura(rs.getString(8));
                factura.setCancelada(rs.getBoolean(9));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return factura;
    }

    public int Modificar(Factura factura) throws Exception {
        int resultado;
        String sentencia = "update Facturas set TotalMonto = (SELECT SUM(PrecioUnitario * CantidadFactura) as sumPrice "
                + "FROM DetalleFacturas "
                + "WHERE IDFactura = ? "
                + "GROUP BY IDFactura "
                + ") where IDFactura = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, factura.getIdFactura());
            ps.setInt(2, factura.getIdFactura());
            resultado = ps.executeUpdate();

//            System.out.println(factura.getIdFactura());
            if (resultado > 0) {
                _mensaje = "Factura actualizada satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int ModificarTotal(Factura factura) throws Exception {
        int resultado;
        String sentencia = "update Facturas set Facturas.MontoPagar = ((Facturas.TotalMonto * 0.13) + (Facturas.TotalMonto)) where IDFactura = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, factura.getIdFactura());
            resultado = ps.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Factura factura) throws Exception {
        int resultado;
        String sentencia = "update Facturas set Cancelada = 1 where IDFactura = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, factura.getIdFactura());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Factura anulada satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

}
