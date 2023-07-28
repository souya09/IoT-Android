package jp.ac.hec.cm0107.iot_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.start_button);

        btnStart.setOnClickListener(v -> {
            TextView txtDesc = findViewById(R.id.txtDesc);
            txtDesc.setText("現在、監視中です");
        });

      //  startActivity(new Intent(MainActivity.this, OverlayShowActivity.class));

    }
}
