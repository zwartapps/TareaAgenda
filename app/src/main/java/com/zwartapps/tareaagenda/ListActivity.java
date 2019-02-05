package com.zwartapps.tareaagenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Clase para la actividad de la lista
public class ListActivity extends AppCompatActivity {

    //Creamos objeto de la BBDD para usar sus metodos
    Helper helper = new Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);

        Button insertBoton = findViewById(R.id.insertButton);
        Button cancelBoton = findViewById(R.id.cancelButton);

        insertBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nombre = findViewById(R.id.nombre);
                EditText email = findViewById(R.id.email);
                String temp = nombre.getText().toString();

                //verificamos el formato del mail y si no es correcto salta el mensaje de aviso
                if (!isValidEmailID(email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "por favor introduzca un email valido", Toast.LENGTH_LONG).show();

                } else {

                    //si es correcto agregamos los datos en la BBDD y vaciamos los campos del nombre y email
                    helper.addName(nombre.getText().toString(), email.getText().toString());
                    Toast.makeText(getApplicationContext(), "Se ha insertado " + temp, Toast.LENGTH_LONG).show();

                    nombre.setText("");
                    email.setText("");
                }
            }
        });

        //boton para cancelar se cierra la actividad y se vuelve al anterior (MainActivity)
        cancelBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   finish();
            }
        });
    }

    //Metodo para verificar si el formato del email introducido es correcto
    public boolean isValidEmailID(String email) {
        String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

