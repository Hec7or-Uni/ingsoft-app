package eina.unizar.ingsoftapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import eina.unizar.ingsoftapp.DatabaseHelper;

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
public class RoomsDbAdapter {

    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DESCRIPCION = "descripcion";
    public static final String KEY_CAPACIDAD = "capacidad";
    public static final String KEY_PRECIO = "precio";
    public static final String KEY_PORCENTAJEEXTRA = "porcentaje";
    public static final String KEY_ROWID = "_id";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_TABLE = "habitaciones";

    private final Context mCtx;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public RoomsDbAdapter(Context ctx) {
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
    public RoomsDbAdapter open() throws SQLException {
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
     * @param nombre the title of the note
     * @param descripcion the body of the note
     * @param capacidad the body of the note
     * @param precio the body of the note
     * @param porcentaje the body of the note
     * @return rowId or -1 if failed
     */
    public long createHabitacion(String nombre, String descripcion, String capacidad, String precio, String porcentaje) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DESCRIPCION, descripcion);
        initialValues.put(KEY_NOMBRE, nombre);
        initialValues.put(KEY_CAPACIDAD, capacidad);
        initialValues.put(KEY_PRECIO, precio);
        initialValues.put(KEY_PORCENTAJEEXTRA, porcentaje);


        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteHabitacion(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteAllHabitaciones() {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + KEY_ROWID, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor fetchAllHabitaciones() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NOMBRE,
                KEY_DESCRIPCION, KEY_CAPACIDAD, KEY_PRECIO, KEY_PORCENTAJEEXTRA}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchHabitacion(long rowId) throws SQLException {

        Cursor mCursor =

                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NOMBRE, KEY_DESCRIPCION, KEY_PORCENTAJEEXTRA, KEY_PRECIO, KEY_CAPACIDAD}, KEY_ROWID + "=" + rowId, null,
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
     * @param nombre value to set note title to
     * @param descripcion value to set note body to
     * @param capacidad value to set note body to
     * @param precio value to set note body to
     * @param porcentaje value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateHabitacion(long rowId, String nombre, String descripcion, String capacidad, String precio, String porcentaje) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOMBRE, nombre);
        args.put(KEY_DESCRIPCION, descripcion);
        args.put(KEY_CAPACIDAD, capacidad);
        args.put(KEY_PRECIO, precio);
        args.put(KEY_PORCENTAJEEXTRA, porcentaje);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}