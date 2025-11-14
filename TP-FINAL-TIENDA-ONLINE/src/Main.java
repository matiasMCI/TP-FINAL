import Clases.Administrador;
import Clases.Cliente;
import Enums.CategoriaProducto;
import Enums.ESTADO_CLIENTE;
import Enums.TIPO_CLIENTE;
import sistema.SistemaTienda;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        ///-- CONSTRUCTO --

        ///-- GETTERS SETTERS --

        ///-- METODOS --


        /// CREO UN OBJETO DE LA CLASE SISTEMA.
        /// De esta forma inicializo las listas
        SistemaTienda sistemaTienda = new SistemaTienda();

        /// Utilizo el metodo de sistema para agregar un administrador
        sistemaTienda.agregarAdministrador("Juan","juan@gmail,com","capicua123",30,0023);

        /// Creo un objeto Clases.Administrador y busco por id el que tenga la misma id
        Administrador administrador = sistemaTienda.getAdminPorID(1);
        System.out.println(administrador.toString());

        /// Utilizo el metodo de administrador para sobrecargar un producto en mis listaProducto de mi sistema
        administrador.DarDeAltaProducto(sistemaTienda,"PROD001", "Laptop Lenovo ThinkPad", 899.99, CategoriaProducto.TECNOLOGIA, "Laptop empresarial de 14 pulgadas, 16GB RAM, SSD 512GB");
        administrador.DarDeAltaProducto(sistemaTienda,"PROD002", "Auriculares Sony WH-1000XM4", 299.99, CategoriaProducto.TECNOLOGIA, "Auriculares inalámbricos con cancelación de ruido");
        administrador.DarDeAltaProducto(sistemaTienda,"PROD003", "Café Colombiano Premium 1kg", 14.50, CategoriaProducto.ALIMENTOS, "Café tostado premium de origen colombiano");
        administrador.DarDeAltaProducto(sistemaTienda,"PROD004", "Camiseta Negra Unisex", 12.99, CategoriaProducto.INDUMENTARIA, "Camiseta 100% algodón, cómoda y durable");
        administrador.DarDeAltaProducto(sistemaTienda,"PROD005", "Silla Gamer Cougar", 189.00, CategoriaProducto.HOGAR,"Silla ergonómica para juegos y oficina");
        /// Muestro mediante el metodo de mi administrador los productos de mi listaProductos
       /// administrador.VerListaDeProductos(sistemaTienda);
       ///  Agregamos cliente nuevos mediante los metodos del administrador
        administrador.agregarCliente(sistemaTienda,"martin.lopez@gmail.com", "martin123", "Martín López", 28, TIPO_CLIENTE.DISCAPACIDAD, ESTADO_CLIENTE.ALTA, 500.0, 12000.0);
        administrador.agregarCliente(sistemaTienda,"carla_mendez@hotmail.com", "carlaPass", "Carla Méndez", 35, TIPO_CLIENTE.SOCIO_MARKETforSMILES, ESTADO_CLIENTE.ALTA, 300.0, 45000.0);
        administrador.agregarCliente(sistemaTienda, "juan.perez@yahoo.com", "juan2024", "Juan Pérez", 42, TIPO_CLIENTE.JUBILADO, ESTADO_CLIENTE.BAJA, 700.0, 8000.0);
        administrador.agregarCliente(sistemaTienda,     "sofia.rivera@gmail.com", "sofi789", "Sofía Rivera", 22, TIPO_CLIENTE.UNIVERSITARIO, ESTADO_CLIENTE.ALTA, 250.0, 32000.0);
        administrador.agregarCliente(sistemaTienda,  "nicolas.garcia@outlook.com", "nicoPass", "Nicolás García", 31, TIPO_CLIENTE.SOCIO_MARKETforSMILES, ESTADO_CLIENTE.ALTA, 150.0, 75000.0);
        administrador.agregarCliente(sistemaTienda,  "valeria.montes@gmail.com", "vale2023", "Valeria Montes", 27, TIPO_CLIENTE.SOCIO_MARKETforSMILES, ESTADO_CLIENTE.ALTA, 600.0, 2000.0);
        administrador.agregarCliente(sistemaTienda,   "pablo_rojas@gmail.com", "rojas456", "Pablo Rojas", 45, TIPO_CLIENTE.SOCIO_MARKETforSMILES, ESTADO_CLIENTE.ALTA, 400.0, 56000.0);

        ///administrador.VerClientes(sistemaTienda);

        ///


        Cliente cliente = sistemaTienda.getClientePorID(2);
        System.out.println(cliente.toString());

        cliente.AgregarAlCarrito(sistemaTienda.getProductoPorID(sistemaTienda, "PROD002"));
        cliente.AgregarAlCarrito(sistemaTienda.getProductoPorID(sistemaTienda, "PROD003"));
        cliente.AgregarAlCarrito(sistemaTienda.getProductoPorID(sistemaTienda, "PROD004"));


        /// Entrada al sistema
        Scanner sc = new Scanner(System.in);
        String continuar = "no";
        int opcion;


        System.out.println("--------Bienvenido----------");
        System.out.println("Ingrese su ID");
        int id = sc.nextInt();
        for ( Cliente  c : sistemaTienda.getListaDeClientes().getListaGenerica()){

            if (id == c.getIDusuario()) {
                System.out.println("-- BIENVENIDO AL SISTEMA CLIENTE --");
                do {
                    System.out.println("_____Que Desea Realizar_____");
                    System.out.println("1. ver y agregar posibles productos a su carrito");
                    System.out.println("2.Eliminar un producto de su carrito");
                    System.out.println("3.Modificar su perfil");
                    System.out.println("4.Establecer su domicilio de entrega");
                    System.out.println("5.Realizar la compra de su carrito");
                    System.out.println("6.Ver su lista de pedidos");
                    System.out.println("7.Ver su historial de compras");


                    System.out.println("Elegir opcion:  ");
                    opcion = sc.nextInt();
                    sc.nextLine();

                    switch (opcion) {
                        case 1:
                            administrador.VerListaDeProductos(sistemaTienda);

                            do {

                                System.out.println("ingrese el id del producto");
                                String res = sc.nextLine();
                                cliente.AgregarAlCarrito(sistemaTienda.getProductoPorID(sistemaTienda, res));

                                System.out.println("desea continuar?");
                                continuar = sc.nextLine();

                            } while (continuar.equalsIgnoreCase("si"));
                            break;
                        case 2:

                            do {

                                System.out.println("ingrese el nombre del producto a eliminar");
                                String res = sc.nextLine();
                                cliente.EliminarDelCarrito(res);
                                System.out.println("desea continuar?");

                                continuar = sc.nextLine();

                            } while (continuar.equalsIgnoreCase("si"));

                            break;
                        case 3:

                            System.out.println("(1)Modificar nombre: ");
                            System.out.println("(2)Modificar email: ");
                            System.out.println("(3)Modificar contrasena: ");
                            System.out.println("(4)Modificar edad: ");

                            int cambio = sc.nextInt();
                            cliente.ModificarPerfil(sistemaTienda, cambio, id);


                            break;
                        case 4:

                            System.out.println("ingrese el nombre y altura de su nuevo domicilio");
                            double nuevoDomicilio = sc.nextDouble();
                            cliente.EstablecerDomicilioDeEntrega(sistemaTienda, nuevoDomicilio, id);

                            break;
                        case 5:

                            cliente.RealizarCompra(sistemaTienda, id);

                            break;
                        case 6:
                            cliente.HistorialDeCompra();
                            break;
                        default:
                            System.out.println("Opcion invalida...");
                            break;

                    }



                    System.out.println("Desea continuar? si/no: ");
                    continuar = sc.nextLine();

                } while (continuar.equalsIgnoreCase("si"));

            }

            else {
                for (Administrador administrador1 : sistemaTienda.getListaDeAdministradores().getListaGenerica()){

                    if (id == administrador1.getIDusuario()){
                        System.out.println("-- BIENVENIDO AL SISTEMA ADMINISTRADOR --");
                         do {
                             System.out.println("(1) Agregar Cliente");
                             System.out.println("(2) Agregar Producto");
                             System.out.println("(3) Dar de alta Cliente");
                             System.out.println("(4) Dar de baja Cliente");
                             System.out.println("(5) Eliminar Producto");
                             System.out.println("(6) Modificar Cliente");
                             System.out.println("(7) Modificar Producto");
                             System.out.println("(8) Mostrar Clientes");
                             System.out.println("(9) Mostrar Productos");
                             System.out.println("(10) Mostrar Clientes mas frecuentes ");
                             System.out.println("(11) Generar un Archivo de texto de los productos");
                             System.out.println("(12) Generar un Archivo de texto de los clientes");

                             System.out.println("Elegir opcion: ");
                             opcion = sc.nextInt();
                             sc.nextLine();

                             int idCliente;
                             String idProducto;


                             switch (opcion){
                                 case 1:
                                    // String email, String contrasena, String nombre, int edad, TIPO_CLIENTE tipoCliente, ESTADO_CLIENTE estado , double domicilio , double fondos

                                     administrador.agregarCliente(sistemaTienda,"Carlosmontes@gmail.com", "carlitos111", "Carlos montes", 33, TIPO_CLIENTE.SOCIO_MARKETforSMILES, ESTADO_CLIENTE.ALTA, 500.0, 19000.0);

                                     break;
                                 case 2:
                                     administrador1.DarDeAltaProducto(sistemaTienda,"PROD006", "Coca cola", 2500.00, CategoriaProducto.BEBIDA, "Coca cola de 1.5 lts");

                                     break;
                                 case 3:
                                     System.out.println("Ingrese el id del cliente a dar de alta: ");
                                     idCliente = sc.nextInt();
                                     administrador1.DarDeAltaCliente(sistemaTienda,idCliente);
                                     break;
                                 case 4:
                                     System.out.println("Ingrese el id del cliente a dar de baja: ");
                                     idCliente = sc.nextInt();
                                     administrador1.DarDeBajaCliente(sistemaTienda, idCliente);

                                     break;
                                 case 5:
                                     System.out.println("Ingrese el producto a eliminar: ");
                                     idProducto = sc.nextLine();
                                     administrador1.DarDeBajaProducto(sistemaTienda, idProducto);
                                     break;
                                 case 6:
                                     System.out.println("Ingrese la id del cliente a modificar: ");
                                     idCliente = sc.nextInt();
                                     administrador1.ModificarCliente(sistemaTienda,idCliente);

                                     break;
                                 case 7:
                                     System.out.println("Ingrese la id del producto a modificar: ");
                                     idProducto = sc.nextLine();
                                     administrador1.ModificarProducto(sistemaTienda, idProducto);
                                     break;
                                 case 8:
                                     administrador1.VerClientes(sistemaTienda);
                                     break;
                                 case 9:
                                     administrador1.VerListaDeProductos(sistemaTienda);
                                     break;
                                 case 10:
                                     administrador1.ClienteMasFrecuente(sistemaTienda);
                                     break;
                                 case 11:
                                     administrador1.cargarJsonProducto(sistemaTienda);
                                     break;
                                 case 12:
                                     administrador1.cargarJsonClientes(sistemaTienda);
                                     break;
                                 default:
                                     System.out.println("Opcion invalida...");
                                     break;
                             }

                             System.out.println("Desea continuar? si/no: ");
                             continuar = sc.nextLine();

                         }while (continuar.equalsIgnoreCase("si"));


                    }
                }
            }
        }






















    }
}