//package com.example.alexmaran.theapp.db;
//
///**
// * Created by Alex on 12/21/2016.
// */
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.example.alexmaran.theapp.models.Potato;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MyDBHandler extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "potatoDB.db";
//    private static final String TABLE_POTATOS = "potatos";
//
//    public static final String COLUMN_ID = "_id";
//    public static final String COLUMN_POTATONAME = "potatoname";
//
//
//    public MyDBHandler(Context context, String name,
//                       SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_POTATOES_TABLE = "CREATE TABLE " +
//                TABLE_POTATOS + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_POTATONAME
//                + " TEXT" + ")";
//        db.execSQL(CREATE_POTATOES_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion,
//                          int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POTATOS);
//        onCreate(db);
//    }
//
//    public void addPotato(Potato product) {
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_POTATONAME, product.getName());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.insert(TABLE_POTATOS, null, values);
//        db.close();
//    }
//
////    public Potato findPotato(String potatoname) {
////        String query = "Select * FROM " + TABLE_POTATOS + " WHERE " + COLUMN_POTATONAME + " =  \"" + potatoname + "\"";
////
////        SQLiteDatabase db = this.getWritableDatabase();
////
////        Cursor cursor = db.rawQuery(query, null);
////
////        Potato potato = new Potato();
////
////        if (cursor.moveToFirst()) {
////            cursor.moveToFirst();
////            potato.setId(Integer.parseInt(cursor.getString(0)));
////            potato.setName(cursor.getString(1));
////            cursor.close();
////        } else {
////            potato = null;
////        }
////        db.close();
////        return potato;
////    }
//
//    public boolean deletePotato(String potatoname) {
//
//        boolean result = false;
//
//        String query = "Select * FROM " + TABLE_POTATOS + " WHERE " + COLUMN_POTATONAME + " =  \"" + potatoname + "\"";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        Potato potato = new Potato();
//
//        if (cursor.moveToFirst()) {
//            potato.setId(Integer.parseInt(cursor.getString(0)));
//            db.delete(TABLE_POTATOS, COLUMN_ID + " = ?",
//                    new String[] { String.valueOf(potato.getId()) });
//            cursor.close();
//            result = true;
//        }
//        db.close();
//        return result;
//    }
//
//
//    public void updatePotato(String name, String updatedName)
//    {
////        String query = "UPDATE " + TABLE_POTATOS + " SET " + COLUMN_POTATONAME + " = " + updatedName + " where " + COLUMN_POTATONAME +" = " + name;
////
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues  args = new ContentValues();
//                args.put(COLUMN_POTATONAME,updatedName);
//        db.update(TABLE_POTATOS,args,COLUMN_POTATONAME + " = ? ", new String[]{name});
////        db.execSQL(query);
//        db.close();
//    }
//
//    public List<Potato> getAllPotatoes() {
//        List<Potato> potatos = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + TABLE_POTATOS;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Potato potato = new Potato();
//                potato.setId(Integer.parseInt(cursor.getString(0)));
//                potato.setName(cursor.getString(1));
//
//                potatos.add(potato);
//            } while (cursor.moveToNext());
//        }
//
//        return potatos;
//    }
//}