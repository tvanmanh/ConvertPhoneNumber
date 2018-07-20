package com.project.tranvanmanh.convertphonenumber.manager;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.project.tranvanmanh.convertphonenumber.interfaces.ConvertState;
import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;
import com.project.tranvanmanh.convertphonenumber.view.MainActivity;

import java.util.ArrayList;

public class WriteContacts {

    private static WriteContacts instance;


    private WriteContacts(){}

    public static WriteContacts getInstance(){
        if(instance == null){
            instance = new WriteContacts();
        }
        return instance;
    }

    public void writeContacts(Context context, ArrayList<Contact> convertList, Boolean update){
        ContentValues values = new ContentValues();
        values.clear();
        for (int i = 0; i < convertList.size(); i++) {
            ArrayList<Phone> phoneNumbers = convertList.get(i).getPhoneNumbers();
            for (int j = 0; j < phoneNumbers.size(); j++) {
                if(phoneNumbers.get(j).getNewPhoneNumber()!= null) {
                    if (update){
                        updateContactList(context, convertList.get(i).getId(), phoneNumbers.get(j).getNewPhoneNumber(), phoneNumbers.get(j).getType(), phoneNumbers.get(j).getLabel());
                    }else {
                        insertContacts(context, phoneNumbers.get(j).getNewPhoneNumber(), phoneNumbers.get(j).getType(),convertList.get(i).getId(), phoneNumbers.get(j).getLabel());
                    }
                }
            }
        }

    }

    public boolean updateContactList(Context context,String contactId,String newPhoneNumber, String type, String label){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone._ID) + " = ?";
        String[] params = new String[] {contactId,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                type,
                label};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(context, "update new number successfully!", Toast.LENGTH_SHORT).show();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "failed!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean addContactList(Context context,int id, String contactName, String contactNumber){

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName);
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        ops.add(builder.build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    private void insertContacts(Context context, String phoneNUmber, String type, String contactId, String label){
        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValue(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, contactId)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, type)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNUmber)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(context, "insert new number successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "failed!", Toast.LENGTH_LONG);
        }
    }


}
