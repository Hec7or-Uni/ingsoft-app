package eina.unizar.ingsoftapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eina.unizar.send.SendAbstractionImpl;

public class ReservationEditActivity extends AppCompatActivity {
    // Database
    private RoomsDbAdapter mDbRoomHelper;
    private ReservationDbAdapter mDbReservationHelper;
    private HabitacionesReservasDbAdapter mDbRoomMixHelper;
    private Cursor cursor;

    // Fields
    private EditText mNombreText;
    private EditText mTelefonoText;
    private EditText mFechaEntradaText;
    private EditText mFechaSalidaText;
    private TextView mPrecioText;

    // ---
    private Long mRowId;
    private ListView rooms;
    private DatePickerDialog picker;
    private List<String> itemsID;
    private List<String> itemsOcupacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reservation);

        // Database
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
        AppCompatButton add = findViewById(R.id.addRoom);
        rooms = (ListView) findViewById(R.id.list_rooms_2);

        // mods
        mFechaEntradaText.setInputType(InputType.TYPE_NULL);
        mFechaSalidaText.setInputType(InputType.TYPE_NULL);

        // Declaración de la lista que contiene el desplegable
        itemsID = new ArrayList<>();
        itemsOcupacion = new ArrayList<>();

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
                if(cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(RoomsDbAdapter.KEY_ROWID));
                    String ocupacion = cursor.getString(cursor.getColumnIndexOrThrow(RoomsDbAdapter.KEY_CAPACIDAD));
                    itemsID.add(id);
                    itemsOcupacion.add(ocupacion);

                    ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, itemsID, itemsOcupacion);
                    rooms.setAdapter(adapter);
                }
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


    private void populateFields () throws SQLException {
        if ( mRowId != null ) {
            Cursor note = mDbReservationHelper.fetchReserva( mRowId ) ;
            startManagingCursor( note ) ;
            mNombreText.setText( note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_NOMBRE ) )) ;
            mTelefonoText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_TELEFONO ) ) ) ;
            mFechaEntradaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHAENTRADA ) ) ) ;
            mFechaSalidaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHASALIDA ) ) ) ;
            mPrecioText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_PRECIO ) ) + " €" ) ;
            // Bucle que se supone que lee las habitaciones reservadas de la BD para una reserva mRowId
            // y las añade al adaptador
            Cursor cursorHabs = mDbRoomMixHelper.fetchAllHabitacionReserva(mRowId);
            if (cursorHabs.moveToFirst()) {
                do {
                    String id = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(HabitacionesReservasDbAdapter.KEY_IDHABITACION));
                    String ocupacion = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(HabitacionesReservasDbAdapter.KEY_OCUPACION));
                    itemsID.add(id);
                    itemsOcupacion.add(ocupacion);
                } while (cursorHabs.moveToNext());
            }

            ListViewAdapter adapter = new ListViewAdapter(ReservationEditActivity.this, itemsID, itemsOcupacion);
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

    /**
     * Devuelve true si la fecha de entrada es anterior a la fecha de salida, en caso contrario devuelve fasle
     * @param fechaEntrada
     * @param fechaSalida
     * @return
     */
    private boolean fechasCorrectas( String fechaEntrada, String fechaSalida){
        try {
            // Crea un formateador de fecha con el formato especificado
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Convierte las fechas de cadena a objetos Date
            Date d1 = sdf.parse(fechaEntrada);
            Date d2 = sdf.parse(fechaSalida);

            // Compara las fechas
            return d1.before(d2);
        } catch (ParseException e) {
            // En caso de error al parsear las fechas, se devuelve false
            return false;
        }
    }

    /**
     * Devuelva true si fechaSalida y fechaEntrada se encuentran en el periodo marcado por
     * fechaEntradaOld y fechaSalidaOld
     * @param fechaEntradaOld
     * @param fechaSalidaOld
     * @param fechaEntrada
     * @param fechaSalida
     * @return
     */
    private boolean fechasOcupadas(String fechaEntradaOld, String fechaSalidaOld, String fechaEntrada, String fechaSalida){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Convierte las fechas de cadena a objetos Date
            Date entrada = sdf.parse(fechaEntrada);
            Date salida = sdf.parse(fechaSalida);
            Date entradaOld = sdf.parse(fechaEntradaOld);
            Date salidaOld = sdf.parse(fechaSalidaOld);


            // comparamos las fechas
            if (entrada.after(entradaOld) && entrada.before(salidaOld)) {
                // la fecha de entrada se encuentra en el intervalo
                return true;
            } else if (salida.after(entradaOld) && salida.before(salidaOld)) {
                // la fecha de salida se encuentra en el intervalo
                return true;
            } else if (entrada.before(entradaOld) && salida.after(salidaOld)) {
                // el intervalo se encuentra dentro del periodo marcado por fechaEntradaOld y fechaSalidaOld
                return true;
            }else if( entrada.equals(entradaOld) || salida.equals(salidaOld)){
                // es el mismo periodo de reserva
                return true;
            } else {
                // ninguna de las fechas se encuentra en el intervalo
                return false;
            }
        }catch (ParseException e) {
            // En caso de error al parsear las fechas, se devuelve false
            return false;
        }
    }

    private void saveState () {
        String nombre = mNombreText.getText().toString();
        String telefono = mTelefonoText.getText().toString();
        String fechaEntrada = mFechaEntradaText.getText().toString();
        String fechaSalida = mFechaSalidaText.getText().toString();
        Double precioRooms = 0.0;
        String precio = "";
        Integer estaReservada = 0;
        if (!(nombre != null && !nombre.equals("") && telefono != null && telefono.length() > 0 && fechasCorrectas(fechaEntrada,fechaSalida))) {
            Toast.makeText(getApplicationContext(),"Reserva no creada/modificada, campos inválidos",Toast.LENGTH_SHORT).show();
        } else{
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

                Cursor cursorRes = mDbReservationHelper.fetchAllReservas();
                if (cursorRes.moveToFirst()) {
                    do {
                        String id = cursorRes.getString(cursorRes.getColumnIndexOrThrow(ReservationDbAdapter.KEY_ROWID));
                        String fechaEntradaOld = cursorRes.getString(cursorRes.getColumnIndexOrThrow(ReservationDbAdapter.KEY_FECHAENTRADA));
                        String fechaSalidaOld = cursorRes.getString(cursorRes.getColumnIndexOrThrow(ReservationDbAdapter.KEY_FECHASALIDA));

                        Cursor cursorHabs = mDbRoomMixHelper.fetchAllHabitacionReserva(Long.parseLong(id));
                        // Caso especial: sumamos 1 si la reserva aun no tiene habitaciones añadidas ya que
                        // significa que va a añadir una y podria ser del id que se busca
                        if( cursorHabs.getCount() == 0 && Long.parseLong(id) == mRowId && fechasOcupadas(fechaEntradaOld, fechaSalidaOld, fechaEntrada, fechaSalida) ){
                            estaReservada ++;
                        }
                        if (cursorHabs.moveToFirst()) {
                            do {
                                String idRoom = cursorHabs.getString(cursorHabs.getColumnIndexOrThrow(HabitacionesReservasDbAdapter.KEY_IDHABITACION));
                                if(Long.parseLong(idRoom) == field1Value && fechasOcupadas(fechaEntradaOld, fechaSalidaOld, fechaEntrada, fechaSalida)){
                                    estaReservada ++;
                                }
                            } while (cursorHabs.moveToNext());
                        }
                    } while (cursorRes.moveToNext());
                }

                if (Integer.parseInt(field2Value) <= 0 || estaReservada > 1){
                    mDbRoomMixHelper.deleteHabitacionReserva( field1Value, mRowId);
                } else{
                    if ( mDbRoomMixHelper.exiteHabitacionReserva(field1Value, mRowId ) ) {
                        mDbRoomMixHelper.updateHabitacionReserva( field1Value, mRowId , field2Value );
                    } else {
                        mDbRoomMixHelper.createHabitacionReserva(field1Value, mRowId, field2Value);
                    }
                    Cursor cursorR = mDbRoomHelper.fetchHabitacion(field1Value);

                    String precioR = cursorR.getString(cursorR.getColumnIndexOrThrow(RoomsDbAdapter.KEY_PRECIO));
                    precioRooms = precioRooms + Double.parseDouble(precioR);

                }
            }
            DecimalFormat df = new DecimalFormat("#.##");
            precio = df.format(precioRooms);
            mPrecioText.setText(precio + " €");
            mDbReservationHelper.updateReserva( mRowId , nombre , telefono, fechaEntrada, fechaSalida, precio );

            // Enviamos confirmación de la reserva
            Cursor cursorCli = mDbReservationHelper.fetchReserva(mRowId);
            String client = cursorCli.getString(cursorCli.getColumnIndexOrThrow(ReservationDbAdapter.KEY_NOMBRE));
            SendAbstractionImpl Sender = new SendAbstractionImpl(ReservationEditActivity.this, "SMS");
            Sender.send(client, "Reserva confirmada");
        }
    }

}
