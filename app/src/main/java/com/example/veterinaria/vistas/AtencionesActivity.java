package com.example.veterinaria.vistas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.example.veterinaria.adapter.AtencionAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Atencion;
import com.example.veterinaria.model.Mascota;
import com.example.veterinaria.model.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AtencionesActivity extends AppCompatActivity {

    Spinner spMascota8, spVeterinario8, spinner3;
    EditText editTextText, editTextText2, editTextText3, editTextTextMultiLine;
    Button btnGuardar8;
    RecyclerView rvAtenciones8;
    AtencionAdapter adapter;

    List<Mascota> mascotas;
    List<Usuario> veterinarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atenciones);

        spMascota8 = findViewById(R.id.spMascota8);
        spVeterinario8 = findViewById(R.id.spVeterinario8);
        editTextText = findViewById(R.id.editTextText);            // Fecha
        editTextText2 = findViewById(R.id.editTextText2);          // Hora
        editTextText3 = findViewById(R.id.editTextText3);          // Motivo
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine); // Observaciones
        spinner3 = findViewById(R.id.spinner3);                    // Estado
        btnGuardar8 = findViewById(R.id.btnGuardar8);
        rvAtenciones8 = findViewById(R.id.rvAtenciones8);

        // Spinner de mascotas
        mascotas = Data.getInstance().getMascotas();
        ArrayAdapter<Mascota> mascotaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mascotas);
        mascotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMascota8.setAdapter(mascotaAdapter);

        // Spinner de veterinarios (usuarios con rol Veterinario)
        veterinarios = new ArrayList<>();
        for (Usuario u : Data.getInstance().getUsuarios())
            if (u.getRol().equals(Usuario.Rol_Veterinario)) veterinarios.add(u);
        ArrayAdapter<Usuario> vetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, veterinarios);
        vetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVeterinario8.setAdapter(vetAdapter);

        // Spinner de estado
        ArrayAdapter<CharSequence> estadoAdapter = ArrayAdapter.createFromResource(this,
                R.array.estados_atencion, android.R.layout.simple_spinner_item);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(estadoAdapter);

        // Fecha: al tocar, abre DatePickerDialog
        editTextText.setFocusable(false);
        editTextText.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) ->
                    editTextText.setText(String.format("%02d/%02d/%04d", day, month + 1, year)),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Hora: al tocar, abre TimePickerDialog
        editTextText2.setFocusable(false);
        editTextText2.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) ->
                    editTextText2.setText(String.format("%02d:%02d", hour, minute)),
                    c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        rvAtenciones8.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AtencionAdapter(Data.getInstance().getAtenciones());
        rvAtenciones8.setAdapter(adapter);

        btnGuardar8.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        if (mascotas.isEmpty() || veterinarios.isEmpty()) {
            Toast.makeText(this, "Necesitas al menos una mascota y un veterinario registrados", Toast.LENGTH_SHORT).show();
            return;
        }
        String fecha = editTextText.getText().toString().trim();
        String hora = editTextText2.getText().toString().trim();
        String motivo = editTextText3.getText().toString().trim();
        String observaciones = editTextTextMultiLine.getText().toString().trim();
        String estado = spinner3.getSelectedItem() != null ? spinner3.getSelectedItem().toString() : Atencion.PENDIENTE;

        if (fecha.isEmpty() || hora.isEmpty() || motivo.isEmpty()) {
            Toast.makeText(this, "Fecha, hora y motivo son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Mascota mascotaSel = (Mascota) spMascota8.getSelectedItem();
        Usuario vetSel = (Usuario) spVeterinario8.getSelectedItem();

        Atencion nueva = new Atencion(0, mascotaSel.getId(), vetSel.getId(), fecha, hora, motivo);
        nueva.setObservaciones(observaciones);
        nueva.setEstado(estado);
        Data.getInstance().agregarAtencion(nueva);

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Atención guardada", Toast.LENGTH_SHORT).show();

        editTextText.setText("");
        editTextText2.setText("");
        editTextText3.setText("");
        editTextTextMultiLine.setText("");
    }
}
