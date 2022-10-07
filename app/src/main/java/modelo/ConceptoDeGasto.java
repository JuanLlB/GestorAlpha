package modelo;

import java.util.ArrayList;
import java.util.List;

public class ConceptoDeGasto {
/*
    private String concepto;

    public ConceptoDeGasto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }*/

    private List<String> conceptos;

    private void rellenar()
    {
        conceptos=new ArrayList <String>();
        conceptos.add("Casa");
        conceptos.add("Trabajo");
        conceptos.add("Viajes");
        conceptos.add("Otros");


    }

    public ConceptoDeGasto(){
        rellenar();
    }

    public int getNumero(){
        return conceptos.size();
    }

    public String getValor(int i){
        if (i>=0 && i<conceptos.size()){
            return conceptos.get(i);

        }
        return null;
    }

    public List<String> getLista(){
        return  conceptos;
    }

}
