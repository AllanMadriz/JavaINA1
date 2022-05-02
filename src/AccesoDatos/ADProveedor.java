package AccesoDatos;

import Config.Config;
import Entidades.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADProveedor {
    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADProveedor() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Proveedor proveedor) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Proveedores (NombreProveedor, DescripcionProveedor) values (?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getDescripcionProveedor());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Proveedor ingresado satisfactoriamente";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<Proveedor> ListarProveedores(String condicion) throws SQLException {
        ResultSet rs;
        List<Proveedor> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idproveedor, nombreproveedor, descripcionproveedor, activo from Proveedores";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Proveedor(rs.getInt("idproveedor"), rs.getString("nombreproveedor"), rs.getString("descripcionproveedor"),
                        rs.getBoolean("activo")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    public Proveedor ObtenerProveedor(String condicion) throws SQLException {
        Proveedor proveedor = new Proveedor();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idproveedor, nombreproveedor, descripcionproveedor, activo from Proveedores";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                proveedor.setIdProveedor(rs.getInt(1));
                proveedor.setNombreProveedor(rs.getString(2));
                proveedor.setDescripcionProveedor(rs.getString(3));
                proveedor.setActivo(rs.getBoolean(4));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return proveedor;
    }

    public int Modificar(Proveedor proveedor) throws Exception {
        int resultado;
        String sentencia = "update Proveedores set NombreProveedor=?, DescripcionProveedor=? where IDProveedor=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getDescripcionProveedor());
            ps.setInt(3, proveedor.getIdProveedor());

            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Proveedor actualizado satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Proveedor proveedor) throws Exception {
        int resultado;
        String sentencia = "update Proveedores set Activo = 0 where IDProveedor=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, proveedor.getIdProveedor());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Proveedor borrado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
