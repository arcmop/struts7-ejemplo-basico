/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;

/**
 *
 * @author mastermind
 */
public class ProductoAction extends ActionSupport {

    private List<Producto> listaProducto;
    private Integer anio;
    private String anioTexto;

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public Integer getAnio() {
        return anio;
    }

    public String getAnioTexto() {
        return anioTexto;
    }

    @Override
    public String execute() throws Exception {
        this.listaProducto = TiendaService.getListaProductos();
        this.anio = Year.now().getValue();
        this.anioTexto = this.anio.toString();
        return SUCCESS;
    }
}
