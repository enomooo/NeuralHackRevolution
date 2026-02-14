package com.example.neuralhackrevolution.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neuralhackrevolution.R;
import com.example.neuralhackrevolution.ui.history.HistoryActivity;
import com.example.neuralhackrevolution.ui.form.ThreeGoodThingsActivity;
import com.example.neuralhackrevolution.ui.form.ShadowWorkActivity;
import com.example.neuralhackrevolution.ui.form.CbtFormActivity;

/**
 * アプリの入口となるメイン画面です。
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 先ほど作った白黒のレイアウトを表示する
        setContentView(R.layout.activity_main);

        // buttonの紐づけ
        Button btn3gt = findViewById(R.id.btn_3gt);
        Button btnShadow = findViewById(R.id.btn_shadow);
        Button btnCbt = findViewById(R.id.btn_cbt);
        Button btnHistory = findViewById(R.id.btn_history);

        // 3GTボタンが押された時の遷移処理
        btn3gt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // intent（画面遷移の意思）を作成
                Intent intent = new Intent(MainActivity.this, ThreeGoodThingsActivity.class);
                startActivity(intent);
            }
        });

        // SHADOW WORKボタンが押された時の遷移処理
        btnShadow.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, ShadowWorkActivity.class);
            startActivity(intent);
        });

        // CBTLogボタンが押された時の遷移処理
        btnCbt.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, CbtFormActivity.class);
            startActivity(intent);
        });


        // 履歴一覧（タイムライン）のボタンが押された時の遷移処理
        btnHistory.setOnClickListener(v -> {
            android.util.Log.d("CHECK", "CBTボタンのクリックを検知しました！");
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 日記の親データを保存する（非同期処理が必要なため、本来はスレッドを分けます）
     */
    private void saveInitialDiary(String type) {
        // 今はこのメソッドの中身を空にして、まずはアプリが起動するか確認しましょう
    }
}