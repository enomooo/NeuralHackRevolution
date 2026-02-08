package com.example.neuralhackrevolution.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Twitter感覚で書けるフリースペース（つぶやき）のデータを保持するクラスです。
 */
@Entity(
        tableName = "free_spaces",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diaryId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diaryId")}
)
public class FreeSpace {
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** 親テーブル(Diary)との紐付け用ID */
    public int diaryId;

    /** 自由記述の内容 */
    public String content;

    /**
     * コンストラクタ
     * @param diaryId 親日記のID
     * @param content つぶやき内容
     */
    public FreeSpace(int diaryId, String content) {
        this.diaryId = diaryId;
        this.content = content;
    }
}