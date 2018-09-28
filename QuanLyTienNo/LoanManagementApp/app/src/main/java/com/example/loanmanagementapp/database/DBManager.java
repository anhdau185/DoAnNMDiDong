package com.example.loanmanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loanmanagementapp.model.Debtor;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "loan_manager";
    private static final String TABLE_NAME = "debtors";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String DEBT = "debt";
    private static final String INTEREST_RATE = "interest_rate";
    private static final String DATE = "date";
    private static final String INTEREST = "interest";
    private static final int VERSION = 1;
    private Context context;

    private String SQLquery ="CREATE TABLE "+TABLE_NAME+" ("+
            ID +" integer primary key, "+
            NAME +" TEXT, "+
            PHONE +" TEXT, "+
            ADDRESS +" TEXT, "+
            DEBT +" INT, "+
            INTEREST_RATE +" DOUBLE, "+
            DATE +" DATE, "+
            INTEREST +" DOUBLE)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addDebtor(Debtor debtor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, debtor.getmName());
        values.put(PHONE,debtor.getmPhone());
        values.put(ADDRESS, debtor.getmAddress());
        values.put(DEBT, debtor.getmDebt());
        values.put(INTEREST_RATE, debtor.getmInterest_rate());
        values.put(DATE, debtor.getmDate());
        values.put(INTEREST, debtor.getmInterest());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Debtor> getAllDebtor(){
        List<Debtor> ListDebtor = new ArrayList<>();

        String selectQuery = "SELECT NAME, PHONE, DEBT FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToNext()){
            do{
                Debtor debtor = new Debtor();
//                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
//                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
//                debtor.setmInterest_rate(cursor.getDouble(5));
//                debtor.setmDate(cursor.getString(6));
//                debtor.setmInterest(cursor.getDouble(7));
                ListDebtor.add(debtor);
            }while(cursor.moveToNext());
        }
        return ListDebtor;
    }
}
