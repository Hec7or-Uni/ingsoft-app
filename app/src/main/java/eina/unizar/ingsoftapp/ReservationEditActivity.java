package eina.unizar.ingsoftapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ReservationEditActivity extends AppCompatActivity {
    private RoomsDbAdapter mDbRoomHelper;
    private ReservationDbAdapter mDbReservationHelper;
    private HabitacionesReservasDbAdapter mDbRoomMixHelper;
    private EditText mNombreText;
    private EditText mTelefonoText;
    private EditText mFechaEntradaText;
    private EditText mFechaSalidaText;
    private TextView mPrecioText;
    private Long mRowId;
    private ListView rooms;
    private DatePickerDialog picker;
    private List<String> items;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reservation); //indicar el layout correspondiente

        //Database
        mDbReservationHelper = new ReservationDbAdapter( this );
        mDbReservationHelper.open();
        mDbRoomHelper = new RoomsDbAdapter( this );
        mDbRoomHelper.open();
        mDbRoomMixHelper = new HabitacionesReservasDbAdapter( this );
        mDbRoomMixHelper.open();
        setTitle(R.string.edit_reservation);

        // References
        mNombreText = (EditText) findViewById(R.id.name_res);
        mTelefonoText = (EditText) findViewById(R.id.phone_res);
        mFechaEntradaText = (EditText) findViewById(R.id.entry_date_res);
        mFechaSalidaText = (EditText) findViewById(R.id.departure_date_res);
        mPrecioText = (TextView) findViewById(R.id.price_res);
        ImageButton exitButton = (ImageButton) findViewById(R.id.exit_reservation);
        Button saveButton = (Button) findViewById(R.id.save_reservation);
        Button deleteButton = (Button) findViewById(R.id.delete_reservation);
        Button add = findViewById(R.id.addRoom);
        rooms = (ListView) findViewById(R.id.list_rooms_2);

        //fillData();
        // mods
        mFechaEntradaText.setInputType(InputType.TYPE_NULL);
        mFechaSalidaText.setInputType(InputType.TYPE_NULL);

        // Declaración de la lista que contiene el desplegable
        items = new ArrayList<>();


        mRowId = (savedInstanceState == null )?null :
                (Long)savedInstanceState.getSerializable(ReservationDbAdapter.KEY_ROWID ) ;
        if(mRowId == null){
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null)?
                    extras.getLong(ReservationDbAdapter.KEY_ROWID):null ;
        }

        // Listeners
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mDbRoomMixHelper.createHabitacionReserva(1, 1, "2");
                for (int i = 0; i < rooms.getCount(); i++) {
                    View listItem = rooms.getChildAt(i);
                    Spinner field1 = listItem.findViewById(R.id.spinner);
                    EditText field2 = listItem.findViewById(R.id.ocupacion);
                    DropDownData data = (DropDownData) field1.getSelectedItem();
                    long field1Value = Long.parseLong(data.getId());
                    String field2Value = field2.getText().toString();
                    // Do something with the values of field1 and field2
                    if ( mDbRoomMixHelper.exiteHabitacionReserva(field1Value, mRowId ) ) {
                        mDbRoomMixHelper.updateHabitacionReserva( field1Value, mRowId , field2Value );
                    } else {
                        mDbRoomMixHelper.createHabitacionReserva(field1Value, mRowId, field2Value);
                    }
                }
                setResult(RESULT_OK);
                finish();
            }
        });

        deleteButton.setOnClickListener(view -> {
            boolean eliminado = mDbReservationHelper.deleteReserva(mRowId );
            if(eliminado){
                finish();
            }

        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cuando se clica el botón añadir se insertara un elemento de la lista y se creara en la BD
                cursor = mDbRoomHelper.fetchAllHabitaciones();
                if (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(RoomsDbAdapter.KEY_ROWID));
                    items.add(id);
                }
                ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, items);
                rooms.setAdapter(adapter);
                // Se ha añadido un elemento a la lista y lo guardamso en la BD, si no esta creado se crea y sino se actualiza

            }
        });

        mFechaEntradaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ReservationEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mFechaEntradaText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        mFechaSalidaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ReservationEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mFechaSalidaText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    /*private void fillData(){
        if ( mRowId != null ) {
            Cursor cursorHabs = mDbRoomMixHelper.fetchAllHabitacionReserva(mRowId);
            String[] from = new String[] {HabitacionesReservasDbAdapter.KEY_IDHABITACION};
            int[] to = new int[] { R.id.spinner};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.dropdown, cursorHabs, from, to);

            List<String> data = new ArrayList<>();
            while (cursorHabs.moveToNext()) {
                String id = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(HabitacionesReservasDbAdapter.KEY_IDHABITACION));
                data.add(id);
            }
            ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, data);

            rooms.setAdapter(adapter);
        }

    }*/

    private void populateFields () throws SQLException {
        if ( mRowId != null ) {
            Cursor note = mDbReservationHelper.fetchReserva( mRowId ) ;
            startManagingCursor( note ) ;
            mNombreText.setText( note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_NOMBRE ) )) ;
            mTelefonoText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_TELEFONO ) ) ) ;
            mFechaEntradaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHAENTRADA ) ) ) ;
            mFechaSalidaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHASALIDA ) ) ) ;
            mPrecioText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_PRECIO ) ) ) ;
            // Bucle que se supone que lee las habitaciones reservadas de la BD para una reserva mRowId
            // y las añade al adaptador
            Cursor cursorHabs = mDbRoomMixHelper.fetchAllHabitacionReserva(mRowId);
            if (cursorHabs.moveToFirst()) {
                do {
                    String id = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(HabitacionesReservasDbAdapter.KEY_IDHABITACION));
                    items.add(id);
                } while (cursorHabs.moveToNext());
            }
            ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, items);
            rooms.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState ( Bundle outState ) {
        super.onSaveInstanceState( outState ) ;
        saveState();
        outState.putSerializable (ReservationDbAdapter.KEY_ROWID , mRowId ) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //fillData();

    }

    @Override
    protected void onPause () {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume () {
        super.onResume();
        try {
            populateFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveState () {
        String nombre = mNombreText.getText().toString();
        String telefono = mTelefonoText.getText().toString();
        String fechaEntrada = mFechaEntradaText.getText().toString();
        String fechaSalida = mFechaSalidaText.getText().toString();
        String precio = mPrecioText.getText().toString();

        if ( mRowId == null ) {
            long id = mDbReservationHelper.createReserva( nombre , telefono, fechaEntrada, fechaSalida, precio );
            if ( id > 0) {
                mRowId = id ;
            }
        } else {
            mDbReservationHelper.updateReserva( mRowId , nombre , telefono, fechaEntrada, fechaSalida, precio );
        }
        // Bucle que lee la lista de habitaciones y almacena en la BD las habitaciones para una reserva,
        // si la reserva ya existia se actualiza y sino se crea
        for (int i = 0; i < rooms.getCount(); i++) {
            View listItem = rooms.getChildAt(i);
            Spinner field1 = listItem.findViewById(R.id.spinner);
            EditText field2 = listItem.findViewById(R.id.ocupacion);
            DropDownData data = (DropDownData) field1.getSelectedItem();
            long field1Value = Long.parseLong(data.getId());
            String field2Value = field2.getText().toString();
            // Do something with the values of field1 and field2
            if ( mDbRoomMixHelper.exiteHabitacionReserva(field1Value, mRowId ) ) {
                mDbRoomMixHelper.updateHabitacionReserva( field1Value, mRowId , field2Value );
            } else {
                mDbRoomMixHelper.createHabitacionReserva(field1Value, mRowId, field2Value);
            }
        }
    }

}
