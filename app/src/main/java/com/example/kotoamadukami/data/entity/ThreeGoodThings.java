package com.example.kotoamadukami.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 「三つの善いこと (3GT)」のデータを保持するエンティティ。
 * * <p>脳のポジティブ・スキャン機能を強化し、日常の中の肯定的なデータを蓄積します。
 * 本クラスは {@link Diary} エンティティと 1対1 の関係を持ちます。</p>
 */
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

    /** データベースによって自動生成される一意のID。 */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** * 親エントリである {@link Diary} の ID。
     * 外部キー制約により、親データが削除されるとこのデータも自動的に破棄されます。
     */
    @ColumnInfo(name = "diary_id")
    public int diaryId;

    /** 1つ目の善いこと。 */
    @ColumnInfo(name = "good_01")
    public String good01;

    /** 2つ目の善いこと。 */
    @ColumnInfo(name = "good_02")
    public String good02;

    /** 3つ目の善いこと。 */
    @ColumnInfo(name = "good_03")
    public String good03;

    /**
     * Roomライブラリ用のデフォルトコンストラクタ。
     * DBからのデータを持つため。
     */
    public ThreeGoodThings() {
    }

    /**
     * 指定された diaryId と内容で 3GT インスタンスを生成します。
     * new ThreeGoodThings(diaryId, g1, g2, g3) と一行で記述するため。
     * ユーザーの入力データを保存するため。
     *
     * @param diaryId 親日記のID
     * @param good01 1つ目の善いこと
     * @param good02 2つ目の善いこと
     * @param good03 3つ目の善いこと
     */
    public ThreeGoodThings(int diaryId, String good01, String good02, String good03) {
        this.diaryId = diaryId;
        this.good01 = good01;
        this.good02 = good02;
        this.good03 = good03;
    }
}
