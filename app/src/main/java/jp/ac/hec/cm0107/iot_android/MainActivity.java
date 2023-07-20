package jp.ac.hec.cm0107.iot_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ファイルディレクトリのパスを取得
                File fileDir = getExternalFilesDir(null);

                // ファイルディレクトリ内のすべてのファイルを削除
                if (fileDir != null) {
                    deleteDataRecursive(fileDir);
                }
            }
        });
    }

    // 再帰的にディレクトリ内のファイルやサブディレクトリを削除する
    private void deleteDataRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] children = fileOrDirectory.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDataRecursive(child);
                }
            }
        }
        fileOrDirectory.delete();
    }
}