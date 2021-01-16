package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int gold;
    private int clickIncome;
    private int passiveIncome;

    private int up1;
    private int up2;
    private int up3;
    private int up4;
    private int up5;
    private int up6;

    private final String GOLD_KEY = "gold";
    private final String CLICK_KEY = "clickIncome";
    private final String PASSIVE_KEY = "passiveIncome";
    private final String UP1_KEY = "upgrade1";
    private final String UP2_KEY = "upgrade2";
    private final String UP3_KEY = "upgrade3";
    private final String UP4_KEY = "upgrade4";
    private final String UP5_KEY = "upgrade5";
    private final String UP6_KEY = "upgrade6";


    private TextView goldText;
    private TextView gold_per_s_Text;
    private TextView up1Text;
    private TextView up2Text;
    private TextView up3Text;
    private TextView up4Text;
    private TextView up5Text;
    private TextView up6Text;

    private Button up1Btn;
    private Button up2Btn;
    private Button up3Btn;
    private Button up4Btn;
    private Button up5Btn;
    private Button up6Btn;

    private Handler mHandler = new Handler();

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.clicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Find the TextViews by ID
        goldText = findViewById(R.id.gold);
        gold_per_s_Text = findViewById(R.id.gold_per_s);
        up1Text = findViewById(R.id.up1);
        up2Text = findViewById(R.id.up2);
        up3Text = findViewById(R.id.up3);
        up4Text = findViewById(R.id.up4);
        up5Text = findViewById(R.id.up5);
        up6Text = findViewById(R.id.up6);

        // Find the Buttons by ID
        up1Btn = findViewById(R.id.upgrade1);
        up2Btn = findViewById(R.id.upgrade2);
        up3Btn = findViewById(R.id.upgrade3);
        up4Btn = findViewById(R.id.upgrade4);
        up5Btn = findViewById(R.id.upgrade5);
        up6Btn = findViewById(R.id.upgrade6);

        // Restore data
        gold = mPreferences.getInt(GOLD_KEY, 0);
        goldText.setText("Gold " + Integer.toString(gold));
        clickIncome = mPreferences.getInt(CLICK_KEY, 1);
        passiveIncome = mPreferences.getInt(PASSIVE_KEY, 0);
        gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
        up1 = mPreferences.getInt(UP1_KEY, 0);
        up1Text.setText(Integer.toString(up1));
        up2 = mPreferences.getInt(UP2_KEY, 0);
        up2Text.setText(Integer.toString(up2));
        up3 = mPreferences.getInt(UP3_KEY, 0);
        up3Text.setText(Integer.toString(up3));
        up4 = mPreferences.getInt(UP4_KEY, 0);
        up4Text.setText(Integer.toString(up4));
        up5 = mPreferences.getInt(UP5_KEY, 0);
        up5Text.setText(Integer.toString(up5));
        up6 = mPreferences.getInt(UP6_KEY, 0);
        up6Text.setText(Integer.toString(up6));

        // Update Buttons text
        up1Btn.setText(Integer.toString((int) Math.round(100 * (1 + 0.1 * up1))));
        up2Btn.setText(Integer.toString((int) Math.round(500 * (1 + 0.1 * up2))));
        up3Btn.setText(Integer.toString((int) Math.round(1500 * (1 + 0.1 * up3))));
        up4Btn.setText(Integer.toString((int) Math.round(5000 * (1 + 0.1 * up4))));
        up5Btn.setText(Integer.toString((int) Math.round(10000 * (1 + 0.1 * up5))));
        up6Btn.setText(Integer.toString((int) Math.round(50000 * (1 + 0.1 * up6))));

        // Update gold every second
        mHandler.postDelayed(passiveIncrease, 1000);
    }

    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(GOLD_KEY, gold);
        preferencesEditor.putInt(CLICK_KEY, clickIncome);
        preferencesEditor.putInt(PASSIVE_KEY, passiveIncome);
        preferencesEditor.putInt(UP1_KEY, up1);
        preferencesEditor.putInt(UP2_KEY, up2);
        preferencesEditor.putInt(UP3_KEY, up3);
        preferencesEditor.putInt(UP4_KEY, up4);
        preferencesEditor.putInt(UP5_KEY, up5);
        preferencesEditor.putInt(UP6_KEY, up6);
        preferencesEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.dark_mode).setTitle(R.string.light_mode);
        }
        else {
            menu.findItem(R.id.dark_mode).setTitle(R.string.dark_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if the correct item was clicked
        if(item.getItemId() == R.id.dark_mode) {
            // Get the night mode state of the appp.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            // Set the theme mode for the restarted activity
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            // Recreate the activity for the theme change to take effect.
            recreate();
        }

        return true;
    }

    public void increaseByClick(View view) {
        gold += clickIncome;
        goldText.setText("Gold " + String.valueOf(gold));
    }

    // Update gold every second
    private Runnable passiveIncrease = new Runnable() {
        @Override
        public void run() {
            gold += passiveIncome;
            goldText.setText("Gold " + String.valueOf(gold));
            mHandler.postDelayed(passiveIncrease, 1000);
        }
    };

    public void upgrade1(View view) {
        if(gold >= (100 * (1 + 0.1 * up1))){
            clickIncome += 1;
            gold -= 100 * (1 + 0.1 * up1);
            goldText.setText("Gold " + String.valueOf(gold));
            up1++;
            up1Text.setText(Integer.toString(up1));
            up1Btn.setText(Integer.toString((int) Math.round(100 * (1 + 0.1 * up1))));
        }
    }

    public void upgrade2(View view) {
        if(gold >= (500 * (1 + 0.1 * up2))) {
            passiveIncome += 1;
            gold -= 500 * (1 + 0.1 * up2);
            gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
            up2++;
            up2Text.setText(Integer.toString(up2));
            up2Btn.setText(Integer.toString((int) Math.round(500 * (1 + 0.1 * up2))));
        }
    }

    public void upgrade3(View view) {
        if(gold >= (1500 * (1 + 0.1 * up3))) {
            passiveIncome += 2;
            gold -= 1500 * (1 + 0.1 * up3);
            gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
            up3++;
            up3Text.setText(Integer.toString(up3));
            up3Btn.setText(Integer.toString((int) Math.round(1500 * (1 + 0.1 * up3))));
        }
    }

    public void upgrade4(View view) {
        if(gold >= (5000 * (1 + 0.1 * up4))) {
            passiveIncome += 4;
            gold -= 5000 * (1 + 0.1 * up4);
            gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
            up4++;
            up4Text.setText(Integer.toString(up4));
            up4Btn.setText(Integer.toString((int) Math.round(5000 * (1 + 0.1 * up4))));
        }
    }

    public void upgrade5(View view) {
        if(gold >= (10000 * (1 + 0.1 * up5))) {
            passiveIncome += 10;
            gold -= 10000 * (1 + 0.1 * up5);
            gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
            up5++;
            up5Text.setText(Integer.toString(up5));
            up5Btn.setText(Integer.toString((int) Math.round(10000 * (1 + 0.1 * up5))));
        }
    }

    public void upgrade6(View view) {
        if(gold >= (50000 * (1 + 0.1 * up6))) {
            passiveIncome += 60;
            gold -= 50000 * (1 + 0.1 * up6);
            gold_per_s_Text.setText("Gold " + passiveIncome + "/s");
            up6++;
            up6Text.setText(Integer.toString(up6));
            up6Btn.setText(Integer.toString((int) Math.round(50000 * (1 + 0.1 * up6))));
        }
    }
}