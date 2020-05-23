package au_edu_jcu_cp3406.quizapp.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import au_edu_jcu_cp3406.quizapp.R;

import static au_edu_jcu_cp3406.quizapp.utils.Utils.moveToNext;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView btPlay, tvHighestScore, tvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //        // Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "au_edu_jcu_cp3406.quizapp",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        initView();

    }



    public void initView() {
        btPlay = findViewById(R.id.btPlay);
        tvHighestScore = findViewById(R.id.tvHighestScore);
        tvSetting = findViewById(R.id.tvSetting);

        //Register the listener
        btPlay.setOnClickListener(this);
        tvHighestScore.setOnClickListener(this);
        tvSetting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          case  R.id.btPlay:

               moveToNext(HomeActivity.this, PlayActivity.class);
            break;

            case   R.id.tvHighestScore:
            moveToNext(HomeActivity.this, HighestScoreActivity.class);
            break;

            case    R.id.tvSetting:
            moveToNext(HomeActivity.this, SettingActivity.class);
            break;
        }
    }
}
