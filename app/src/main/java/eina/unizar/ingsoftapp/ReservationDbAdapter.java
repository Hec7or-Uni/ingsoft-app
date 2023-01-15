package eina.unizar.ingsoftapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


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
public class ReservationDbAdapter {

    public static final String KEY_NOMBRE       = "nombreCliente";
    public static final String KEY_TELEFONO     = "telefono";
    public static final String KEY_FECHAENTRADA = "fechaEntrada";
    public static final String KEY_FECHASALIDA  = "fechaSalida";
    public static final String KEY_PRECIO       = "precio";
    public static final String KEY_ROWID        = "_id";

    private static final String DATABASE_TABLE = "reservas";
    private final Context mCtx;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public ReservationDbAdapter(Context ctx) {
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
    public ReservationDbAdapter open() throws SQLException {
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
     * @param nombreCliente the title of the note
     * @param telefono the body of the note
     * @param fechaEntrada the body of the note
     * @param fechaSalida the body of the note
     * @param precio the body of the note
     * @return rowId or -1 if failed
     */
    public long createReserva(String nombreCliente, String telefono, String fechaEntrada,
                              String fechaSalida, String precio) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateIn = dateFormat.parse(fechaEntrada);
            Date dateOut = dateFormat.parse(fechaSalida);

            if (nombreCliente == null || nombreCliente.length() <= 0 ) { return -1; }
            else if (telefono == null || telefono.length() < 9 || telefono.length() > 9 || !telefono.matches("\\d+")) { return -1; }
            else if (fechaEntrada == null || dateIn.compareTo(dateOut) >= 0) { return -1; }
            else if (fechaSalida == null) { return -1; }
            else if (precio == null || Float.parseFloat(precio) < 0) { return -1; }
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOMBRE, nombreCliente);
        initialValues.put(KEY_TELEFONO, telefono);
        initialValues.put(KEY_FECHAENTRADA, fechaEntrada);
        initialValues.put(KEY_FECHASALIDA, fechaSalida);
        initialValues.put(KEY_PRECIO, precio);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteReserva(long rowId) {
        try {
            if (rowId < 1) { return false; }
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteAllReservas() {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + KEY_ROWID, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor fetchAllReservas() {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                KEY_TELEFONO, KEY_FECHAENTRADA, KEY_FECHASALIDA, KEY_PRECIO },
                null, null, null, null, null);
    }

    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor sortReservas(int type) {
        switch (type) {
            case 1:
                return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                        KEY_TELEFONO, KEY_FECHAENTRADA, KEY_FECHASALIDA, KEY_PRECIO },
                        null, null, null, null, KEY_NOMBRE );
            case 2:
                return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                        KEY_TELEFONO, KEY_FECHAENTRADA, KEY_FECHASALIDA, KEY_PRECIO },
                        null, null, null, null,KEY_TELEFONO );
            default:
                return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                        KEY_TELEFONO, KEY_FECHAENTRADA, KEY_FECHASALIDA, KEY_PRECIO },
                        null, null, null, null,KEY_FECHAENTRADA );
        }
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchReserva(long rowId) throws SQLException {
        Cursor mCursor =
            mDb.query(true, DATABASE_TABLE, new String[] {
                    KEY_ROWID, KEY_NOMBRE, KEY_TELEFONO, KEY_FECHAENTRADA,
                    KEY_FECHASALIDA, KEY_PRECIO
                }, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     *
     * @param rowId id of note to update
     * @param nombreCliente the title of the note
     * @param telefono the body of the note
     * @param fechaEntrada the body of the note
     * @param fechaSalida the body of the note
     * @param precio the body of the note
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateReserva(long rowId, String nombreCliente, String telefono,
                                 String fechaEntrada, String fechaSalida, String precio) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateIn = dateFormat.parse(fechaEntrada);
            Date dateOut = dateFormat.parse(fechaSalida);

            if (rowId <= -1) { return false; }
            else if (nombreCliente == null || nombreCliente.length() <= 0 ) { return false; }
            else if (telefono == null || telefono.length() < 9 || telefono.length() > 9 || !telefono.matches("\\d+")) { return false; }
            else if (fechaEntrada == null || dateIn.compareTo(dateOut) >= 0) { return false; }
            else if (fechaSalida == null) { return false; }
            else if (precio == null || Float.parseFloat(precio) < 0) { return false; }
        } catch (Exception e) {
            Log.w(DATABASE_TABLE, e.getStackTrace().toString());
        }

        ContentValues args = new ContentValues();
        args.put(KEY_NOMBRE, nombreCliente);
        args.put(KEY_TELEFONO, telefono);
        args.put(KEY_FECHAENTRADA, fechaEntrada);
        args.put(KEY_FECHASALIDA, fechaSalida);
        args.put(KEY_PRECIO, precio);


        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}