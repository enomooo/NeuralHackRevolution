package com.example.kotoamadukami.ui.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kotoamadukami.R;
import com.example.kotoamadukami.data.AppDatabase;
import com.example.kotoamadukami.ui.form.ThreeGoodThingsActivity;
import com.example.kotoamadukami.ui.form.ShadowWorkActivity;
import com.example.kotoamadukami.ui.form.CbtFormActivity;
import com.example.kotoamadukami.ui.history.HistoryActivity;

import java.util.Calendar;

/**
 * 「ことあまづかみ」のメインエントリーポイント。
 * * <p>各デバッグワーク（3GT, ShadowWork, CBT）へのナビゲーションと、
 * 当日の進捗状況に応じたボタンUIの動的更新を担当します。</p>
 */
public class MainActivity extends AppCompatActivity {

    private Button btn3gt, btnShadow, btnCbt, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // buttonの紐づけ
        btn3gt = findViewById(R.id.btn_3gt);
        btnShadow = findViewById(R.id.btn_shadow);
        btnCbt = findViewById(R.id.btn_cbt);
        btnHistory = findViewById(R.id.btn_history);

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
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    // 画面に戻ってきたときにボタンの色を更新する
    @Override
    protected void onResume() {
        super.onResume();
        updateButtonColors();
    }

    private void updateButtonColors() {
        long todayStart = getStartOfToday();
        AppDatabase db = AppDatabase.getDatabase(this); // getInstance ではなく getDatabase に修正

        new Thread(() -> {
            // 保存時と同じ文字列（"3GT", "SHADOW WORK", "CBT"）でカウント
            int count3GT = db.diaryDao().getCountForToday("3GT", todayStart);
            int countShadow = db.diaryDao().getCountForToday("SHADOW WORK", todayStart);
            int countCBT = db.diaryDao().getCountForToday("CBT", todayStart);

            runOnUiThread(() -> {
                applyColor(btn3gt, count3GT > 0);
                applyColor(btnShadow, countShadow > 0);
                applyColor(btnCbt, countCBT > 0);
            });
        }).start();
    }



    /**
     * 日記の親データを保存する（非同期処理が必要なため、本来はスレッドを分けます）
     */
    private void saveInitialDiary(String type) {
        // 今はこのメソッドの中身を空にして、まずはアプリが起動するか確認しましょう
    }

    private void applyColor(Button button, boolean isDone) {
        if (isDone) {
            // 完了：黒背景、白文字
            button.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            button.setTextColor(Color.WHITE);
        } else {
            // 未完了：白背景、黒文字（枠線が見えるように適宜調整してください）
            button.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            button.setTextColor(Color.BLACK);
        }
    }
    private long getStartOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}

    private long getStartOfToday() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    // MainActivity.java 内に追加
    private void updateButtonColors() {
        long todayStart = getStartOfToday();
        AppDatabase db = AppDatabase.getInstance(this);

        // 非同期で行うのが理想ですが、まずは動作確認のために簡易的に記述する例
        new Thread(() -> {
            int count3GT = db.diaryDao().getCountForToday("3GT", todayStart);
            int countShadow = db.diaryDao().getCountForToday("SHADOW", todayStart);
            int countCBT = db.diaryDao().getCountForToday("CBT", todayStart);

            runOnUiThread(() -> {
                applyColor(btnThreeGt, count3GT > 0);
                applyColor(btnShadowWork, countShadow > 0);
                applyColor(btnCbt, countCBT > 0);
            });
        }).start();
    }

    private void applyColor(Button button, boolean isDone) {
        if (isDone) {
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.BLACK));
            button.setTextColor(android.graphics.Color.WHITE);
            button.setText(button.getText() + "済"); // オプションで「済」とか付けても良いですね
        } else {
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.LTGRAY)); // 未完了はグレー
            button.setTextColor(android.graphics.Color.BLACK);
        }
    }
}