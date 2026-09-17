package com.example.veterinaria;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.veterinaria.vistas.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MainActivity solo redirige al Login, no dibuja nada.
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}