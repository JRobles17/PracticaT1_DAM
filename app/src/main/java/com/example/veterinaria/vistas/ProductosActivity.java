package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.ProductoAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Producto;

public class ProductosActivity extends AppCompatActivity {

    EditText txtNombre6, txtPrecio6, txtStock6;
    Button btnGuardar6;
    RecyclerView rvProductos6;
    ProductoAdapter adapter;

    Producto productoEnEdicion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        txtNombre6 = findViewById(R.id.txtNombre6);
        txtPrecio6 = findViewById(R.id.txtPrecio6);
        txtStock6 = findViewById(R.id.txtStock6);
        btnGuardar6 = findViewById(R.id.btnGuardar6);
        rvProductos6 = findViewById(R.id.rvProductos6);

        rvProductos6.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(Data.getInstance().getProductos(), new ProductoAdapter.Listener() {
            @Override
            public void onEditar(Producto p) {
                productoEnEdicion = p;
                txtNombre6.setText(p.getNombre());
                txtPrecio6.setText(String.valueOf(p.getPrecio()));
                txtStock6.setText(String.valueOf(p.getStock()));
                btnGuardar6.setText("Actualizar");
            }

            @Override
            public void onEliminar(Producto p) {
                Data.getInstance().eliminarProducto(p.getId());
                adapter.notifyDataSetChanged();
                Toast.makeText(ProductosActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        rvProductos6.setAdapter(adapter);

        btnGuardar6.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String nombre = txtNombre6.getText().toString().trim();
        String precioStr = txtPrecio6.getText().toString().trim();
        String stockStr = txtStock6.getText().toString().trim();

        if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        double precio = Double.parseDouble(precioStr);
        int stock = Integer.parseInt(stockStr);

        if (productoEnEdicion != null) {
            productoEnEdicion.setNombre(nombre);
            productoEnEdicion.setPrecio(precio);
            productoEnEdicion.setStock(stock);
            productoEnEdicion = null;
            btnGuardar6.setText("Guardar");
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Data.getInstance().agregarProducto(new Producto(0, nombre, precio, stock));
            Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
        txtNombre6.setText("");
        txtPrecio6.setText("");
        txtStock6.setText("");
    }
}
