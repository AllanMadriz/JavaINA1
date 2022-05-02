package AccesoDatos;

import Config.Config;
import Entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADUsuario {

    private Connection _cnn;
    private String _mensaje;

    public String get_mensaje() {

        return _mensaje;
    }

    public ADUsuario() throws SQLException, ClassNotFoundException {
        try {
            String url = Config.getConnectionString();
            _cnn = DriverManager.getConnection(url);
            _mensaje = "";
        } catch (Exception e) {
            throw e;
        }
    }

    public int Insertar(Usuario usuario) throws Exception {
        int idUser = 1;
        String sentencia = "insert into Usuarios (IDUsuarioRol, Correo, Password) values (?, ?, ?)";

        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, usuario.getIdUsuarioRol());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                idUser = rs.getInt(1);
                _mensaje = "Usuario registrado satisfactoriamente";

            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return idUser;
    }

    public List<Usuario> ListarUsuarios(String condicion) throws SQLException {
        ResultSet rs;
        List<Usuario> lista = new ArrayList<>();

        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select IDUsuario, IDUsuarioRol, Correo, Password, Activo from Usuarios";
            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("IDUsuario"), rs.getInt("IDUsuarioRol"), rs.getString("Correo"),
                        rs.getString("Password"), rs.getBoolean("Activo")));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return lista;
    }

    public Usuario ObtenerUsuario(String condicion) throws SQLException {
        Usuario usuario = new Usuario();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select IDUsuario, IDUsuarioRol, Correo, Password, Activo from Usuarios";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt(1));
                usuario.setIdUsuarioRol(rs.getInt(2));
                usuario.setCorreo(rs.getString(3));
                usuario.setPassword(rs.getString(4));
                usuario.setActivo(rs.getBoolean(5));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return usuario;
    }

    //Metodo que usamos para podernos loguear dentro del sistema
    public Usuario LoginUsuario(String condicion) throws SQLException {
        Usuario usuario = new Usuario();
        ResultSet rs;
        try {
            Statement stm = _cnn.createStatement();
            String sentencia = "select Correo, Password, IDUsuarioRol from Usuarios";

            if (!condicion.equals("")) {
                sentencia = String.format("%s where %s", sentencia, condicion);
            }

            rs = stm.executeQuery(sentencia);
            if (rs.next()) {

                usuario.setCorreo(rs.getString(1));
                usuario.setPassword(rs.getString(2));
                usuario.setIdUsuarioRol(rs.getInt(3));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }

        return usuario;
    }

    public int Modificar(Usuario usuario) throws Exception {
        int resultado;
        String sentencia = "update Usuarios set Correo=?, Password=?, Activo=? where IDUsuario=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getPassword());
            ps.setBoolean(3, usuario.isActivo());
            ps.setInt(4, usuario.getIdUsuario());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Usuario actualizado satisfactoriamente!";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }

    public int Eliminar(Usuario usuario) throws Exception {
        int resultado;
        String sentencia = "delete Usuarios where IDUsuario=?";
        try {
            PreparedStatement ps = _cnn.prepareStatement(sentencia);
            ps.setInt(1, usuario.getIdUsuario());
            resultado = ps.executeUpdate();

            if (resultado > 0) {
                _mensaje = "Usuario eliminado satisfactoriamente";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            _cnn = null;
        }
        return resultado;
    }
}
