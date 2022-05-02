package LogicaNegocio;

import AccesoDatos.ADUsuario;
import Entidades.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LNUsuario {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(Usuario usuario) throws Exception {
        int id = -1;
        ADUsuario adUsuario;
        try {
            adUsuario = new ADUsuario();
            id = adUsuario.Insertar(usuario);
            _mensaje = adUsuario.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Usuario> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Usuario> resultado = new ArrayList<>();
        ADUsuario adUsuario;
        try {
            adUsuario = new ADUsuario();
            resultado = adUsuario.ListarUsuarios(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public Usuario ObtenerRegistro(String condicion) throws Exception {
        Usuario resultado;
        ADUsuario adUsuario;
        try {
            adUsuario = new ADUsuario();
            resultado = adUsuario.ObtenerUsuario(condicion);
            if (resultado != null) {
                _mensaje = "Usuario recuperado exitosamente";
            } else {
                _mensaje = "El Usuario no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    //Metodo para validar el Login del usuario
    public Usuario LoginUsuario(String condicion) throws Exception {
        Usuario resultado;
        ADUsuario adUsuario;
        try {
            adUsuario = new ADUsuario();
            resultado = adUsuario.LoginUsuario(condicion);
            if (resultado.getCorreo() != null) {
                _mensaje = "Logeado con exito!!";
            } else {
                _mensaje = "Datos Incorrectos, vuelva a intentarlo";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Modificar(Usuario usuario) throws Exception {
        int resultado = -1;
        ADUsuario adUsuario;

        try {
            adUsuario = new ADUsuario();
            resultado = adUsuario.Modificar(usuario);
            _mensaje = adUsuario.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(Usuario usuario) throws Exception {
        int resultado = -1;
        ADUsuario adUsuario;

        try {
            adUsuario = new ADUsuario();
            resultado = adUsuario.Eliminar(usuario);
            _mensaje = adUsuario.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
}
