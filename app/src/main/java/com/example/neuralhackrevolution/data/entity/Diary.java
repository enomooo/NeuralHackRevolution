package com.example.neuralhackrevolution.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "diaries")
public class Diary{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long date;   // 日付（Unixタイムスタンプ）
    public String formatType;  // どのフォーマットかを示す区分 [cite: 34]

    // 引数なしのコンストラクタ
    public Diary() {
    }

    @Ignore
    public Diary(long date, String formatType) {
        this.date = date;
        this.formatType = formatType;
    }
}