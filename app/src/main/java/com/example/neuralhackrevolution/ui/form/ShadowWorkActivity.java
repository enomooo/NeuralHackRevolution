package com.example.neuralhackrevolution.ui.form;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.neuralhackrevolution.R;
import com.example.neuralhackrevolution.data.AppDatabase;
import com.example.neuralhackrevolution.data.entity.Diary;
import com.example.neuralhackrevolution.data.entity.ShadowWork;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShadowWorkActivity extends AppCompatActivity{
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_work);

        AppDatabase db = AppDatabase.getDatabase(this);
        Button btnSave = findViewById(R.id.btn_save_shadow);

        btnSave.setOnClickListener(V -> {
            // 7つの入力フィールドから値を取得
            String event = ((EditText) findViewById(R.id.edit_shadow_1)).getText().toString();
            String w1    = ((EditText) findViewById(R.id.edit_shadow_2)).getText().toString();
            String w2    = ((EditText) findViewById(R.id.edit_shadow_3)).getText().toString();
            String w3    = ((EditText) findViewById(R.id.edit_shadow_4)).getText().toString();
            String w4    = ((EditText) findViewById(R.id.edit_shadow_5)).getText().toString();
            String root  = ((EditText) findViewById(R.id.edit_shadow_6)).getText().toString();
            String act   = ((EditText) findViewById(R.id.edit_shadow_7)).getText().toString();

            executorService.execute(() -> {
                // 1. 親（Diary）を保存
                Diary diary = new Diary();
                diary.date = System.currentTimeMillis();
                diary.formatType = "SHADOW WORK";
                int diaryId = (int) db.diaryDao().insertDiary(diary);

                // 2.詳細（ShadowWork）を保存
                ShadowWork sw = new ShadowWork();
                sw.diaryId = diaryId;
                sw.badEvent = event;
                sw.why01 = w1;
                sw.why02 = w2;
                sw.why03 = w3;
                sw.why04 = w4;
                sw.rootCause = root;
                sw.action = act;
                db.diaryDao().insertShadowWork(sw);

                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(),"影が光に変わりました",Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}












