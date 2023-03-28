package com.code.todolistapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountHandle extends SQLiteOpenHelper {
    static String dbname = "account2.db";
    static String account_CREATE = "CREATE TABLE IF NOT EXISTS Account(username TEXT NOT NULL, email TEXT NOT NULL, password TEXT NOT NULL)";
    static String account_DROP = "DROP TABLE IF EXISTS Account";
    static String TABLE_NAME = "Account";
    static int version = 1;

    public AccountHandle(Context context){
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(this.account_CREATE);
        }catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(this.account_DROP);
        onCreate(sqLiteDatabase);
    }

    public boolean insertAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", account.getUsername());
        cv.put("email", account.getEmail());
        cv.put("password", account.getPassword());

        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        if(result == -1)
            return false;
        return true;
    }

    public Cursor findAccount(String user) {
        Cursor c;
        SQLiteDatabase db = this.getWritableDatabase();
        String []cols = new String[3];
        cols[0] = "username";
        cols[1] = "password";
        cols[2] = "email";

        String clause = "username=?";
        String []para = new String[1];
        para[0] = user;
        c = db.query(TABLE_NAME, cols, clause, para,null, null, null);
        return c;
    }
}
