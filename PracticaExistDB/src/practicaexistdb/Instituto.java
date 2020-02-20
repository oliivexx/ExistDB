
package practicaexistdb;

import java.util.ArrayList;

public class Instituto {
    int codInsti;
    String nombre;
    String provincia;
    String calle;

    public Instituto() {
    }

    public Instituto(int codInsti, String nombre, String provincia, String calle) {
        this.codInsti = codInsti;
        this.nombre = nombre;
        this.provincia = provincia;
        this.calle = calle;
    }
    
    

    public int getCodInsti() {
        return codInsti;
    }

    public void setCodInsti(int codInsti) {
        this.codInsti = codInsti;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String toString() {
        return "Instituto{" + "codInsti=" + codInsti + ", nombre=" + nombre + ", provincia=" + provincia + ", calle=" + calle + '}';
    }
 
}
