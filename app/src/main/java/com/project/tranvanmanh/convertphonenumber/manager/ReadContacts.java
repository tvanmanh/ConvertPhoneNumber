package com.project.tranvanmanh.convertphonenumber.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;

import java.util.ArrayList;

public class ReadContacts {

    private static ReadContacts instance;


    private ReadContacts(){ }

    public static ReadContacts getInstance() {

        if (instance == null) {
            instance = new ReadContacts();
        }
        return instance;
    }


    public ArrayList<Contact> readContacts(Context context) {

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        String phoneNumber = null;
        String type = null;
        String label = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
        String LABEL = ContactsContract.CommonDataKinds.Phone._ID;
        ContentResolver contentResolver = context.getContentResolver();

        try {
            Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

            // lap lai moi lien lac trong dien thoai
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                    ArrayList<Phone> nums = new ArrayList<>();

                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                    if (hasPhoneNumber > 0) {
                        //Doc so dien thoai liet ke cung so lien lac
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
                                new String[]{contact_id}, null);

                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            type = phoneCursor.getString(phoneCursor.getColumnIndex(TYPE));
                            label = phoneCursor.getString(phoneCursor.getColumnIndex(LABEL));
                            nums.add(new Phone(phoneNumber, type, label));
                        }
                        phoneCursor.close();
                    }
                    // them vao array list
                    contactArrayList.add(new Contact(contact_id,name, nums));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return contactArrayList;
    }

}
