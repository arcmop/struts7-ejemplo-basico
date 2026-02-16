/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.ServletResponseAware;

/**
 *
 * @author mastermind
 */
public class NotFoundAction extends ActionSupport implements ServletResponseAware {

    private HttpServletResponse response;

    @Override
    public String execute() {
        if (response != null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return SUCCESS;
    }

    @Override
    public void withServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}
