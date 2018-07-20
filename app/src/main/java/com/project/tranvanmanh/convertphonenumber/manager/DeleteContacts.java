package com.project.tranvanmanh.convertphonenumber.manager;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.provider.ContactsContract;

import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;

import java.util.ArrayList;

public class DeleteContacts {
    private FilterContacts mFilterContacts;
    private ArrayList<Contact> mContactList;
    private static DeleteContacts instance;

    private DeleteContacts(){}
    public static DeleteContacts getInstance(){
        if(instance == null){
            instance = new DeleteContacts();
        }
        return instance;
    }
    public ArrayList<Contact> Delete(Context context){
        mContactList = new ArrayList<>();
        mFilterContacts = new FilterContacts();
        mContactList = ReadContacts.getInstance().readContacts(context);
            mContactList = mFilterContacts.filterContacts(mContactList);
            for (int i = 0; i < mContactList.size(); i++) {
                ArrayList<Phone> phoneNumbers = mContactList.get(i).getPhoneNumbers();
                for (int j = 0; j < phoneNumbers.size(); j++) {
                    if (phoneNumbers.get(j).getOldPhoneNumber() != null) {
                        deleteOldPhoneNUmber(context, mContactList.get(i).getId(),
                                mContactList.get(i).getPhoneNumbers().get(j).getType(),
                                mContactList.get(i).getPhoneNumbers().get(j).getLabel());
                    }
                }
            }
        return mContactList;
    }

    private void deleteOldPhoneNUmber(Context context, String contactId, String type, String label){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone._ID) + " = ?";

        String[] params = new String[] {contactId,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                type,
                label};
        ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
