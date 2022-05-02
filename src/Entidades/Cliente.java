package Entidades;

import java.util.Date;

//Heredamos de la clase persona, sus atributos y metodos
public class Cliente extends Persona {

    private int idCliente;

    public Cliente() {

    }

    //Se usa el super, para poder "concatenar" los demas atributos heredados
    public Cliente(int cedula, String nombre, String primerApellido, String segundoApellido, String direccionResidencia, Date fechaNacimiento, int telefono,
            String genero, int idUsuario, boolean activo, int idCliente) {
        super(cedula, nombre, primerApellido, segundoApellido, direccionResidencia, fechaNacimiento, telefono, genero, idUsuario, activo);
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
