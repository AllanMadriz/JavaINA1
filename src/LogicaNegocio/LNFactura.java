package LogicaNegocio;

import AccesoDatos.ADFactura;
import Entidades.Factura;
import java.sql.SQLException;
import java.util.List;

public class LNFactura {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(Factura factura) throws Exception {
        int id = -1;
        ADFactura adFactura;
        try {
            adFactura = new ADFactura();
            id = adFactura.Insertar(factura);
            _mensaje = adFactura.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<Factura> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<Factura> resultado;
        ADFactura adFactura;
        try {
            adFactura = new ADFactura();
            resultado = adFactura.ListarFacturas(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public Factura ObtenerRegistro(String condicion) throws Exception {
        Factura resultado;
        ADFactura adFactura;
        try {
            adFactura = new ADFactura();
            resultado = adFactura.ObtenerFactura(condicion);
            if (resultado != null) {
                _mensaje = "Factura recuperada exitosamente";
            } else {
                _mensaje = "La factura no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Modificar(Factura factura) throws Exception {
        int resultado = -1;
        ADFactura adFactura;

        try {
            adFactura = new ADFactura();
            resultado = adFactura.Modificar(factura);

            _mensaje = adFactura.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int ModificarTotal(Factura factura) throws Exception {
        int resultado = -1;
        ADFactura adFactura;

        try {
            adFactura = new ADFactura();
            resultado = adFactura.ModificarTotal(factura);

        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(Factura factura) throws Exception {
        int resultado = -1;
        ADFactura adFactura;

        try {
            adFactura = new ADFactura();
            resultado = adFactura.Eliminar(factura);
            _mensaje = adFactura.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

}
