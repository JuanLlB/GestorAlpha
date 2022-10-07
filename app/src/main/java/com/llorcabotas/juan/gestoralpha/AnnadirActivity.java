package com.llorcabotas.juan.gestoralpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import modelo.BBDD;
import modelo.ConceptoDeGasto;
import modelo.Gasto;
import modelo.Usuario;

public class AnnadirActivity extends AppCompatActivity {

    Spinner spinnerCDG;
    Button bv, ba;
    EditText cantidad, descripcion;
    ConceptoDeGasto c=new ConceptoDeGasto();

    public BBDD bd;
    Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annadir);
        Bundle bundle = getIntent().getExtras();
        bv=(Button) findViewById(R.id.buttonVolver3);
        ba=(Button) findViewById(R.id.buttonAnnadir2);
        cantidad=(EditText) findViewById(R.id.editTextCantidad);
        descripcion=(EditText) findViewById(R.id.editTextDescripcion);
        try {
            u = (Usuario) bundle.getSerializable("usuario");
        }
        catch (Exception e){
            u=null;
        }

        //Llama a la base de datos del usuario
        bd = new BBDD(this);



        List<String> cdg=new ConceptoDeGasto().getLista();

        // Creación de adapter para spinner

        ArrayAdapter<String> myAdapterCDG = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cdg);

        // Define estilo
        myAdapterCDG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCDG=(Spinner) findViewById(R.id.opcionesConceptosDeGasto);
        // Adjunta datos del adapter al spinner
        spinnerCDG.setAdapter(myAdapterCDG);

        //Te regresa a la pantalla del menu
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AnnadirActivity.this,MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Lee los campos introducidos
                    String cantidades = cantidad.getText().toString();
                    String descripciones = descripcion.getText().toString();

                    double cantidad = Double.parseDouble(cantidades);
                    String concepto = spinnerCDG.getSelectedItem().toString();
                    //Coje la fecha de creación del gasto y lo convierte a String
                    Date d= Calendar.getInstance().getTime();
                    String  año=String.valueOf(d.getYear());
                    String  mes=String.valueOf(d.getMonth());
                    String   dia= String.valueOf(d.getDay());
                    //Creamos un nuevo gasto con los datos obtenidos
                    Gasto g = new Gasto(u.getId(), cantidad, concepto, año,mes,dia, descripciones);
                    //Insertamos el gasto en la base de datos
                    bd.insertarGasto(g);

                }catch (Exception e){
                    Toast.makeText(AnnadirActivity.this,"Error insertar",Toast.LENGTH_SHORT).show();
                }

                Intent i=new Intent(AnnadirActivity.this,MenuActivity.class);
                i.putExtra("usuario",u);
                startActivity(i);
                finish();

            }
        });

        List<String> o=rellenarOpciones();
        // Creación de adapter para spinner
        ArrayAdapter<String> myAdapterO= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, o);

        // Define estilo
        myAdapterO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCDG=(Spinner) findViewById(R.id.opcionesConceptosDeGasto);
        // Adjunta datos del adapter al spinner
        spinnerCDG.setAdapter(myAdapterO);
    }

    //Rellena todas las o
    private List<String> rellenarOpciones(){
        List<String> meses=new ArrayList<String>();
        meses=c.getLista();


        return meses;
    }
}