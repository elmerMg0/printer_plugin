    package com.monolitica.crud.dto;

    public class OrderDetailDTO {

        private Integer id;
        private Integer cantidad;
        private String estado;
        private String nombre;
        private String tipo;
        private String precio_venta;
        private String precio_compra;
        private Integer producto_id;

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getPrecio_venta() {
            return precio_venta;
        }

        public void setPrecio_venta(String precio_venta) {
            this.precio_venta = precio_venta;
        }

        public String getPrecio_compra() {
            return precio_compra;    
        }


        public void setPrecio_compra(String precio_compra) {
            this.precio_compra = precio_compra;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProducto_id(){
            return producto_id;
        }

        public void setProducto_id(Integer producto_id){
            this.producto_id = producto_id;
        }
    }
