package com.example.helloworld;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView sentenceTextView;
    private Button generateButton;
    private List<String> first;
    private List<String> second;
    private List<String> third;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sentenceTextView = findViewById(R.id.sentenceTextView);
        generateButton = findViewById(R.id.generateButton);

        first = loadWords("first.txt");
        second = loadWords("second.txt");
        third = loadWords("third.txt");

        generateButton.setOnClickListener(view -> generateSentence());
    }
    private List<String> loadWords(String filename) {
        List<String> words = new ArrayList<>();
        try (InputStream is = getAssets().open(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private void generateSentence() {
        Random random = new Random();
        String firstWord = first.get(random.nextInt(first.size()));
        String secondWord = second.get(random.nextInt(second.size()));
        String thirdWord = third.get(random.nextInt(third.size()));

        String sentence = firstWord + " " + secondWord + " " + thirdWord + ".";
        sentenceTextView.setText(sentence);
    }
}