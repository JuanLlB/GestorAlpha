/*
package modelo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llorcabotas.juan.gestoralpha.R;

import java.util.ArrayList;

public class CrearGasto extends AppCompatActivity {

    private EditText nombre;
    private EditText contenido;
    public ArrayList<Gasto> gastos;
    public BBDD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);
        inicializar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bd.cerrar();
        mostrarMensaje("Base de datos cerrada");
    }

    private void inicializar() {
        nombre = (EditText) findViewById(R.id.etNombre);
        contenido = (EditText) findViewById(R.id.etContenido);
        bd = new BBDD(this);
        mostrarMensaje("Base de datos abierta");
        gastos = bd.obtenerGasto();
    }

    public void crearNota(View view) {
        if ((nombre.getText().length() > 0) && (contenido.getText().length() > 0)) {
            // Se inserta una nota en la tabla, muestra un mensaje y limpia el formulario
            bd.insertarGasto(nombre.getText().toString(), contenido.getText().toString());
            mostrarMensaje("Nota creada con éxito");
            limpiar();
        } else {
            mostrarMensaje("Debes introducir el nombre y contenido de la nota");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        // Se situa el foco en el campo Nombre y se vacían los campos
        contenido.clearFocus();
        nombre.setFocusable(true);
        nombre.setText("");
        contenido.setText("");
    }

}


 */