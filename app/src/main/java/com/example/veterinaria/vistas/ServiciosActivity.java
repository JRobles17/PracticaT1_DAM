package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.ServicioAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Servicio;

public class ServiciosActivity extends AppCompatActivity {

    EditText txtNombre7, txtPrecio7, txtDescripcion7;
    Button btnGuardar7;
    RecyclerView rvServicios7;
    ServicioAdapter adapter;

    Servicio servicioEnEdicion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        txtNombre7 = findViewById(R.id.txtNombre7);
        txtPrecio7 = findViewById(R.id.txtPrecio7);
        txtDescripcion7 = findViewById(R.id.txtDescripcion7);
        btnGuardar7 = findViewById(R.id.btnGuardar7);
        rvServicios7 = findViewById(R.id.rvServicios7);

        rvServicios7.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServicioAdapter(Data.getInstance().getServicios(), new ServicioAdapter.Listener() {
            @Override
            public void onEditar(Servicio s) {
                servicioEnEdicion = s;
                txtNombre7.setText(s.getNombre());
                txtPrecio7.setText(String.valueOf(s.getPrecio()));
                txtDescripcion7.setText(s.getDescripcion());
                btnGuardar7.setText("Actualizar");
            }

            @Override
            public void onEliminar(Servicio s) {
                Data.getInstance().eliminarServicio(s.getId());
                adapter.notifyDataSetChanged();
                Toast.makeText(ServiciosActivity.this, "Servicio eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        rvServicios7.setAdapter(adapter);

        btnGuardar7.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String nombre = txtNombre7.getText().toString().trim();
        String precioStr = txtPrecio7.getText().toString().trim();
        String descripcion = txtDescripcion7.getText().toString().trim();

        if (nombre.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Nombre y precio son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        double precio = Double.parseDouble(precioStr);

        if (servicioEnEdicion != null) {
            servicioEnEdicion.setNombre(nombre);
            servicioEnEdicion.setPrecio(precio);
            servicioEnEdicion.setDescripcion(descripcion);
            servicioEnEdicion = null;
            btnGuardar7.setText("Guardar");
            Toast.makeText(this, "Servicio actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Data.getInstance().agregarServicio(new Servicio(0, nombre, precio, descripcion));
            Toast.makeText(this, "Servicio guardado", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
        txtNombre7.setText("");
        txtPrecio7.setText("");
        txtDescripcion7.setText("");
    }
}
