package modelo;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

import java.util.List;

public class Datos implements Serializable {
    Usuario u;
    List<Gasto> lista;

    public Datos(){
        u=null;
        lista=null;
    }


}
