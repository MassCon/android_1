package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import android.graphics.Color;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);

        LocalDateTime targetDateTime = LocalDateTime.of(2024, 2, 7, 8, 5);

        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(currentDateTime, targetDateTime);

        String msg = formatTimeLeft(duration.toDays(), duration.toHours() % 24, duration.toMinutes() % 60);

        b3.setOnClickListener(v -> Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show());

        b.setOnClickListener(v -> v.setBackgroundColor(generateRandomColor()));

        final int[] clicksCount = {0};

        b2.setOnClickListener( v -> ( ( Button ) v ).setText( ( "Кількість кліків: " + ++(clicksCount[0])) ) );
    }

    private int generateRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
    private static String formatTimeLeft(long days, long hours, long minutes) {
        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" днів").append(" ");
        }

        if (hours > 0) {
            result.append(hours).append(" годин").append(" ");
        }

        if (minutes > 0) {
            result.append(minutes).append(" хвилин");
        }

       return result.toString();
    }
}