/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.inject.Inject;
import java.math.BigDecimal;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 *
 * @author mastermind
 */
public class ProductoRegistrarAction extends ActionSupport {

    private static final int NOMBRE_MIN = 3;
    private static final int NOMBRE_MAX = 120;
    private static final int DESCRIPCION_MAX = 500;
    private static final int PRECIO_SCALE = 2;
    private static final BigDecimal PRECIO_MAX = new BigDecimal("99999999.99");
    private static final int STOCK_MAX = 2147483647;

    private TiendaService tiendaService;
    private Producto producto;

    @Override
    public String execute() {
        if (!isProductoValido()) {
            addActionError(getText("error.producto.invalid"));
            return INPUT;
        }

        tiendaService.crearProducto(producto);
        return SUCCESS;
    }

    @Override
    public void validate() {
        if (producto == null) {
            addActionError(getText("error.producto.invalid"));
            return;
        }

        String nombre = producto.getNombre();
        if (nombre != null) {
            nombre = nombre.trim();
            producto.setNombre(nombre);
        }
        if (nombre == null || nombre.isEmpty()) {
            addFieldError("producto.nombre", getText("error.producto.nombre.required"));
        } else if (nombre.length() < NOMBRE_MIN || nombre.length() > NOMBRE_MAX) {
            addFieldError("producto.nombre", getText("error.producto.nombre.length"));
        }

        String descripcion = producto.getDescripcion();
        if (descripcion != null) {
            descripcion = descripcion.trim();
            producto.setDescripcion(descripcion);
            if (descripcion.length() > DESCRIPCION_MAX) {
                addFieldError("producto.descripcion", getText("error.producto.descripcion.length"));
            }
        }

        Double precio = producto.getPrecio();
        if (precio == null) {
            addFieldError("producto.precio", getText("error.producto.precio.required"));
        } else if (!Double.isFinite(precio)) {
            addFieldError("producto.precio", getText("error.producto.precio.invalid"));
        } else {
            if (precio < 0) {
                addFieldError("producto.precio", getText("error.producto.precio.min"));
            }
            BigDecimal precioDecimal = BigDecimal.valueOf(precio);
            if (precioDecimal.abs().compareTo(PRECIO_MAX) > 0) {
                addFieldError("producto.precio", getText("error.producto.precio.max"));
            }
            if (normalizeScale(precioDecimal) > PRECIO_SCALE) {
                addFieldError("producto.precio", getText("error.producto.precio.scale"));
            }
        }

        Integer stock = producto.getStock();
        if (stock == null) {
            addFieldError("producto.stock", getText("error.producto.stock.required"));
        } else {
            if (stock < 0) {
                addFieldError("producto.stock", getText("error.producto.stock.min"));
            } else if (stock > STOCK_MAX) {
                addFieldError("producto.stock", getText("error.producto.stock.max"));
            }
        }
    }

    @StrutsParameter(depth = 1)
    public Producto getProducto() {
        return producto;
    }

    @StrutsParameter(depth = 1)
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Inject("tiendaService")
    public void setTiendaService(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    private boolean isProductoValido() {
        if (producto == null) {
            return false;
        }
        String nombre = producto.getNombre();
        if (nombre != null) {
            nombre = nombre.trim();
            producto.setNombre(nombre);
        }
        if (nombre == null || nombre.isEmpty()) {
            return false;
        }
        if (nombre.length() < NOMBRE_MIN || nombre.length() > NOMBRE_MAX) {
            return false;
        }

        String descripcion = producto.getDescripcion();
        if (descripcion != null) {
            descripcion = descripcion.trim();
            producto.setDescripcion(descripcion);
            if (descripcion.length() > DESCRIPCION_MAX) {
                return false;
            }
        }

        if (!isPrecioValido(producto.getPrecio())) {
            return false;
        }
        if (!isStockValido(producto.getStock())) {
            return false;
        }
        return true;
    }

    private boolean isPrecioValido(Double precio) {
        if (precio == null || !Double.isFinite(precio) || precio < 0) {
            return false;
        }
        BigDecimal precioDecimal = BigDecimal.valueOf(precio);
        if (precioDecimal.abs().compareTo(PRECIO_MAX) > 0) {
            return false;
        }
        return normalizeScale(precioDecimal) <= PRECIO_SCALE;
    }

    private boolean isStockValido(Integer stock) {
        return stock != null && stock >= 0 && stock <= STOCK_MAX;
    }

    private int normalizeScale(BigDecimal value) {
        return Math.max(value.scale(), 0);
    }
}
