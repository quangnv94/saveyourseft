package com.androidtutorialpoint.mynavigationdrawer.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.androidtutorialpoint.mynavigationdrawer.ContactsModel;
import com.androidtutorialpoint.mynavigationdrawer.MainActivity;
import com.androidtutorialpoint.mynavigationdrawer.MyDatabaseHelper;
import com.androidtutorialpoint.mynavigationdrawer.R;

/**
 * Created by Admin on 6/22/2017.
 */

public class DialogEnterContactInfo extends DialogFragment {
    private MainActivity currentActivity;
    private AlertDialog dialog;
    private EditText edt_name;
    private EditText edt_phone;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        final ContactsModel contactsModel = (ContactsModel) bundle.getSerializable("contacts");
        View view = LayoutInflater.from(currentActivity).inflate(R.layout.dialog_enter_info_contact, null);
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);
        edt_name.setText(contactsModel.getName());
        edt_phone.setText(contactsModel.getNumberPhone());
        dialog = new AlertDialog.Builder(currentActivity).setView(view)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (contactsModel.getId() == 9999) {
                            Log.d("cochayhongday", "true");
                            createContact(new ContactsModel(99999, edt_name.getText().toString().trim(), edt_phone.getText().toString().trim()));
                        }else {
                            updateContact(new ContactsModel(contactsModel.getId(), edt_name.getText().toString().trim(),
                                    edt_phone.getText().toString().trim()));
                        }
                    }
                }).create();
        return dialog;
    }

    // Update Contacts
    private void updateContact(ContactsModel contactsModel) {
        MyDatabaseHelper db = new MyDatabaseHelper(getActivity().getApplicationContext());
        db.updateContacts(contactsModel);

        ((MainActivity) currentActivity).updateData(1);
    }
    // Create new Contact
    private void createContact(ContactsModel contactsModel){
        MyDatabaseHelper db = new MyDatabaseHelper(getActivity().getApplicationContext());
        db.addContact(contactsModel);
        Log.d("caigidayday","true");
        ((MainActivity) currentActivity).updateData(2);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            updateData mData = (updateData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    public interface updateData {
        public void updateData(int action);
    }
}
