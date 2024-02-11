package ir.NinjaTechnology.Connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    PlayerSelectionFragment playerSelectionFragment;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("prefrences", MODE_PRIVATE);
        frameLayout = findViewById(R.id.MainLayout_FrameLayout);
        //Fragments
        playerSelectionFragment = new PlayerSelectionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.MainLayout_FrameLayout, playerSelectionFragment).commit();
        SetTheme();
    }

    //Change Status and Navigation Bar Color
    private void SetTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String AppTheme = preferences.getString("Theme","light");
            if (AppTheme.equals("light")){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));
            }
             else if (AppTheme.equals("dark")) {
                TextView tv_appname = findViewById(R.id.tv_appname);
                RelativeLayout MainLayout_loadingActivity = findViewById(R.id.MainLayout_loadingActivity);
                tv_appname.setTextColor(getResources().getColor(R.color.black));
                MainLayout_loadingActivity.setBackgroundColor(getResources().getColor(R.color.dark_background));
                getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.dark_background));
                getWindow().setNavigationBarColor(ContextCompat.getColor(MainActivity.this, R.color.dark_background));
            }
        }
    }

    public void dropIn(View view) {
        playerSelectionFragment.dropIn(view);
    }
}

