package com.example.friendsradar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.logging.Logger;

import modelo.Posicion;
import modelo.Usuario;

public class pantallaRegistrarse extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    EditText nombre, apellido, email, contrasenia, carrera;
    Integer semestre;
    String signoZodiacal;
    private Spinner semestreSpinner;
    private Spinner signoSpinner;
    private static final int CAMARA_PERMISO_ID = 101;
    String permLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERM_ID = 112;
    private FusedLocationProviderClient fusedLocationClient;
    Uri imageUri;
    Posicion posicion;
    private static final String TAG = pantallaRegistrarse.class.getName();
    private final int GALLERY_PERMISSION_ID = 102;
    private final Logger logger = Logger.getLogger(TAG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrarse);
        System.out.println("LLEGUEEE A ON CREATE");
        semestreSpinner = (Spinner) findViewById(R.id.spSemestre);
        signoSpinner = (Spinner) findViewById(R.id.spSigno);
        //Initialize Firebase database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        String camaraPerm = Manifest.permission.CAMERA;
        ProgressDialog progressDialog;
        ArrayList<Integer> semestres = new ArrayList<>();
        semestres.add(1);
        semestres.add(2);
        semestres.add(3);
        semestres.add(4);
        semestres.add(5);
        semestres.add(6);
        semestres.add(7);
        semestres.add(8);
        semestres.add(9);
        semestres.add(10);

        ArrayList<String> signos = new ArrayList<>();
        signos.add("Acuario");
        signos.add("Aries");
        signos.add("Cáncer");
        signos.add("Capricornio");
        signos.add("Escorpio");
        signos.add("Géminis");
        signos.add("Leo");
        signos.add("Libra");
        signos.add("Piscis");
        signos.add("Sagitario");
        signos.add("Tauro");
        signos.add("Virgo");
        ArrayAdapter adp = new ArrayAdapter(pantallaRegistrarse.this, android.R.layout.simple_spinner_dropdown_item, semestres);


        semestreSpinner.setAdapter(adp);
        semestreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semestre = (Integer) semestreSpinner.getAdapter().getItem(i);
                Toast.makeText(pantallaRegistrarse.this, "Seleccionaste el semestre: " + semestre, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adp2 = new ArrayAdapter(pantallaRegistrarse.this, android.R.layout.simple_spinner_dropdown_item,signos);
        signoSpinner.setAdapter(adp2);
        signoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                signoZodiacal = (String) signoSpinner.getAdapter().getItem(i);
                Toast.makeText(pantallaRegistrarse.this, "Seleccionaste este signo: " + signoZodiacal, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        nombre = findViewById(R.id.nomCrear);
        apellido = findViewById(R.id.crearApellido);
        email = findViewById(R.id.crearEmail);
        contrasenia = findViewById(R.id.crearContrase);
        carrera = findViewById(R.id.crearCarrera);
        System.out.println("NOMBRE "+nombre);
        System.out.println("MAIL "+email);
        System.out.println("PASS "+contrasenia);
        pedirPermisos(this, camaraPerm);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(this,
                    permLocation,
                    "Location is required",
                    LOCATION_PERM_ID);
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void pedirPermisos(Activity context, String permiso){
        if (ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(context, "Permiso para utilizar la camara", Toast.LENGTH_SHORT).show();
            }
            // request the permission.
            ActivityCompat.requestPermissions(context, new String[]{permiso}, pantallaRegistrarse.CAMARA_PERMISO_ID);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERM_ID){
            Intent intent=new Intent();
            intent.setClass(this, this.getClass());
            this.startActivity(intent);
            this.finish();
        }
    }

    private void requestPermissions(Activity context, String permission, String justification, int id) {
        if(ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(context, justification, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(context, new String []{permission}, id);
        }
    }

    public void crearUsuario(View view) {
        System.out.println("LLEGUE A CREAR USUARIOOO");
        if (String.valueOf(email.getText()) == "" ){
            Toast.makeText(pantallaRegistrarse.this,
                    "Falta ingresar su correo electronico", Toast.LENGTH_SHORT).show();
            return;
        }
        if (String.valueOf(contrasenia.getText())== ""){
            Toast.makeText(pantallaRegistrarse.this,
                    "Falta ingresar su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("nombre "+ String.valueOf(nombre.getText()));
        System.out.println("nombre "+ String.valueOf(email.getText()));
        System.out.println("nombre "+ String.valueOf(signoZodiacal));
        Usuario nuevo = new Usuario(String.valueOf(nombre.getText()), String.valueOf(apellido.getText()), String.valueOf(email.getText()), String.valueOf(contrasenia.getText()), String.valueOf(imageUri), Integer.valueOf(semestre), String.valueOf(signoZodiacal),  posicion, false);
        String key = myRef.push().getKey();
        myRef=database.getReference("usuarios/" + key);
        myRef.setValue(nuevo);
        uploadFile(imageUri, key);
        System.out.println("email " + nuevo.getEmail());
        System.out.println("ps " +nuevo.getPassword());
        mAuth.createUserWithEmailAndPassword(nuevo.getEmail(), nuevo.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                System.out.println("ENTREEE qa coomplete");
                if (task.isSuccessful()){
                    Toast.makeText(pantallaRegistrarse.this,
                            "Usuario: "+nuevo.getNombre()+"\nRegistrado correctamente!", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(pantallaRegistrarse.this,
                            "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
        Intent intent = new Intent(this, pantallaRegistrarse.class);
        startActivity(intent);
    }

    private void uploadFile(Uri imagen, String key) {
        StorageReference imageRef = mStorageRef.child("gs://friendradar-917fa.appspot.com"+ key +"/");
        imageRef.putFile(imagen)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Log.i("FBApp", "Succesfully upload image");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(), pantallaInicio.class);
            intent.putExtra("user", currentUser.getEmail());
            startActivity(intent);
        } else {
            email.setText("");
            contrasenia.setText("");
        }
    }

    @SuppressLint("MissingPermission")
    public void obetenerUbi(View view) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Posicion posi = new Posicion(location.getLatitude(), location.getLongitude());
                            // Logic to handle location object
                            posicion = posi;
                            Toast.makeText(pantallaRegistrarse.this,
                                    "Latitud: "+ posicion.getLatitud() + "\nLongitud: "+ posicion.getLongitud(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void abrirImagen(View view) {
        Intent pickGalleryImage = new Intent(Intent.ACTION_PICK);
        pickGalleryImage.setType("image/*");
        startActivityForResult(pickGalleryImage, GALLERY_PERMISSION_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case GALLERY_PERMISSION_ID:
                    assert data != null;
                    imageUri = data.getData();
                    //imagen.setImageURI(imageUri);
                    logger.info("Image loaded successfully.");
                    Toast.makeText(pantallaRegistrarse.this,
                            "Imagen: "+ imageUri, Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }
}