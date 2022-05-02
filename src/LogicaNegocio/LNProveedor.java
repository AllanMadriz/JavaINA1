package LogicaNegocio;

import AccesoDatos.ADProveedor;
import Entidades.Proveedor;

import java.sql.SQLException;
import java.util.List;

public class LNProveedor {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(Proveedor proveedor) throws Exception {
        int id = -1;
        ADProveedor adProveedor;
        try {
            adProveedor = new ADProveedor();
            id = adProveedor.Insertar(proveedor);
            _mensaje = adProveedor.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Proveedor> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Proveedor> resultado;
        ADProveedor adProveedor;
        try {
            adProveedor = new ADProveedor();
            resultado = adProveedor.ListarProveedores(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public Proveedor ObtenerRegistro(String condicion) throws Exception {
        Proveedor resultado;
        ADProveedor adProveedor;
        try {
            adProveedor = new ADProveedor();
            resultado = adProveedor.ObtenerProveedor(condicion);
            if (resultado != null) {
                _mensaje = "Proveedor recuperado exitosamente";
            } else {
                _mensaje = "El proveedor no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Modificar(Proveedor proveedor) throws Exception {
        int resultado = -1;
        ADProveedor adProveedor;

        try {
            adProveedor = new ADProveedor();
            resultado = adProveedor.Modificar(proveedor);
            _mensaje = adProveedor.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(Proveedor proveedor) throws Exception {
        int resultado = -1;
        ADProveedor adProveedor;

        try {
            adProveedor = new ADProveedor();
            resultado = adProveedor.Eliminar(proveedor);
            _mensaje = adProveedor.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }


}
