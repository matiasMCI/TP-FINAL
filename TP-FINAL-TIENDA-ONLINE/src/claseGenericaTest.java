import Excepciones.ElementoDuplicadoEx;
import Excepciones.IDdontExistEX;

import java.util.HashMap;

public class claseGenericaTest <T>{
    HashMap<String, T> listaGenericaTest = new HashMap<>();



    public void add(String id, T t)throws ElementoDuplicadoEx {
        if(listaGenericaTest.containsKey(id)){
            throw new ElementoDuplicadoEx("Ya existe un elemento con esa id: " + id);
        }
        listaGenericaTest.put(id,t);
    }
    public void remove(String id)throws IDdontExistEX {
        if(!listaGenericaTest.containsKey(id)){
            throw new IDdontExistEX("No existe el producto con esa id: " + id);
        }
        listaGenericaTest.remove(id);
    }

    public T getPorId(String id){
        T t = listaGenericaTest.get(id);
        return t;
    }

    public void mostrarGenericoTest(){
        for(T t : listaGenericaTest.values()){
            System.out.println(t.toString());
        }
    }


}
