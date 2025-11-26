package Clases;

import Excepciones.ElementoDuplicadoEx;

import java.util.Map;
import java.util.TreeMap;

/**
 * Clase genérica que gestiona un mapa de elementos identificados por un {@code String} ID.
 *
 * <p>Permite agregar, eliminar, verificar existencia y recuperar elementos por su ID.
 * Utiliza un {@link TreeMap} para mantener los elementos ordenados por clave.</p>
 *
 * @param <T> tipo de los elementos que se almacenan en el mapa
 */
public class ClaseGenericaMap <T>{
    /** Mapa genérico que almacena los elementos con su ID correspondiente. */
    private Map<String, T> listaMapGenerica = new TreeMap<>();


    /**
     * Agrega un elemento al mapa con su ID.
     *
     * @param id identificador único del elemento
     * @param t elemento a agregar
     * @throws ElementoDuplicadoEx si ya existe un elemento con la misma ID
     */
    public void agregarGenerico(String id, T t) throws ElementoDuplicadoEx{
        if(listaMapGenerica.containsKey(id)){
            throw  new ElementoDuplicadoEx("Elemento duplicado...");
        }
        listaMapGenerica.put(id,t);
    }
    /**
     * Obtiene un elemento por su ID.
     *
     * @param id identificador del elemento
     * @return el elemento asociado al ID, o {@code null} si no existe
     */
    public T getPorId(String id){
        return listaMapGenerica.get(id);
    }
    /**
     * Elimina un elemento del mapa por su ID.
     *
     * @param id identificador del elemento a eliminar
     */
    public void eliminar(String id){
        listaMapGenerica.remove(id);
    }
    /**
     * Verifica si un ID existe en el mapa.
     *
     * @param id identificador a comprobar
     * @return {@code true} si el ID existe, {@code false} en caso contrario
     */
    public boolean existeId(String id){
        return listaMapGenerica.containsKey(id);
    }
    /**
     * Muestra todos los elementos del mapa en consola.
     */
    public void mostrar(){
        for(String id :  listaMapGenerica.keySet()){
            System.out.println(id + " " + listaMapGenerica.get(id));
        }
    }
    /**
     * Devuelve el mapa completo de elementos.
     *
     * @return mapa genérico con todos los elementos
     */
    public Map<String, T> getListaMapGenerica() {
        return listaMapGenerica;
    }

    /**
     * Comprueba si el mapa está vacío.
     *
     * @return {@code true} si no contiene elementos, {@code false} en caso contrario
     */
    public boolean isListaVacia() {
        return listaMapGenerica.isEmpty();
    }


}
