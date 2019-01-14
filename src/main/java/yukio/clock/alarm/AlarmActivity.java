package trikita.talalarmo.alarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import trikita.jedux.Action;
import trikita.talalarmo.Actions;
import trikita.talalarmo.App;
import trikita.talalarmo.R;
import trikita.talalarmo.ui.Theme;

public class AlarmActivity extends Activity {

    EditText et1;
    TextView tv1;
    private PowerManager.WakeLock mWakeLock;

    final int min = 20;
    final int max = 80;
    int random;
    int random2;
    String ranSumm;

    int inputResult;

   @SuppressLint("SetTextI18n")
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "AlarmActivity");
        mWakeLock.acquire();
        random = new Random().nextInt((max - min) + 1) + min;
        random2 = new Random().nextInt((max - min) + 1) + min;
        ranSumm = String.valueOf(random+random2);

        setContentView(R.layout.activity_alarm);

       tv1 = (TextView)findViewById(R.id.textView2);
       et1 = (EditText) findViewById(R.id.editText2);

       tv1.setText(random+ "  +  "+random2);

       et1.setOnKeyListener(new View.OnKeyListener()
                                 {
                                     public boolean onKey(View v, int keyCode, KeyEvent event)
                                     {
                                         if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                 (keyCode == KeyEvent.KEYCODE_ENTER))
                                         {

                                             if(et1.getText() != null){
                                                 String temp = et1.getText().toString();
                                                 //  inputResult = Integer.valueOf(temp);

                                                 if(ranSumm.equals(temp)){
                                                     stopAlarm();
                                                 }
                                             }
                                             return true;
                                         }
                                         return false;
                                     }
                                 }
       );

       if(et1.getText() != null){
           String temp = et1.getText().toString();
         //  inputResult = Integer.valueOf(temp);

           if(ranSumm.equals(temp)){
               stopAlarm();
           }
       }
    }

    @Override
    protected void onUserLeaveHint() {
        stopAlarm();
        super.onUserLeaveHint();
    }

@Override
public void onBackPressed() {
 stopAlarm();
 super.onBackPressed();
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }

    private void stopAlarm() {
        App.dispatch(new Action<>(Actions.Alarm.DISMISS));
        finish();
    }


}
