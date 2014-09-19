package ru.vsu.csf.enlightened.accelerometertester;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity { //} implements SensorEventListener {

    //region Declarations
    private SensorManager msensorManager; //Менеджер сенсоров аппрата

    /*private float[] rotationMatrix;     //Матрица поворота
    private float[] accelData;           //Данные с акселерометра
    private float[] magnetData;       //Данные геомагнитного датчика
    private float[] OrientationData; //Матрица положения в пространстве

    private TextView xyView;
    private TextView xzView;
    private TextView zyView;*/

    private static final String TAG = "ru.vsu.csf.enlightened.accelerometertester.Main";

    private Button btnStart;
    private Button btnStop;

    private TextView doneView;
    private TextView infoTextView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            Log.i(TAG, "Recieved!");

            if (bundle != null) {
                int resultCode = bundle.getInt("result");
                int sum = bundle.getInt("SUM");

                Log.i(TAG, "Bundle is not null, the result is " + resultCode + "; the sum is " + sum);

                if (resultCode == RESULT_OK) {
                    //Toast.makeText(receiver, ("Task completed, the result is " + sum), Toast.LENGTH_LONG);
                    infoTextView.setText("The result is " + sum);
                }
                else {
                    infoTextView.setText("Task failed.");
                }
            }
        }
    };
    //endregion

    //region Standart Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //msensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        /*rotationMatrix = new float[16];
        accelData = new float[3];
        magnetData = new float[3];
        OrientationData = new float[3];

        xyView = (TextView) findViewById(R.id.xyValue);  //
        xzView = (TextView) findViewById(R.id.xzValue);  // Наши текстовые поля для вывода показаний
        zyView = (TextView) findViewById(R.id.zyValue);  //*/

        doneView = (TextView) findViewById(R.id.textViewDone);
        infoTextView = (TextView) findViewById(R.id.textViewInfo);
        btnStart =  (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestService.class);
                MainActivity.this.startService(intent);
                doneView.setText("Service Started!");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestService.class);
                MainActivity.this.stopService(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Sensor Stuff
    /*@Override
    public void onSensorChanged(SensorEvent event) {

        /*SensorManager.getRotationMatrix(rotationMatrix, null, accelData, magnetData); //Получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, OrientationData); //Получаем данные ориентации устройства в пространстве

        if((xyView==null)||(xzView==null)||(zyView==null)){  //Без этого работать отказалось.
            xyView = (TextView) findViewById(R.id.xyValue);
            xzView = (TextView) findViewById(R.id.xzValue);
            zyView = (TextView) findViewById(R.id.zyValue);
        }

        //Выводим результат
        xyView.setText(String.valueOf(Math.round(Math.toDegrees(OrientationData[0]))));
        xzView.setText(String.valueOf(Math.round(Math.toDegrees(OrientationData[1]))));
        zyView.setText(String.valueOf(Math.round(Math.toDegrees(OrientationData[2]))));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }*/


    @Override
    protected void onResume() {
        super.onResume();
        /*msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        msensorManager.registerListener(this, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );*/

        registerReceiver(receiver, new IntentFilter(TestService.TAG));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //msensorManager.unregisterListener(this);

        unregisterReceiver(receiver);
    }
    //endregion


    /*private void loadNewSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //Определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //Если акселерометр
            accelData = event.values.clone();
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //Если геомагнитный датчик
            magnetData = event.values.clone();
        }
    }*/

    public void onClick(View view) {


    }
}
