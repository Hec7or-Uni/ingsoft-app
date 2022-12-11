package eina.unizar.ingsoftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity_main);

        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);

        // Mods

        // Listeners
        Intent intent = new Intent(RoomActivity.this, ReservationActivity.class);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}