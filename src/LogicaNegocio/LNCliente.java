package LogicaNegocio;

import AccesoDatos.ADCliente;
import Entidades.Cliente;

import java.sql.SQLException;
import java.util.List;

//No se comentaran todas las clases de la capa Logica de Negocio ya que son muy similares y simples de entender a la vista
public class LNCliente {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    //Metodo par insertar, en todos los metodos se llama a los de la Capa AD
    public int Insertar(Cliente cliente) throws Exception {
        int id = -1;
        ADCliente adCliente;
        try {
            adCliente = new ADCliente();
            id = adCliente.Insertar(cliente);
            _mensaje = adCliente.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    //Metodo para listar todos los registros
    public List<Cliente> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Cliente> resultado;
        ADCliente adCliente;
        try {
            adCliente = new ADCliente();
            resultado = adCliente.ListarClientes(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    //Metodo para obtener un solo registro unicamente
    public Cliente ObtenerRegistro(String condicion) throws Exception {
        Cliente resultado;
        ADCliente adCliente;
        try {
            adCliente = new ADCliente();
            resultado = adCliente.ObtenerCliente(condicion);
            if (resultado != null) {
                _mensaje = "Cliente recuperado exitosamente";
            } else {
                _mensaje = "El Cliente no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    //Metodo para modificar un registro
    public int Modificar(Cliente cliente) throws Exception {
        int resultado = -1;
        ADCliente adCliente;

        try {
            adCliente = new ADCliente();
            resultado = adCliente.Modificar(cliente);
            _mensaje = adCliente.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    //Metodo para eliminar un registro
    public int Eliminar(Cliente cliente) throws Exception {
        int resultado = -1;
        ADCliente adCliente;

        try {
            adCliente = new ADCliente();
            resultado = adCliente.Eliminar(cliente);
            _mensaje = adCliente.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }
}
