

public class Main {
    public static void main(String[] args) {


        ///-- CONSTRUCTO --

        ///-- GETTERS SETTERS --

        ///-- METODOS --

        SistemaTienda sistemaTienda = new SistemaTienda();

        sistemaTienda.agregarAdministrador("Juan","juan@gmail,com","capicua123",30,0023);

        Administrador administrador = sistemaTienda.getAdminPorID(1);
        System.out.println(administrador.toString());




    }
}