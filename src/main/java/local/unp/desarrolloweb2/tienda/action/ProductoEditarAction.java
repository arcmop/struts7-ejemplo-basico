/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;

/**
 *
 * @author mastermind
 */
public class ProductoEditarAction extends ActionSupport {

    private Integer id;

    public Producto producto;

    @Override
    public String execute() {
        if (id == null) {
            addActionError(getText("error.producto.id"));
            return INPUT;
        }

        try {
            producto = TiendaService.getProductoPorId(id);
            if (producto == null) {
                addActionError(getText("error.producto.not_found"));
                return INPUT;
            }
        } catch (RuntimeException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    @StrutsParameter
    public Integer getId() {
        return id;
    }

    @StrutsParameter
    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
