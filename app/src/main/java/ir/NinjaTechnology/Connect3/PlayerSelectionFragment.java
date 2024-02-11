package ir.NinjaTechnology.Connect3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class PlayerSelectionFragment extends Fragment implements View.OnClickListener {
    Toolbar toolbar;
    SharedPreferences preferences;
    View rootView;
    Spinner spinner_player1 ,spinner_player2;
    String[] color;
    int[] colors;
    Button btn_start;
    int player1Color, player2Color;
    String player1Name,player2Name;
    EditText player1_ed_name , player2_ed_name;
    GameMainFragment GameMainFragment;
    @Nullable
    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView= inflater.inflate(R.layout.player_selection_layout,container,false);
            preferences = Objects.requireNonNull(getContext()).getSharedPreferences("Connect3Pref", MODE_PRIVATE);
            GameMainFragment = new GameMainFragment();
            if(preferences.contains("Reset")){
                        preferences.edit().remove("Reset").apply();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.MainLayout_FrameLayout, GameMainFragment)
                        .commit();
                    }
            color = new String[]{"Red","Blue","Yellow"};
            colors = new int[]{R.color.red, R.color.blue,R.color.yellow};
            toolbar = rootView.findViewById(R.id.PlayerSelection_Toolbar);
            spinner_player1 = rootView.findViewById(R.id.spinner_player1);
            spinner_player2 = rootView.findViewById(R.id.spinner_player2);
            player1_ed_name = rootView.findViewById(R.id.player1_ed_name);
            player2_ed_name = rootView.findViewById(R.id.player2_ed_name);
            btn_start = rootView.findViewById(R.id.btn_Start);
            btn_start.setOnClickListener(this);
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(),colors,color);
            spinner_player1.setAdapter(spinnerAdapter);
            spinner_player2.setAdapter(spinnerAdapter);
            return rootView;
        }

    @Override
    public void onClick(View v) {
        //set Vars
        player1Name = player1_ed_name.getText().toString().trim();
        player2Name = player2_ed_name.getText().toString().trim();
        player1Color = (int) spinner_player1.getSelectedItem();
        player2Color = (int) spinner_player2.getSelectedItem();
        //CheckTrue
        if (player1Name.equals(player2Name)| player1Color==player2Color|player1Name.isEmpty()|player2Name.isEmpty()){
            Toast.makeText(getContext(), "Players name & color should be different.", Toast.LENGTH_SHORT).show();
        } else {
            preferences.edit().putInt("player1Color", player1Color)
                    .putInt("player2Color", player2Color)
                    .putString("player1Name",player1Name)
                    .putString("player2Name",player2Name).apply();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.MainLayout_FrameLayout, GameMainFragment)
                    .commit();
        }
    }
    public void dropIn(View view){
        GameMainFragment.dropIn(view);
    }
}