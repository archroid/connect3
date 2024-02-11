package ir.NinjaTechnology.Connect3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class GameMainFragment extends Fragment implements View.OnClickListener {

    SharedPreferences preferences;
    private View rootView;
    Toolbar toolbar;
    public TextView tv_player1_name , tv_player2_name , tv_player1winsNum , tv_player2winsNum , tv_drawNum;
    ResultsFragment resultsFragment;
    public Button btn_reset , btn_resetGameResult;
    String Player1Name,Player2Name;
    int Player1Color,Player2Color;
    int NOT_PLAYED = 0;
    int PLAYER1 = 1;
    int PLAYER2 = 2;
    int ActivePlayer;
    int NO_WINNER = 3;
    int player1WinsNum,player2WinsNum,Draw;
    int [] GameState = {NOT_PLAYED,NOT_PLAYED,NOT_PLAYED,
            NOT_PLAYED,NOT_PLAYED,NOT_PLAYED,
            NOT_PLAYED,NOT_PLAYED,NOT_PLAYED};
    int[][] winnigPositions={
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}};
    Boolean lock=false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.game_main_layout,container,false);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("Connect3Pref", MODE_PRIVATE);
        resultsFragment = new ResultsFragment();
        loadResults();
        SetViews();
        initPlayersAndData();
        return rootView;
    }



    private void SetViews() {
        tv_player1_name = rootView.findViewById(R.id.tv_player1_name);
        tv_player2_name = rootView.findViewById(R.id.tv_player2_name);
        tv_player1winsNum = rootView.findViewById(R.id.tv_player1winsNum);
        tv_player2winsNum = rootView.findViewById(R.id.tv_player2winsNum);
        tv_drawNum = rootView.findViewById(R.id.tv_drawNum);
        btn_reset = rootView.findViewById(R.id.btn_reset);
        btn_resetGameResult = rootView.findViewById(R.id.btn_resetGameMain);
        toolbar = rootView.findViewById(R.id.gamemain_Toolbar);
        btn_reset.setOnClickListener(this);
        btn_resetGameResult.setOnClickListener(this);
        }
        //set players name and color and TV colors
    @SuppressLint("SetTextI18n")
    private void initPlayersAndData() {
        Player1Name = preferences.getString("player1Name" , "");
        Player2Name = preferences.getString("player2Name" , "");
        Player1Color = preferences.getInt("player1Color" , 0);
        Player2Color = preferences.getInt("player2Color" , 0);
        //set Names
        tv_player1_name.setText(Player1Name);
        tv_player2_name.setText(Player2Name);
        //Set Colors
        tv_player1_name.setTextColor(getResources().getColor(Player1Color));
        tv_player1winsNum.setTextColor(getResources().getColor(Player1Color));
        tv_player2_name.setTextColor(getResources().getColor(Player2Color));
        tv_player2winsNum.setTextColor(getResources().getColor(Player2Color));
        //Set Wins Num
        tv_player1winsNum.setText(Integer.toString(player1WinsNum));
        tv_player2winsNum.setText(Integer.toString(player2WinsNum));
        tv_drawNum.setText(Integer.toString(Draw));
    }
        //Om click listener
        @Override
    public void onClick(View v) {
        if(v.getId() == btn_reset.getId()){
            Intent intent = new Intent(getActivity(),IntroductionActivity.class);
            preferences.edit().putInt("Reset",0).apply();
            startActivity(intent);
            getActivity().finish();
        }
        if(v.getId() == btn_resetGameResult.getId()){
            Toast.makeText(getContext(), "s", Toast.LENGTH_SHORT).show();
            preferences.edit().clear().apply();
            Intent intent = new Intent(getActivity(),LoadingActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
    //On ImageView Clicked
    public void dropIn(View view) {
        //check Lock
        if (lock)
            return;

        ImageView img = (ImageView) view;
        int Clicked_Tag = Integer.parseInt((String) img.getTag());
        if(GameState[Clicked_Tag]== NOT_PLAYED){
            switch (ActivePlayer){
                case 1 :
                    if (Player1Color== R.color.red){ img.setImageResource(R.drawable.game_red_player);}
                    else if (Player1Color==R.color.yellow){ img.setImageResource(R.drawable.game_yellow_player);}
                    else if (Player1Color==R.color.blue){ img.setImageResource(R.drawable.game_blue_player);}
                    img.animate().alpha(1).setDuration(500);
                    ActivePlayer = PLAYER2;
                    GameState[Clicked_Tag] = PLAYER1;
                    if (CheckWinner() == PLAYER1){ SaveAndExit(1); }
                    IsDraw();

                    break;
                case 2:
                    if (Player2Color== R.color.red){ img.setImageResource(R.drawable.game_red_player);}
                    else if (Player2Color==R.color.yellow){ img.setImageResource(R.drawable.game_yellow_player);}
                    else if (Player2Color==R.color.blue){ img.setImageResource(R.drawable.game_blue_player);}
                    img.animate().alpha(1).setDuration(500);
                    ActivePlayer = PLAYER1;
                    GameState[Clicked_Tag] = PLAYER2;
                    if (CheckWinner() == PLAYER2){ SaveAndExit(2); }
                    IsDraw();
                    break;
            }
        }
    }

    // Save all results and move to results fragment
    private void SaveAndExit(int PlayerNum) {
        lock = true;
        if(PlayerNum==1){
            preferences.edit().putInt("player1WinsNum",player1WinsNum+1)
                .putInt("Winner",1)
                .putInt("ActivePlayer",PLAYER2).apply();
        }
        if(PlayerNum==2){
            preferences.edit().putInt("player2WinsNum",player2WinsNum+1)
                .putInt("Winner",2)
                .putInt("ActivePlayer",PLAYER1).apply();
        }
        if(PlayerNum==0){
            preferences.edit().putInt("Winner",0)
                    .putInt("Draw",Draw+1)
                    .apply();
            if (ActivePlayer==PLAYER1){
                preferences.edit().putInt("ActivePlayer",PLAYER2).apply();
            }
            if (ActivePlayer==PLAYER2){
                preferences.edit().putInt("ActivePlayer",PLAYER1).apply();
            }

        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.MainLayout_FrameLayout,resultsFragment)
                .commit();
    }
    // Check If there is a winner or no
    public int CheckWinner() {
        for (int[] positions : winnigPositions) {
            if (GameState[positions[0]] == GameState[positions[1]] &&
                    GameState[positions[1]] == GameState[positions[2]] &&
                    GameState[positions[0]] != NOT_PLAYED) {
                return GameState[positions[0]];
            }
        }
        return NO_WINNER;
    }
    public void IsDraw(){
        if(CheckWinner()==NO_WINNER){
            if(filled()){
                SaveAndExit(0);
            }
        }
    }
    public boolean filled(){
        for (int i = 0; i < GameState.length; i++) {
            if (GameState[i] == NOT_PLAYED) {
                return false;
            }
        }
        return true;
    }
    //Load Recent Results from prefs
    private void loadResults() {
        player1WinsNum = preferences.getInt("player1WinsNum",0);
        player2WinsNum = preferences.getInt("player2WinsNum",0);
        Draw = preferences.getInt("Draw",0);
        ActivePlayer = preferences.getInt("ActivePlayer",PLAYER1);

    }

}
