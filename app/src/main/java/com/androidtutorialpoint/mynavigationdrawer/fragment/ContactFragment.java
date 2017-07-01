package com.androidtutorialpoint.mynavigationdrawer.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.androidtutorialpoint.mynavigationdrawer.ContactsModel;
import com.androidtutorialpoint.mynavigationdrawer.MyArrayAdapter;
import com.androidtutorialpoint.mynavigationdrawer.MyDatabaseHelper;
import com.androidtutorialpoint.mynavigationdrawer.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private EditText inputSearch;
    private ListView listView;
    private List<ContactsModel> listContacts = new ArrayList<ContactsModel>();
    private MyArrayAdapter adapter;
    private ContextMenu myContextMenu;
    private EditText edt_name;
    private EditText edt_phoneNumber;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_DELETE = 444;
    private static final int MY_REQUEST_CODE = 1000;
    private List<ContactsModel> list;
    private MyDatabaseHelper databaseHelperb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ////// create values of list view
        databaseHelperb = new MyDatabaseHelper(getActivity().getApplicationContext());
        databaseHelperb.createDefaultContactsIfNeed();
        list = databaseHelperb.getAllContacts();
        Log.d("nguyendangquang0", list.size() + "");
        //// create adapter
        adapter = new MyArrayAdapter(getActivity().getApplicationContext(), this.listContacts);
        this.listContacts.addAll(list);
        //// init
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        listView = (ListView) view.findViewById(R.id.listData);
        // set adapter cho listview
        listView.setAdapter(adapter);
        // Sing Context menu up ListView.
        registerForContextMenu(this.listView);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEnterContactInfo dialogFragmentBrithday = new DialogEnterContactInfo();
                Bundle bundle = new Bundle();
                ContactsModel contactsModel = new ContactsModel(9999, "", "");
                bundle.putSerializable("contacts", contactsModel);
                dialogFragmentBrithday.setArguments(bundle);
                dialogFragmentBrithday.show(getActivity().getSupportFragmentManager(), "Dialog");
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ContactFragment.this.adapter.getFilter().filter(cs);


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        return view;

    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle(getString(R.string.headerTitle));
        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_EDIT, 0, getString(R.string.editTitle));
        menu.add(0, MENU_ITEM_DELETE, 1, getString(R.string.deleteTitle));
    }

    private ContactsModel tmp;

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ContactsModel selectedContact = (ContactsModel) this.listView.getItemAtPosition(info.position);
        tmp = selectedContact;
        if (item.getItemId() == MENU_ITEM_EDIT) {
            DialogEnterContactInfo dialogFragmentBrithday = new DialogEnterContactInfo();
            Bundle bundle = new Bundle();
            bundle.putSerializable("contacts", selectedContact);
            dialogFragmentBrithday.setArguments(bundle);
            dialogFragmentBrithday.show(getActivity().getSupportFragmentManager(), "Dialog");
        } else if (item.getItemId() == MENU_ITEM_DELETE) {
            showAlertDialog();
        } else {
            return false;
        }
        return true;
    }

    // Người dùng đồng ý xóa một Contact
    private void deleteContacst(ContactsModel contactsModel) {
        MyDatabaseHelper db = new MyDatabaseHelper(getActivity().getApplicationContext());
        db.deleteContact(contactsModel);
        list = databaseHelperb.getAllContacts();
        adapter = new MyArrayAdapter(getActivity().getApplicationContext(), this.listContacts);
        listView.setAdapter(adapter);

//        this.listContacts.remove(contactsModel);
//
//        // Refresh ListView.
        adapter.notifyDataSetChanged();
        listView.requestFocus();
    }

    public void receiverAction(int action) {
        MyDatabaseHelper databaseHelperb = new MyDatabaseHelper(getActivity().getApplicationContext());
        databaseHelperb.createDefaultContactsIfNeed();
        List<ContactsModel> list = databaseHelperb.getAllContacts();
        adapter = new MyArrayAdapter(getActivity().getApplicationContext(), list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void showAlertDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.deleteTitle));
        builder.setMessage(getString(R.string.deleteMessage));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton(getString(R.string.deleteTitle), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Delete", "true");
                deleteContacst(tmp);
                dialogInterface.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public interface updateData123 {
        public void updateData(int action);
    }

}
