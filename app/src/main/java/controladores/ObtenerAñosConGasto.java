package controladores;

/*@author Juan Llorca Botas

* */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import util.BBDD;

public class ObtenerAñosConGasto {
    /**
     * @return Lista de Años dónde hay algun gasto
     */
    int yearInit=2011;

public ArrayList<String> ejecutar(){
    ArrayList<Integer> anyos=new ArrayList<Integer>();
    //obtenemos los años
    int year=Calendar.getInstance().get(Calendar.YEAR);
    Calendar.getInstance().get(Calendar.YEAR);
    if (yearInit<Calendar.getInstance().get(Calendar.YEAR)) {
        ArrayList<String> años = new ArrayList<String>();
        while (year>=yearInit) {
            anyos.add(year);
            year--;
        }
    }

    Integer anyoActual = Calendar.getInstance().get(Calendar.YEAR);

    if (!anyos.contains(anyoActual))
        anyos.add(anyoActual);

       //lo convertimos a string para el spinner
    ArrayList<String> lista=new ArrayList<String>();
    for (Integer i:anyos)
        lista.add(i.toString());

    return lista;




}

}
