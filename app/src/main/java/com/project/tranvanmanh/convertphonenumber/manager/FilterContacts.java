package com.project.tranvanmanh.convertphonenumber.manager;

import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;

import java.util.ArrayList;

public class FilterContacts{

    public FilterContacts(){}

    public ArrayList<Contact> filterContacts(ArrayList<Contact> contactArrayList){
        ArrayList<Contact> filterList = new ArrayList<>();
        ArrayList<Phone> filterPhoneNumbers = null;
        ArrayList<Phone> phoneNumbers = new ArrayList<>();
        String phoneNumber = null;
        try {
            for (int i = 0; i < contactArrayList.size() ; i++) {
                phoneNumbers = contactArrayList.get(i).getPhoneNumbers();
                filterPhoneNumbers = new ArrayList<>();
                for (int j = 0; j < phoneNumbers.size() ; j++) {
                    phoneNumber = phoneNumbers.get(j).getOldPhoneNumber();
                    if(phoneNumber != null){
                        // remove space on phone number
                        phoneNumber = removeSpace(phoneNumber);
                        // substitute country code +84 to 0
                        phoneNumber = subCountryCode(phoneNumber);
                        if (phoneNumber.length() == 11) {
                            filterPhoneNumbers.add(new Phone(
                                    phoneNumber,
                                    phoneNumbers.get(j).getType(),
                                    phoneNumbers.get(j).getLabel()));
                        }
                    }
                }
                if(filterPhoneNumbers.size() >= 1) {
                    filterList.add(new Contact(contactArrayList.get(i).getId(),
                            contactArrayList.get(i).getName(),
                            filterPhoneNumbers));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return filterList;
    }
    private String removeSpace(String s){
        s = s.trim();
        s = s.replaceAll("\\s+","");
        return s;
    }

    private String subCountryCode(String s){
        String phoneCode = "+84";
        if (s.indexOf(phoneCode) == 0) {
            s = s.replace("+84", "0");
        }
        return s;
    }
}
