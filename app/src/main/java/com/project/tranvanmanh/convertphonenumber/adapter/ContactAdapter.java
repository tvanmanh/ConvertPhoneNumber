package com.project.tranvanmanh.convertphonenumber.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.tranvanmanh.convertphonenumber.R;
import com.project.tranvanmanh.convertphonenumber.manager.ConvertTypePhoneNumber;
import com.project.tranvanmanh.convertphonenumber.model.Contact;
import com.project.tranvanmanh.convertphonenumber.model.Phone;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>  {

    ArrayList<Contact> contactArrayList;



    public ContactAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<Phone> phoneNumbers = contactArrayList.get(position).getPhoneNumbers();
        Contact contact = contactArrayList.get(position);
        String phoneNumber = "";
        for (int i = 0; i < phoneNumbers.size() ; i++) {

            phoneNumber = phoneNumber + "\n " + ConvertTypePhoneNumber.ConvertType( phoneNumbers.get(i).getType()) + ": "+
                                              phoneNumbers.get(i).getOldPhoneNumber()+""+"->" +
                                              phoneNumbers.get(i).getNewPhoneNumber()+"";
        }
        if(phoneNumbers.size()==0) {
            holder.tvDisplay.setText("no number to convert");
        }else {
            holder.tvDisplay.setText("\n Name: " + contact.getName() + phoneNumber);
        }
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisplay;
        public ViewHolder(View itemView) {
            super(itemView);
            tvDisplay = (TextView) itemView.findViewById(R.id.custom_tvDisplay);
        }
    }
}
