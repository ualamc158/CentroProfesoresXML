package model;

import java.util.Set;

public class Asignatura {
    private String codAsig; // CHAR(6)
    private String nombreAsi;
    private Set<Profesor> profesores; // Relación N:M con Profesores

    public Asignatura() {}

    public String getCodAsig() {
        return codAsig;
    }

    public void setCodAsig(String codAsig) {
        this.codAsig = codAsig;
    }

    public String getNombreAsi() {
        return nombreAsi;
    }

    public void setNombreAsi(String nombreAsi) {
        this.nombreAsi = nombreAsi;
    }

    public Set<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(Set<Profesor> profesores) {
        this.profesores = profesores;
    }
}