/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.service;

import java.util.ArrayList;
import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;

/**
 *
 * @author mastermind
 */
public class TiendaService {

    private static TiendaService tiendaService;
    private List<Producto> listaProductos = null;

    private TiendaService() {
        if (null == listaProductos) {
            listaProductos = new ArrayList();
            listaProductos.add(new Producto(1, "Laptop Lenovo Legion 5 Pro", "Core i9 14000", 9100D));
            listaProductos.add(new Producto(2, "Laptop ASUS TUF 15", "Core i7 12000", 7500D));
            listaProductos.add(new Producto(3, "Laptop HP 840 Gen3", "Core i5 9000", 4000D));
            listaProductos.add(new Producto(4, "Mouse Gamer Genius", "Mouse optico High DPI", 250D));
            listaProductos.add(new Producto(5, "Teclado HP Gamer", "Teclado mecanico QWERTY", 100D));
        }
    }

    public static List<Producto> getListaProductos() {
        if (null == tiendaService) {
            tiendaService = new TiendaService();
        }
        return tiendaService.listaProductos;
    }
}
