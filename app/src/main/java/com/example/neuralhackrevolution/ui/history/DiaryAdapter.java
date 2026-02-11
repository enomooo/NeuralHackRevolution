package com.example.neuralhackrevolution.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neuralhackrevolution.R;
import com.example.neuralhackrevolution.data.entity.Diary;
import com.example.neuralhackrevolution.ui.detail.DiaryDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private final List<Diary> diaryList;

    public DiaryAdapter(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_diary.xmlを1行分として読み込む
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary diary = diaryList.get(position);

        // 日付を「2026/01/10 07:10」のような形式に変換
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        String dateString = sdf.format(new Date(diary.date));
        holder.textDate.setText(dateString);
        holder.textType.setText(diary.formatType);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DiaryDetailActivity.class);
            // putExtraが荷物作り、startActivityでAndroidシステムに渡す、受け取り先のgetIntent()でidを取得する。
            intent.putExtra("DIARY_ID", diary.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder {
        TextView textDate, textType;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.text_date);
            textType = itemView.findViewById(R.id.text_type);
        }
    }
}