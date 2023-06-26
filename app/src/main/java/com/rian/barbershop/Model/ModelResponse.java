package com.rian.barbershop.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelBarbershop> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelBarbershop> getData() {
        return data;
    }
}
