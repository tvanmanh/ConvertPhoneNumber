package com.project.tranvanmanh.convertphonenumber.manager;

import android.provider.ContactsContract;

public class ConvertTypePhoneNumber {

    public ConvertTypePhoneNumber(){

    }
    public static String ConvertType(String type){
        switch (type){
            case "1": return "HOME";
            case "2": return "MOBILE";
            case "3" : return "WORK";
            default: return "OTHER";
        }
    }
}
