package com.example.mizuno.prog_movie;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mizuno on 2016/02/27.
 */
public class AsyncHttp extends AsyncTask<Double, Integer, Boolean> {

    private String id;

    public AsyncHttp(String id) {

        this.id = id;
        //Log.d("Constructor", this.id);
    }

    @Override
    protected Boolean doInBackground(Double... params) {
        //URLの設定（AndroidからホストPCのローカルに接続するには10.0.2.2を利用する
        String urlinput = null;
        String postDataSample = null;
        boolean flg = false;
        try {

            //Log.d("Check_ID", String.valueOf(params[0]));
            urlinput = "http://j11000.sangi01.net/cakephp/movies/add";
            postDataSample = "device_id="+this.id+"&check="+params[0];

            //HttpURLConnectionの利用手順
           /*
           1.url.openConnection()を呼び出し接続開始
           取得できる型はURLConnection型なので、キャストする必要あり
           2.ヘッダーの設定
           3.bodyを設定する場合はHttpURLConnection.setDoOutputにbodyが存在することを明示
           4.connect()で接続を確立する
           5.レスポンスをgetInputStream()で取得する
           * */
            URL url = new URL(urlinput);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);


            //POSTパラメータ設定
            OutputStream out = urlConnection.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();


            //レスポンスを受け取る
            //InputStream is = urlConnection.getInputStream();
            urlConnection.getInputStream();

            flg = true;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flg;
    }
}
