package com.example.neuralhackrevolution.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "shadow_works",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diaryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diaryId")}
)
public class ShadowWork {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int diaryId;

    public String badEvent;  // その日一番嫌なこと [cite: 39]
    public String why01;     // なぜ1 [cite: 39]
    public String why02;     // なぜ2 [cite: 39]
    public String why03;     // なぜ3 [cite: 39]
    public String why04;     // なぜ4 [cite: 39]
    public String rootCause; // 根本原因 [cite: 39]
    public String action;    // 改善行動 [cite: 39]
}
