package com.llorcabotas.juan.gestoralpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modelo.Usuario;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    Usuario usuario;
    EditText nombre, contrasenna;
    String email, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_GestorAlpha);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = null;

        nombre = (EditText) findViewById(R.id.editTextMainNombre);
        contrasenna = (EditText) findViewById(R.id.editPassword);
        Button bl = (Button) findViewById(R.id.buttonMainLog);
        Button bc = (Button) findViewById(R.id.buttonCrear);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message1");

        myRef.setValue("Hello, World!1");

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                /*
                Código para facilitar las pruebas al no tener que meter el usuario
                email="benjullb@gmail.com";
                contra="Benju2001";
                */
                //Establece las variables de email y contraseña como los text views
                email = nombre.getText().toString();
                contra = contrasenna.getText().toString();
                //Compruba que en efecto los text views no estan vacios antes de revisarlos
                if (!email.equals("") && !contra.equals("")) {
                    //Buscamos un usuario en firebaso a potir del email y la contraseña
                mAuth.signInWithEmailAndPassword(email, contra).
                        addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //En caso de exito llamamos al usuario para abrir la aplicación
                                        user = mAuth.getCurrentUser();
                                        Toast.makeText(MainActivity.this, "correcto ", Toast.LENGTH_SHORT).show();
                                        //Preparamos todo para abrir el menu, la siguiente ventana y el usuario que pasaremos
                                        Intent i = new Intent(MainActivity.this, MenuActivity.class);

                                        i.putExtra("usuario", new Usuario(user.getEmail(), contra, user.getUid()));
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("message3");

                                        myRef.setValue("Hello, World!3");

                                        startActivity(i);
                                        finish();
                                    } else {
                                        // Comunica si ocurrio algún error
                                        nombre.setText("");
                                        contrasenna.setText("");
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });
                } else {
                    //Comunica si hay campos vaciós
                    Toast.makeText(MainActivity.this, "Email y contraseña necesarios.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        bc.setOnClickListener(new View.OnClickListener() {
            //Te lleva a la ventana de creación de usuario
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
/*
mAuth = FirebaseAuth.getInstance();
String email = nombre.getText().toString();
mAuth.sendPasswordResetEmail(email);
*/