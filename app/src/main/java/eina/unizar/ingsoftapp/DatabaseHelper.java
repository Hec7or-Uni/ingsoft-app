package eina.unizar.ingsoftapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *
 */
class DatabaseHelper extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 2;
    private static final String DATABASE_NAME    = "data";
    private static final String TAG              = "DatabaseHelper";

    /**
     * Database creation sql statement
     */
    private static final String HABITACIONES = "create table habitaciones ("
        + "_id           integer primary key autoincrement,"
        + "nombre        text    not null,"
        + "descripcion   text    not null,"
        + "capacidad     integer not null,"
        + "precio        integer not null,"
        + "porcentaje    integer not null"
        + "); ";

    private static final String RESERVAS = "create table reservas ("
        + "_id           integer primary key autoincrement,"
        + "nombreCliente text    not null,"
        + "telefono      text    not null,"
        + "fechaEntrada  text    not null,"
        + "fechaSalida   text    not null,"
        + "precio        integer not null"
        + "); ";

    private static final String HABITACIONES_RESERVAS = "create table habitaciones_reservas ("
        + "idHabitacion integer not null,"
        + "idReserva    integer not null,"
        + "ocupacion    integer not null,"
        + "foreign key(idHabitacion) references habitaciones(_id),"
        + "foreign key(idReserva) references reservas(_id)"
        + "); ";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HABITACIONES);
        db.execSQL(RESERVAS);
        db.execSQL(HABITACIONES_RESERVAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS habitaciones");
        db.execSQL("DROP TABLE IF EXISTS reservas");
        db.execSQL("DROP TABLE IF EXISTS habitaciones_reservas");
        onCreate(db);
    }
}