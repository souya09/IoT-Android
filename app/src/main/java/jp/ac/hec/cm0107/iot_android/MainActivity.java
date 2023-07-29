package jp.ac.hec.cm0107.iot_android;


    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.os.HandlerCompat;


    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;



public class MainActivity extends AppCompatActivity {
    private ExecutorService executorService;
    private Handler handler;
    private static final long INTERVAL = 30 * 1000; // 1分（1分 = 60秒 * 1000ミリ秒）

    private Handler  subHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            subHandler.postDelayed(this, INTERVAL);
            Toast.makeText(MainActivity.this, "run", Toast.LENGTH_SHORT).show();
            new UriBuild();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        subHandler.postDelayed(runnable, INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subHandler.removeCallbacks(runnable);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = findViewById(R.id.start_button);

        btnStart.setOnClickListener(v -> {
            TextView txtDesc = findViewById(R.id.txtDesc);
            txtDesc.setText("現在、監視中です");
        });

        Looper mainLooper = getMainLooper();
        handler = HandlerCompat.createAsync(mainLooper);
        executorService = Executors.newSingleThreadExecutor();
        new UriBuild();
    }

    private class UriBuild {
        public  UriBuild() {
// https://app-cm-jec.lolipop.io/iot/getJson.php?param=CM08
            Uri.Builder uriBuilder = new Uri.Builder();

            uriBuilder.scheme("https");
            uriBuilder.authority("app-cm-jec.lolipop.io");
            uriBuilder.path("iot/getJson.php");
            uriBuilder.appendQueryParameter("param", "CM08");

            Log.i("MainActivity", uriBuilder.build().toString());

            AsyncHttpRequest asyncHttpRequest =
                    new AsyncHttpRequest(handler, MainActivity.this, uriBuilder.toString());
            executorService.submit(asyncHttpRequest);
        }
    }
}
