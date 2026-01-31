/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionSupport;

/**
 *
 * @author mastermind
 */
public class SaludarAction extends ActionSupport {

    private String saludo;

    public String getSaludo() {
        return saludo;
    }

    @Override
    public String execute() throws Exception {
        saludo = "Hola invitado, bienvenido a Apache Struts 2 versión 7";
        return SUCCESS;
    }

}
