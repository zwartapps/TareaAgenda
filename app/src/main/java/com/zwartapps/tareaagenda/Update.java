package com.zwartapps.tareaagenda;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Clase para actulizar datos de la lista
public class Update extends AppCompatActivity {

    Helper helper = new Helper(this);
    ListActivity list = new ListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Button insertBoton = findViewById(R.id.insertButton);
        Button cancelBoton = findViewById(R.id.cancelButton);

        insertBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperamos el valor del id de la lista que teniamos en la primera activity
                Bundle bundle = getIntent().getExtras();
                long tmp = bundle.getLong("id");
                SQLiteDatabase db = helper.getWritableDatabase();

                EditText nombre = findViewById(R.id.nombre);
                EditText email = findViewById(R.id.email);

                String check = email.getText().toString();

                // Introducimos los valores nuevos
                ContentValues values = new ContentValues();
                values.put(helper.NAME, nombre.getText().toString());

                //Siempre verificarmos si el mail está en el formato correcto
                if (!list.isValidEmailID(check)) {
                    Toast.makeText(getApplicationContext(), "por favor introduzca un email valido", Toast.LENGTH_LONG).show();
                } else {

                    //Actualizamos los datos en la BBDD
                    values.put(helper.EMAIL, email.getText().toString());
                    String selection = helper._id + " LIKE ?";
                    String[] selectionArgs = {((String.valueOf(tmp)))};

                    db.update(
                            helper.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);
                    Toast.makeText(getApplicationContext(), "Se ha modificado " + tmp, Toast.LENGTH_LONG).show();
                    nombre.setText("");
                    email.setText("");
                }
            }
        });

        //Cancelamos y volvemos a la actividad anterior
        cancelBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
