package com.example.mizuno.prog_movie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MovieActivity extends ActionBarActivity {

    //onCreate内で定義すると、onClickListenerから参照できないため、ここで定義
    public VideoView video;
    public EditText counter;
    private AsyncHttp asynchttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        video = (VideoView) findViewById(R.id.videoView);
        //動画メディアの指定
        video.setVideoPath("android.resource://" + this.getPackageName() + "/" + R.raw.iwish20151214);

        Button b_stop = (Button) findViewById(R.id.Stop);
        Button b_start = (Button) findViewById(R.id.Start);
        Button b_check = (Button)findViewById(R.id.Check);
        counter = (EditText) findViewById(R.id.Counter);

        //停止ボタンが押された時の定義
        b_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.pause();

            }
        });
        //再生ボタンが押された時の定義
        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.start();
            }
        });

        //再生時間表示に関する処理
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter.post(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(String.valueOf((double) video.getCurrentPosition() / 1000) + "s");
                    }
                });
            }
        }, 0, 50);

        b_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Android_ID",android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
                asynchttp = new AsyncHttp(android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
                asynchttp.execute((double) video.getCurrentPosition() / 1000);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
