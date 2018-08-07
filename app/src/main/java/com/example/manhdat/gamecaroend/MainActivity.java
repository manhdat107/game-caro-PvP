package com.example.manhdat.gamecaroend;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int length;
    public boolean sound = true;
    MediaPlayer nhacnen = new MediaPlayer();
    private View setfull;
    Button btnPlay, btnExit;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setfull = getWindow().getDecorView();
        btnPlay = (Button) findViewById( R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Second.class);
                startActivity(i);
            }
        });
        nhacnen = MediaPlayer.create(this, R.raw.song1);
        nhacnen.start();
        final ImageView imgAmthanh = (ImageView) findViewById(R.id.imgAmthanh);
        imgAmthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound){
                    imgAmthanh.setImageResource(R.drawable.soundoff);
                    nhacnen.pause();
                    length = nhacnen.getCurrentPosition();
                    sound = false;
                }else {
                    imgAmthanh.setImageResource(R.drawable.soundon);
                    nhacnen.seekTo(length);
                    nhacnen.start();
                    sound = true;
                }
            }
        });

        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit")
                        .setMessage("Ban co muon thoat khoi Game ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                System.exit(1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        setfull.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}
