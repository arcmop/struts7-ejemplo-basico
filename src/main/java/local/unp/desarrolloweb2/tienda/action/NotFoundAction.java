/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author mastermind
 */
public class NotFoundAction extends ActionSupport {

    @Override
    public String execute() {
        HttpServletResponse response = ServletActionContext.getResponse();
        if (response != null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return SUCCESS;
    }
}
