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
                startActivityForResult(toCreateRoom, 0);
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
            try {
//                Room.run(this);
//                Reservation.run(this);
                Mix.run(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Opcion 1 pulsada", Toast.LENGTH_LONG).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Opcion 2 pulsada", Toast.LENGTH_LONG).show();
            TestVolumen.crearCienHabitaciones();
            fillData();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Opcion 3 pulsada", Toast.LENGTH_LONG).show();
            TestVolumen.pruebaSobrecarga();
            fillData();
        }else if (id == R.id.item4) {
            Toast.makeText(this, "Opcion 4 pulsada", Toast.LENGTH_LONG).show();
            TestVolumen.borrarHabitaciones();
            fillData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillData() {
        Cursor notesCursor = mDbHelper.fetchAllHabitaciones();
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