package com.project.tranvanmanh.convertphonenumber.model;

public class Phone {

    private String newPhoneNumber;
    private String oldPhoneNumber;
    private String type;
    private String label;
    public Phone(){}

    public Phone(String oldPhoneNumber, String type, String label) {
        this.oldPhoneNumber = oldPhoneNumber;
        this.type = type;
        this.label = label;
    }
    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }

    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
