package com.example.travelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get user name from Intent
        String userName = getIntent().getStringExtra("userName");

        // Initialize views
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        startButton = findViewById(R.id.startButton);

        // Set the welcome message
        if (userName != null) {
            welcomeMessage.setText(String.format("Hello %s\n\nWelcome to your Personal Companion\n\nHope You are Having a Great Day\n\nI am here to assist you today\n\nClick below to START", userName));
        } else {
            welcomeMessage.setText("Hello User\n\nWelcome to your Personal Companion\n\nHope You are Having a Great Day\n\nI am here to assist you today\n\nClick below to START");
        }

        // Set button click listener
        startButton.setOnClickListener(v -> {
            // Trigger "TheMainData()" function and navigate to SuggestionsActivity
            Toast.makeText(this, "Main Data Function Triggered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SuggestionsActivity.class);
            startActivity(intent);
        });
    }

}
