package com.example.veterinaria.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaria.R;
import com.example.veterinaria.sesion.Sesion;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Sesion.getInstancia().haySesionActiva()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_menu);

        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText("Bienvenido de nuevo, " + Sesion.getInstancia().getUsuarioActual().getNombre());

        findViewById(R.id.btnClientes2).setOnClickListener(v -> abrir(ClienteActivity.class));
        findViewById(R.id.btnMascotas2).setOnClickListener(v -> abrir(MascotasActivity.class));
        findViewById(R.id.btnUsuarios2).setOnClickListener(v -> abrir(UsuariosActivity.class));
        findViewById(R.id.btnProductos2).setOnClickListener(v -> abrir(ProductosActivity.class));
        findViewById(R.id.btnServicios2).setOnClickListener(v -> abrir(ServiciosActivity.class));
        findViewById(R.id.btnAtencion2).setOnClickListener(v -> abrir(AtencionesActivity.class));
        findViewById(R.id.btnVentas2).setOnClickListener(v -> abrir(VentasActivity.class));
        findViewById(R.id.btnReportes2).setOnClickListener(v -> abrir(ReportesActivity.class));
        findViewById(R.id.btnCerrarSesion2).setOnClickListener(v -> cerrarSesion());
    }

    private void abrir(Class<?> destino) {
        startActivity(new Intent(this, destino));
    }

    private void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
