package controladores;

import java.util.ArrayList;
import java.util.List;

import modelo.BBDD;
import modelo.Gasto;
import modelo.Usuario;

public class CargarResumenMes {

    public BBDD bd;

    public List<Gasto> run(Usuario usuario, int mes, int año){
       ArrayList<Gasto> gastos =new ArrayList<Gasto>();
        return gastos;
    }
}
