package com.example.neuralhackrevolution.ui.form;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.neuralhackrevolution.R;
import com.example.neuralhackrevolution.data.AppDatabase;
import com.example.neuralhackrevolution.data.entity.Diary;
import com.example.neuralhackrevolution.data.entity.CbtLog;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CbtFormActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbt_form);

        AppDatabase db = AppDatabase.getDatabase(this);
        Button btnSave = findViewById(R.id.btn_save_cbt);

        btnSave.setOnClickListener(v -> {
            // 入力値の取得
            String sit = ((EditText) findViewById(R.id.edit_cbt_1)).getText().toString();
            String emoType = ((EditText) findViewById(R.id.edit_cbt_2_type)).getText().toString();
            String emoIntStr = ((EditText) findViewById(R.id.edit_cbt_2_intensity)).getText().toString();
            String autoThought = ((EditText) findViewById(R.id.edit_cbt_3)).getText().toString();
            String evi = ((EditText) findViewById(R.id.edit_cbt_4)).getText().toString();
            String counter = ((EditText) findViewById(R.id.edit_cbt_5)).getText().toString();
            String alt = ((EditText) findViewById(R.id.edit_cbt_6)).getText().toString();
            String emoAfter = ((EditText) findViewById(R.id.edit_cbt_7)).getText().toString();

            // 数値変換の安全策
            int emoInt = emoIntStr.isEmpty() ? 0 : Integer.parseInt(emoIntStr);

            executorService.execute(() -> {
                // 1. 親（Diary）の保存
                Diary diary = new Diary();
                diary.date = System.currentTimeMillis();
                diary.formatType = "CBT"; // 識別子
                int diaryId = (int) db.diaryDao().insertDiary(diary);

                // 2. 詳細（CbtLog）の保存
                CbtLog cbt = new CbtLog();
                cbt.diaryId = diaryId;
                cbt.situation = sit;
                cbt.emotionType = emoType;
                cbt.emotionIntensity = emoInt;
                cbt.automaticThought = autoThought;
                cbt.evidence = evi;
                cbt.counterEvidence = counter;
                cbt.alternativeThought = alt;
                cbt.emotionIntensityAfter = emoAfter;

                db.diaryDao().insertCbtLog(cbt);

                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "認知が再構成されました", Toast.LENGTH_SHORT).show();
                    finish(); // 保存したら画面を閉じる
                });
            });
        });
    }
}
