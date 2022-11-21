package com.example.friendsradar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText emailEdit, passEdit;
    private static final String TAG = MainActivity.class.getName();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final int CAMARA_PERMISO_ID = 101;
    private final int GALLERY_PERMISSION_ID = 102;
    String camaraPerm = Manifest.permission.CAMERA;
    private final int LOCATION_PERMISSION_ID = 103;
    String locationPerm = Manifest.permission.ACCESS_FINE_LOCATION;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(this, camaraPerm, "Permiso para utilizar la camara", CAMARA_PERMISO_ID);
        progressDialog = new ProgressDialog(this);

        emailEdit = findViewById(R.id.loginEmail);
        passEdit = findViewById(R.id.loginPass);
        mAuth = FirebaseAuth.getInstance();
    }

    private void requestPermission(Activity context, String permission, String justification, int id){
        // Verificar si no hay permisos
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_DENIED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                Toast.makeText(context, justification, Toast.LENGTH_SHORT).show();
            }
            // request the permission.
            ActivityCompat.requestPermissions(context, new String[]{permission}, id);
        }
    }

    public void registrarse(View view) {
        Intent intent = new Intent(this, pantallaRegistrarse.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = emailEdit.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Required.");
            valid = false;
        } else {
            emailEdit.setError(null);
        }
        String password = passEdit.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passEdit.setError("Required.");
            valid = false;
        } else {
            passEdit.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(), pantallaInicio.class);
            intent.putExtra("user", currentUser.getEmail());
            startActivity(intent);
        } else {
            emailEdit.setText("");
            passEdit.setText("");
        }
    }

    private void signInUser(String email, String password) {
        if (validateForm()) {
            progressDialog.setMessage("Iniciando sesión...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressDialog.dismiss();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    public static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void login(View view){
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();

        if(!isEmailValid(email)){
            Toast.makeText(MainActivity.this, "Email is not a valid format",
                    Toast.LENGTH_SHORT).show();
            return;
        }
//        loadSubscriptionPeople(email, pass);
        signInUser(email, pass);
    }
}