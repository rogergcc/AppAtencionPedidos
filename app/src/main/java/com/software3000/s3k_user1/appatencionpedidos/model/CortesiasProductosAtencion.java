package com.software3000.s3k_user1.appatencionpedidos.model;

import java.util.List;

public class CortesiasProductosAtencion {
    private List<CortesiaProductos> cortesiaProductosList;
    private CortesiaAtencion cortesiaAtencion;
    public List<CortesiaCombo> cortesiaCombosList;

    public List<CortesiaCombo> getCortesiaCombosList() {
        return cortesiaCombosList;
    }

    public void setCortesiaCombosList(List<CortesiaCombo> cortesiaCombosList) {
        this.cortesiaCombosList = cortesiaCombosList;
    }

    public CortesiasProductosAtencion() {

    }

    public List<CortesiaProductos> getCortesiaProductosList() {
        return cortesiaProductosList;
    }

    public void setCortesiaProductosList(List<CortesiaProductos> cortesiaProductosList) {
        this.cortesiaProductosList = cortesiaProductosList;
    }

    public CortesiaAtencion getCortesiaAtencion() {
        return cortesiaAtencion;
    }

    public void setCortesiaAtencion(CortesiaAtencion cortesiaAtencion) {
        this.cortesiaAtencion = cortesiaAtencion;
    }
}
