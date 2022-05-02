package LogicaNegocio;

import AccesoDatos.ADDetalleFactura;
import Entidades.DetalleFactura;

import java.sql.SQLException;
import java.util.List;

public class LNDetalleFactura {

    private String _mensaje;

    public String getMensaje() {

        return _mensaje;
    }

    public int Insertar(DetalleFactura detalleFactura) throws Exception {
        int id = -1;
        ADDetalleFactura adDetalleFactura;
        try {
            adDetalleFactura = new ADDetalleFactura();
            id = adDetalleFactura.Insertar(detalleFactura);
            _mensaje = adDetalleFactura.get_mensaje();
        } catch (Exception e) {
            throw e;
        }

        return id;
    }

    public List<DetalleFactura> ListarRegistros(String condicion) throws SQLException, ClassNotFoundException {
        List<DetalleFactura> resultado;
        ADDetalleFactura adDetalleFactura;
        try {
            adDetalleFactura = new ADDetalleFactura();
            resultado = adDetalleFactura.ListarDetallesFactura(condicion);
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public DetalleFactura ObtenerRegistro(String condicion) throws Exception {
        DetalleFactura resultado;
        ADDetalleFactura adDetalleFactura;
        try {
            adDetalleFactura = new ADDetalleFactura();
            resultado = adDetalleFactura.ObtenerDetalle(condicion);
            if (resultado != null) {
                _mensaje = "Detalle recuperado exitosamente";
            } else {
                _mensaje = "El Detalle no existe";
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public int Eliminar(DetalleFactura detalleFactura) throws Exception {
        int resultado = -1;
        ADDetalleFactura adDetalleFactura;

        try {
            adDetalleFactura = new ADDetalleFactura();
            resultado = adDetalleFactura.Eliminar(detalleFactura);
            _mensaje = adDetalleFactura.get_mensaje();
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

}
