package au_edu_jcu_cp3406.quizapp.utils;

import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import static au_edu_jcu_cp3406.quizapp.utils.Constants.*;

public class ThemeHelper {
    public void applyTheme(String themePref) {
        switch (themePref) {
            case  LIGHT_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case DARK_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
        }
    }
}
