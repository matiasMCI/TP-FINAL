import java.util.HashMap;

public class claseGenericaTest <T>{
    HashMap<String, T> listaGenericaTest = new HashMap<>();



    public void add(String id, T t){
        listaGenericaTest.put(id,t);
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
