package com.example.friendsradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class pantallaPerfil extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout:
                Toast.makeText(pantallaPerfil.this,"LOG OUT",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(pantallaPerfil.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.mensajes:
                Toast.makeText(pantallaPerfil.this,"MENSAJES",Toast.LENGTH_SHORT).show();
                Intent intentMensajes = new Intent(pantallaPerfil.this, pantallaMensajes.class);
                startActivity(intentMensajes);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}