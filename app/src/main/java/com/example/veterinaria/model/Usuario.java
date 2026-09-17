package com.example.veterinaria.model;

public class Usuario {
    public static final String Rol_Veterinario = "Veterinario";
    public static final String Rol_Asistente = "Asistente";

    private int id;
    private String nombre;
    private String Usuario;
    private String Contraseña;
    private String rol;
    private boolean activo = true;

    public Usuario(int id, String nombre, String usuario, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        Usuario = usuario;
        Contraseña = contraseña;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getRol() {return rol;}
    public void setRol(String rol) {this.rol = rol;}
    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo ;}

    @Override
    public String toString() {return nombre + " ( " + rol + ")";}
}
