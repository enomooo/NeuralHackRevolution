package com.example.neuralhackrevolution.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cbt_logs",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diaryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diaryId")}
)
public class CbtLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int diaryId;

    public int moodBefore;         // 書く前の気分(%) [cite: 39]
    public String automaticThought; // 自動思考 [cite: 39]
    public String evidence;         // 根拠 [cite: 39]
    public String counterEvidence;  // 反証 [cite: 39]
    public String adaptiveThought;  // 適応的思考 [cite: 39]
    public int moodAfter;          // 書いた後の気分(%) [cite: 39]
}