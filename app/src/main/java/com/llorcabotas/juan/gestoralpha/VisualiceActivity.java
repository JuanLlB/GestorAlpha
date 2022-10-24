package com.llorcabotas.juan.gestoralpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import controladores.ObtenerAñosConGasto;
import modelo.BBDD;
import modelo.Gasto;
import modelo.Usuario;

public class VisualiceActivity extends AppCompatActivity {

    Spinner spinnerD, spinnerM, spinnerA;
    String day, month, year;
    int seleccion;
    ListView listView;
    Usuario u;
    BBDD bd;
    public ArrayList<Gasto> gastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualice);
        Button bv=(Button) findViewById(R.id.buttonVolver2);
        listView = (ListView) findViewById(R.id.listaGastos);

        Bundle bundle = getIntent().getExtras();
        try {
            u = (Usuario) bundle.getSerializable("usuario");
        }
        catch (Exception e){
            u=null;
        }


        TextView tfa=(TextView) findViewById(R.id.fechaActual2);
        month=fijarMes(Calendar.getInstance().get(Calendar.MONTH));
        year=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        day="na";
        tfa.setText(month+"/"+year);

        List<String> m2=rellenarMeses();
        List<String> a=rellenarAnnos();
        List<String> d=rellenarDias();
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

        spinnerD=(Spinner) findViewById(R.id.opcionesDia);
        spinnerM=(Spinner) findViewById(R.id.opcionesMes2);
        spinnerA=(Spinner) findViewById(R.id.opcionesAño);
        // Adjunta datos del adapter al spinner
        spinnerD.setAdapter(myAdapterD);

        spinnerM.setAdapter(myAdapterM);

        spinnerA.setAdapter(myAdapterA);

        //Selecciona un gasto
        seleccion=-1;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(VisualiceActivity.this,"Ok "+position,Toast.LENGTH_SHORT).show();
                seleccion=position;
            }
        });

        //Llama a la base de datos
        bd = new BBDD(this);

      rellenarListaGastos();



      //Regresas al menu
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VisualiceActivity.this,MenuActivity.class);
                i.putExtra("usuario",u);
                startActivity(i);
                finish();
            }
        });

        //Cambia la fecha actual a la del año y mes seleccionados
        Button bcf=(Button) findViewById(R.id.botonCambio);
        bcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month=spinnerM.getSelectedItem().toString();
                year=spinnerA.getSelectedItem().toString();
                day=spinnerD.getSelectedItem().toString();

                tfa.setText(month+"/"+year);
                rellenarListaGastos();
            }
        });

        //Si has seleccionado un gasto de la lista este se borra
        Button Borrar=(Button) findViewById(R.id.botonBorrar);
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seleccion>=0 && gastos!=null) {
                    Gasto g = gastos.get(seleccion);
                    bd.borrarGasto(g.getId());
                    rellenarListaGastos();

                }
            }
        });
    }

    //Mete los días del mes en el spinner
    private List<String> rellenarDias() {
        //ArrayList<String> dias = new ObtenerAñosConGasto().ejecutar();
        ArrayList<String> dias = new ArrayList<String>();
        String item=month;
        if (item.equals("Enero") || item.equals("Marzo") || item.equals("Mayo") || item.equals("Julio") || item.equals("Agosto") || item.equals("Octubre") || item.equals("Diciembre")){
            dias.add("na");
            for (int i=0; i<=31; i++){
                dias.add(i+"");
            }
        }else if (item.equals("Abril") || item.equals("Junio") || item.equals("Septiembre") || item.equals("Noviembre")){
            dias.add("na");
            for (int i=0; i<=30; i++){
                dias.add(i+"");
            }
        }else if (item.equals("Febrero")){
            item=year;
            if (Integer.parseInt(item)%4==0){
                dias.add("na");
                for (int i=0; i<=29; i++){
                    dias.add(i+"");
                }
            }else{
                dias.add("na");
                for (int i=0; i<=28; i++){
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

    //Devuelve un array list con todos los gastos que queremos mostrar
    private ArrayList<String> conversionGastos(){
        ArrayList<String> datos=new ArrayList<String>();
        if (gastos!=null){
            for (Gasto g:gastos){
                datos.add(g.imprimir());
            }
        }
        return datos;
    }

    //Pone los datos de los gastos en el listView
    private void rellenarListaGastos(){
        try {
            String aux=u.getId();

            if (this.day=="na"){
                gastos=bd.obtenerGasto(aux,this.month,this.year);
            }else{
                gastos=bd.obtenerGasto(aux,this.day,this.month,this.year);
            }
        }
        catch (Exception e){
            gastos=null;
        }

        if (gastos!=null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, conversionGastos());
            listView.setAdapter(adapter);
        }
    }

}