package com.example.checktech.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "POS";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_MAESTROS = "t_maestros";

    public static final String TABLE_AULA = "t_aula";
    public static final String TABLE_CARRERA = "t_carrera";
    public static final String TABLE_ACADEMIA = "t_academia";
    public static final String TABLE_USER = "t_user";
    public static  final  String TABLE_ChequeoClases ="cheque_clases";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(@Nullable Context abcDocentes, @Nullable String POS, @Nullable SQLiteDatabase.CursorFactory factory, int DATABASE_VERSION) {
        super(abcDocentes, POS, factory, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_MAESTROS + "("+
                "id_maestro INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT NOT NULL,"+
                "apellidos TEXT NOT NULL,"+
                "academia TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CARRERA + "("+
                "id_carrera INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ACADEMIA + "("+
                "id_academia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "codigo TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + "("+
                "id_user INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "usuario TEXT NOT NULL,"+ "contraseña TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_AULA + "("+
                "id_aula INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ChequeoClases + "("+
                "id_chequeo_clase INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
                "nombreAula TEXT NOT NULL,"
                +"nombreDocente TEXT NOT NULL,"
                +"Hora TEXT NOT NULL,"+"" +
                "Accion TEXT NOT NULL)");
    }


    public Cursor DatasAula() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select nombre from t_aula", null);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_MAESTROS);
        onCreate(sqLiteDatabase);
    }

    public Cursor DatosMaestro(String maestro) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select nombre, apellidos from t_maestros", null);
    }
    /*public Cursor DatosReporte(String query) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(query, null);
    }*/


    public void insertarChequeoClases(String aula, String hora, String docente, String accion) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombreAula", aula);
        values.put("Hora", hora);
        values.put("nombreDocente", docente);  // Utiliza "nombreDocente" en lugar de "nombre"
        values.put("Accion", accion);

        db.insert("chequeo_clases", null, values);

        db.close();
    }
}