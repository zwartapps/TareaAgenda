# Tarea Agenda
## Tarea UT4 de Programación Multimedia y Dispositivos Móviles

### Unidad de Trabajo 04
#### Unidad de Trabajo 04
#### SQLite

#### Práctica a Entregar
Crear un proyecto Android que nos cree una agenda donde nos permita almacenar los siguientes datos:
- Nombre de la persona (tipo String)
- Email de la persona (tipo String)
- Id del contacto (tipo Integer, deberá rellenarse de manera automática)

Para ver los detalles, ver el PDF: 
- [PDF Tarea 4](../master/0489_PDMP_UT04_Practica_2018_v1.0.pdf)

Creamos el proyecto en Android Studio. El codigo está comentado para entender que hacemos.
Se crea una clase Helper para usar la base de datos SQLite de Android.

```
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
```


La aplicacion ha quedado así:

Cuando se carga la aplicacion, la agenda está vacía:

<img src="http://i68.tinypic.com/2v9zztl.jpg">

Pulsamos en el boton + para añadir un nombre y e-mail, se carga la siguiente actividad.
Introducimos nombre y e-mail. El formato del mail se comprueba con la siguiente expresion regular:

```
//Metodo para verificar si el formato del email introducido es correcto
    public boolean isValidEmailID(String email) {
        String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
```

Si no se cumple los requisitos sale el mensaje:

<img src="http://i65.tinypic.com/2helog1.jpg">

Cuando lo ponemos correcto, se inserta en la base de datos el conctacto con su nombre y e-mail:

<img src="http://i64.tinypic.com/fkcuaa.jpg">

Se actualiza la vista actual y muestra el nuevo contacto:

<img src="http://i68.tinypic.com/263j9cy.jpg">

Cuando mantemos pulsado un item de la lista, aperece el menu contextual para borrar o modificar la entrada:

<img src="http://i66.tinypic.com/a7bqc.jpg">

Pulsando modificar nos lleva a la siguiente pantalla para modificar la entrada:

<img src="http://i67.tinypic.com/23ibckp.jpg">

Cuando aceptamos vemos que se ha modificado correctamente en la lista:

<img src="http://i65.tinypic.com/2ut157a.jpg">

Si elegimos borrar nos sale un mensaje de alerta, para confirmar si queremos borrar la entrada o no:
Hemos usado este codigo para la ventana de alerta:
```
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
```
<img src="http://i66.tinypic.com/2ltaib.jpg">

Si elegimos OK se borra la entrada y se refresca la lista de la agenda:


<img src="http://i68.tinypic.com/2871bg1.jpg">


### Todo funciona correctamente!

