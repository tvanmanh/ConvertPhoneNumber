package com.project.tranvanmanh.convertphonenumber.model;

import android.content.ContentValues;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Contact {
    private String id;
    private String name;
    private ArrayList<Phone> phoneNumbers;

    public Contact(){}



    public String getId() {
        return id;
    }

    public Contact(String id, String name, ArrayList<Phone> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {

        String nums = "";
        for (int i = 0; i < phoneNumbers.size() ; i++) {
           nums =  nums + "\n num: " +  phoneNumbers.get(i).toString();
        }
        return "\n id" + id + "\n name" + name + nums + "\n";
    }
}
