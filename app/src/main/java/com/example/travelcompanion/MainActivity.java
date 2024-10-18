package com.example.travelcompanion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner indoorSpinner, outdoorSpinner;
    private EditText breakfastTime, lunchTime, dinnerTime;
    private Spinner breakfastSpinner, lunchSpinner, dinnerSpinner;
    private Button submitButton;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        indoorSpinner = findViewById(R.id.indoorSpinner);
        outdoorSpinner = findViewById(R.id.outdoorSpinner);
        breakfastTime = findViewById(R.id.breakfastTime);
        lunchTime = findViewById(R.id.lunchTime);
        dinnerTime = findViewById(R.id.dinnerTime);
        breakfastSpinner = findViewById(R.id.breakfastSpinner);
        lunchSpinner = findViewById(R.id.lunchSpinner);
        dinnerSpinner = findViewById(R.id.dinnerSpinner);
        submitButton = findViewById(R.id.submitButton);

        setupSpinners();

        submitButton.setOnClickListener(v -> sendPreferences());
    }

    private void setupSpinners() {
        String[] indoorActivities = {"Table Tennis", "Chess", "Badminton"};
        String[] outdoorActivities = {"Cricket", "Football", "Hiking"};
        String[] breakfastMeals = {"Pancakes", "Omelette", "Smoothie"};
        String[] lunchMeals = {"Pizza", "Salad", "Burger"};
        String[] dinnerMeals = {"Steak", "Pasta", "Soup"};

        setSpinnerAdapter(indoorSpinner, indoorActivities);
        setSpinnerAdapter(outdoorSpinner, outdoorActivities);
        setSpinnerAdapter(breakfastSpinner, breakfastMeals);
        setSpinnerAdapter(lunchSpinner, lunchMeals);
        setSpinnerAdapter(dinnerSpinner, dinnerMeals);
    }

    private void setSpinnerAdapter(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void sendPreferences() {
        String indoorActivity = indoorSpinner.getSelectedItem().toString();
        String outdoorActivity = outdoorSpinner.getSelectedItem().toString();
        String breakfastTimeInput = breakfastTime.getText().toString();
        String lunchTimeInput = lunchTime.getText().toString();
        String dinnerTimeInput = dinnerTime.getText().toString();
        String breakfastMeal = breakfastSpinner.getSelectedItem().toString();
        String lunchMeal = lunchSpinner.getSelectedItem().toString();
        String dinnerMeal = dinnerSpinner.getSelectedItem().toString();

        String TAG = "Details";
        android.util.Log.i(TAG, "Indoor Activity: " + indoorActivity);
        android.util.Log.i(TAG, "Outdoor Activity: " + outdoorActivity);
        android.util.Log.i(TAG, "Breakfast Time: " + breakfastTimeInput);
        android.util.Log.i(TAG, "Lunch Time: " + lunchTimeInput);
        android.util.Log.i(TAG, "Dinner Time: " + dinnerTimeInput);
        android.util.Log.i(TAG, "Breakfast Meal: " + breakfastMeal);
        android.util.Log.i(TAG, "Lunch Meal: " + lunchMeal);
        android.util.Log.i(TAG, "Dinner Meal: " + dinnerMeal);

//        JSONObject json = new JSONObject();
//        try {
//            json.put("indoor_activity", indoorActivity);
//            json.put("outdoor_activity", outdoorActivity);
//            json.put("breakfast_time", breakfastTimeInput);
//            json.put("lunch_time", lunchTimeInput);
//            json.put("dinner_time", dinnerTimeInput);
//            json.put("breakfast_meal", breakfastMeal);
//            json.put("lunch_meal", lunchMeal);
//            json.put("dinner_meal", dinnerMeal);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //sendToBackend(json);
        Toast.makeText(this, "User Details Stored Successfully", Toast.LENGTH_SHORT).show();
    }

    private void sendToBackend(JSONObject json) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json.toString());

        Request request = new Request.Builder()
                .url("http://your-ip-address:5000/preferences")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Failed to send data", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Preferences sent!", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
