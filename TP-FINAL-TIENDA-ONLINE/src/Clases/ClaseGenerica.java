package Clases;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase genérica que maneja una lista de elementos de tipo T.
 * Permite agregar, obtener y ver todos los elementos.
 **/
public class ClaseGenerica <T>{
    List<T> listaGenerica = new ArrayList<>();

    public void agregarItem(T t){
        listaGenerica.add(t);
    }



    public String verGenerico(){
        String res = "";
        for(T t : listaGenerica){
            res += t.toString() + "\n";
        }
        return res;
    }

    public T get(int indice) {
        indice = indice-1;
        return listaGenerica.get(indice);
    }

    public List<T> getListaGenerica() {
        return listaGenerica;
    }
}
