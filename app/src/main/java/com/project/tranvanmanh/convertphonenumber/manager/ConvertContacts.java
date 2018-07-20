package com.project.tranvanmanh.convertphonenumber.manager;

import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;
import com.project.tranvanmanh.convertphonenumber.model.Prefix;

import java.util.ArrayList;

public class ConvertContacts  {

    private ArrayList<Prefix> mPrefixList;
    public ConvertContacts(){
        initNumbers();
    }
    private void initNumbers(){
        mPrefixList = new ArrayList<>();
        mPrefixList.add(new Prefix("0120","070"));
        mPrefixList.add(new Prefix("0121","079"));
        mPrefixList.add(new Prefix("0122","077"));
        mPrefixList.add(new Prefix("0126","076"));
        mPrefixList.add(new Prefix("0128","078"));
        mPrefixList.add(new Prefix("0123","083"));
        mPrefixList.add(new Prefix("0124","084"));
        mPrefixList.add(new Prefix("0125", "085"));
        mPrefixList.add(new Prefix("0127","081"));
        mPrefixList.add(new Prefix("0129","082"));
        mPrefixList.add(new Prefix("0162","032"));
        mPrefixList.add(new Prefix("0163","033"));
        mPrefixList.add(new Prefix("0164","034"));
        mPrefixList.add(new Prefix("0165","035"));
        mPrefixList.add(new Prefix("0166","036"));
        mPrefixList.add(new Prefix("0167","037"));
        mPrefixList.add(new Prefix("0168","038"));
        mPrefixList.add(new Prefix("0169","039"));
        mPrefixList.add(new Prefix("0186","056"));
        mPrefixList.add(new Prefix("0188","058"));
        mPrefixList.add(new Prefix("0199","059"));
    }
    public ArrayList<Contact> convertContacts(ArrayList<Contact> filterList){
        ArrayList<Contact> convertList = new ArrayList<>();
        String oldPhoneNumber = null;
        String newPhoneNumber = null;
        try {
            ArrayList<Phone> phoneNumbers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++){
                phoneNumbers = filterList.get(i).getPhoneNumbers();
                for (int j = 0; j<phoneNumbers.size(); j++) {
                    oldPhoneNumber = phoneNumbers.get(j).getOldPhoneNumber();
                    String numbers = oldPhoneNumber.substring(0, 4);
                    for (int k = 0; k < mPrefixList.size(); k++) {
                        if (numbers.equals(mPrefixList.get(k).getmOldPrefix())) {
                            newPhoneNumber = oldPhoneNumber.replace(numbers, mPrefixList.get(k).getmNewPrefix());
                            newPhoneNumber = formatPhoneNumber(newPhoneNumber);
                            oldPhoneNumber = formatOldPhoneNUmber(oldPhoneNumber);
                            filterList.get(i).getPhoneNumbers().get(j).setOldPhoneNumber(oldPhoneNumber);
                            filterList.get(i).getPhoneNumbers().get(j).setNewPhoneNumber(newPhoneNumber);
                        }
                    }
                }
            }
            convertList = filterList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertList;
    }

    private String formatOldPhoneNUmber(String s){
        s = s.substring(0,4) + " " + s.substring(4,7) + " " + s.substring(7,11);
        return s;
    }
    private String formatPhoneNumber(String s){
        s = s.substring(0,3) + " " + s.substring(3,6) + " " + s.substring(6,10);
        return s;
    }


}
