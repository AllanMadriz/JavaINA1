package AccesoDatos;

import Config.Config;
import Entidades.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADEmpleado {

    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADEmpleado() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Empleado empleado) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Empleados (IDUsuario, Cedula, Nombre, PrimerApellido, SegundoApellido, DireccionResidencia, FechaContratacion,"
                + "FechaNacimiento, Telefono, Genero) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, empleado.getIdUsuario());
            ps.setInt(2, empleado.getCedula());
            ps.setString(3, empleado.getNombre());
            ps.setString(4, empleado.getPrimerApellido());
            ps.setString(5, empleado.getSegundoApellido());
            ps.setString(6, empleado.getDireccionResidencia());
            ps.setDate(7, (Date) empleado.getFechaContratacion());
            ps.setDate(8, (Date) empleado.getFechaNacimiento());
            ps.setInt(9, empleado.getTelefono());
            ps.setString(10, empleado.getGenero());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Empleado registrado satisfactoriamente";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<Empleado> ListarEmpleados(String condicion) throws SQLException {
        ResultSet rs;
        List<Empleado> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idempleado, idusuario, cedula, nombre, primerapellido, segundoapellido, direccionresidencia, fechacontratacion, fechanacimiento, telefono, "
                    + "genero, activo from Empleados";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Empleado(rs.getInt("idempleado"), rs.getDate("fechacontratacion"), rs.getInt("cedula"), rs.getString("nombre"), rs.getString("primerapellido"),
                        rs.getString("segundoapellido"), rs.getString("direccionresidencia"), rs.getDate("fechanacimiento"),
                        rs.getInt("telefono"), rs.getString("genero"), rs.getInt("idusuario"), rs.getBoolean("activo")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    public Empleado ObtenerEmpleado(String condicion) throws SQLException {
        Empleado empleado = new Empleado();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select idempleado, idusuario, cedula, nombre, primerapellido, segundoapellido, direccionresidencia, fechacontratacion, fechanacimiento, telefono, "
                    + "genero, activo from Empleados";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                empleado.setIdEmpleado(rs.getInt(1));
                empleado.setIdUsuario(rs.getInt(2));
                empleado.setCedula(rs.getInt(3));
                empleado.setNombre(rs.getString(4));
                empleado.setPrimerApellido(rs.getString(5));
                empleado.setSegundoApellido(rs.getString(6));
                empleado.setDireccionResidencia(rs.getString(7));
                empleado.setFechaContratacion(rs.getDate(8));
                empleado.setFechaNacimiento(rs.getDate(9));
                empleado.setTelefono(rs.getInt(10));
                empleado.setGenero(rs.getString(11));
                empleado.setActivo(rs.getBoolean(12));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return empleado;
    }

    public int Modificar(Empleado empleado) throws Exception {
        int resultado;
        String sentencia = "update Empleados set Cedula=?, Nombre=?, PrimerApellido=?, SegundoApellido=?, DireccionResidencia=?, FechaContratacion=?"
                + ",FechaNacimiento=?, Telefono=? where IDUsuario=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, empleado.getCedula());
            ps.setString(2, empleado.getPrimerApellido());
            ps.setString(3, empleado.getSegundoApellido());
            ps.setString(4, empleado.getDireccionResidencia());
            ps.setDate(5, (Date) empleado.getFechaContratacion());
            ps.setDate(6, (Date) empleado.getFechaNacimiento());
            ps.setInt(7, empleado.getTelefono());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Empleado actualizado satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Empleado empleado) throws Exception {
        int resultado;
        String sentencia = "update Empleados set Activo=0 where IDEmpleado=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, empleado.getIdEmpleado());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Empleado inactivado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
