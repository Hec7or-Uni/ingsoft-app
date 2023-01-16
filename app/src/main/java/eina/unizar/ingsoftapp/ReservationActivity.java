package eina.unizar.ingsoftapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;

public class ReservationActivity extends AppCompatActivity {

    private TestVolSob TestVolumen;
    private ReservationDbAdapter mDbHelper;
    ListView reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_activity_main);

        TestVolumen = new TestVolSob(this);

        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);
        FloatingActionButton addNew = findViewById(R.id.add);
        Button cli = findViewById(R.id.client);
        Button tel = findViewById(R.id.phone);
        Button date = findViewById(R.id.date);
        reservations = (ListView) findViewById(R.id.list_reservations);

        // Mods
        res.setBackgroundColor(Color.parseColor("#000000"));
        res.setTextColor(Color.parseColor("#FFFFFF"));
        hab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        hab.setTextColor(Color.parseColor("#000000"));

        // Database Connection
        mDbHelper = new ReservationDbAdapter(this);
        mDbHelper.open();
        fillData();

        // Switch Activity on Event
        Intent toRoom = new Intent(ReservationActivity.this, RoomActivity.class);
        hab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toRoom);
            }
        });

        Intent toCreateReservation = new Intent(ReservationActivity.this, ReservationEditActivity.class);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(toCreateReservation, 0);
            }
        });

        Intent toEditReservation = new Intent(this, ReservationEditActivity.class);
        reservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toEditReservation.putExtra(ReservationDbAdapter.KEY_ROWID, id);
                startActivityForResult(toEditReservation, 1);
            }
        });

        // Events for filters
        cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(1);
            }
        });

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(2);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(this, "Opcion 2 pulsada", Toast.LENGTH_LONG).show();
            TestVolumen.crearDosmilReservas();
            fillData();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Opcion 3 pulsada", Toast.LENGTH_LONG).show();
        }else if (id == R.id.item4) {
            Toast.makeText(this, "Opcion 4 pulsada", Toast.LENGTH_LONG).show();
            TestVolumen.borrarReservas();
            fillData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillData() {
        Cursor notesCursor = mDbHelper.fetchAllReservas();
        String[] from = new String[] { ReservationDbAdapter.KEY_NOMBRE, ReservationDbAdapter.KEY_FECHAENTRADA, ReservationDbAdapter.KEY_PRECIO};
        int[] to = new int[] { R.id.title_res, R.id.dates_res, R.id.price_res};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.reservation, notesCursor, from, to);
        reservations.setAdapter(adapter);
    }

    private void sort(int type) {
        Cursor notesCursor = mDbHelper.sortReservas(type);
        String[] from = new String[] { ReservationDbAdapter.KEY_NOMBRE, ReservationDbAdapter.KEY_FECHAENTRADA, ReservationDbAdapter.KEY_PRECIO};
        int[] to = new int[] { R.id.title_res, R.id.dates_res, R.id.price_res};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.reservation, notesCursor, from, to);
        reservations.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}