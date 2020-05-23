package au_edu_jcu_cp3406.quizapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import au_edu_jcu_cp3406.quizapp.R;
import au_edu_jcu_cp3406.quizapp.utils.Utils;
import au_edu_jcu_cp3406.quizapp.utils.MyAnimation;

public class SplashActivity extends AppCompatActivity {
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    public void initView() {
        ivImage = findViewById(R.id.ivImage);
        Animation anim = new MyAnimation(ivImage, 100F);
        anim.setDuration(1900);
        ivImage.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Utils.moveToNext(SplashActivity.this, HomeActivity.class);
                finish();
            }
        }, 2000);

    }

}


