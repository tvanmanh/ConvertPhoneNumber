package com.project.tranvanmanh.convertphonenumber.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.tranvanmanh.convertphonenumber.adapter.ContactAdapter;
import com.project.tranvanmanh.convertphonenumber.R;
import com.project.tranvanmanh.convertphonenumber.manager.ConvertFacade;
import com.project.tranvanmanh.convertphonenumber.manager.DeleteContacts;
import com.project.tranvanmanh.convertphonenumber.model.Contact;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private final int REQUEST_CONTACTS = 123;
    private RecyclerView mRecyclerview;
    private ArrayList<Contact> contactList;
    private ContactAdapter adapter;
    private Button btnConvert;
    private Button mbtnDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerview = (RecyclerView) findViewById(R.id.main_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        contactList = new ArrayList<>();

       //handle with click on button convert
        btnConvert = (Button) findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS)== PackageManager.PERMISSION_GRANTED){
                    notifyDialogConvert();
                }else {
                   requestPermissionContacts();
                }
            }
        });

        //handle with click on button delete
        mbtnDelete = (Button) findViewById(R.id.btnDelete);
        mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               notifyDialogDelete();
            }
        });

    }


    private void notifyDialogDelete(){
        //dilalog to notify delete contacts
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("Are you sure to Delete all old phone number?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactList = DeleteContacts.getInstance().Delete(MainActivity.this);
                adapter = new ContactAdapter(contactList);
                mRecyclerview.setAdapter(adapter);
            }
        });
        dialog.show();
    }

    private void notifyDialogConvert(){
        //Dialog to notify convert contacts
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want To keep old phone number?");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactList = ConvertFacade.getInstance().Convert(MainActivity.this, true);
                adapter = new ContactAdapter(contactList);
                mRecyclerview.setAdapter(adapter);
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactList = ConvertFacade.getInstance().Convert(MainActivity.this, false);
                adapter = new ContactAdapter(contactList);
                mRecyclerview.setAdapter(adapter);
            }
        });
        builder.show();
    }

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void requestPermissionContacts() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CONTACTS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "thanks you! now try again to convert", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}