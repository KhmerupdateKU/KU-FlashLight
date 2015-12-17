package materialflashlight.khmerupdate.com.materialflashlight;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ZoomButton;



public class MainActivity extends Activity {


    ImageButton imageButton;
	Parameters params;
	MediaPlayer mp;
	private Camera camera;
	private boolean isFlashOn;
	private boolean hasFlash;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        imageButton = (ImageButton) findViewById(R.id.btnON);
		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		if (!hasFlash) {
			AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
			.create();
			alert.setTitle("Error");
			alert.setMessage("Sorry Your Devices Don't Have Flash Camera!!");
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alert.show();
			return;
		}
		getCamera();
		toggleButtonImage();
		imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                    Toast.makeText(getApplicationContext(), R.string.ligh_turn_of, Toast.LENGTH_SHORT).show();
                } else {
                    // turn on flash
                    turnOnFlash();
                    Toast.makeText(getApplicationContext(), R.string.ligh_turn_on, Toast.LENGTH_SHORT).show();
                }
            }
        });
	}

	//To Get Camera
	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Erro Camera. Open Camera Fial. Error: ", e.getMessage());
			}
		}
	}
    //Turning on flash by Using FLASH_MODE_TORCH
	private void turnOnFlash() {
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;
			toggleButtonImage();
		}
	}

	//Turning off flash by Using FLASH_MODE_OFF
	private void turnOffFlash() {
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}
			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;
			toggleButtonImage();
		}
	}
	private void playSound(){
		if(isFlashOn){
			mp = MediaPlayer.create(MainActivity.this, R.raw.off_sound);
		}else{
			mp = MediaPlayer.create(MainActivity.this, R.raw.on_sound);
		}
		mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
		mp.start();
	}
	private void toggleButtonImage(){
		if(isFlashOn){
			imageButton.setImageResource(R.drawable.btnon);
		}else{
			imageButton.setImageResource(R.drawable.btnoff);
		}

	}

//android Life cycle app by Dadada(KU)
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// on pause turn off the flash
		turnOffFlash();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// on resume turn on the flash
		if(hasFlash)
			turnOnFlash();
        Toast.makeText(this, R.string.ligh_turn_on, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// on starting the app get the camera params
		getCamera();
	}
	@Override
	protected void onStop() {
		super.onStop();

		// on stop release the camera
		if (camera != null) {
			camera.release();
			camera = null;
		}
    }

    //Exit App by KU
    @Override
    public void onBackPressed() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
    //Menu Option By KU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1,Menu.NONE,R.string.about);
        menu.add(Menu.NONE, 2,Menu.NONE,R.string.contact_me);
        menu.add(Menu.NONE, 3,Menu.NONE,R.string.web);
        menu.add(Menu.NONE, 4,Menu.NONE,R.string.exitapp);



        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case 1:
           Intent intent = new Intent(this,About.class);
               startActivity(intent);
               return true;
           case 2:

               Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:093256456"));
               startActivity(call);
        return true;
           case 3:
               Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://droidkh.com/"));
               startActivity(web);
           case 4:

               Dialog dialog = new AlertDialog.Builder(this)
                       .setTitle(R.string.exit)
                       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               finish();
                           }
                       })
                       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       }).create();
               dialog.show();

               return true;

        }

        return super.onOptionsItemSelected(item);
    }

}