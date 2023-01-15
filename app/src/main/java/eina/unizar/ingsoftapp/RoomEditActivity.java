package eina.unizar.ingsoftapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RoomEditActivity extends AppCompatActivity {

    private RoomsDbAdapter mDbHelper;
    private EditText mNombreText;
    private EditText mDescripcionText;
    private EditText mCapacidadText;
    private EditText mPrecioText;
    private EditText mPorcentajeEstraText;
    private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_room);

        // Database Connection
        mDbHelper = new RoomsDbAdapter( this );
        mDbHelper.open();

        // References
        mNombreText = (EditText) findViewById(R.id.name_room);
        mCapacidadText = (EditText) findViewById(R.id.capacidad_room);
        mPrecioText = (EditText) findViewById(R.id.price_room);
        mPorcentajeEstraText = (EditText) findViewById(R.id.extra_room);
        mDescripcionText = (EditText) findViewById(R.id.description_room);

        ImageButton exitButton = (ImageButton) findViewById(R.id.exit_room);
        Button saveButton = (Button) findViewById(R.id.save_room);
        Button deleteButton = (Button) findViewById(R.id.delete_room);

        // Mods
        setTitle(R.string.edit_room);

        mRowId = (savedInstanceState == null) ? null :
                (Long)savedInstanceState.getSerializable(RoomsDbAdapter.KEY_ROWID ) ;
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = (extras != null)?extras.getLong(RoomsDbAdapter.KEY_ROWID):null ;
        }

        // Switch Activity on Event
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean eliminado = mDbHelper.deleteHabitacion(mRowId);
                if (eliminado) { finish(); }
            }
        });
    }

    private void populateFields () {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchHabitacion(mRowId);
            startManagingCursor(note);
            mNombreText.setText(note.getString(note.getColumnIndexOrThrow(RoomsDbAdapter.KEY_NOMBRE)));
            mDescripcionText.setText(note.getString(note.getColumnIndexOrThrow(RoomsDbAdapter.KEY_DESCRIPCION)));
            mCapacidadText.setText(note.getString(note.getColumnIndexOrThrow(RoomsDbAdapter.KEY_CAPACIDAD)));
            mPrecioText.setText(note.getString(note.getColumnIndexOrThrow(RoomsDbAdapter.KEY_PRECIO)));
            mPorcentajeEstraText.setText(note.getString(note.getColumnIndexOrThrow(RoomsDbAdapter.KEY_PORCENTAJEEXTRA)));
        }
    }

    @Override
    protected void onSaveInstanceState ( Bundle outState ) {
        super.onSaveInstanceState( outState ) ;
        saveState();
        outState.putSerializable (RoomsDbAdapter.KEY_ROWID , mRowId ) ;
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
        String descripcion = mDescripcionText.getText().toString();
        String capacidad = mCapacidadText.getText().toString();
        String precio = mPrecioText.getText().toString();
        String porcentajeExtra = mPorcentajeEstraText.getText().toString();
        if (!(nombre != null && !nombre.equals("") && capacidad != null && capacidad.length() > 0 && precio != null
                && Float.parseFloat(precio) > 0 && porcentajeExtra != null && Float.parseFloat(porcentajeExtra) > 0)) {
            Toast.makeText(getApplicationContext(),"Habitación no creada/modificada, campos inválidos",Toast.LENGTH_SHORT).show();
        } else{
            if ( mRowId == null ) {
                long id = mDbHelper.createHabitacion( nombre , descripcion, capacidad, precio, porcentajeExtra );
                if (id > 0) { mRowId = id; }
            } else {
                mDbHelper.updateHabitacion( mRowId , nombre , descripcion, capacidad, precio, porcentajeExtra );
            }
        }

    }
}