package com.example.kotoamadukami.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 認知行動療法 (CBT: 認知再構成法) のデータを保持するエンティティ。
 *
 * <p>認知の歪みをデバッグし、感情的なエラー（moodBefore）を、
 * 適応的思考（adaptiveThought）を通じて正常な状態（moodAfter）へ
 * リライト（書き換え）するプロセスを記録します。</p>
 */
@Entity(
        tableName = "cbt_logs",
        foreignKeys = @ForeignKey(
                entity = Diary.class,
                parentColumns = "id",
                childColumns = "diary_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("diary_id")}
)
public class CbtLog {

    /** データベースによって自動生成される一意のID。 */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** 親エントリである {@link Diary} の ID。 */
    @ColumnInfo(name = "diary_id")
    public int diaryId;

    /** ワーク開始前の気分の強さ (0-100%)。 */
    @ColumnInfo(name = "mood_before")
    public int moodBefore;

    /** 脳に瞬間的に浮かんだ思考（自動思考 / エラーコード）。 */
    @ColumnInfo(name = "automatic_thought")
    public String automaticThought;

    /** 自動思考を裏付ける事実（論理的根拠）。 */
    @ColumnInfo(name = "evidence")
    public String evidence;

    /** 自動思考と矛盾する事実（デバッグ用の反証データ）。 */
    @ColumnInfo(name = "counter_evidence")
    public String counterEvidence;

    /** バランスの取れた新しい考え方（最適化された思考）。 */
    @ColumnInfo(name = "adaptive_thought")
    public String adaptiveThought;

    /** ワーク実施後の気分の強さ (0-100%)。 */
    @ColumnInfo(name = "mood_after")
    public int moodAfter;

    /**
     * Roomライブラリ用のデフォルトコンストラクタ。
     * DBからのデータを持つため。
     */
    public CbtLog() {
    }

    /**
     * CBTログの全項目を指定してインスタンスを生成するコンストラクタ。
     * ユーザーの入力データを保存するため。
     *
     * @param diaryId 親日記のID
     * @param moodBefore 開始前の気分
     * @param automaticThought 自動思考
     * @param evidence なぜなら（根拠）
     * @param counterEvidence 反証
     * @param adaptiveThought 適応的思考
     * @param moodAfter 終了後の気分
     */
    public CbtLog(int diaryId, int moodBefore, String automaticThought, String evidence,
                  String counterEvidence, String adaptiveThought, int moodAfter) {
        this.diaryId = diaryId;
        this.moodBefore = moodBefore;
        this.automaticThought = automaticThought;
        this.evidence = evidence;
        this.counterEvidence = counterEvidence;
        this.adaptiveThought = adaptiveThought;
        this.moodAfter = moodAfter;
    }
}