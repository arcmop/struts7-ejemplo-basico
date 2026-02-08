/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import local.unp.desarrolloweb2.tienda.service.TiendaService;

/**
 *
 * @author mastermind
 */
public class ProductoEliminarAction extends ActionSupport {

    private Integer id;

    @Override
    public String execute() {
        if (id == null) {
            addActionError(getText("error.producto.id"));
            return ERROR;
        }

        TiendaService.eliminarProducto(id);
        return SUCCESS;
    }

    public Integer getId() {
        return id;
    }

    @StrutsParameter(depth = 1)
    public void setId(Integer id) {
        this.id = id;
    }
}
