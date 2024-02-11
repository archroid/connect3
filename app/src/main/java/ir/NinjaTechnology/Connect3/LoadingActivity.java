package ir.NinjaTechnology.Connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {
    SharedPreferences preferences , intropref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        preferences = getSharedPreferences("Connect3Pref", MODE_PRIVATE);
        intropref = getSharedPreferences("intropref",MODE_PRIVATE);
        SetTheme();
        Counter();
        }

    private void ResetAllData() {
        preferences.edit().clear().apply();
    }

    //Count and Move
    private void Counter() {
        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished){}
            @Override
            public void onFinish() {
                //Go to introduction activity
                if (! intropref.contains("FirstTime")) {
                    intropref.edit().putString("FirstTime","FirstTime").apply();
                    Intent intent = new Intent(LoadingActivity.this, IntroductionActivity.class);
                    startActivity(intent);
                    finish();
                }
                //Go to Main activity
                else {
                    ResetAllData();
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    //Change Status and Navigation Bar Color
    private void SetTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String AppTheme = preferences.getString("Theme","light");
            if (AppTheme.equals("light")){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR| View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                getWindow().setStatusBarColor(ContextCompat.getColor(LoadingActivity.this, R.color.white));
                getWindow().setNavigationBarColor(ContextCompat.getColor(LoadingActivity.this, R.color.white));
            }
            else if (AppTheme.equals("dark")){
                TextView tv_appname = findViewById(R.id.tv_appname);
                RelativeLayout MainLayout_loadingActivity = findViewById(R.id.MainLayout_loadingActivity);
                tv_appname.setTextColor(getResources().getColor(R.color.black));
                MainLayout_loadingActivity.setBackgroundColor(getResources().getColor(R.color.black));
                getWindow().setStatusBarColor(ContextCompat.getColor(LoadingActivity.this, R.color.dark_background));
                getWindow().setNavigationBarColor(ContextCompat.getColor(LoadingActivity.this, R.color.dark_background));
            }
        }
    }
}

