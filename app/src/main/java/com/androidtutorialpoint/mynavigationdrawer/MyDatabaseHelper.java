package com.androidtutorialpoint.mynavigationdrawer;

/**
 * Created by Admin on 6/20/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "SaveYourSelf";


    // Tên bảng: Contacts
    private static final String TABLE_CONTACTS = "Contacts";

     static final String COLUMN_CONTACTS_ID = "Id";
    private static final String COLUMN_CONTACTS_NAME ="Name";
    private static final String COLUMN_CONTACTS_PHONE = "PhoneNumber";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_CONTACTS + "("
                + COLUMN_CONTACTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONTACTS_NAME + " TEXT,"
                + COLUMN_CONTACTS_PHONE + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);


        // Và tạo lại.
        onCreate(db);
    }

    // Nếu trong bảng Note chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.
    public void createDefaultContactsIfNeed()  {
        int count = this.getContacsCount();
        if(count == 0 ) {
            ContactsModel ly = new ContactsModel("Khánh Ly", "01644546355");
           this.addContact(ly);
        }
    }


    public void addContact(ContactsModel contactsModel) {
       Log.i(TAG, "MyDatabaseHelper.addContact ... " + contactsModel.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACTS_NAME, contactsModel.getName());
        values.put(COLUMN_CONTACTS_PHONE, contactsModel.getNumberPhone());

        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_CONTACTS, null, values);
        // Đóng kết nối database.
        db.close();
    }


//    public ContactsModel getNote(int id) {
//        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { COLUMN_CONTACTS_ID,
//                        COLUMN_CONTACTS_NAME, COLUMN_CONTACTS_PHONE }, COLUMN_CONTACTS_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        ContactsModel contactsModel = new ContactsModel(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return contactsmodel
//        return contactsModel;
//    }


    public List<ContactsModel> getAllContacts() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<ContactsModel> listContacts = new ArrayList<ContactsModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                ContactsModel contactsModel = new ContactsModel();
                contactsModel.setId(Integer.parseInt(cursor.getString(0)));
                contactsModel.setName(cursor.getString(1));
                contactsModel.setNumberPhone(cursor.getString(2));

                // Thêm vào danh sách.
                listContacts.add(contactsModel);
            } while (cursor.moveToNext());
        }

        // return note list
        return listContacts;
    }

    public int getContacsCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }



    public int updateContacts(ContactsModel contactsModel) {
        Log.i(TAG, "MyDatabaseHelper.update ... "  + contactsModel.getId());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACTS_NAME, contactsModel.getName());
        values.put(COLUMN_CONTACTS_PHONE, contactsModel.getNumberPhone());

        // updating row
        return db.update(TABLE_CONTACTS, values, COLUMN_CONTACTS_ID + " = ?",
                new String[]{String.valueOf(contactsModel.getId())});
    }

    public void deleteContact(ContactsModel contactsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_CONTACTS_ID + " = ?",
                new String[] { String.valueOf(contactsModel.getId()) });
        db.close();
    }

}
