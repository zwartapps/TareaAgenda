package com.zwartapps.tareaagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Clase para la definicion y creacion de la base de datos SQLite
public class Helper extends SQLiteOpenHelper {

    //Creamos la tabla con sus nombres de las filas
    public static final String TABLE_NAME = "Datos";
    public static final String _id = "_id";
    public static final String NAME = "Nombre";
    public static final String EMAIL = "Email";

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME + " TEXT," +
                    EMAIL + " TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    //Version y nombre de la BBDD
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Agenda.db";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //Metodo para cuando cambiemos la version de la BBDD
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //Metodo para cuando volvamos a la version anterior
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Metodo para añadir una persona a la agenda
    public long addName(String nombre, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_id, (Integer) null);
        values.put(NAME, nombre);
        values.put(EMAIL, email);

        return db.insert(TABLE_NAME, null, values);
    }

    //Metodo para borrar una persona de la agenda
    public void deleteName(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + _id
                + "=" + id + "");
    }
}
