/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import local.unp.desarrolloweb2.tienda.model.Producto;
import org.apache.struts2.ActionSupport;

/**
 *
 * @author mastermind
 */
public class ProductoNuevoAction extends ActionSupport {

    private Producto producto;

    @Override
    public String execute() {
        producto = new Producto();
        producto.setStock(0);
        return SUCCESS;
    }

    public Producto getProducto() {
        return producto;
    }
}
