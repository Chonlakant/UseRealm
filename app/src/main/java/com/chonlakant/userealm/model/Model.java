package com.chonlakant.userealm.model;

import io.realm.RealmObject;

/**
 * Created by Bas on 7/28/2017 AD.
 */

public class Model  extends RealmObject{


    private String nama;
    private String foto;

    public Model() {
    }

    public Model(String nama, String foto) {
        this.nama = nama;
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
