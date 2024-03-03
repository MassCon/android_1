package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button[][] buttons;
    private boolean isPlayerX = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        initializeButtons();
    }

    private void initializeButtons() {
        buttons = new Button[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick((Button) v);
                    }
                });
            }
        }
    }

    private void onButtonClick(Button button) {
        if (button.getText().toString().equals("")) {
            if (isPlayerX) {
                button.setText("X");
            } else {
                button.setText("O");
            }

            if (checkForWinner()) {
                String winner = isPlayerX ? "Checks" : "Zeros";
                Toast.makeText(this, winner + " Win!", Toast.LENGTH_SHORT).show();
                resetGame();
            } else if (isBoardFull()) {
                Toast.makeText(this, "draw!", Toast.LENGTH_SHORT).show();
                resetGame();
            } else {
                isPlayerX = !isPlayerX;
            }
        }
    }

    private boolean checkForWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString())
                    && buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString())
                    && !buttons[i][0].getText().toString().equals("")) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().toString().equals(buttons[1][i].getText().toString())
                    && buttons[0][i].getText().toString().equals(buttons[2][i].getText().toString())
                    && !buttons[0][i].getText().toString().equals("")) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString())
                && buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString())
                && !buttons[0][0].getText().toString().equals("")) {
            return true;
        }

        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString())
                && buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString())
                && !buttons[0][2].getText().toString().equals("")) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        // Clear the text on all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        // Reset the player to X
        isPlayerX = true;
    }
}