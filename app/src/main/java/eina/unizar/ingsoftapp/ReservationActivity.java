package eina.unizar.ingsoftapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_activity_main);

        // References
        Button res = findViewById(R.id.reservations);
        Button hab = findViewById(R.id.rooms);
        Button info = findViewById(R.id.add);

        // Mods
        res.setBackgroundColor(Color.parseColor("#000000"));
        res.setTextColor(Color.parseColor("#FFFFFF"));
        hab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        hab.setTextColor(Color.parseColor("#000000"));

        // Listeners
        Intent intent = new Intent(ReservationActivity.this, RoomActivity.class);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Intent intent1 = new Intent(ReservationActivity.this, ReservationEditActivity.class);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent1, 0);
            }
        });
    }
}