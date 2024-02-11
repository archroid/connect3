package ir.NinjaTechnology.Connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Objects;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        SharedPreferences preferences = getSharedPreferences("Connect3Pref", MODE_PRIVATE);
        if(preferences.contains("Reset")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
