package com.example.loanmanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.loanmanagementapp.ListActivity;
import com.example.loanmanagementapp.model.Debtor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class DBManager extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    private static final String DATABASE_NAME = "loan_manager";
    private static final String TABLE_NAME = "debtors";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";
    private static final String DEBT = "debt";
    private static final String INTEREST_RATE = "interest_rate";
    private static final String DATE = "date";
    private static final String INTEREST_DATE = "interest_date";
    private static final String DESCRIPTION = "description";
    private static final int VERSION = 1;
    private Context context;

    private String SQLquery ="CREATE TABLE "+TABLE_NAME+" ("+ ID + " integer primary key, "+
            NAME + " TEXT, "+
            PHONE + " TEXT, "+
            ADDRESS + " TEXT, "+
            DEBT + " integer, "+
            INTEREST_RATE +" DOUBLE, "+
            DATE + " TEXT, "+
            INTEREST_DATE + " TEXT, " +
            DESCRIPTION + " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "DBManager:");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLquery);
        Log.d(TAG, "on Create:");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addDebtor(Debtor debtor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,debtor.getmName());
        values.put(PHONE,debtor.getmPhone());
        values.put(ADDRESS,debtor.getmAddress());
        values.put(DEBT,debtor.getmDebt());
        values.put(INTEREST_RATE,debtor.getmInterest_rate());
        values.put(DATE,debtor.getmDate());
        values.put(INTEREST_DATE,debtor.getmInterest_date());
        values.put(DESCRIPTION,debtor.getmDescription());
        db.insert(TABLE_NAME,null, values);
        db.close();
        Log.d(TAG, "add Debtor");
    }

    public List<Debtor> getAllDebtorNameASC(){
        List<Debtor> ListDebtor = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + NAME + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Debtor debtor = new Debtor();
                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
                debtor.setmInterest_rate(cursor.getDouble(5));
                debtor.setmDate(cursor.getString(6));
                debtor.setmInterest_date(cursor.getString(7));
                debtor.setmDescription(cursor.getString(8));
                ListDebtor.add(debtor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDebtor;
    }

    public List<Debtor> getAllDebtorNameDESC(){
        List<Debtor> ListDebtor = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + NAME + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Debtor debtor = new Debtor();
                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
                debtor.setmInterest_rate(cursor.getDouble(5));
                debtor.setmDate(cursor.getString(6));
                debtor.setmInterest_date(cursor.getString(7));
                debtor.setmDescription(cursor.getString(8));
                ListDebtor.add(debtor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDebtor;
    }
    public List<Debtor> getAllDebtorDebtASC(){
        List<Debtor> ListDebtor = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DEBT + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Debtor debtor = new Debtor();
                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
                debtor.setmInterest_rate(cursor.getDouble(5));
                debtor.setmDate(cursor.getString(6));
                debtor.setmInterest_date(cursor.getString(7));
                debtor.setmDescription(cursor.getString(8));
                ListDebtor.add(debtor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDebtor;
    }
    public List<Debtor> getAllDebtorDebtDESC(){
        List<Debtor> ListDebtor = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DEBT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Debtor debtor = new Debtor();
                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
                debtor.setmInterest_rate(cursor.getDouble(5));
                debtor.setmDate(cursor.getString(6));
                debtor.setmInterest_date(cursor.getString(7));
                debtor.setmDescription(cursor.getString(8));
                ListDebtor.add(debtor);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDebtor;
    }
    public boolean UpdateDebtor(Debtor debtor)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, debtor.getmName());
        values.put(PHONE,debtor.getmPhone());
        values.put(ADDRESS, debtor.getmAddress());
        values.put(DEBT, debtor.getmDebt());
        values.put(INTEREST_RATE, debtor.getmInterest_rate());
        values.put(DATE, debtor.getmDate());
        values.put(INTEREST_DATE, debtor.getmInterest_date());
        values.put(DESCRIPTION, debtor.getmDescription());
        Log.d("Update: ", "start");
        if(db.update(TABLE_NAME, values, ID +"=?", new String[]{String.valueOf(ListActivity.Id)} )!=0) {
            Log.d("DB update:", ListActivity.Id+"");
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    public boolean deleteDebtor(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(TABLE_NAME, ID+ "=?", new String[]{String.valueOf(id)})>0){
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    public int sumOfDebt()
    {
        int sumDetb=0;
        String selectQuery = "SELECT  SUM(debt) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
            sumDetb=cursor.getInt(0);
        cursor.close();
        db.close();
        return sumDetb;
    }
    public Debtor getDebtorById(int id){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "+ ID +" = " +id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        Debtor debtor = new Debtor();
        if(cursor.moveToFirst()) {
            debtor.setmID(cursor.getInt(0));
            debtor.setmName(cursor.getString(1));
            debtor.setmPhone(cursor.getString(2));
            debtor.setmAddress(cursor.getString(3));
            debtor.setmDebt(cursor.getInt(4));
            debtor.setmInterest_rate(cursor.getDouble(5));
            debtor.setmDate(cursor.getString(6));
            debtor.setmInterest_date(cursor.getString(7));
            debtor.setmDescription(cursor.getString(8));
        }
        cursor.close();
        db.close();
        return debtor;
    }
    public int[] getIdByName(String name)
    {
        int ID[] = new int[0];
        String query = "SELECT ID FROM DEBTORS WHERE NAME LIKE"  + '"' + name + "%"+'"' + "ORDER BY NAME ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do {
                int[] temp = new int[ID.length];
                System.arraycopy(ID, 0, temp, 0, ID.length);
                ID = new int[ID.length+1];
                System.arraycopy(temp, 0, ID, 0, temp.length);
                ID[temp.length] = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return ID;
    }
    public double calculateInterest(Debtor debtor)
    {
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        String date = debtor.getmDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date interest = new Date();
        try {
            interest = simpleDateFormat.parse(date);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        long totalDay = (toDay.getTime() - interest.getTime())/(24*3600*1000);
        double interestRate = debtor.getmInterest_rate()/100/365 * debtor.getmDebt() * totalDay;
        return Math.round(interestRate*10)/10;
    }
    public List<Debtor> notifyDebtor()
    {
        List<Debtor> ListDebtor = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME +" ORDER BY " + DEBT + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Debtor debtor = new Debtor();
                debtor.setmID(cursor.getInt(0));
                debtor.setmName(cursor.getString(1));
                debtor.setmPhone(cursor.getString(2));
                debtor.setmAddress(cursor.getString(3));
                debtor.setmDebt(cursor.getInt(4));
                debtor.setmInterest_rate(cursor.getDouble(5));
                debtor.setmDate(cursor.getString(6));
                debtor.setmInterest_date(cursor.getString(7));
                debtor.setmDescription(cursor.getString(8));
                debtor.setmDebt((int)Math.round(calculateInterest(debtor)));

                Calendar calendar = Calendar.getInstance();
                int today = calendar.get(Calendar.DATE);
                if(debtor.getmDate().length() >5)
                {
                    Log.d("Check:", debtor.getmName() + debtor.getmDate().substring(0,2));
                    if(today == Integer.valueOf(debtor.getmDate().substring(0,2)))
                        ListDebtor.add(debtor);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ListDebtor;
    }
}
