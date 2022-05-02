package LogicaNegocio;

import AccesoDatos.ADEmpleado;
import Entidades.Empleado;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LNEmpleado {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(Empleado empleado) throws Exception {
        int id = -1;
        ADEmpleado adEmpleado;
        try {
            adEmpleado = new ADEmpleado();
            id = adEmpleado.Insertar(empleado);
            _mensaje = adEmpleado.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Empleado> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Empleado> resultado = new ArrayList<>();
        ADEmpleado adEmpleado;
        try {
            adEmpleado = new ADEmpleado();
            resultado = adEmpleado.ListarEmpleados(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public Empleado ObtenerRegistro(String condicion) throws Exception {
        Empleado resultado;
        ADEmpleado adEmpleado;
        try {
            adEmpleado = new ADEmpleado();
            resultado = adEmpleado.ObtenerEmpleado(condicion);
            if (resultado != null) {
                _mensaje = "Empleado recuperado exitosamente";
            } else {
                _mensaje = "El Empleado no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Modificar(Empleado empleado) throws Exception {
        int resultado = -1;
        ADEmpleado adEmpleado;

        try {
            adEmpleado = new ADEmpleado();
            resultado = adEmpleado.Modificar(empleado);
            _mensaje = adEmpleado.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(Empleado empleado) throws Exception {
        int resultado = -1;
        ADEmpleado adEmpleado;

        try {
            adEmpleado = new ADEmpleado();
            resultado = adEmpleado.Eliminar(empleado);
            _mensaje = adEmpleado.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
    
    
}
