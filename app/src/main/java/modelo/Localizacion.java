package modelo;

import com.google.android.gms.maps.model.LatLng;

public class Localizacion {
    private LatLng posicion;
    private String nombre;

    public Localizacion(LatLng posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
    }

    public LatLng getPosicion() {
        return posicion;
    }

    public void setPosicion(LatLng posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
