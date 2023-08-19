package com.example.checktech.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import androidx.annotation.Nullable;

public class dbCarrera extends SQLiteOpenHelper {

    public static final String NOMBRE_DB="carrera_db";
    public static final int VERSION_DB = 1;
    public  static  final String TABLE_CARRERA="CREATE TABLE CARRERA(id_carrera INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL)";

    public dbCarrera(@Nullable Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CARRERA);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRERA);
        sqLiteDatabase.execSQL(TABLE_CARRERA);
    }


    public void insertarCarrera(Editable id_carrera, Editable nombre) {
        SQLiteDatabase db=getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO CARRERA VALUES ('"+id_carrera+"','"+nombre+"')");
            db.close();
        }
    }
}
