package com.example.veterinaria.model;

public class Venta {

    private int id;
    private int idCliente;
    private Integer idAtencion;
    private String tipo;
    private int idItem;
    private String nombreItem;
    private int cantidad;
    private double precioUnitario;
    private String fecha;
    private int idVendedor;

    public Venta(int id, int idCliente, Integer idAtencion, String tipo, int idItem, String nombreItem,
                 int cantidad, double precioUnitario, String fecha, int idVendedor) {
        this.id = id;
        this.idCliente = idCliente;
        this.idAtencion = idAtencion;
        this.tipo = tipo;
        this.idItem = idItem;
        this.nombreItem = nombreItem;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
        this.idVendedor = idVendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(Integer idAtencion) {
        this.idAtencion = idAtencion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }


    public double getTotal() {
        return cantidad * precioUnitario;
    }
}
