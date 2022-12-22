package com.example.qldbh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "QLDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "danhba";
    private static final String COLUMN_SDT = "sdt";
    private static final String COLUMN_TEN = "ten";
    private static final String COLUMN_DIACHI = "diachi";
    DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_SDT + " TEXT PRIMARY KEY, " +
                COLUMN_TEN + " TEXT, " +
                COLUMN_DIACHI + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void Them(String sdt, String ten, String dc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SDT, sdt);
        cv.put(COLUMN_TEN, ten);
        cv.put(COLUMN_DIACHI, dc);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Thêm Thành Công!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor LayDL(){
        String query = "SELECT * FROM danhba ORDER BY TEN ASC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor timkiem(String ten){
        String query = "Select * FROM danhba WHERE TEN LIKE '%" + ten + "%'" +"OR SDT LIKE '%" + ten + "%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void Sua(String sdt, String ten, String dc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SDT, sdt);
        cv.put(COLUMN_TEN, ten);
        cv.put(COLUMN_DIACHI, dc);

        long result = db.update(TABLE_NAME, cv, "SDT=?", new String[]{sdt});
        if(result == -1){
            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Sửa Thành Công!", Toast.LENGTH_SHORT).show();
        }

    }

    void Xoa(String sdt){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "SDT=?", new String[]{sdt});
        if(result == -1){
            Toast.makeText(context, "Lỗi.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Xóa Thành Công.", Toast.LENGTH_SHORT).show();
        }
    }

    void XoaAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
