package au_edu_jcu_cp3406.quizapp.ui;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import au_edu_jcu_cp3406.quizapp.R;
import au_edu_jcu_cp3406.quizapp.data.QuestionRes;
import au_edu_jcu_cp3406.quizapp.data.SessionManagement;
import au_edu_jcu_cp3406.quizapp.utils.AudioUtil;
import au_edu_jcu_cp3406.quizapp.utils.ChronometerWithPause;
import au_edu_jcu_cp3406.quizapp.utils.Constants;
import au_edu_jcu_cp3406.quizapp.utils.DialogUtil;
import au_edu_jcu_cp3406.quizapp.utils.Utils;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener, DialogUtil.DialogCallback {

    TextView tvAction, tvQuestion, tvQuestionNumber, tvScore;
    //    ,tvShare
    ChronometerWithPause tvTimer;
    RadioGroup rdgOption;
    RadioButton rdOption1, rdOption2, rdOption3, rdOption4;
    ArrayList<QuestionRes> questionsArrayList = new ArrayList<>();
    private int questionNumber = 0;
    private int maxQuestion = 19;
    private int score = 0;
    private int correctQuestions = 0;
    private int wrongQuestions = 0;
    private long mLastClickTime = 0;
    SessionManagement sessionManagement;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private SensorManager sensorMan;
    private Sensor accelerometer;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                finish();
            }

            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onError(FacebookException error) {
                finish();
            }
        });

        initView();
    }

    public void showScore(String score, String correct) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .setQuote("Congratulations you have finished you Quiz \n" + "Score :" + score + " Correct answer :" + correct)
                    .build();
            shareDialog.show(linkContent);
        }
    }

    public void initView() {
        tvAction = findViewById(R.id.tvAction);
        tvScore = findViewById(R.id.tvScore);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        rdgOption = findViewById(R.id.rdgOption);
        rdOption1 = findViewById(R.id.rdOption1);
        rdOption2 = findViewById(R.id.rdOption2);
        rdOption3 = findViewById(R.id.rdOption3);
        rdOption4 = findViewById(R.id.rdOption4);
        tvTimer = findViewById(R.id.tvTimer);

        sessionManagement = new SessionManagement(this);
        tvAction.setOnClickListener(this);

        //ready the Question and set first Question
        getDataReady();
        setQuestion();

        tvScore.setText("" + score);
        tvQuestionNumber.setText("" + (questionNumber + 1) + "/20");

        //Timer
        tvTimer.start();
        //Play music
        if (sessionManagement.getBackMusic())
            AudioUtil.playAudio(this, R.raw.background);

        //Sensor
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private void getDataReady() {
        try {
            JSONObject obj = new JSONObject(Utils.readJSONFromAsset(this));
            JSONArray questionsArray = new JSONArray();
            String level = sessionManagement.getDifficultyLevel();
            String questionType = "questionsEasy";
            switch (level) {
                case Constants.DIFFICULTY_LEVEL_LOW:
                    questionType = "questionsEasy";
                    break;
                case Constants.DIFFICULTY_LEVEL_MEDIUM:
                    questionType = "questionsMedium";
                    break;
                case Constants.DIFFICULTY_LEVEL_HIGH:
                    questionType = "questionsTough";
                    break;
            }
            questionsArray = obj.getJSONArray(questionType);
            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject jsonObject = questionsArray.getJSONObject(i);
                String question = jsonObject.getString("question");
                String option1 = jsonObject.getString("option1");
                String option2 = jsonObject.getString("option2");
                String option3 = jsonObject.getString("option3");
                String option4 = jsonObject.getString("option4");
                String answer = jsonObject.getString("answer");
                questionsArrayList.add(new QuestionRes(question, option1, option2, option3, option4, answer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setQuestion() {
        if (questionNumber <= maxQuestion) {
            if (questionNumber == maxQuestion)
                tvAction.setText("Done");
            tvQuestion.setText(questionsArrayList.get(questionNumber).getQuestion());
            rdOption1.setText(questionsArrayList.get(questionNumber).getOption1());
            rdOption2.setText(questionsArrayList.get(questionNumber).getOption2());
            rdOption3.setText(questionsArrayList.get(questionNumber).getOption3());
            rdOption4.setText(questionsArrayList.get(questionNumber).getOption4());
            tvQuestionNumber.setText("" + (questionNumber + 1) + "/20");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.tvAction: {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                //One of option is selected
                if (rdgOption.getCheckedRadioButtonId() == -1) {
                    Utils.showToast(this, "First select option");
                } else {
                    //Question Number <= Max Question
                    if (questionNumber <= maxQuestion) {
                        //Common functionality
                        //Answer check and increase in the score
                        RadioButton selectedButton = findViewById(rdgOption.getCheckedRadioButtonId());
                        if (questionsArrayList.get(questionNumber).getAnswer().equals(selectedButton.getText())) {
                            score = score + 10;
                            correctQuestions = correctQuestions + 1;
                            tvScore.setText("" + score);
                        } else {
                            wrongQuestions = wrongQuestions + 1;
                        }
                        //Show Result
                        if (questionNumber == maxQuestion) {
                            int highestScore = sessionManagement.getHighestScore();
                            if (highestScore < score) {
                                sessionManagement.setHighestScore(score);
                            }
                            tvTimer.stop();
                            //   AudioUtil.stopPlayAudio(this);
                            DialogUtil.showResultDialog(
                                    this, score, correctQuestions, wrongQuestions, tvTimer.getText().toString(), this
                            );
                        }
                        //Display new question
                        else {
                            questionNumber += 1;
                            setQuestion();
                            unCheck();
                        }
                    }
                }
            }
            break;
        }
    }

    private void unCheck() {
        rdgOption.clearCheck();
    }

    //Screen Rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt("questionNumber", questionNumber);
        savedInstanceState.putInt("correctQuestions", correctQuestions);
        savedInstanceState.putInt("wrongQuestions", wrongQuestions);
        savedInstanceState.putInt("score", score);
        tvTimer.saveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        questionNumber = savedInstanceState.getInt("questionNumber");
        correctQuestions = savedInstanceState.getInt("correctQuestions");
        wrongQuestions = savedInstanceState.getInt("wrongQuestions");
        score = savedInstanceState.getInt("score");
        tvTimer.restoreInstanceState(savedInstanceState);
        tvScore.setText("" + score);
        tvQuestionNumber.setText("" + (questionNumber + 1) + "/20");
        setQuestion();
        super.onRestoreInstanceState(savedInstanceState);
    }

    //Sensor management
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Shake detection
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // motion you want to detect
            if (mAccel > 10) {
                tvAction.performClick();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        if (sessionManagement.getBackMusic())
            AudioUtil.stopPlayAudio(this);
        super.onDestroy();
    }


    @Override
    public void shareClick() {
        showScore("" + score, "" + correctQuestions);

    }
}


