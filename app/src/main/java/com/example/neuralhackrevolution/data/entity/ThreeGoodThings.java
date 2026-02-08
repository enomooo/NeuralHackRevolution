package com.example.neuralhackrevolution.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "three_good_things",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diaryId",
                onDelete = ForeignKey.CASCADE // 親が消えたら連動して削除
        ),
        indices = {@Index("diaryId")}
)
public class ThreeGoodThings {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int diaryId; // Diaryテーブルのidと紐付け

    public String good01;
    public String good02;
    public String good03;
}
