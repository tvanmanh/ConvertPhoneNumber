package com.project.tranvanmanh.convertphonenumber.manager;

import android.content.Context;

import com.project.tranvanmanh.convertphonenumber.model.Contact;

import java.util.ArrayList;

public class ConvertFacade{

    private static ConvertFacade instance;

    private ReadContacts mReadContacts;
    private FilterContacts mFilterContacts;
    private ConvertContacts mConvertContacts;
    private WriteContacts mWriteContacts;

    private ArrayList<Contact> mconvertContactList;


    public static ConvertFacade getInstance(){
        if(instance == null){
            instance = new ConvertFacade();
        }
        return instance;
    }

    private ConvertFacade (){
        mReadContacts = ReadContacts.getInstance();
        mFilterContacts = new FilterContacts();
        mConvertContacts = new ConvertContacts();
        mWriteContacts = WriteContacts.getInstance();
        mconvertContactList  = new ArrayList<>();
    }

    public ArrayList<Contact> Convert(Context context, Boolean update){
            mconvertContactList = mReadContacts.readContacts(context);
            mconvertContactList = mFilterContacts.filterContacts(mconvertContactList);
            mconvertContactList = mConvertContacts.convertContacts(mconvertContactList);
            mWriteContacts.writeContacts(context, mconvertContactList, update);
           return mconvertContactList;
    }
}
