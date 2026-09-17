package com.example.veterinaria.vistas;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.example.veterinaria.R;
import com.example.veterinaria.data.Data;
import com.example.veterinaria.model.Usuario;
import com.example.veterinaria.sesion.Sesion;
import com.example.veterinaria.vistas.MenuActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.veterinaria.R;

public class LoginActivity extends AppCompatActivity {

    EditText usuario, contraseña;
    Button btnIngresar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.txtUsuario1);
        contraseña = findViewById(R.id.txtContraseña1);
        btnIngresar = findViewById(R.id.btnLogin1);

        btnIngresar.setOnClickListener(v -> intentarLogin());

    }
    private void intentarLogin(){
        String user = usuario.getText().toString().trim();
        String pass = contraseña.getText().toString().trim();

        Usuario u = Data.getInstance().validarLogin(user, pass);
        if(u != null){
            Sesion.getInstancia().iniciarSesionn(u);
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

    }
}