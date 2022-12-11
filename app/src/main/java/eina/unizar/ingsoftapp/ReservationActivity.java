package eina.unizar.ingsoftapp;

import android.content.Intent;
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

        // Mods

        // Listeners
        Intent intent = new Intent(ReservationActivity.this, RoomActivity.class);
        hab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}