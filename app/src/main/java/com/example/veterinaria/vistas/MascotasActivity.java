package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.MascotaAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Cliente;
import com.example.veterinaria.model.Mascota;

import java.util.List;

public class MascotasActivity extends AppCompatActivity {

    EditText txtNombreMascota4, txtEspecie4, txtRaza4, txtEdad4;
    Spinner spCliente4;
    Button btnGuardar4;
    RecyclerView rvMascotas4;
    MascotaAdapter adapter;

    List<Cliente> clientes;
    Mascota mascotaEnEdicion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);

        txtNombreMascota4 = findViewById(R.id.txtNombreMascota4);
        txtEspecie4 = findViewById(R.id.txtEspecie4);
        txtRaza4 = findViewById(R.id.txtRaza4);
        txtEdad4 = findViewById(R.id.txtEdad4);
        spCliente4 = findViewById(R.id.spCliente4);
        btnGuardar4 = findViewById(R.id.btnGuardar4);
        rvMascotas4 = findViewById(R.id.rvMascotas4);

        clientes = Data.getInstance().getClientes();
        ArrayAdapter<Cliente> spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clientes);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCliente4.setAdapter(spAdapter);

        rvMascotas4.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MascotaAdapter(Data.getInstance().getMascotas(), new MascotaAdapter.Listener() {
            @Override
            public void onEditar(Mascota m) {
                mascotaEnEdicion = m;
                txtNombreMascota4.setText(m.getNombre());
                txtEspecie4.setText(m.getEspecie());
                txtRaza4.setText(m.getRaza());
                txtEdad4.setText(String.valueOf(m.getEdad()));
                int pos = indexOfCliente(m.getIdCliente());
                if (pos >= 0) spCliente4.setSelection(pos);
                btnGuardar4.setText("Actualizar");
            }

            @Override
            public void onEliminar(Mascota m) {
                Data.getInstance().eliminarMascota(m.getId());
                adapter.notifyDataSetChanged();
                Toast.makeText(MascotasActivity.this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
            }
        });
        rvMascotas4.setAdapter(adapter);

        btnGuardar4.setOnClickListener(v -> guardar());
    }

    private int indexOfCliente(int idCliente) {
        for (int i = 0; i < clientes.size(); i++) if (clientes.get(i).getId() == idCliente) return i;
        return -1;
    }

    private void guardar() {
        if (clientes.isEmpty()) {
            Toast.makeText(this, "Primero registra al menos un cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        String nombre = txtNombreMascota4.getText().toString().trim();
        String especie = txtEspecie4.getText().toString().trim();
        String raza = txtRaza4.getText().toString().trim();
        String edadStr = txtEdad4.getText().toString().trim();

        if (nombre.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, "Nombre y edad son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        int edad = Integer.parseInt(edadStr);
        Cliente clienteSel = (Cliente) spCliente4.getSelectedItem();

        if (mascotaEnEdicion != null) {
            mascotaEnEdicion.setNombre(nombre);
            mascotaEnEdicion.setEspecie(especie);
            mascotaEnEdicion.setRaza(raza);
            mascotaEnEdicion.setEdad(edad);
            mascotaEnEdicion.setIdCliente(clienteSel.getId());
            mascotaEnEdicion = null;
            btnGuardar4.setText("Guardar");
            Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Data.getInstance().agregarMascota(new Mascota(0, nombre, especie, raza, edad, clienteSel.getId()));
            Toast.makeText(this, "Mascota guardada", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
        txtNombreMascota4.setText("");
        txtEspecie4.setText("");
        txtRaza4.setText("");
        txtEdad4.setText("");
    }
}
