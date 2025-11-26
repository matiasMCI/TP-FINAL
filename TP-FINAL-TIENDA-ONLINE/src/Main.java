import Excepciones.ElementoDuplicadoEx;
import Excepciones.ListasVaciasEx;
import JSONUtiles.JSONUtiles;
import Utilidades.Etiquetas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sistema.SistemaTienda;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


try {
    /// COPIAMOS LA INFORMACION DE LOS JSON EN EL SISTEMA
    String data = JSONUtiles.downloadJSON("tiendaOnlineDatos");
    JSONObject jsonData = new JSONObject(data);
    String data2 = JSONUtiles.downloadJSON("tiendaOnlineProductos");
    JSONArray jsonArrayData2 = new JSONArray(data2);
    String dataPedido = JSONUtiles.downloadJSON("tiendaOnlinePedidos");
    JSONArray jsonArrayDataPedido = new JSONArray(dataPedido);
    SistemaTienda sistema = new SistemaTienda(jsonData, jsonArrayData2, jsonArrayDataPedido);

    Scanner sc = new Scanner(System.in);
    boolean activo = true;

    do {
        Menu.Menu.menuLogin();

        int opcion = sistema.leerEnteroSeguro(sc,"opcion: ");

        switch (opcion) {
            case 1:
                /// Menu.Menu logueo
                try {
                    sistema.loguearse();
                } catch (ListasVaciasEx e) {
                    System.out.println(Etiquetas.ERROR + "al loguerse: " + e.getMessage());
                }
                break;
            case 2:
                /// Menu.Menu Registrarse
                try {
                    sistema.registrarse();
                } catch (ElementoDuplicadoEx e) {
                    System.out.println(Etiquetas.ERROR + "al registrar:" + e.getMessage());
                }
                break;
            case 3:
                activo = false;
                System.out.println(Etiquetas.INFO + "Saliendo del sistema, Hasta luego!");
                break;
            default:
                System.out.println(Etiquetas.WARNING + "Opcion Invalida..");
                break;
        }
    } while (activo);


}catch (JSONException je ){
    System.out.println(Etiquetas.ERROR + " json" + je.getMessage());
}



    }

}

