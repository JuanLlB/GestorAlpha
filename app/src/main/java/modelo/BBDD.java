package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;

public class BBDD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "Gastos.db";
    private static final int VERSION_BD = 3;
    private static final String NOMBRE_TABLA_GASTOS = "gastos";
    private static final String CREAR_TABLA_GASTOS =
            "CREATE TABLE " + NOMBRE_TABLA_GASTOS +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_usario TEXT, " +
                    "cantidad REAL," +
                    "concepto TEXT," +
                    "descripcion TEXT," +
                    "dia TEXT,"+
                    "mes TEXT,"+
                    "anyo TEXT)";
    private SQLiteDatabase bd;

    public BBDD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
        bd = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(CREAR_TABLA_GASTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        bd.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA_GASTOS);
        onCreate(bd);
    }

    public void insertarGasto(Gasto g) {
        ContentValues cv = new ContentValues();
        cv.put("id_usario", g.getId_usuario());
        cv.put("cantidad", g.getCantidad());
        cv.put("concepto", g.getConcepto());
        cv.put("anyo",g.getAño());
        cv.put("mes",g.getMes());
        cv.put("dia",g.getDia());

        cv.put("descripcion", g.getDescripcion());
        bd.insert(NOMBRE_TABLA_GASTOS, null, cv);
    }

    public void actualizarGasto(int id, Gasto g) {
        ContentValues cv = new ContentValues();
        cv.put("cantidad", g.getCantidad());
        cv.put("concepto", g.getConcepto());
        cv.put("descripcion", g.getDescripcion());
        bd.update(NOMBRE_TABLA_GASTOS, cv,  " _id = " + id, null);
    }

    public void borrarGasto(int id) {
        // Elimina la nota seleccionada
        bd.delete(NOMBRE_TABLA_GASTOS, " _id = " + id, null);
    }

    public void cerrar() {
        // Cierra la base de datos
        bd.close();
    }

    public ArrayList<Gasto> obtenerGasto(String id_usuario) {
        ArrayList<Gasto> gastos = new ArrayList<Gasto>();

        String sentenciaSql = "SELECT _id,cantidad,concepto,descripcion,anyo,mes,dia" +
                "" + " FROM " + NOMBRE_TABLA_GASTOS +
                " WHERE id_usario = '"+id_usuario+"'";
        Cursor c=null;
       try {
           c = bd.rawQuery(sentenciaSql, null);
       }
       catch (Exception e){
           return null;
       }
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndexOrThrow("_id"));


                String cantidad = c.getString(c.getColumnIndexOrThrow("cantidad"));
                String concepto = c.getString(c.getColumnIndexOrThrow("concepto"));

                String año = c.getString(c.getColumnIndexOrThrow("anyo"));
                String mes = c.getString(c.getColumnIndexOrThrow("mes"));
                String dia = c.getString(c.getColumnIndexOrThrow("dia"));
                String descripcion = c.getString(c.getColumnIndexOrThrow("descripcion"));
                Gasto gasto = new Gasto(id, id_usuario, Double.parseDouble(cantidad), concepto, año,mes,dia, descripcion);
                gastos.add(gasto);
            } while (c.moveToNext());
        }
        c.close();
        return gastos;
    }


    public ArrayList<Gasto> obtenerGasto(String id_usuario,String mes,String año) {

        ArrayList<Gasto> gastos = new ArrayList<Gasto>();
        String mesNumero=numero(mes);
        String mesAño=numeroAño(año);
        String sentenciaSql = "SELECT _id,cantidad,concepto,descripcion,dia" +
                " FROM " + NOMBRE_TABLA_GASTOS +
                " WHERE id_usario = '"+id_usuario+"'"
                +" AND anyo='"+mesAño+"'"
              +" AND mes='"+mesNumero+"'";
        Cursor c=null;
        try {
            c = bd.rawQuery(sentenciaSql, null);
        }
        catch (Exception e){
            return null;
        }
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                int id = c.getInt(c.getColumnIndexOrThrow("_id"));


                String cantidad = c.getString(c.getColumnIndexOrThrow("cantidad"));
                String concepto = c.getString(c.getColumnIndexOrThrow("concepto"));
                String dia = c.getString(c.getColumnIndexOrThrow("dia"));

                String descripcion = c.getString(c.getColumnIndexOrThrow("descripcion"));
                Gasto gasto = new Gasto(id, id_usuario, Double.parseDouble(cantidad), concepto, mes,año,dia, descripcion);
                gastos.add(gasto);
            } while (c.moveToNext());
        }
        c.close();
        return gastos;
    }

    private String numeroAño(String año) {

        try{
            //pasamos a entero el año
            int aux=Integer.parseInt(año);
            aux=aux-1900;
            return String.valueOf(aux);
        }
        catch (Exception e){return "";}
    }

    /*

    private static final String NOMBRE_BASE_DATOS =
            "pagos.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    public BBDD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, Context contexto) {
        super(context, name, factory, version);
        this.contexto = contexto;
    }

    interface Tablas {
        String GASTO = "gasto";
    }


    /*
    interface Referencias {
        String ID_GASTO = String.format("REFERENCES %s(%s) ON DELETE CASCADE", Tablas.GASTO, Gasto.ID_CABECERA_PEDIDO);
    }
    public void BaseDatosGastos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null,
                VERSION_ACTUAL);
        this.contexto = contexto;
    }*/ /*
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL,%s DATETIME NOT NULL,%s TEXT NOT NULL %s," +
                "%s TEXT NOT NULL %s)",
                Tablas.ID_GASTO, BaseColumns._ID,
                CabecerasPedido.ID_CABECERA_PEDIDO,
                CabecerasPedido.FECHA,
                CabecerasPedido.ID_CLIENTE,
                Referencias.ID_CLIENTE,
                CabecerasPedido.ID_FORMA_PAGO,
                Referencias.ID_FORMA_PAGO));*/
    /*}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +
                Tablas.GASTO);
        onCreate(db);
    } */

private String numero(String mes) {
    if (mes.equals("Enero")) return "0";
    if (mes.equals("Febrero")) return "1";
    if (mes.equals("Marzo")) return "2";
    if (mes.equals("Abril")) return "3";
    if (mes.equals("Mayo")) return "4";
    if (mes.equals("Junio")) return "5";
    if (mes.equals("Julio")) return "6";
    if (mes.equals("Agosto")) return "7";
    if (mes.equals("Septiembre")) return "8";
    if (mes.equals("Octubre")) return "9";
    if (mes.equals("Noviembre")) return "10";
    if (mes.equals("Diciembre")) return "11";
    return "00";
}
}
