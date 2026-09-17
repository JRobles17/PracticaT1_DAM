package com.example.veterinaria.data;

import com.example.veterinaria.model.Atencion;
import com.example.veterinaria.model.Cliente;
import com.example.veterinaria.model.Mascota;
import com.example.veterinaria.model.Producto;
import com.example.veterinaria.model.Servicio;
import com.example.veterinaria.model.Usuario;
import com.example.veterinaria.model.Venta;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instancia;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Mascota> mascotas = new ArrayList<>();
    private ArrayList<Producto> productos = new ArrayList<>();
    private ArrayList<Servicio> servicios = new ArrayList<>();
    private ArrayList<Atencion> atenciones = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();

    private int nextUsuarioId = 1, nextClienteId = 1, nextMascotaId = 1,
            nextProductoId = 1, nextServicioId = 1, nextAtencionId = 1, nextVentaId = 1;
    private Data(){
        precargarDatos();
    }
   public static synchronized Data getInstance() {
   if (instancia ==null) instancia = new Data();
   return instancia;
   }

   private void precargarDatos(){
       usuarios.add(new Usuario(nextUsuarioId++, "Dr.Ricardito Chapatin", "Ricardo", "1234", Usuario.Rol_Veterinario));
       usuarios.add(new Usuario(nextUsuarioId++, "Dr.Imanol Pancracio", "Imanol", "4321", Usuario.Rol_Asistente));
   }
    public List<Usuario> getUsuarios() { return usuarios; }
    public void agregarUsuario(Usuario u) {
        Usuario nuevo = new Usuario(nextUsuarioId++, u.getNombre(), u.getUsuario(), u.getContraseña(), u.getRol());
        nuevo.setActivo(u.isActivo());
        usuarios.add(nuevo);
    }
    public void eliminarUsuario(int id) { usuarios.removeIf(u -> u.getId() == id); }

    public Usuario validarLogin(String usuario, String password) {

        for (Usuario u : usuarios)

            if (u.getUsuario().equals(usuario) && u.getContraseña().equals(password) && u.isActivo())

                return u;

        return null;

    }

    // CLIENTES
    public List<Cliente> getClientes() { return clientes; }
    public void agregarCliente(Cliente c) { clientes.add(new Cliente(nextClienteId++, c.getNombre(), c.getTelefono(), c.getDireccion(), c.getDNI())); }
    public void eliminarCliente(int id) { clientes.removeIf(c -> c.getId() == id); }
    public Cliente buscarCliente(int id) { for (Cliente c: clientes) if (c.getId()==id) return c; return null; }

    //  MASCOTAS
    public List<Mascota> getMascotas() { return mascotas; }
    public void agregarMascota(Mascota m) { mascotas.add(new Mascota(nextMascotaId++,
            m.getNombre(), m.getEspecie(), m.getRaza(), m.getEdad(), m.getIdCliente())); }
    public void eliminarMascota(int id) { mascotas.removeIf(m -> m.getId() == id); }
    public Mascota buscarMascota(int id) { for (Mascota m: mascotas) if (m.getId()==id) return m; return null; }
    public List<Mascota> mascotasDeCliente(int idCliente) {

        List<Mascota> r = new ArrayList<>();

        for (Mascota m : mascotas) if (m.getIdCliente() == idCliente) r.add(m);

        return r;

    }

    //  PRODUCTOS
    public List<Producto> getProductos() { return productos; }
    public void agregarProducto(Producto p) { productos.add(new Producto(nextProductoId++, p.getNombre(),
            p.getPrecio(), p.getStock())); }
    public void eliminarProducto(int id) { productos.removeIf(p -> p.getId() == id); }

    //  SERVICIOS
    public List<Servicio> getServicios() { return servicios; }
    public void agregarServicio(Servicio s) { servicios.add(new Servicio(nextServicioId++,
            s.getNombre(), s.getPrecio(), s.getDescripcion())); }
    public void eliminarServicio(int id) { servicios.removeIf(s -> s.getId() == id); }

    // ATENCIONES

    public List<Atencion> getAtenciones() { return atenciones; }
    public void agregarAtencion(Atencion a) {
        Atencion nueva = new Atencion(nextAtencionId++, a.getIdMascota(), a.getIdVeterinario(), a.getFecha(), a.getHora(), a.getMotivo());
        nueva.setObservaciones(a.getObservaciones());
        nueva.setEstado(a.getEstado() != null ? a.getEstado() : Atencion.PENDIENTE);
        atenciones.add(nueva);
    }
    public void cambiarEstadoAtencion(int id, String nuevoEstado) {

        for (Atencion a : atenciones) if (a.getId() == id) a.setEstado(nuevoEstado);

    }
    public List<Atencion> atencionesEntreFechas(String desde, String hasta) {
        List<Atencion> r = new ArrayList<>();
        for (Atencion a : atenciones)
            if (a.getFecha().compareTo(desde) >= 0 && a.getFecha().compareTo(hasta) <= 0) r.add(a);
        return r;
    }

    //  VENTAS
    public List<Venta> getVentas() { return ventas; }
    public void registrarVenta(Venta v) {
        ventas.add(new Venta(nextVentaId++, v.getIdCliente(), v.getIdAtencion(), v.getTipo(),
                v.getIdItem(), v.getNombreItem(), v.getCantidad(), v.getPrecioUnitario(),
                v.getFecha(), v.getIdVendedor()));

    }
    public List<Venta> ventasEntreFechas(String desde, String hasta) {
        List<Venta> r = new ArrayList<>();
        for (Venta v : ventas)
            if (v.getFecha().compareTo(desde) >= 0 && v.getFecha().compareTo(hasta) <= 0) r.add(v);
        return r;
    }
    public double totalRecaudadoEntreFechas(String desde, String hasta) {
        double total = 0;
        for (Venta v : ventasEntreFechas(desde, hasta)) total += v.getTotal();
        return total;
    }


}
