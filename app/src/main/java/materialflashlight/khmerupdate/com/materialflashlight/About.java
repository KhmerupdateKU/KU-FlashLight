package materialflashlight.khmerupdate.com.materialflashlight;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class About extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void fB(View view) {
        Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Dadadadroid") );
        startActivity(fb);
    }

    public void google(View view) {
        Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Dadadadroid") );
        startActivity(fb);
    }
}
