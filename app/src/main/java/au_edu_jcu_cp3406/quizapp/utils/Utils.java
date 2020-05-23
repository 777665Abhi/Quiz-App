package au_edu_jcu_cp3406.quizapp.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static void moveToNext(Context source , Class<?> destination)   {
        Intent i = new Intent(source, destination);
        source.startActivity(i);
    }

    public static  void showToast(Context context,String msg )
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String readJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
