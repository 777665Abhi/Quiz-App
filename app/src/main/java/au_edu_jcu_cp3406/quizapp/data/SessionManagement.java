package au_edu_jcu_cp3406.quizapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import au_edu_jcu_cp3406.quizapp.utils.Constants;

public class SessionManagement {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SessionManagement(Context activity) {
        preferences = activity.getSharedPreferences("QuizApp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    //   Theme
    public void setTheme(Boolean theme) {
        editor.putBoolean(Constants.THEME_TYPE, theme);
        editor.commit();
    }

    public Boolean getTheme()  {
        return preferences.getBoolean(Constants.THEME_TYPE, Constants.DARK_THEME_OFF);
    }

    // BackGround music
    public void setBackMusic(Boolean music ) {
        editor.putBoolean(Constants.BACK_MUSIC, music);
        editor.commit();
    }

    public Boolean getBackMusic()  {
        return preferences.getBoolean(Constants.BACK_MUSIC, Constants.BACK_MUSIC_ON);
    }

    // Difficulty Level
    public void setDifficultyLevel(String level ) {
        editor.putString(Constants.DIFFICULTY_LEVEL, level);
        editor.commit();
    }

    public String getDifficultyLevel() {
        return preferences.getString(Constants.DIFFICULTY_LEVEL, Constants.DIFFICULTY_LEVEL_LOW);
    }

    // Highest Score
    public void setHighestScore(int score) {
        editor.putInt(Constants.HIGHEST_SCORE, score);
        editor.commit();
    }

    public int getHighestScore() {
        return preferences.getInt(Constants.HIGHEST_SCORE, Constants.DEFAULT_HIGHEST_SCORE);
    }
}