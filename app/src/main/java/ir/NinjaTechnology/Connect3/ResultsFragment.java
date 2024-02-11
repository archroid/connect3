package ir.NinjaTechnology.Connect3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ResultsFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Button btn_TryAgain, btn_GameReset;
    TextView tv_Player1Name,tv_Player2Name, tv_Player1WinsNum,tv_Player2WinsNum,tv_Draw, tv_WinnerName;
    int player1Color, player2Color;
    String player1Name,player2Name;
    int Winner;
    int player1WinsNum,player2WinsNum,Draw;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.result_layout,container,false);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("Connect3Pref", MODE_PRIVATE);
        initViews();
        loadData();
        SetData();
        return rootView;
    }
    private void initViews() {
        tv_Player1Name = rootView.findViewById(R.id.tv_player1Name);
        tv_Player2Name = rootView.findViewById(R.id.tv_player2Name);
        tv_Player1WinsNum = rootView.findViewById(R.id.tv_player1winsNumResult);
        tv_Player2WinsNum = rootView.findViewById(R.id.tv_player2winsNumResult);
        btn_GameReset = rootView.findViewById(R.id.btn_ResetGameResult);
        tv_Draw = rootView.findViewById(R.id.tv_DrawNumResult);
        tv_WinnerName = rootView.findViewById(R.id.tv_WinnerName);
        btn_TryAgain = rootView.findViewById(R.id.btn_tryagain);
        btn_TryAgain.setOnClickListener(this);
        btn_GameReset.setOnClickListener(this);
    }

    private void loadData() {
        player1Name = preferences.getString("player1Name","Player1");
        player2Name = preferences.getString("player2Name","Player2");
        player1Color = preferences.getInt("player1Color" , 0);
        player2Color = preferences.getInt("player2Color" , 0);
        Winner = preferences.getInt("Winner",1);
        player1WinsNum = preferences.getInt("player1WinsNum",0);
        player2WinsNum = preferences.getInt("player2WinsNum",0);
        Draw = preferences.getInt("Draw" , 0);
    }

    @SuppressLint("SetTextI18n")
    private void SetData() {
        tv_Player1Name.setTextColor(getResources().getColor(player1Color));
        tv_Player2Name.setTextColor(getResources().getColor(player2Color));
        tv_Player1WinsNum.setTextColor(getResources().getColor(player1Color));
        tv_Player2WinsNum.setTextColor(getResources().getColor(player2Color));
        tv_Player1Name.setText(player1Name);
        tv_Player2Name.setText(player2Name);
        tv_Player1WinsNum.setText(Integer.toString(player1WinsNum));
        tv_Player2WinsNum.setText(Integer.toString(player2WinsNum));
        tv_Draw.setText(Integer.toString(Draw));
        if (Winner==1){
            tv_WinnerName.setText(player1Name);
            tv_WinnerName.setTextColor(getResources().getColor(player1Color));
        }
        if (Winner==2){
            tv_WinnerName.setText(player2Name);
            tv_WinnerName.setTextColor(getResources().getColor(player2Color));
        }
        if(Winner == 0){
            tv_WinnerName.setText("No player");
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btn_TryAgain.getId()){
            Intent intent = new Intent(getActivity(),IntroductionActivity.class);
            preferences.edit().putInt("Reset",0).apply();
            startActivity(intent);
            getActivity().finish();
        }
        if(v.getId() == btn_GameReset.getId()){
            preferences.edit().clear().apply();
            Intent intent = new Intent(getActivity(),LoadingActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }
}
