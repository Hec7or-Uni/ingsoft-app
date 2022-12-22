package eina.unizar.ingsoftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class RoomActivity extends AppCompatActivity {
    private RoomsDbAdapter mDbHelper;
    ListView rooms;

    String[] maintitle ={
            "Title 1","Title 2",
            "Title 3","Title 4",
            "Title 5",
    };

    String[] subtitle ={
            "Sub Title 1","Sub Title 2",
            "Sub Title 3","Sub Title 4",
            "Sub Title 5",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity_main);

        // Database
        // mDbHelper = new RoomsDbAdapter(this);
        // mDbHelper.open();

        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);
        Button info = findViewById(R.id.add);
        rooms = (ListView) findViewById(R.id.list_rooms);

        // Mods
        hab.setBackgroundColor(Color.parseColor("#000000"));
        hab.setTextColor(Color.parseColor("#FFFFFF"));
        res.setBackgroundColor(Color.parseColor("#FFFFFF"));
        res.setTextColor(Color.parseColor("#000000"));

        //Leer de la base de datos
        mDbHelper = new RoomsDbAdapter(this);
        mDbHelper.open();
        fillData();

        // Listeners
        Intent intent = new Intent(RoomActivity.this, ReservationActivity.class);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent intent1 = new Intent(RoomActivity.this, RoomEditActivity.class);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent1, 0);
            }
        });

        rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start the new activity here
                startActivityForResult(intent1, 0);
            }
        });
    }
    private void fillData() {
        Cursor notesCursor = mDbHelper.fetchAllHabitaciones();
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