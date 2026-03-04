package model;

import java.util.Set;

public class Centro {
    private Integer codCentro;
    private String nomCentro;
    private Profesor director; // Relación 1:1 o N:1 con Profesor (El director es un profesor)
    private String direccion;
    private String localidad;
    private String provincia;
    private Set<Profesor> profesores; // Relación 1:N con Profesores

    public Centro() {}

    public Integer getCodCentro() {
        return codCentro;
    }

    public void setCodCentro(Integer codCentro) {
        this.codCentro = codCentro;
    }

    public String getNomCentro() {
        return nomCentro;
    }

    public void setNomCentro(String nomCentro) {
        this.nomCentro = nomCentro;
    }

    public Profesor getDirector() {
        return director;
    }

    public void setDirector(Profesor director) {
        this.director = director;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Set<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(Set<Profesor> profesores) {
        this.profesores = profesores;
    }
}