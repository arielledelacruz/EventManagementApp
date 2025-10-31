package com.fit2081.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment3.provider.Event;
import com.fit2081.assignment3.provider.EventCategory;
import com.fit2081.assignment3.provider.EventCategoryViewModel;
import com.fit2081.assignment3.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends AppCompatActivity implements View.OnTouchListener {

    DrawerLayout drawerlayout;
    EditText eventNameInput;
    EditText eventCategoryIdInput;
    EditText ticketsAvailableInput;
    Switch eventIsActiveSwitch;
    TextView generatedEventIdTextView;
    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter recyclerAdapter;
    private ArrayList<EventCategory> eventCategories;
    private EventCategoryViewModel mEventCategoryViewModel;
    private EventViewModel mEventViewModel;

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_layout);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        drawerlayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        eventNameInput = findViewById(R.id.eventNameInput);
        eventCategoryIdInput = findViewById(R.id.eventCategoryIdInput);
        ticketsAvailableInput = findViewById(R.id.ticketsAvailableInput);
        eventIsActiveSwitch = findViewById(R.id.eventIsActiveSwitch);
        generatedEventIdTextView = findViewById(R.id.generatedEventIdTextView);

        FloatingActionButton fab = findViewById(R.id.fab_add_event);
        fab.setOnClickListener(this::onSaveEvent);

        loadData();

        recyclerView = findViewById(R.id.event_category_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventCategories = new ArrayList<>();
        recyclerAdapter = new CategoryRecyclerAdapter(eventCategories, this);
        recyclerView.setAdapter(recyclerAdapter);

        mEventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
        mEventCategoryViewModel.getAllEventCategories().observe(this, newData -> {
            recyclerAdapter.setData(newData);
        });

        mEventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MyGestureListener myGestureListener = new MyGestureListener();
        mDetector = new GestureDetector(this, myGestureListener);
        mDetector.setOnDoubleTapListener(myGestureListener);

        View touchpad = findViewById(R.id.touchpad);
        touchpad.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onSaveEvent(null);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            clearFields();
        }
    }

    private void loadData() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, FragmentListCategory.newInstance("", ""))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadData();
            return true;
        }
        if (item.getItemId() == R.id.option_clear_event) {
            clearFields();
            return true;
        } else if (item.getItemId() == R.id.option_delete_all_categories) {
            deleteAllCategories();
            return true;
        } else if (item.getItemId() == R.id.option_delete_all_events) {
            deleteAllEvents();
            return true;
        }
        return true;
    }

    private void clearFields() {
        eventNameInput.setText("");
        eventCategoryIdInput.setText("");
        ticketsAvailableInput.setText("");
        eventIsActiveSwitch.setChecked(false);
        generatedEventIdTextView.setText("");
    }

    private void deleteAllCategories() {
        mEventCategoryViewModel.deleteAll();
        Toast.makeText(this, "All categories deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteAllEvents() {
        mEventViewModel.deleteAll();
        Toast.makeText(this, "All events deleted", Toast.LENGTH_SHORT).show();
    }

    public void onSaveEvent(View view) {
        String eventId = generateEventId();
        String eventName = eventNameInput.getText().toString();
        String categoryId = eventCategoryIdInput.getText().toString();
        String ticketsAvailableString = ticketsAvailableInput.getText().toString();
        int ticketsAvailable = 0;

        if (!ticketsAvailableString.isEmpty()) {
            ticketsAvailable = Integer.parseInt(ticketsAvailableString);
            if (ticketsAvailable < 0) {
                Toast.makeText(this, "Tickets available must be a positive integer.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        boolean isActive = eventIsActiveSwitch.isChecked();

        Event event = new Event(eventId, eventName, categoryId, ticketsAvailable, isActive);
        mEventViewModel.insert(event);

        generatedEventIdTextView.setText(eventId);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Event saved: " + eventId + " to " + categoryId, Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> {
            mEventViewModel.deleteEventByName(eventName);
            Toast.makeText(this, "Event deleted: " + eventId, Toast.LENGTH_SHORT).show();
        });
        snackbar.show();
    }

    public String generateEventId() {
        Random r = new Random();
        char char1 = (char) (r.nextInt(26) + 'A');
        char char2 = (char) (r.nextInt(26) + 'A');
        int number = 10000 + r.nextInt(90000);
        return "E" + char1 + char2 + "-" + number;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.option_view_all_categories) {
                Intent intent = new Intent(Dashboard.this, ListCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.option_add_category) {
                Intent intent = new Intent(Dashboard.this, NewEventCategoryPage.class);
                startActivity(intent);
            } else if (id == R.id.option_view_all_events) {
                Intent intent = new Intent(Dashboard.this, ListEventActivity.class);
                startActivity(intent);
            } else if (id == R.id.option_logout) {
                Intent intent = new Intent(Dashboard.this, LogInPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            drawerlayout.closeDrawers();

            return true;
        }
    }
}


