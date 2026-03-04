package model;

import java.util.Date;
import java.util.Set;

public class Profesor {
    private Integer codProf;
    private String nombreApe;
    private Especialidad especialidad; // Relación N:1
    private Profesor jefe; // Relación reflexiva N:1 (Un jefe es un profesor)
    private Date fechaNac;
    private String sexo;
    private Centro centro; // Relación N:1
    private Set<Asignatura> asignaturas; // Relación N:M

    public Profesor() {}

    public Integer getCodProf() {
        return codProf;
    }

    public void setCodProf(Integer codProf) {
        this.codProf = codProf;
    }

    public String getNombreApe() {
        return nombreApe;
    }

    public void setNombreApe(String nombreApe) {
        this.nombreApe = nombreApe;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Profesor getJefe() {
        return jefe;
    }

    public void setJefe(Profesor jefe) {
        this.jefe = jefe;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Set<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Set<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}