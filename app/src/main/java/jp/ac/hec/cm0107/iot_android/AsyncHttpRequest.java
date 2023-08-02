package jp.ac.hec.cm0107.iot_android;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Stack;

import javax.net.ssl.HttpsURLConnection;

// https://app-cm-jec.lolipop.io/iot/getJson.php?param=CM08

public class AsyncHttpRequest implements Runnable {
    private Handler handler;
    private MainActivity mainActivity;
    private String urlStr;
    private String resStr;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd(EE) HH:mm:ss");

    public AsyncHttpRequest(Handler handler, MainActivity mainActivity, String urlStr) {
        this.handler = handler;
        this.mainActivity = mainActivity;
        this.urlStr = urlStr;
    }

    @Override
    public void run() {
        Log.i("RssReader", "BackgroundTask start...");
        // バックグラウンド(非同期)で実行する処理を記述する
        // HTTP 通信の処理
        resStr = "取得に失敗しました。";
        HttpsURLConnection connection = null;

        try {
            //接続先 Web サイトの URL の文字列を URL クラスのオブジェクトにする
            URL url = new URL(urlStr);
            //接続先 Web サイトへの接続を開始
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            //接続先から取得した InputStream を文字列データにする
            resStr = inputStreamToString(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AsyncHttpRequest", e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        // 非同期処理後の処理
        handler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        });
    }

    private String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

//
    private void onPostExecute() {
        Log.i("RssReader", "onPostExecute start...");
        // 非同期処理後に実行する処理を記述する
        Stack<IoTItem> ary = JsonHelper.parseJson(resStr);
        double lastData = ary.lastElement().getData1();
        Log.i("lastItem", String.valueOf(lastData));
        if (lastData == 4.0) {
            Intent intent = new Intent(mainActivity.getApplicationContext(),OverlayShowActivity.class);
            mainActivity.startActivity(intent);
        }
    }

}