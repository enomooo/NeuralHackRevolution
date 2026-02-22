package com.example.kotoamadukami.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * すべてのデバッグログの基底となる親エントリ・エンティティ。
 * * <p>「ことあまづかみ」システムにおける各ワーク（3GT, ShadowWork, CBT等）の
 * 共通メタデータ（実行日時、フォーマット種別）を管理します。</p>
 */
@Entity(tableName = "diaries")
public class Diary{

    /** * ユニークな日記ID。データベースによって自動生成されます。
     * 子テーブルとのリレーションシップにおける外部キーとして機能します。
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 実行日時。Unixタイムスタンプ（ミリ秒）で保持します。
     */
    @ColumnInfo(name = "date")
    public long date;

    /**
     * ワークのフォーマット種別。
     * 例: "3GT", "SHADOW_WORK", "CBT" など。
     */
    @ColumnInfo(name = "format_type")
    public String formatType;

    /**
     * Roomライブラリ用のデフォルトコンストラクタ。
     * DBからのデータを持つため。
     */
    public Diary() {
    }

    /**
     * 新規エントリ作成用のコンストラクタ。
     * idは自動生成されるため、この段階では指定しません。
     *
     * @param date 実行日時
     * @param formatType ワークの種別
     */
    @Ignore
    public Diary(long date, String formatType) {
        this.date = date;
        this.formatType = formatType;
    }

    // todo:今後の改修プラン
    // --- Getter / Setter (必要に応じて追加) ---
    // Google Style ではフィールドをpublicにするか、
    // privateにしてGetter/Setterを置くか選択しますが、
    // Android Entityではコードの簡潔さを優先してpublicフィールドも許容されます。
}