package model;

public class Domicilio {

    private int id;
    private String calle;
    private int numero;
    private int cp;


    public Domicilio(int id, String calle, int numero, int cp) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
    }

    public Domicilio(String calle, int numero, int cp) {
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", cp=" + cp +
                '}';
    }
}
