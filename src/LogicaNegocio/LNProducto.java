package LogicaNegocio;

import AccesoDatos.ADProducto;
import Entidades.Producto;

import java.sql.SQLException;
import java.util.List;

public class LNProducto {
    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(Producto producto) throws Exception {
        int id = -1;
        ADProducto adProducto;
        try {
            adProducto = new ADProducto();
            id = adProducto.Insertar(producto);
            _mensaje = adProducto.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Producto> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Producto> resultado;
        ADProducto adProducto;
        try {
            adProducto = new ADProducto();
            resultado = adProducto.ListarProductos(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public Producto ObtenerRegistro(String condicion) throws Exception {
        Producto resultado;
        ADProducto adProducto;
        try {
            adProducto = new ADProducto();
            resultado = adProducto.ObtenerProducto(condicion);
            if (resultado != null) {
                _mensaje = "Producto recuperado exitosamente";
            } else {
                _mensaje = "El Producto no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Modificar(Producto producto) throws Exception {
        int resultado = -1;
        ADProducto adProducto;

        try {
            adProducto = new ADProducto();
            resultado = adProducto.Modificar(producto);
            _mensaje = adProducto.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(Producto producto) throws Exception {
        int resultado = -1;
        ADProducto adProducto;

        try {
            adProducto = new ADProducto();
            resultado = adProducto.Eliminar(producto);
            _mensaje = adProducto.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
}
