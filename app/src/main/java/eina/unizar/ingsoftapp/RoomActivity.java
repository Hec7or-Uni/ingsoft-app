package eina.unizar.ingsoftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.InvocationTargetException;

public class RoomActivity extends AppCompatActivity {
    private TestVolSob TestVolumen;
    private RoomsDbAdapter mDbHelper;
    ListView rooms;

    private final int ROOM_LIMIT = 100;
    private int roomsLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity_main);

        TestVolumen = new TestVolSob(this);
        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);
        FloatingActionButton addNew = findViewById(R.id.add);
        Button id = findViewById(R.id.id);
        Button ocs = findViewById(R.id.ocs);
        Button price = findViewById(R.id.price);
        rooms = (ListView) findViewById(R.id.list_rooms);

        // Mods
        hab.setBackgroundColor(Color.parseColor("#000000"));
        hab.setTextColor(Color.parseColor("#FFFFFF"));
        res.setBackgroundColor(Color.parseColor("#FFFFFF"));
        res.setTextColor(Color.parseColor("#000000"));

        // Database Connection
        mDbHelper = new RoomsDbAdapter(this);
        mDbHelper.open();
        fillData();

        // Switch Activity on Event
        Intent toReservation = new Intent(RoomActivity.this, ReservationActivity.class);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toReservation);
            }
        });

        Intent toCreateRoom = new Intent(RoomActivity.this, RoomEditActivity.class);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomsLimit < ROOM_LIMIT) {
                    startActivityForResult(toCreateRoom, 0);
                } else {
                    Toast.makeText(RoomActivity.this, "Limite de habitaciones alcanzado", Toast.LENGTH_LONG).show();
                }
            }
        });

        Intent toEditRoom = new Intent(this, RoomEditActivity.class);
        rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toEditRoom.putExtra(RoomsDbAdapter.KEY_ROWID, id);
                startActivityForResult(toEditRoom, 1);
            }
        });

        // Events for filters
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(1);
            }
        });

        ocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(2);
            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(0);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        RoomUnitTest Room = new RoomUnitTest();
        ReservationUnitTest Reservation = new ReservationUnitTest();
        HabitacionesReservasUnitTest Mix = new HabitacionesReservasUnitTest();

        if (id == R.id.item1) {
            if (0 == Room.run(this)){ Toast.makeText(this, "Test habitaciones OK", Toast.LENGTH_LONG).show(); }
            else { Toast.makeText(this, "ERROR Test habitaciones", Toast.LENGTH_LONG).show(); }

            if (0 == Reservation.run(this)){ Toast.makeText(this, "Test reservas OK", Toast.LENGTH_LONG).show(); }
            else { Toast.makeText(this, "ERROR Test reservas", Toast.LENGTH_LONG).show(); }

            if (0 == Mix.run(this)){ Toast.makeText(this, "Test both OK", Toast.LENGTH_LONG).show(); }
            else { Toast.makeText(this, "ERROR Test both", Toast.LENGTH_LONG).show(); }
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Test volumen para habitaciones", Toast.LENGTH_LONG).show();
            TestVolumen.crearCienHabitaciones();
            fillData();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Test de sobrecarga", Toast.LENGTH_LONG).show();
            TestVolumen.pruebaSobrecarga();
            fillData();
        } else if (id == R.id.item4) {
            Toast.makeText(this, "Eliminar habitaciones", Toast.LENGTH_LONG).show();
            TestVolumen.borrarHabitaciones();
            fillData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillData() {
        Cursor notesCursor = mDbHelper.fetchAllHabitaciones();
        roomsLimit = notesCursor.getCount();
        String[] from = new String[] { RoomsDbAdapter.KEY_NOMBRE, RoomsDbAdapter.KEY_CAPACIDAD, RoomsDbAdapter.KEY_PRECIO, RoomsDbAdapter.KEY_ROWID  };
        int[] to = new int[] { R.id.title, R.id.ocupantes, R.id.precio, R.id.identifier };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.room, notesCursor, from, to);
        rooms.setAdapter(adapter);
    }

    private void sort(int type) {
        Cursor notesCursor = mDbHelper.sortHabitaciones(type);
        String[] from = new String[] { RoomsDbAdapter.KEY_NOMBRE, RoomsDbAdapter.KEY_CAPACIDAD, RoomsDbAdapter.KEY_PRECIO, RoomsDbAdapter.KEY_ROWID  };
        int[] to = new int[] { R.id.title, R.id.ocupantes, R.id.precio, R.id.identifier };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.room, notesCursor, from, to);
        rooms.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}