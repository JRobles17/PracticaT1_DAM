package com.example.veterinaria.sesion;
import com.example.veterinaria.model.Usuario;

public class Sesion {
    private static Sesion instancia;
    private Usuario usuarioActual;

    private Sesion(){
    }
    public static synchronized Sesion getInstancia(){
        if (instancia == null) instancia = new Sesion();
        return instancia;
    }

    public void iniciarSesionn(Usuario usuario){ this.usuarioActual = usuario; }
    public void cerrarSesion(){ this.usuarioActual = null; }
    public boolean haySesionActiva(){ return usuarioActual != null; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public boolean esVeterinario() { return usuarioActual != null && usuarioActual.getRol().equals(Usuario.Rol_Veterinario); }
}
