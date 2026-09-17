package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.VentaAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Cliente;
import com.example.veterinaria.model.Producto;
import com.example.veterinaria.model.Servicio;
import com.example.veterinaria.model.Venta;
import com.example.veterinaria.sesion.Sesion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VentasActivity extends AppCompatActivity {

    Spinner spCliente9, spTipo9, spItem9;
    EditText txtCantidad9, txtSubtotal9;
    Button btnRegistrarVentas9;
    RecyclerView rvVentas9;
    VentaAdapter adapter;

    List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        spCliente9 = findViewById(R.id.spCliente9);
        spTipo9 = findViewById(R.id.spTipo9);
        spItem9 = findViewById(R.id.spItem9);
        txtCantidad9 = findViewById(R.id.txtCantidad9);
        txtSubtotal9 = findViewById(R.id.txtSubtotal9);
        btnRegistrarVentas9 = findViewById(R.id.btnRegistrarVentas9);
        rvVentas9 = findViewById(R.id.rvVentas9);

        clientes = Data.getInstance().getClientes();
        ArrayAdapter<Cliente> clienteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientes);
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCliente9.setAdapter(clienteAdapter);

        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_venta, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo9.setAdapter(tipoAdapter);

        cargarItems();
        spTipo9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                cargarItems();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        txtCantidad9.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { calcularSubtotal(); }
            @Override public void afterTextChanged(Editable s) { }
        });
        spItem9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) { calcularSubtotal(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        rvVentas9.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VentaAdapter(Data.getInstance().getVentas());
        rvVentas9.setAdapter(adapter);

        btnRegistrarVentas9.setOnClickListener(v -> registrarVenta());
    }

    /** Llena spItem9 con Productos o Servicios según lo elegido en spTipo9. */
    private void cargarItems() {
        boolean esProducto = spTipo9.getSelectedItem() == null || spTipo9.getSelectedItem().toString().equals("Producto");
        List<?> items = esProducto ? Data.getInstance().getProductos() : Data.getInstance().getServicios();
        ArrayAdapter<Object> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, (List<Object>) items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spItem9.setAdapter(itemAdapter);
        calcularSubtotal();
    }

    private double precioDelItemSeleccionado() {
        Object sel = spItem9.getSelectedItem();
        if (sel instanceof Producto) return ((Producto) sel).getPrecio();
        if (sel instanceof Servicio) return ((Servicio) sel).getPrecio();
        return 0;
    }

    private void calcularSubtotal() {
        int cantidad = 0;
        try { cantidad = Integer.parseInt(txtCantidad9.getText().toString().trim()); } catch (Exception ignored) { }
        double subtotal = cantidad * precioDelItemSeleccionado();
        txtSubtotal9.setText(String.format(Locale.getDefault(), "%.2f", subtotal));
    }

    private void registrarVenta() {
        if (clientes.isEmpty()) {
            Toast.makeText(this, "Primero registra al menos un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        Object itemSel = spItem9.getSelectedItem();
        if (itemSel == null) {
            Toast.makeText(this, "No hay productos/servicios registrados para vender", Toast.LENGTH_SHORT).show();
            return;
        }
        String cantidadStr = txtCantidad9.getText().toString().trim();
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Ingresa la cantidad", Toast.LENGTH_SHORT).show();
            return;
        }
        int cantidad = Integer.parseInt(cantidadStr);
        Cliente clienteSel = (Cliente) spCliente9.getSelectedItem();
        boolean esProducto = spTipo9.getSelectedItem().toString().equals("Producto");

        int idItem; String nombreItem; double precio;
        if (esProducto) {
            Producto p = (Producto) itemSel;
            idItem = p.getId(); nombreItem = p.getNombre(); precio = p.getPrecio();
        } else {
            Servicio s = (Servicio) itemSel;
            idItem = s.getId(); nombreItem = s.getNombre(); precio = s.getPrecio();
        }

        int idVendedor = Sesion.getInstancia().haySesionActiva() ? Sesion.getInstancia().getUsuarioActual().getId() : 0;
        String fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new java.util.Date());

        Venta venta = new Venta(0, clienteSel.getId(), null, esProducto ? "Producto" : "Servicio",
                idItem, nombreItem, cantidad, precio, fecha, idVendedor);
        Data.getInstance().registrarVenta(venta);

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Venta registrada", Toast.LENGTH_SHORT).show();

        txtCantidad9.setText("");
        txtSubtotal9.setText("");
    }
}
