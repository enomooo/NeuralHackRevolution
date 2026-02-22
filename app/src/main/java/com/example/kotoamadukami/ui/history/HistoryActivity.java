package com.example.kotoamadukami.ui.history;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotoamadukami.R;
import com.example.kotoamadukami.data.AppDatabase;
import com.example.kotoamadukami.data.entity.Diary;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiaryAdapter adapter;
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = AppDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recycler_view);

        // リストの並べ方を「縦一列」に設定
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 検索機能
        EditText editSearch = findViewById(R.id.edit_search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文字が入力されるたびに検索を実行
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // 全履歴の表示
        loadHistory();
    }

    /**
     * 入力されたクエリに基づいてデータベースを検索し、リストを更新します。
     * @param query = 検索wordを受け取って出来たクエリ
     */
    private void performSearch(String query) {
        executorService.execute(() -> {
            List<Diary> result;
            if (query.isEmpty()) {
                result = db.diaryDao().getAllDiaries();
            } else {
                result = db.diaryDao().searchDiaries(query);
            }
            runOnUiThread(() -> {
                adapter = new DiaryAdapter(result);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    /**
     * データベースから日記履歴を非同期で読み込み、リスト表示を更新します。
     * * <p>処理の流れ:</p>
     * 1. {@link ExecutorService} を使用してバックグラウンドスレッドで全データを取得（メインスレッドの凍結防止）。
     * 2. データの取得完了後、{@link #runOnUiThread(Runnable)} を使用してメインスレッドに戻る。
     * 3. 取得したリストを {@link DiaryAdapter} に渡し、{@link RecyclerView} にセットして画面を更新。
     */
    private void loadHistory() {
        executorService.execute(() -> {
            // DBからの全件取得(新しい順)
            List<Diary> diaryList = db.diaryDao().getAllDiaries();

            // UIの更新はメインスレッドで行う
            runOnUiThread(() -> {
                adapter = new DiaryAdapter(diaryList);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}






