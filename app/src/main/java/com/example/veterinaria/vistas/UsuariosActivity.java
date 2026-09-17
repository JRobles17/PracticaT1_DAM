package com.example.veterinaria.vistas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaria.R;
import com.example.veterinaria.adapter.UsuarioAdapter;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Usuario;

public class UsuariosActivity extends AppCompatActivity {

    EditText txtNombre5, txtUsuario5, txtContraseña5;
    Spinner spRol5;
    Switch swActivo;
    Button btnGuardar5;
    RecyclerView rvUsuarios5;
    UsuarioAdapter adapter;

    Usuario usuarioEnEdicion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        txtNombre5 = findViewById(R.id.txtNombre5);
        txtUsuario5 = findViewById(R.id.txtUsuario5);
        txtContraseña5 = findViewById(R.id.txtContraseña5);
        spRol5 = findViewById(R.id.spRol5);
        swActivo = findViewById(R.id.swActivo);
        btnGuardar5 = findViewById(R.id.btnGuardar5);
        rvUsuarios5 = findViewById(R.id.rvUsuarios5);

        ArrayAdapter<CharSequence> rolAdapter = ArrayAdapter.createFromResource(this,
                R.array.roles_usuario, android.R.layout.simple_spinner_item);
        rolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRol5.setAdapter(rolAdapter);

        rvUsuarios5.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioAdapter(Data.getInstance().getUsuarios(), new UsuarioAdapter.Listener() {
            @Override
            public void onEditar(Usuario u) {
                usuarioEnEdicion = u;
                txtNombre5.setText(u.getNombre());
                txtUsuario5.setText(u.getUsuario());
                txtContraseña5.setText(u.getContraseña());
                spRol5.setSelection(u.getRol().equals(Usuario.Rol_Veterinario) ? 0 : 1);
                swActivo.setChecked(u.isActivo());
                btnGuardar5.setText("Actualizar");
            }

            @Override
            public void onEliminar(Usuario u) {
                Data.getInstance().eliminarUsuario(u.getId());
                adapter.notifyDataSetChanged();
                Toast.makeText(UsuariosActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        rvUsuarios5.setAdapter(adapter);

        btnGuardar5.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String nombre = txtNombre5.getText().toString().trim();
        String usuario = txtUsuario5.getText().toString().trim();
        String pass = txtContraseña5.getText().toString().trim();
        String rol = spRol5.getSelectedItem().toString();
        boolean activo = swActivo.isChecked();

        if (nombre.isEmpty() || usuario.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Completa nombre, usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioEnEdicion != null) {
            usuarioEnEdicion.setNombre(nombre);
            usuarioEnEdicion.setUsuario(usuario);
            usuarioEnEdicion.setContraseña(pass);
            usuarioEnEdicion.setRol(rol);
            usuarioEnEdicion.setActivo(activo);
            usuarioEnEdicion = null;
            btnGuardar5.setText("Guardar");
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Usuario nuevo = new Usuario(0, nombre, usuario, pass, rol);
            nuevo.setActivo(activo);
            Data.getInstance().agregarUsuario(nuevo);
            Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
        txtNombre5.setText("");
        txtUsuario5.setText("");
        txtContraseña5.setText("");
        swActivo.setChecked(true);
    }
}
