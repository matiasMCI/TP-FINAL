import java.util.ArrayList;
import java.util.List;

public class ClaseGenerica <T>{
    List<T> listaDeInventario;


    public ClaseGenerica(){
        listaDeInventario = new ArrayList<>();
    }


    public void agregarItem(T t){
        listaDeInventario.add(t);
    }

    public String verGenerico(){
        String res = "";
        for(T t : listaDeInventario){
            res = res + t.toString();
        }
        return res;
    }

}
