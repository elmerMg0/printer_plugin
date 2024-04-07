package com.monolitica.crud.dto;
import java.util.List;
public class PrintDTO {
    private Integer nro_pedido;
    private String nro_mesa;
    private String cliente;
    private String note;
    private String printerName;
    private String place;
    private String username;
    private String cantidad_total;

    private List<OrderDetailDTO> orderDetails;


    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getNro_pedido() {
        return nro_pedido;
    }

    public void setNro_pedido(Integer nro_pedido) {
        this.nro_pedido = nro_pedido;
    }

    public String getNro_mesa() {
        return nro_mesa;
    }

    public void setNro_mesa(String nro_mesa) {
        this.nro_mesa = nro_mesa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(String cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    // Otros métodos

}
