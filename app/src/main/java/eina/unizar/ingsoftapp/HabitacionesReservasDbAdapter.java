package eina.unizar.ingsoftapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 *
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class HabitacionesReservasDbAdapter {

    public static final String KEY_IDHABITACION = "idHabitacion";
    public static final String KEY_IDRESERVA    = "idReserva";
    public static final String KEY_OCUPACION    = "ocupacion";

    private static final String DATABASE_TABLE = "habitaciones_reservas";
    private final Context mCtx;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public HabitacionesReservasDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public HabitacionesReservasDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @param idHabitacion the title of the note
     * @param idReserva the body of the note
     * @param ocupacion the body of the note
     */
    public long createHabitacionReserva(long idHabitacion, long idReserva, String ocupacion) {
        try {
            if (idHabitacion <= -1) { return -1; }
            else if (idReserva <= -1) { return -1; }
            else if (ocupacion == null || Integer.parseInt(ocupacion) < 1) {return -1;}
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_IDHABITACION, idHabitacion);
        initialValues.put(KEY_IDRESERVA, idReserva);
        initialValues.put(KEY_OCUPACION, ocupacion);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     *
     * @param idHabitacion id of note to delete
     * @param idReserva id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteHabitacionReserva(long idHabitacion, long idReserva) {
        try {
            if (idHabitacion <= -1) { return false; }
            else if (idReserva <= -1) { return false; }
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        return mDb.delete(DATABASE_TABLE, KEY_IDHABITACION + "=" + idHabitacion + " AND " +
                KEY_IDRESERVA + "=" + idReserva, null) > 0;
    }

    public boolean exiteHabitacionReserva(long idHabitacion, long idReserva  ) {

        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_IDHABITACION, KEY_IDRESERVA, KEY_OCUPACION}, KEY_IDHABITACION + "=" + idHabitacion+ " AND " +
                        KEY_IDRESERVA + "=" + idReserva, null,
                null, null, null, null);
        return mCursor.getCount()>0;

    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param idHabitacion id of note to delete
     * @param idReserva id of note to delete
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchHabitacionReserva(long idHabitacion, long idReserva) throws SQLException {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_IDHABITACION, KEY_IDRESERVA, KEY_OCUPACION}, KEY_IDHABITACION + "=" + idHabitacion+ " AND " +
                                KEY_IDRESERVA + "=" + idReserva, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllHabitacionReserva(long idReserva) throws SQLException {
        return mDb.query(true, DATABASE_TABLE, new String[] {KEY_IDHABITACION, KEY_IDRESERVA, KEY_OCUPACION}, KEY_IDRESERVA + "=" + idReserva, null,
                        null, null, null, null);
    }

    public boolean updateHabitacionReserva(long idHabitacion, long idReserva, String ocupacion) {
        try {
            if (idHabitacion <= -1) { return false; }
            else if (idReserva <= -1) { return false; }
            else if (ocupacion == null || Integer.parseInt(ocupacion) < 1) {return false; }
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_IDHABITACION, idHabitacion);
        initialValues.put(KEY_IDRESERVA, idReserva);
        initialValues.put(KEY_OCUPACION, ocupacion);

        return mDb.update(DATABASE_TABLE, initialValues, KEY_IDHABITACION + "=" + idHabitacion + " AND " + KEY_IDRESERVA + "=" + idReserva , null) > 0;
    }

    public List<String> getListData() throws SQLException {
        List<String> list = new ArrayList<>();

        // Ejecuta la consulta y obtén el resultado
        Cursor rs = mDb.rawQuery("SELECT * FROM habitaciones_reservas", null);

        // Recorre el ResultSet y agrega los datos a la lista
        while (rs.moveToNext()) {
            list.add(rs.getString(rs.getColumnIndexOrThrow(KEY_IDHABITACION)));
        }

        return list;
    }
}