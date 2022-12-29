package eina.unizar.ingsoftapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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

        saveButton.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
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
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(mDbRoomHelper.KEY_ROWID));
                    items.add(id);
                }
                ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, items);
                rooms.setAdapter(adapter);

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

    private void fillData() {
        Cursor cursorHabs = mDbRoomMixHelper.fetchAllHabitacionReserva(mRowId);
        while (cursorHabs.moveToNext()) {
            String id = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(mDbRoomMixHelper.KEY_IDHABITACION));
            items.add(id);
        }
        ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, items);
        rooms.setAdapter(adapter);
    }

    private void populateFields () {
        if ( mRowId != null ) {
            Cursor note = mDbReservationHelper.fetchReserva( mRowId ) ;
            startManagingCursor( note ) ;
            mNombreText.setText( note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_NOMBRE ) )) ;
            mTelefonoText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_TELEFONO ) ) ) ;
            mFechaEntradaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHAENTRADA ) ) ) ;
            mFechaSalidaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHASALIDA ) ) ) ;
            mPrecioText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_PRECIO ) ) ) ;

            fillData();

        }
    }

    @Override
    protected void onSaveInstanceState ( Bundle outState ) {
        super.onSaveInstanceState( outState ) ;
        saveState();
        outState.putSerializable (ReservationDbAdapter.KEY_ROWID , mRowId ) ;
    }

    @Override
    protected void onPause () {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume () {
        super.onResume();
        populateFields();
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

        for (int i = 0; i < rooms.getCount(); i++) {
            View listItem = rooms.getChildAt(i);
            Spinner field1 = listItem.findViewById(R.id.spinner);
            EditText field2 = listItem.findViewById(R.id.ocupacion);
            String field1Value = field1.toString();
            String field2Value = field2.getText().toString();
            // Do something with the values of field1 and field2
            mDbRoomMixHelper.createHabitacionReserva(field1Value, ReservationDbAdapter.KEY_ROWID, field2Value);

        }
    }

}
