package au_edu_jcu_cp3406.quizapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import au_edu_jcu_cp3406.quizapp.R;


public class DialogUtil {
    public static void showResultDialog(
            Activity context,
            int score,
            int correctQuestions,
            int wrongQuestions,
            String time,
            DialogCallback mcallback
    ) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_result);
        TextView tvYourScore = dialog.findViewById(R.id.tvYourScore);
        TextView tvCorrectQuestions = dialog.findViewById(R.id.tvCorrectQuestions);
        TextView tvWrongQuestions = dialog.findViewById(R.id.tvWrongQuestions);
        TextView tvTimeTaken = dialog.findViewById(R.id.tvTimeTaken);

        tvYourScore.setText("" + score);
        tvCorrectQuestions.setText("" + correctQuestions);
        tvWrongQuestions.setText("" + wrongQuestions);
        tvTimeTaken.setText("" + time);
        TextView dialogButton = dialog.findViewById(R.id.tvDone);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                context.finish();
            }
        });

        TextView dialogShareButton = dialog.findViewById(R.id.tvShare);
        dialogShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mcallback.shareClick();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public interface DialogCallback {
        public void shareClick();
    }
}
