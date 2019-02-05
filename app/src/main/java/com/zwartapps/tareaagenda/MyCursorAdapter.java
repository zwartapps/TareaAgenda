package com.zwartapps.tareaagenda;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

// Clase para crear un Cursor personalizado para asignar los variables con los datos de la BBDD
public class MyCursorAdapter extends CursorAdapter {

    // Default constructor
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        // Buscar los nombres que queremos asignar
        TextView nombre = view.findViewById(R.id.nombre);
        TextView email = view.findViewById(R.id.email);
        // Extraer las propiedades del Cursor (puntero)
        Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String body = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow("Email"));

        //mostramos los datos de la BBDD
        nombre.setText(body);
        email.setText(String.valueOf(priority));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.datos, parent, false);
    }
}