package com.example.neuralhackrevolution.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neuralhackrevolution.R;
import com.example.neuralhackrevolution.data.AppDatabase;
import com.example.neuralhackrevolution.data.entity.Diary;
import com.example.neuralhackrevolution.data.entity.ShadowWork;
import com.example.neuralhackrevolution.data.entity.ThreeGoodThings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiaryDetailActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        int diaryId = getIntent().getIntExtra("DIARY_ID", -1);
        AppDatabase db = AppDatabase.getDatabase(this);

        executorService.execute(() -> {
            // 1. まず「親(Diary)」を取得して、何の種類かを確認する
            Diary diary = db.diaryDao().getDiaryById(diaryId);
            if (diary == null) return;

            // 日付表示用の文字列作成
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
            String dateString = sdf.format(new Date(diary.date));

            // 2. 種類（formatType）によって処理を分岐
            if ("3GT".equals(diary.formatType)) {
                ThreeGoodThings detail = db.diaryDao().get3GTByDiaryId(diaryId);
                runOnUiThread(() -> {
                    setupBasicInfo(dateString, "3 GOOD THINGS");
                    display3GT(detail);
                });
            } else if ("SHADOW WORK".equals(diary.formatType)) {
                ShadowWork sw = db.diaryDao().getShadowWorkByDiaryId(diaryId);
                runOnUiThread(() -> {
                    setupBasicInfo(dateString, "SHADOW WORK");
                    displayShadowWork(sw);
                });
            }
        });
    }

    // 日付とタイトルの共通セットアップ
    private void setupBasicInfo(String date, String title) {
        ((TextView) findViewById(R.id.detail_date)).setText(date);
        ((TextView) findViewById(R.id.detail_title)).setText(title);
    }

    // 3GT用の表示
    private void display3GT(ThreeGoodThings t) {
        if (t == null) return;
        findViewById(R.id.line_4).setVisibility(View.GONE);
        findViewById(R.id.line_5).setVisibility(View.GONE);

        ((TextView) findViewById(R.id.line_1)).setText("1. " + t.good01);
        ((TextView) findViewById(R.id.line_2)).setText("2. " + t.good02);
        ((TextView) findViewById(R.id.line_3)).setText("3. " + t.good03);
    }

    // ShadowWork用の表示
// ShadowWork用の表示メソッドを以下のように修正
    private void displayShadowWork(ShadowWork s) {
        if (s == null) return;
        findViewById(R.id.line_4).setVisibility(View.VISIBLE);
        findViewById(R.id.line_5).setVisibility(View.VISIBLE);

        // 最新の項目名（badEvent, why01...）に合わせて取得
        String d1 = "出来事: " + s.badEvent;
        String d2 = "なぜ1: " + s.why01;
        String d3 = "なぜ2: " + s.why02;
        String d4 = "なぜ3: " + s.why03;
        String d5 = "なぜ4: " + s.why04;
        // 必要に応じて s.rootCause や s.action も追加可能

        ((TextView) findViewById(R.id.line_1)).setText(d1);
        ((TextView) findViewById(R.id.line_2)).setText(d2);
        ((TextView) findViewById(R.id.line_3)).setText(d3);
        ((TextView) findViewById(R.id.line_4)).setText(d4);
        ((TextView) findViewById(R.id.line_5)).setText(d5);
    }

    public static class DiaryDetailActivity extends AppCompatActivity {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_diary_detail);

            // 呼び出し元から受け取ったIDを取得
            int diaryId = getIntent().getIntExtra("DIARY_ID", -1);
            AppDatabase db = AppDatabase.getDatabase(this);

            executorService.execute(() -> {
                // 1. 親データ(Diary)を取得。メソッド名は getDiaryById (単数) です
                Diary diary = db.diaryDao().getDiaryById(diaryId);
                if (diary == null) return;

                // 日付のフォーマット
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                String dateString = sdf.format(new Date(diary.date));

                // 2. formatTypeによって表示を分岐
                if ("3GT".equals(diary.formatType)) {
                    ThreeGoodThings detail = db.diaryDao().get3GTByDiaryId(diaryId);
                    runOnUiThread(() -> {
                        setupBasicInfo(dateString, "3 GOOD THINGS");
                        display3GT(detail);
                    });
                } else if ("SHADOW WORK".equals(diary.formatType)) {
                    // ShadowWorkの詳細を取得
                    ShadowWork sw = db.diaryDao().getShadowWorkByDiaryId(diaryId);
                    runOnUiThread(() -> {
                        setupBasicInfo(dateString, "SHADOW WORK");
                        displayShadowWork(sw);
                    });
                }
            });
        }

        // 日付とタイトルのセットアップ
        private void setupBasicInfo(String date, String title) {
            ((TextView) findViewById(R.id.detail_date)).setText(date);
            ((TextView) findViewById(R.id.detail_title)).setText(title);
        }

        // 3GT表示用のメソッド
        private void display3GT(ThreeGoodThings t) {
            if (t == null) return;
            findViewById(R.id.line_4).setVisibility(View.GONE);
            findViewById(R.id.line_5).setVisibility(View.GONE);

            ((TextView) findViewById(R.id.line_1)).setText("1. " + t.good01);
            ((TextView) findViewById(R.id.line_2)).setText("2. " + t.good02);
            ((TextView) findViewById(R.id.line_3)).setText("3. " + t.good03);
        }

        // ShadowWork表示用のメソッド（エラー箇所を修正済み）
        private void displayShadowWork(ShadowWork s) {
            if (s == null) return;

            // 7項目あるので、必要に応じて line_6, line_7 も XML に追加してください
            findViewById(R.id.line_4).setVisibility(View.VISIBLE);
            findViewById(R.id.line_5).setVisibility(View.VISIBLE);

            // Entity（ShadowWork.java）の変数名と完全に一致させます
            String q1 = "出来事: " + s.badEvent;
            String q2 = "なぜ1: " + s.why01;
            String q3 = "なぜ2: " + s.why02;
            String q4 = "なぜ3: " + s.why03;
            String q5 = "なぜ4: " + s.why04;
            String root = "根本原因: " + s.rootCause;
            String act = "改善行動: " + s.action;

            ((TextView) findViewById(R.id.line_1)).setText(q1);
            ((TextView) findViewById(R.id.line_2)).setText(q2);
            ((TextView) findViewById(R.id.line_3)).setText(q3);
            ((TextView) findViewById(R.id.line_4)).setText(q4);
            ((TextView) findViewById(R.id.line_5)).setText(q5);
            // line_6, line_7 が XML にあるなら以下もセット
            // ((TextView) findViewById(R.id.line_6)).setText(root);
            // ((TextView) findViewById(R.id.line_7)).setText(act);
        }
        }
}
