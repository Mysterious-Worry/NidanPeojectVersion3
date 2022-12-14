package in.co.okservices.nidanhospitaapp3.costom_packages;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.zip.DeflaterInputStream;

import in.co.okservices.nidanhospitaapp3.R;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "PatientLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_patients";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SR_NO = "sr_no";
    private static final String COLUMN_CHECKED = "checked";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DATE = "date";

    private static final String DAY_TABLE_NAME = "day_record_table";
    private static final String DAY_PATIENT_COUNT = "patient_count";
    private static final String DAY_COLLECTED_MONEY = "collected_money";

    private static final String DAY_NORMAL = "normal_count";
    private static final String DAY_EMERGENCY = "emergency_count";
    private static final String DAY_NORMAL_PAPER_VALID = "normal_paper_valid_count";
    private static final String DAY_PAPER_VALID_EMERGENCY = "paper_valid_emergency_count";
    private static final String DAY_DISCOUNT = "discount_count";
    private static final String DAY_CANCEL = "cancel_count";

    private static final String queryPatientTable = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SR_NO + " INTEGER, " +
            COLUMN_CHECKED + " TEXT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_DATE + " TEXT);";

    private static final String queryDayTable = "CREATE TABLE " + DAY_TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " TEXT, " +
            DAY_PATIENT_COUNT + " TEXT, " +
            DAY_COLLECTED_MONEY + " TEXT, " +
            DAY_NORMAL + " TEXT, " +
            DAY_EMERGENCY + " TEXT, " +
            DAY_NORMAL_PAPER_VALID + " TEXT, " +
            DAY_PAPER_VALID_EMERGENCY + " TEXT, " +
            DAY_DISCOUNT + " TEXT, " +
            DAY_CANCEL + " TEXT);";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(queryPatientTable);
            sqLiteDatabase.execSQL(queryDayTable);
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("dbAdapter", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DAY_TABLE_NAME + ";");
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        onCreate(sqLiteDatabase);
    }

    public String addRawData2(){
        SQLiteDatabase db=this.getWritableDatabase();
        float res = 0;
        ContentValues cv=new ContentValues();
        for (int i = 1; i <= 150; i++){
            cv.put(COLUMN_SR_NO,i);
            cv.put(COLUMN_CHECKED,"no");
            cv.put(COLUMN_TYPE,String.valueOf(R.color.black2));
            cv.put(COLUMN_DATE,this.getDate());
            res = db.insert(TABLE_NAME  ,null,cv);
        }

        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";
    }
    public boolean checkIfRecordExist(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = '" + date + "';";
        try {
            Cursor c = db.rawQuery(query, null);
            if(c.getCount()>0) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getDate(){
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date.toString().trim();
    }
    public Cursor readalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM my_patients WHERE date = '" + getDate() + "'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }
    public Cursor readDataByDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM my_patients WHERE date = '" + date + "'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }
    public boolean updateCheckedYes(String id) {
        try {
            SQLiteDatabase myDb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CHECKED, "yes");
            cv.put(COLUMN_ID, id);
            myDb.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(Integer.parseInt(id))});
            return true;
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean updateCheckedNo(String id) {
        try {
            SQLiteDatabase myDb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CHECKED, "no");
            cv.put(COLUMN_ID, id);
            myDb.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(Integer.parseInt(id))});
            return true;
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean setNewType(String type, String id){
        try {
            SQLiteDatabase myDb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TYPE, type);
            cv.put(COLUMN_ID, id);
            myDb.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(Integer.parseInt(id))});

            return true;
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /* Day Record Methods */
    public String  addDayRawData(){
        SQLiteDatabase db=this.getWritableDatabase();
        float res = 0;
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_DATE, getDate());
        cv.put(DAY_PATIENT_COUNT, 0);
        cv.put(DAY_COLLECTED_MONEY, 0);
        cv.put(DAY_NORMAL, 0);
        cv.put(DAY_EMERGENCY, 0);
        cv.put(DAY_NORMAL_PAPER_VALID, 0);
        cv.put(DAY_PAPER_VALID_EMERGENCY, 0);
        cv.put(DAY_DISCOUNT, 0);
        cv.put(DAY_CANCEL, 0);
        res = db.insert(DAY_TABLE_NAME  ,null,cv);

        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";
    }

    public void updateColumn(String column_name, int data) {
        try {
            SQLiteDatabase myDb = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(column_name, data);
            cv.put(COLUMN_DATE, getDate());
            myDb.update(DAY_TABLE_NAME, cv, COLUMN_DATE + "=?", new String[]{getDate()});
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    public int senderCell(String column_name){
        int rv = -1;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + column_name + " from " + DAY_TABLE_NAME + " where " + COLUMN_DATE + "=?", new String[]{getDate()});
        if(cursor.moveToNext()){
            rv = (int) cursor.getLong(cursor.getColumnIndex(column_name));
        }
        return rv;
    }

    public Cursor fetchDayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor fetchDayData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_TABLE_NAME + " WHERE " + COLUMN_DATE + "='" + date + "';";
        return db.rawQuery(query, null);
    }

    public Cursor fetchMonthData(String month){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_TABLE_NAME + " WHERE " + COLUMN_DATE + " LIKE '%" + month + "%';";
        return db.rawQuery(query, null);
    }
}
