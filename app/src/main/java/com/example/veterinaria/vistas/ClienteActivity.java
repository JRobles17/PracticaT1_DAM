package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.ClienteAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Cliente;

public class ClienteActivity extends AppCompatActivity {

    EditText txtNombre3, txtTelefono3, txtDireccion3, txtDNI3;
    Button btnGuardar3, btnLimpiar3;
    RecyclerView rvClientes3;
    ClienteAdapter adapter;

    Cliente clienteEnEdicion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        txtNombre3 = findViewById(R.id.txtNombre3);
        txtTelefono3 = findViewById(R.id.txtTelefono3);
        txtDireccion3 = findViewById(R.id.txtDireccion3);
        txtDNI3 = findViewById(R.id.txtDNI3);
        btnGuardar3 = findViewById(R.id.btnGuardar3);
        btnLimpiar3 = findViewById(R.id.btnLimpiar3);
        rvClientes3 = findViewById(R.id.rvClientes3);

        rvClientes3.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClienteAdapter(Data.getInstance().getClientes(), new ClienteAdapter.Listener() {
            @Override
            public void onEditar(Cliente c) {
                clienteEnEdicion = c;
                txtNombre3.setText(c.getNombre());
                txtTelefono3.setText(c.getTelefono());
                txtDireccion3.setText(c.getDireccion());
                txtDNI3.setText(c.getDNI());
                btnGuardar3.setText("Actualizar");
            }

            @Override
            public void onEliminar(Cliente c) {
                Data.getInstance().eliminarCliente(c.getId());
                adapter.notifyDataSetChanged();
                Toast.makeText(ClienteActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        rvClientes3.setAdapter(adapter);

        btnGuardar3.setOnClickListener(v -> guardar());
        btnLimpiar3.setOnClickListener(v -> limpiar());
    }

    private void guardar() {
        String nombre = txtNombre3.getText().toString().trim();
        String telefono = txtTelefono3.getText().toString().trim();
        String direccion = txtDireccion3.getText().toString().trim();
        String dni = txtDNI3.getText().toString().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Nombre y teléfono son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clienteEnEdicion != null) {
            clienteEnEdicion.setNombre(nombre);
            clienteEnEdicion.setTelefono(telefono);
            clienteEnEdicion.setDireccion(direccion);
            clienteEnEdicion.setDNI(dni);
            Toast.makeText(this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Data.getInstance().agregarCliente(new Cliente(0, nombre, telefono, direccion, dni));
            Toast.makeText(this, "Cliente guardado", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
        limpiar();
    }

    private void limpiar() {
        clienteEnEdicion = null;
        txtNombre3.setText("");
        txtTelefono3.setText("");
        txtDireccion3.setText("");
        txtDNI3.setText("");
        btnGuardar3.setText("Guardar");
    }
}
