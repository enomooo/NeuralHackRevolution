package com.example.kotoamadukami.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "magic_therapies",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diaryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diaryId")}
)
public class MagicTherapy {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int diaryId;

    public String traumaProblem;   // 問題特定 [cite: 39]
    public String pastEvent;       // 過去の原因 [cite: 39]
    public String idealSelf;       // 理想の姿 [cite: 39]
    public String feltSenseName;   // フェルトセンス命名 [cite: 39]
    public String emotionRelease;  // 感情の放出記録 [cite: 39]
    public String acceptance;      // 容認・思い込み外し [cite: 39]
    public String reconfirmation;  // 再確認結果 [cite: 39]
}