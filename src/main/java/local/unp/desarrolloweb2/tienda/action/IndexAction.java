package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 *
 * @author mastermind
 */
public class IndexAction extends ActionSupport {

    private String numero1;
    private String numero2;
    private Integer resultado;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String sumar() {
        resultado = Integer.parseInt(numero1) + Integer.parseInt(numero2);
        return SUCCESS;
    }

    public String getNumero1() {
        return numero1;
    }

    @StrutsParameter
    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getNumero2() {
        return numero2;
    }

    @StrutsParameter
    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }

}

