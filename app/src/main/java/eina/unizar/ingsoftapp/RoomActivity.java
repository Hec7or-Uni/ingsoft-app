package eina.unizar.ingsoftapp;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity_main);

        // Database
        mDbHelper = new RoomsDbAdapter(this);
        mDbHelper.open();

        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);
        ListView rooms = findViewById(R.id.list_rooms);

        // Mods
        hab.setBackgroundColor(Color.parseColor("#000000"));
        hab.setTextColor(Color.parseColor("#FFFFFF"));
        res.setBackgroundColor(Color.parseColor("#FFFFFF"));
        res.setTextColor(Color.parseColor("#000000"));

        // Adapters
        RoomAdapter adapter=new RoomAdapter(this);
        rooms.setAdapter(adapter);

        // Listeners
        Intent intent = new Intent(RoomActivity.this, ReservationActivity.class);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void fillData() {
        // Get all of the notes from the database and
        // create the item list
        Cursor roomsCursor = mDbHelper.fetchAllHabitaciones();
        // Create an array to specify the fields we want to
        // display in the list ( only TITLE )
        String[] from = new String[] { RoomsDbAdapter.KEY_NOMBRE }; // aqui poner mas campos
        // and an array of the fields we want to bind
        // those fields to (in this case just text1 )
        int[] to = new int[] { 1 };
        // Now create an array adapter and set it to
        // display using our row
        RoomsAdapter rooms = new RoomsAdapter (this, R.layout.list_rooms, roomsCursor, from, to) ;
        mList.setAdapter(rooms);
    }
}