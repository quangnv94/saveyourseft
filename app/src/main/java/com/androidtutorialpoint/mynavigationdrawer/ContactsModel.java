package com.androidtutorialpoint.mynavigationdrawer;

import java.io.Serializable;

/**
 * Created by Admin on 6/20/2017.
 */

public class ContactsModel implements Serializable {
    private int id;
    private String name;
    private String numberPhone;


    public ContactsModel(int id, String name, String numberPhone) {
        this.id = id;
        this.name = name;
        this.numberPhone = numberPhone;
    }

    public ContactsModel(String name, String numberPhone) {
        this.name = name;
        this.numberPhone = numberPhone;
    }

    public ContactsModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
