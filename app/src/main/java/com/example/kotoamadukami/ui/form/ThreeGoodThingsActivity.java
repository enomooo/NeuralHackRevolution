package com.example.kotoamadukami.ui.form;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kotoamadukami.R;
import com.example.kotoamadukami.data.AppDatabase;
import com.example.kotoamadukami.data.entity.Diary;
import com.example.kotoamadukami.data.entity.ThreeGoodThings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 3 Good Things（朝のワーク）の入力と保存を担当するActivityです。
 */
public class ThreeGoodThingsActivity extends AppCompatActivity {

    private EditText editGood1, editGood2, editGood3;
    private AppDatabase db;
    // 非同期処理用：データベース操作をバックグラウンドで行うためのスレッドプール
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_good_things);

        // データベースインスタンスの取得
        db = AppDatabase.getDatabase(this);

        // XMLの部品をJava変数に紐付け
        editGood1 = findViewById(R.id.edit_good_1);
        editGood2 = findViewById(R.id.edit_good_2);
        editGood3 = findViewById(R.id.edit_good_3);
        Button btnSave = findViewById(R.id.btn_save);

        // 保存ボタンが押された時の動作設定
        btnSave.setOnClickListener(v -> saveToDatabase());
    }

    /**
     * 入力内容をバリデーションし、非同期でデータベースに保存します。
     */
    private void saveToDatabase() {
        String g1 = editGood1.getText().toString().trim();
        String g2 = editGood2.getText().toString().trim();
        String g3 = editGood3.getText().toString().trim();

        // バリデーション：3つ全て入力されているか確認
        if (g1.isEmpty() || g2.isEmpty() || g3.isEmpty()) {
            Toast.makeText(this, "3つ全て絞り出して入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        // データベース操作はバックグラウンドスレッドで実行（メインスレッドのフリーズ回避）
        executorService.execute(() -> {
            // Diary(親)の保存
            Diary diary = new Diary(System.currentTimeMillis(), "3GT");
            long diaryId = db.diaryDao().insertDiary(diary);

            // 2. 子データ（ThreeGoodThings）の作成と挿入
            ThreeGoodThings t3gt = new ThreeGoodThings();
            t3gt.diaryId = (int) diaryId; // insertDiaryから戻ってきたIDをセット
            t3gt.good01 = g1;
            t3gt.good02 = g2;
            t3gt.good03 = g3;
            db.diaryDao().insert3GT(t3gt);

            // ここからログ出力コード
            java.util.List<Diary> diaries = db.diaryDao().getAllDiaries();
            for (Diary d : diaries) {
                Log.d("NEURAL_HACK", "Saved Diary ID: " + d.id + " Type: " + d.formatType);
            }
            Log.d("NEURAL_HACK", "Detail: " + g1 + " / " + g2 + " / " + g3);

            // 3. UIの更新（トースト表示と画面終了）はメインスレッドに戻して実行
            runOnUiThread(() -> {
                Toast.makeText(this, "脳をアップデートしました", Toast.LENGTH_SHORT).show();
                finish(); // 保存が終わったら自動的にメイン画面に戻る
            });
        });
    }
}