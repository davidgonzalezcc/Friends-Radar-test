package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String imagen;
    private String carrera;
    private Integer semestre;
    private Posicion posicion;
    private boolean disponible;
    private String signoZodiacal;



    public Usuario(String nombre, String apellido, String email, String password, String imagen, Integer semestre, String signoZodiacal, Posicion posicion, boolean disponible) {
        System.out.println("EMAAAIL"+ email);
        System.out.println("PASS"+ password);
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.imagen = imagen;
        this.carrera = carrera;
        this.posicion = posicion;
        this.disponible = disponible;
        this.semestre = semestre;
        this.signoZodiacal = signoZodiacal;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public String getSignoZodiacal() {
        return signoZodiacal;
    }

    public void setSignoZodiacal(String signoZodiacal) {
        this.signoZodiacal = signoZodiacal;
    }

}
