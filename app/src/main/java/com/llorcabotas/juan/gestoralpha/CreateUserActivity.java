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

public class CreateUserActivity extends AppCompatActivity {

    EditText nombre, contrasenna, contrasenna2;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nombre = (EditText) findViewById(R.id.editTextCreateNombre);
        contrasenna = (EditText) findViewById(R.id.editTextCreateContraseña);
        contrasenna2 = (EditText) findViewById(R.id.editTextCreateContraseña2);
        Button bv = (Button) findViewById(R.id.buttonVolver);
        Button bc2 = (Button) findViewById(R.id.buttonCrear2);

        //Re gresas a la ventana main
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateUserActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Ejecuta le creacíon de usuario
        bc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contra = contrasenna.getText().toString();
                String contra2 = contrasenna2.getText().toString();
                //Antes de ejecutar el registro comprovamos que la contraseña y su compro vación coinciden y no son nulas
                if (!contra.equals(contra2)||contra.equals("")){
                    Toast.makeText(CreateUserActivity.this, "La contraseña no fue comprobada.",
                            Toast.LENGTH_SHORT).show();

                }else {
                    register();
                }
            }
        });
    }


    private void register() {
        mAuth = FirebaseAuth.getInstance();
        String email = nombre.getText().toString();
        String contra = contrasenna.getText().toString();
        String contra2 = contrasenna2.getText().toString();



        //Ejecuta la creación del nuevo usuario
        mAuth.createUserWithEmailAndPassword(email, contra)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                //Crea el nuevo usuario en el firebase y lo comunica con un toast
                                Toast.makeText(CreateUserActivity.this, "La creación funciono.",
                                        Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(CreateUserActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                //Comunica que algo salío mal en la creación del usuario
                                Toast.makeText(CreateUserActivity.this, "Fallo al crear.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                });
    }

}