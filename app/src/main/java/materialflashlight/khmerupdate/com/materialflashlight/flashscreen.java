package materialflashlight.khmerupdate.com.materialflashlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by KU on 3/13/2015.
 */
public class flashscreen extends Activity {
    @Override
    // Disable Actionbar
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_screen);
        Thread logoTimer = new Thread(){
            public void run(){
                try {
                    sleep(1000);
                    startActivity(new Intent(flashscreen.this, MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }
                }
        };
            logoTimer.start();

    }
}
