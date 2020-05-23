package au_edu_jcu_cp3406.quizapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import au_edu_jcu_cp3406.quizapp.R;
import au_edu_jcu_cp3406.quizapp.data.SessionManagement;
import au_edu_jcu_cp3406.quizapp.utils.Constants;
import au_edu_jcu_cp3406.quizapp.utils.ThemeHelper;


public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    Switch switchBackgroundMusic, switchDarkTheme;
    Spinner spLevel;
    SessionManagement sessionManagement;
    ThemeHelper themeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    public void initView() {
        //Initialisations
        switchBackgroundMusic = findViewById(R.id.switchBackgroundMusic);
        switchDarkTheme = findViewById(R.id.switchDarkTheme);
        spLevel = findViewById(R.id.spLevel);

        sessionManagement = new SessionManagement(this);
        themeHelper = new ThemeHelper();

        //Register listener
        switchBackgroundMusic.setOnCheckedChangeListener(this);
        switchDarkTheme.setOnCheckedChangeListener(this);
        spLevel.setOnItemSelectedListener(this);


        //Default Ui
        switchBackgroundMusic.setChecked(sessionManagement.getBackMusic());
        switchDarkTheme.setChecked(sessionManagement.getTheme());
        switch (sessionManagement.getDifficultyLevel()) {
            case Constants.DIFFICULTY_LEVEL_LOW: {
                spLevel.setSelection(0);
            }
            break;
            case Constants.DIFFICULTY_LEVEL_MEDIUM: {
                spLevel.setSelection(1);
            }
            break;
            case Constants.DIFFICULTY_LEVEL_HIGH: {
                spLevel.setSelection(2);
            }
            break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchBackgroundMusic: {
                if (isChecked) {
                    sessionManagement.setBackMusic(Constants.BACK_MUSIC_ON);
                } else {
                    sessionManagement.setBackMusic(Constants.BACK_MUSIC_OFF);
                }
            }
            break;

            case R.id.switchDarkTheme: {
                if (isChecked) {
                    sessionManagement.setTheme(Constants.DARK_THEME_ON);
                    themeHelper.applyTheme(Constants.DARK_MODE);
                } else {
                    sessionManagement.setTheme(Constants.DARK_THEME_OFF);
                    themeHelper.applyTheme(Constants.LIGHT_MODE);
                }
            }
            break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getSelectedItemPosition()) {
            case 0:
                sessionManagement.setDifficultyLevel(Constants.DIFFICULTY_LEVEL_LOW);
                break;
            case 1:
                sessionManagement.setDifficultyLevel(Constants.DIFFICULTY_LEVEL_MEDIUM);
                break;
            case 2:
                sessionManagement.setDifficultyLevel(Constants.DIFFICULTY_LEVEL_HIGH);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
