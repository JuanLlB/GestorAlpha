package com.llorcabotas.juan.gestoralpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controladores.ObtenerAñosConGasto;
import modelo.BBDD;
import modelo.ConceptoDeGasto;
import modelo.Gasto;
import modelo.Usuario;

public class AnnadirActivity extends AppCompatActivity {

    Spinner spinnerCDG, spinnerD, spinnerM, spinnerA;
    String day, month, year;
    Button bv, ba, bactivar;
    EditText cantidad, descripcion;
    ConceptoDeGasto c=new ConceptoDeGasto();

    public BBDD bd;
    Usuario u;
    boolean fechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annadir);
        Bundle bundle = getIntent().getExtras();
        bv=(Button) findViewById(R.id.buttonVolver3);
        ba=(Button) findViewById(R.id.buttonAnnadir2);
        bactivar=(Button) findViewById(R.id.buttonFechaM);
        cantidad=(EditText) findViewById(R.id.editTextCantidad);
        descripcion=(EditText) findViewById(R.id.editTextDescripcion);
        fechaHoy=true;
        try {
            u = (Usuario) bundle.getSerializable("usuario");
        }
        catch (Exception e){
            u=null;
        }

        //Llama a la base de datos del usuario
        bd = new BBDD(this);

        month=fijarMes(Calendar.getInstance().get(Calendar.MONTH));
        year=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        day="na";


        List<String> cdg=new ConceptoDeGasto().getLista();
        List<String> m2=rellenarMeses();
        List<String> a=rellenarAnnos();
        List<String> d=rellenarDias();

        // Creación de adapter para spinner

        ArrayAdapter<String> myAdapterCDG = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cdg);

        // Define estilo
        myAdapterCDG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creación de adapter para spinner
        ArrayAdapter<String> myAdapterD = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, d);

        // Define estilo
        myAdapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creación de adapter para spinner
        ArrayAdapter<String> myAdapterM = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m2);

        // Define estilo
        myAdapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creación de adapter para spinner
        ArrayAdapter<String> myAdapterA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, a);

        // Define estilo
        myAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCDG=(Spinner) findViewById(R.id.opcionesConceptosDeGasto);
        spinnerD=(Spinner) findViewById(R.id.opcionesDiaC);
        spinnerM=(Spinner) findViewById(R.id.opcionesMesC);
        spinnerA=(Spinner) findViewById(R.id.opcionesAñoC);
        // Adjunta datos del adapter al spinner
        spinnerCDG.setAdapter(myAdapterCDG);
        spinnerD.setAdapter(myAdapterD);
        spinnerM.setAdapter(myAdapterM);
        spinnerA.setAdapter(myAdapterA);

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
                    String  año;
                    String  mes;
                    String  dia;
                    //capturamos la fecha de hoy
                    if (fechaHoy){
                        año=String.valueOf(d.getYear());
                        mes=String.valueOf(d.getMonth());
                        dia= String.valueOf(d.getDay());
                    }
                    else{
                        año=spinnerA.getSelectedItem().toString();
                        mes=spinnerM.getSelectedItem().toString();
                        dia=spinnerD.getSelectedItem().toString();
                    }

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

        bactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaHoy=false;
              /*  if (spinnerD.isEnabled()){
                    spinnerD.setEnabled(false);
                }else{
                    spinnerD.setEnabled(true);
                }
                if (spinnerM.isEnabled()){
                    spinnerM.setEnabled(false);
                }else{
                    spinnerM.setEnabled(true);
                }
                if (spinnerA.isEnabled()){
                    spinnerA.setEnabled(false);
                }else{
                    spinnerA.setEnabled(true);
                }
                */
                spinnerD.setEnabled(true);
                spinnerM.setEnabled(true);
                spinnerA.setEnabled(true);


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

    //Rellena todas las opciones
    private List<String> rellenarOpciones(){
        List<String> meses=new ArrayList<String>();
        meses=c.getLista();


        return meses;
    }

    //Mete los días del mes en el spinner
    private List<String> rellenarDias() {
        //ArrayList<String> dias = new ObtenerAñosConGasto().ejecutar();
        ArrayList<String> dias = new ArrayList<String>();
        String item=month;
        if (item.equals("Enero") || item.equals("Marzo") || item.equals("Mayo") || item.equals("Julio") || item.equals("Agosto") || item.equals("Octubre") || item.equals("Diciembre")){
            for (int i=1; i<=31; i++){
                dias.add(i+"");
            }
        }else if (item.equals("Abril") || item.equals("Junio") || item.equals("Septiembre") || item.equals("Noviembre")){
            for (int i=1; i<=30; i++){
                dias.add(i+"");
            }
        }else if (item.equals("Febrero")){
            item=year;
            if (Integer.parseInt(item)%4==0){
                for (int i=1; i<=29; i++){
                    dias.add(i+"");
                }
            }else{
                for (int i=1; i<=28; i++){
                    dias.add(i+"");
                }
            }
        }

        return dias;
    }

    //Te da una lista con todos los meses para insertar en el spinner
    private List<String> rellenarMeses(){
        ArrayList<String> meses=new ArrayList<String>();
        meses.add(getString(R.string.mes1));
        meses.add(getString(R.string.mes2));
        meses.add(getString(R.string.mes3));
        meses.add(getString(R.string.mes4));
        meses.add(getString(R.string.mes5));
        meses.add(getString(R.string.mes6));
        meses.add(getString(R.string.mes7));
        meses.add(getString(R.string.mes8));
        meses.add(getString(R.string.mes9));
        meses.add(getString(R.string.mes10));
        meses.add(getString(R.string.mes11));
        meses.add(getString(R.string.mes12));


        return meses;
    }

    //Relaciona los meses con un número
    private String fijarMes(int m){
        switch (m+1){
            case 1: return getString(R.string.mes1);
            case 2: return getString(R.string.mes2);
            case 3: return getString(R.string.mes3);
            case 4: return getString(R.string.mes4);
            case 5: return getString(R.string.mes5);
            case 6: return getString(R.string.mes6);
            case 7: return getString(R.string.mes7);
            case 8: return getString(R.string.mes8);
            case 9: return getString(R.string.mes9);
            case 10: return getString(R.string.mes10);
            case 11: return getString(R.string.mes11);
            case 12: return getString(R.string.mes12);
        }
        return null;
    }

    //Mete los años que tienen al menos un gasto en el spinner
    private List<String> rellenarAnnos() {
        ArrayList<String> años = new ObtenerAñosConGasto().ejecutar();

        return años;
    }
}