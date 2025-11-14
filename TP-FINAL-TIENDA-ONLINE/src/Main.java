import Enums.CategoriaProducto;

public class Main {
    public static void main(String[] args) {


        ///-- CONSTRUCTO --

        ///-- GETTERS SETTERS --

        ///-- METODOS --

        SistemaTienda sistemaTienda = new SistemaTienda();

        sistemaTienda.agregarAdministrador("Juan","juan@gmail,com","capicua123",30,0023);

        Administrador administrador = sistemaTienda.getAdminPorID(1);
        System.out.println(administrador.toString());


        administrador.DarDeAltaProducto("PROD001", "Laptop Lenovo ThinkPad", 899.99, CategoriaProducto.TECNOLOGIA, "Laptop empresarial de 14 pulgadas, 16GB RAM, SSD 512GB");
        administrador.DarDeAltaProducto("PROD002", "Auriculares Sony WH-1000XM4", 299.99, CategoriaProducto.TECNOLOGIA, "Auriculares inalámbricos con cancelación de ruido");
        administrador.DarDeAltaProducto("PROD003", "Café Colombiano Premium 1kg", 14.50, CategoriaProducto.ALIMENTOS, "Café tostado premium de origen colombiano");
        administrador.DarDeAltaProducto("PROD004", "Camiseta Negra Unisex", 12.99, CategoriaProducto.INDUMENTARIA, "Camiseta 100% algodón, cómoda y durable");
        administrador.DarDeAltaProducto("PROD005", "Silla Gamer Cougar", 189.00, CategoriaProducto.HOGAR,"Silla ergonómica para juegos y oficina");

        administrador.mostrarListaProductos();




    }
}