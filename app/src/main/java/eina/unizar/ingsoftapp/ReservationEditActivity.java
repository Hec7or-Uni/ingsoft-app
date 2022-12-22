package eina.unizar.ingsoftapp;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class ReservationEditActivity extends AppCompatActivity {
    private ReservationDbAdapter mDbHelper ;
    private EditText mNombreText;
    private EditText mTelefonoText;
    private EditText mFechaEntradaText;
    private EditText mFechaSalidaText;
    private EditText mPrecioText;
    private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reservation); //indicar el layout correspondiente

        //Database
        mDbHelper = new ReservationDbAdapter( this );
        mDbHelper.open();
        setTitle(R.string.edit_reservation);

        mNombreText = (EditText) findViewById(R.id.name_res);
        mTelefonoText = (EditText) findViewById(R.id.phone_res);
        mFechaEntradaText = (EditText) findViewById(R.id.entry_date_res);
        mFechaSalidaText = (EditText) findViewById(R.id.departure_date_res);
        mPrecioText = (EditText) findViewById(R.id.price_res);


        //ImageButton exitButton = (ImageButton) findViewById(R.id.exit_room);
        Button saveButton = (Button) findViewById(R.id.save_reservation);
        Button deleteButton = (Button) findViewById(R.id.delete_reservation);

        mRowId = (savedInstanceState == null )?null :
                (Long)savedInstanceState.getSerializable(ReservationDbAdapter.KEY_ROWID ) ;
        if(mRowId == null){
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null)?
                    extras.getLong(ReservationDbAdapter.KEY_ROWID):null ;
        }

        // Listeners
        /*exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean eliminado = mDbHelper.deleteReserva(mRowId );
                if(eliminado){
                    finish();
                }

            }
        });
    }

    private void populateFields () {
        if ( mRowId != null ) {
            Cursor note = mDbHelper.fetchReserva( mRowId ) ;
            startManagingCursor( note ) ;
            mNombreText.setText( note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_NOMBRE ) )) ;
            mTelefonoText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_TELEFONO ) ) ) ;
            mFechaEntradaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHAENTRADA ) ) ) ;
            mFechaSalidaText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_FECHASALIDA ) ) ) ;
            mPrecioText.setText(note.getString(note.getColumnIndexOrThrow( ReservationDbAdapter.KEY_PRECIO ) ) ) ;

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
            long id = mDbHelper.createReserva( nombre , telefono, fechaEntrada, fechaSalida, precio );
            if ( id > 0) {
                mRowId = id ;
            }
        } else {
            mDbHelper.updateReserva( mRowId , nombre , telefono, fechaEntrada, fechaSalida, precio );
        }
    }

}
