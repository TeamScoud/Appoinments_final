package application.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo1.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "number";
    public static final String CONTACTS_COLUMN_EMAIL = "subject";
    public static final String CONTACTS_COLUMN_STREET = "date";
    public static final String CONTACTS_COLUMN_CITY = "mode";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(
                    "create table contacts " +
                            "(id integer primary key, number TEXT,subject TEXT,date VARCHAR(255), mode TEXT)" //
            );
        }catch (Exception e){
            System.out.println("Database created successfully" +e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertContact(String number, String subject, String date, String mode) { //
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("subject", subject);
        contentValues.put("date", date);
        contentValues.put("mode", mode);

        long newRowId = db.insert("contacts", null, contentValues);
        return newRowId;

    }

    public int getcount1() {
        String query = "SELECT * FROM " + CONTACTS_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();

    }


    public Integer deleteInfo1(String id){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(CONTACTS_TABLE_NAME,"id = ?",new String[] { id });
    }

    public boolean updateInfo1(String id,String number,String subject,String date,String mode){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACTS_COLUMN_ID, id);
        values.put(CONTACTS_COLUMN_NAME, number);
        values.put(CONTACTS_COLUMN_EMAIL, subject);
        values.put(CONTACTS_COLUMN_STREET, date);
        values.put(CONTACTS_COLUMN_CITY, mode);
        sqLiteDatabase.update(CONTACTS_TABLE_NAME, values, "id =?",new String[] { id });

        return true;
    }

    //Get user details according to the name



  public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+CONTACTS_TABLE_NAME,null);
        return res;
  }

}
