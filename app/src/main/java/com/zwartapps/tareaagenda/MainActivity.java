package com.zwartapps.tareaagenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Creamos objeto de la clase Helper de la BBDD para usar sus metodos
    Helper helper = new Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //All crear la actividad se carga de la BBDD en la lista
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM Datos", null);
        MyCursorAdapter customAdapter = new MyCursorAdapter(MainActivity.this, mCursor);

        ListView listView = findViewById(R.id.lista);

        listView.setAdapter(customAdapter);
        customAdapter.changeCursor(mCursor);
        customAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });

        //Cargamos la vista cuando no haya nada en la lista de la agenda
        listView.setEmptyView(findViewById(R.id.emptyResults));
    }

    //Creamos el metodo para el menu contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    //Acciones para cuando mantenemos apretado un elemento en la lista
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.borrado).setTitle(R.string.title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        helper.deleteName(info.id);
                        Toast.makeText(getApplicationContext(), "Entrada Borrada ", Toast.LENGTH_SHORT).show();
                        listar();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Accion Cancelada ", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;

            case R.id.Modify:

                //en caso de querer modificar pasamos los parametros a la actividad "Update"
                Intent i = new Intent(MainActivity.this, Update.class);
                i.putExtra("id", info.id);
                startActivity(i);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /*Metodo para refrescar la lista lo usamos en onResume para cuando se vuelva a la actividad
    principal se carga la lista*/
    public void listar() {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM Datos", null);
        MyCursorAdapter customAdapter = new MyCursorAdapter(MainActivity.this, mCursor);

        ListView listView = findViewById(R.id.lista);

        listView.setAdapter(customAdapter);
        customAdapter.changeCursor(mCursor);
        customAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);
    }

    @Override
    public void onResume() {
        super.onResume();
        listar();
    }
}

