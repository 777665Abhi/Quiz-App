package au_edu_jcu_cp3406.quizapp.ui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import au_edu_jcu_cp3406.quizapp.R;
import au_edu_jcu_cp3406.quizapp.data.SessionManagement;


public class HighestScoreActivity extends AppCompatActivity {
    TextView tvHighestScore;
    LinearLayout lnScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_score);
        initView();
    }


    public void initView() {
        tvHighestScore = findViewById(R.id.tvHighestScore);
        lnScore = findViewById(R.id.lnScore);

        //Setting highest score
        int highestScore = new SessionManagement(this).getHighestScore();
        tvHighestScore.setText("" + highestScore);

        //Animation of view
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        lnScore.startAnimation(animation1);
    }
}
