package eina.unizar.ingsoftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
        hab.setBackgroundColor(Color.parseColor("#000000"));
        hab.setTextColor(Color.parseColor("#FFFFFF"));
        res.setBackgroundColor(Color.parseColor("#FFFFFF"));
        res.setTextColor(Color.parseColor("#000000"));

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