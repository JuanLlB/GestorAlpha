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
        gastos.add(new Gasto(usuario.getId(), 20, "Casa", mes+"", año+"", 1+"", "Prueba"));
        gastos.add(new Gasto(usuario.getId(), 21, "Trabajo", mes+"", año+"", 2+"", "Prueba"));
        gastos.add(new Gasto(usuario.getId(), 22, "Viajes", mes+"", año+"", 3+"", "Prueba"));
        gastos.add(new Gasto(usuario.getId(), 23, "Otro", mes+"", año+"", 4+"", "Prueba"));
        return gastos;
    }
}
