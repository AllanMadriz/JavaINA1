package AccesoDatos;

import Config.Config;
import Entidades.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADCliente {
    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADCliente() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Cliente cliente) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Clientes (IDUsuario, Cedula, Nombre, PrimerApellido, SegundoApellido, DireccionResidencia, FechaNacimiento, Telefono, Genero) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getIdUsuario());
            ps.setInt(2, cliente.getCedula());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getPrimerApellido());
            ps.setString(5, cliente.getSegundoApellido());
            ps.setString(6, cliente.getDireccionResidencia());
            ps.setDate(7, (Date) cliente.getFechaNacimiento());
            ps.setInt(8, cliente.getTelefono());
            ps.setString(9, cliente.getGenero());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Cliente registrado satisfactoriamente";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<Cliente> ListarClientes(String condicion) throws SQLException {
        ResultSet rs;
        List<Cliente> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idcliente, idusuario, cedula, nombre, primerapellido, segundoapellido, direccionresidencia, fechanacimiento, telefono," +
                    " genero, activo from Clientes";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("cedula"),
                        rs.getString("nombre"), rs.getString("primerapellido"), rs.getString("segundoapellido"),
                        rs.getString("direccionresidencia"), rs.getDate("fechanacimiento"), rs.getInt("telefono"),
                        rs.getString("genero"),
                        rs.getInt("idusuario"),rs.getBoolean("activo"), rs.getInt("idcliente")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    //Con este metodo obtenemos unicamente un registro, no mas de uno
    public Cliente ObtenerCliente(String condicion) throws SQLException {
        Cliente cliente = new Cliente();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idcliente, idusuario, cedula, nombre, primerapellido, segundoapellido, direccionresidencia, fechanacimiento," +
                    " telefono, genero, activo from Clientes";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt(1));
                cliente.setIdUsuario(rs.getInt(2));
                cliente.setCedula(rs.getInt(3));
                cliente.setNombre(rs.getString(4));
                cliente.setPrimerApellido(rs.getString(5));
                cliente.setSegundoApellido(rs.getString(6));
                cliente.setDireccionResidencia(rs.getString(7));
                cliente.setFechaNacimiento(rs.getDate(8));
                cliente.setTelefono(rs.getInt(9));
                cliente.setGenero(rs.getString(10));
                cliente.setActivo(rs.getBoolean(11));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return cliente;
    }

    public int Modificar(Cliente cliente) throws Exception {
        int resultado;
        String sentencia = "update Clientes set Cedula=?, Nombre=?, PrimerApellido=?, SegundoApellido=?, DireccionResidencia=?, FechaNacimiento=?, " +
                "Telefono=?, Genero=? where IDCliente = ?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setString(5, cliente.getDireccionResidencia());
            ps.setDate(6, (Date) cliente.getFechaNacimiento());
            ps.setInt(7, cliente.getTelefono());
            ps.setString(8, cliente.getGenero());
            ps.setInt(9, cliente.getIdCliente());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Cliente actualizado satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Cliente cliente) throws Exception {
        int resultado;
        String sentencia = "update Clientes set activo = 0 where idcliente=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, cliente.getIdCliente());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Cliente borrado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
