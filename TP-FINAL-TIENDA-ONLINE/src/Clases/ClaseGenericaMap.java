package Clases;

import Excepciones.ElementoDuplicadoEx;

import java.util.Map;
import java.util.TreeMap;

public class ClaseGenericaMap <T>{

    private Map<String, T> listaMapGenerica = new TreeMap<>();

    public void agregarGenerico(String id, T t) throws ElementoDuplicadoEx{
        if(listaMapGenerica.containsKey(id)){
            throw  new ElementoDuplicadoEx("Elemento duplicado...");
        }
        listaMapGenerica.put(id,t);
    }

    public T getPorId(String id){
        return listaMapGenerica.get(id);
    }
    public void eliminar(String id){
        listaMapGenerica.remove(id);
    }
    public boolean existeId(String id){
        return listaMapGenerica.containsKey(id);
    }
    public void mostrar(){
        for(String id :  listaMapGenerica.keySet()){
            System.out.println(id + " " + listaMapGenerica.get(id));
        }
    }

    public Map<String, T> getListaMapGenerica() {
        return listaMapGenerica;
    }

    public boolean isListaVacia() {
        return listaMapGenerica.isEmpty();
    }


}
