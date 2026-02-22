package com.example.kotoamadukami.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 「影問答 (Shadow Work)」のデータを保持するエンティティ。
 *
 * <p>負の感情（エラーメッセージ）に対し「なぜ？」を繰り返すことで、
 * 脳の深層にある根本原因（Root Cause）を特定し、改善アクションへと変換します。
 * 本クラスは {@link Diary} エンティティと 1対1 の関係を持ちます。</p>
 */
@Entity(
        tableName = "shadow_works",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diary_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diary_id")}
)
public class ShadowWork {

    /** データベースによって自動生成される一意のID。 */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** 親エントリである {@link Diary} の ID。 */
    @ColumnInfo(name = "diary_id")
    public int diaryId;

    /** 発生したネガティブな出来事（デバッグ対象）。 */
    @ColumnInfo(name = "bad_event")
    public String badEvent;

    /** 深掘りプロセス：なぜ？（段階 01）。 */
    @ColumnInfo(name = "why_01")
    public String why01;

    /** 深掘りプロセス：なぜ？（段階 02）。 */
    @ColumnInfo(name = "why_02")
    public String why02;

    /** 深掘りプロセス：なぜ？（段階 03）。 */
    @ColumnInfo(name = "why_03")
    public String why03;

    /** 深掘りプロセス：なぜ？（段階 04）。 */
    @ColumnInfo(name = "why_04")
    public String why04;

    /** 特定されたエラーの根本原因。 */
    @ColumnInfo(name = "root_cause")
    public String rootCause;

    /** 再発防止のための改善アクション。 */
    @ColumnInfo(name = "action")
    public String action;

    /**
     * Roomライブラリ用のデフォルトコンストラクタ。
     * DBからのデータを持つため。
     */
    public ShadowWork() {
    }

    /**
     * 全ての項目を指定して ShadowWork インスタンスを生成するフルコンストラクタ
     * ユーザーの入力データを保存するため。
     *
     * @param diaryId 親日記のID
     * @param badEvent 出来事
     * @param why01 なぜ1
     * @param why02 なぜ2
     * @param why03 なぜ3
     * @param why04 なぜ4
     * @param rootCause 根本原因
     * @param action 改善行動
     */
    public ShadowWork(int diaryId, String badEvent, String why01, String why02,
                      String why03, String why04, String rootCause, String action) {
        this.diaryId = diaryId;
        this.badEvent = badEvent;
        this.why01 = why01;
        this.why02 = why02;
        this.why03 = why03;
        this.why04 = why04;
        this.rootCause = rootCause;
        this.action = action;
    }
}