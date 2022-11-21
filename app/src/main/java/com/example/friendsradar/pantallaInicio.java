package com.example.friendsradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class pantallaInicio extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*Toast.makeText(pantallaInicio.this,"CALENDAR",Toast.LENGTH_SHORT).show();
        Intent intentCalendario = new Intent(pantallaInicio.this, pantallaCalendario.class);
        startActivity(intentCalendario);

        case R.id.restaurantes:
        Toast.makeText(pantallaInicio.this,"RESTAURANTES",Toast.LENGTH_SHORT).show();
        Intent intentRestaurantes = new Intent(pantallaInicio.this, pantallaRestaurantes.class);
        startActivity(intentRestaurantes);
        return true;*/
        switch (item.getItemId()){
            case R.id.logout:
                Toast.makeText(pantallaInicio.this,"LOG OUT",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(pantallaInicio.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.perfil:
                Toast.makeText(pantallaInicio.this,"PERFIL",Toast.LENGTH_SHORT).show();
                Intent intentPerfil = new Intent(pantallaInicio.this, pantallaPerfil.class);
                startActivity(intentPerfil);
                return true;
            case R.id.mensajes:
                Toast.makeText(pantallaInicio.this,"MENSAJES",Toast.LENGTH_SHORT).show();
                Intent intentMensajes = new Intent(pantallaInicio.this, pantallaMensajes.class);
                startActivity(intentMensajes);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}