package com.example.helloworld;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final String APP_LAUNCH_COUNTER = "appLaunchCount";
    private static final String PREFERENCES_NAME = "myPreferences";
    private int chosenRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRatingDialogIfNeeded();
    }
    private void showRatingDialogIfNeeded() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        int launchCount = sharedPreferences.getInt(APP_LAUNCH_COUNTER, 0);
        launchCount++;

        if (launchCount % 1 == 0) {
            showRatingDialog();
        }

        sharedPreferences.edit().putInt(APP_LAUNCH_COUNTER, launchCount).apply();
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.rating_dialog, null);
        builder.setView(dialogView);

        ImageView[] stars = new ImageView[5];

        stars[0] = dialogView.findViewById(R.id.star1);
        stars[1] = dialogView.findViewById(R.id.star2);
        stars[2] = dialogView.findViewById(R.id.star3);
        stars[3] = dialogView.findViewById(R.id.star4);
        stars[4] = dialogView.findViewById(R.id.star5);

        stars[0].setOnClickListener(view -> {
            chosenRating = 1;
            updateStarsFill(stars);
        });
        stars[1].setOnClickListener(view -> {
            chosenRating = 2;
            updateStarsFill(stars);
        });
        stars[2].setOnClickListener(view -> {
            chosenRating = 3;
            updateStarsFill(stars);
        });
        stars[3].setOnClickListener(view -> {
            chosenRating = 4;
            updateStarsFill(stars);
        });
        stars[4].setOnClickListener(view -> {
            chosenRating = 5;
            updateStarsFill(stars);
        });

        builder.setPositiveButton("Submit", (dialog, which) -> {
                    int selectedRating = chosenRating;
                    if (selectedRating <= 3) {
                        showFeedbackForm();
                    } else {
                        showPlayMarketPrompt();
                    }
                })
                .create()
                .show();
    }
    private void updateStarsFill(ImageView[] stars) {
        for(int i = 0; i < chosenRating; i++) {
            stars[i].setImageResource(android.R.drawable.star_big_on);
        }
        for(int i = chosenRating; i < 5; i++) {
            stars[i].setImageResource(android.R.drawable.star_big_off);
        }
    }
    private void showFeedbackForm() {
        EditText feedbackInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String feedback = feedbackInput.getText().toString();
                })
                .create()
                .show();

    }

    private void showPlayMarketPrompt() {
        new AlertDialog.Builder(this)
                .setPositiveButton("Rate on Play Store", (dialog, which) -> {
                    String appUri = "market://details?id=" + getPackageName();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appUri)));
                })
                .create()
                .show();
    }
}