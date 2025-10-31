package com.fit2081.assignment3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.assignment3.provider.EventCategory;
import com.fit2081.assignment3.provider.EventCategoryViewModel;

import java.util.Random;
import java.util.StringTokenizer;

public class NewEventCategoryPage extends AppCompatActivity {

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            if (msg != null && msg.startsWith("category:")) {
                StringTokenizer sT = new StringTokenizer(msg.substring(9), ";");
                int count = sT.countTokens();

                if (count == 3) {
                    String name = sT.nextToken();
                    String eventCount = sT.nextToken();
                    String isActiveString = sT.nextToken();

                    categoryNameInput.setText(name);
                    eventCountInput.setText(eventCount);
                    categoryIsActiveSwitch.setChecked("TRUE".equalsIgnoreCase(isActiveString));
                } else {
                    Toast.makeText(context, "Invalid input.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    TextView generatedCategoryIdTextView;
    EditText categoryNameInput;
    EditText eventCountInput;
    EditText eventLocationInput;
    Switch categoryIsActiveSwitch;

    private EventCategoryViewModel mEventCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_event_category_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        generatedCategoryIdTextView = findViewById(R.id.generatedCategoryIdTextView);
        categoryNameInput = findViewById(R.id.categoryNameInput);
        eventCountInput = findViewById(R.id.eventCountInput);
        eventLocationInput = findViewById(R.id.eventLocationInput);
        categoryIsActiveSwitch = findViewById(R.id.categoryIsActiveSwitch);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);

        mEventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
    }

    public String generateCategoryId() {
        Random r = new Random();
        char char1 = (char) (r.nextInt(26) + 'A');
        char char2 = (char) (r.nextInt(26) + 'A');
        int number = 1000 + r.nextInt(9000);
        return "C" + char1 + char2 + "-" + number;
    }

    public void onSaveCategoryButton(View view) {
        String categoryName = categoryNameInput.getText().toString();
        int eventCount;
        try {
            eventCount = Integer.parseInt(eventCountInput.getText().toString());
        } catch (NumberFormatException e) {
            eventCount = 0;
        }
        String eventLocation = eventLocationInput.getText().toString();

        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Please fill in category name.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean categoryIsActive = categoryIsActiveSwitch.isChecked();
        String generatedCategoryId = generateCategoryId();

        generatedCategoryIdTextView.setText(generatedCategoryId);

        EventCategory newCategory = new EventCategory(generatedCategoryId, categoryName, eventCount, categoryIsActive, eventLocation);
        mEventCategoryViewModel.insert(newCategory);

        Toast.makeText(this, "Category saved successfully: " + generatedCategoryId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NewEventCategoryPage.this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}


