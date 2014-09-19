package ru.vsu.csf.enlightened.accelerometertester;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by enlightenedcsf on 19.09.14.
 */
public class TestService extends IntentService {

    public static final String TAG = "ru.vsu.csf.enlightened.accelerometertester";

    private int result = Activity.RESULT_CANCELED;
    public static int SUM;

    public TestService() {
        super("TestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Intent Service started");

        try {
            int p = 1;
            for (int i = 0; i < 100; i++) {
                p += i;
            }
            SUM = p;
            result = Activity.RESULT_OK;

            Log.i(TAG, "The SUM is " + SUM);

            this.publishResults(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void publishResults(int result) {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        intent.putExtra("SUM", SUM);

        Log.i(TAG, "Exporting results..");

        sendBroadcast(intent);
    }


}
