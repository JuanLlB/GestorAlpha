package modelo;

public class Gasto {

    private String id_usuario;
    private int id;
    private double cantidad;
    private String concepto;
    private String año,mes,dia;
    private String descripcion;
    private static int count=0;

    public Gasto(String id_usuario, double cantidad, String concepto, String año,String mes,String dia, String descripcion) {
        this.id_usuario = id_usuario;
        this.id = -1;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.año=año;
        this.dia=dia;
        this.mes=mes;
        this.descripcion = descripcion;
    }

    public Gasto(int id,String id_usuario, double cantidad, String concepto, String año,String mes,String dia, String descripcion) {
        this.id_usuario = id_usuario;
        this.id = id;
        this.cantidad = cantidad;
        this.concepto = concepto;
        this.año=año;
        this.dia=dia;
        this.mes=mes;
        this.descripcion = descripcion;
    }

    //Devuelve un string con los datos que queremos mostrar para la base de datos
    @Override
    public String toString() {
        return cantidad +
                ",     document.body.appendChild(table);\n='" + concepto + '\'' +
                ", fecha='" + año+"-"+mes+"-"+dia + '\'' +
                '}';
    }

    //Devuelve un string con los datos que queremos mostrar
    public String imprimir() {
        return cantidad + " " + concepto + " " + descripcion + " " + año+"-"+mes+"-"+dia;
    }

    public Gasto(String texto) {
        String[] ct = texto.split(",");
        this.cantidad= Double.parseDouble(ct[1]);
        this.concepto=ct[2];
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return concepto;
    }

    public void setCategoria(String categoria) {
        this.concepto = categoria;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Gasto.count = count;
    }

    public String getAño() {
        return año;
    }

    public String getMes() {
        return mes;
    }

    public String getDia() {
        return dia;
    }
}
