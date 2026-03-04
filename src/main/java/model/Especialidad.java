package model;

import java.util.Set;

public class Especialidad {
    private String idEspecialidad; // CHAR(2)
    private String nombreEspe;
    private Set<Profesor> profesores; // Relación 1:N con Profesores

    public Especialidad() {}

    public String getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspe() {
        return nombreEspe;
    }

    public void setNombreEspe(String nombreEspe) {
        this.nombreEspe = nombreEspe;
    }

    public Set<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(Set<Profesor> profesores) {
        this.profesores = profesores;
    }
}