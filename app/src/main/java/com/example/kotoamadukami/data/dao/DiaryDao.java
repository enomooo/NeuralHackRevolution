package com.example.kotoamadukami.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kotoamadukami.data.entity.CbtLog;
import com.example.kotoamadukami.data.entity.Diary;
import com.example.kotoamadukami.data.entity.FreeSpace;
import com.example.kotoamadukami.data.entity.MagicTherapy;
import com.example.kotoamadukami.data.entity.ShadowWork;
import com.example.kotoamadukami.data.entity.ThreeGoodThings;

import java.util.List;

/**
 * データベースへのアクセスを管理するData Access Object (DAO)インターフェース（注文票）です。
 * 「ことあまづかみ」システムにおける脳のデバッグログ（Diary）および、
 * 各ワーク（3GT, ShadowWork, CBT等）の永続化を担当します。
 */
@Dao
public interface DiaryDao {

    // --- 書き込み操作 (C) ---

    /**
     * デバッグログの親エントリを保存します。
     *
     * @param diary 保存する親エントリ（メタデータ）
     */
    @Insert
    long insertDiary(Diary diary);


    /**
     * Three Good Things (3GT) の詳細データを保存します。
     */
    @Insert
    void insert3GT(ThreeGoodThings threeGoodThings);
    // 日記の親データを保存し、生成されたIDを返す


    /**
     * CBT（認知再構成法）のデバッグログを保存します。
     */
    @Insert
    void insertCbtLog(CbtLog cbtLog);

    /**
     * 影問答（Shadow Work）の詳細データを保存します。
     */
    @Insert
    void insertShadowWork(ShadowWork shadowWork);

    /**
     * 特殊デバッグ（Magic Therapy）のデータを保存します。
     */
    // todo: 実装予定
    @Insert
    void insertMagicTherapy(MagicTherapy magicTherapy);

    /**
     * 自由記述スペース（Free Space）のデータを保存します。
     */
    // todo: 実装予定
    @Insert
    void insertFreeSpace(FreeSpace freeSpace);

    // --- 読み取り操作 (R) ---

    /**
     * 全てのデバッグログを最新の日付順で取得します。
     *
     * @return 降順にソートされた Diary のリスト
     */
    @Query("SELECT * FROM diaries ORDER BY date DESC")
    List<Diary> getAllDiaries();

    /**
     * 指定された ID に基づき、単一の親エントリを取得します。
     *
     * @param id 取得したい日記の ID
     * @return 一致する Diary オブジェクト
     */
    @Query("SELECT * FROM diaries WHERE id = :id")
    Diary getDiaryById(int id);

    /**
     * 3GT の内容をキーワード検索します。
     *
     * @param query 検索文字列
     * @return キーワードに一致する 3GT を含む親エントリのリスト
     */
    @Query("SELECT * FROM diaries WHERE id IN ("
            + "SELECT diaryId FROM three_good_things "
            + "WHERE good01 LIKE '%' || :query || '%' "
            + "OR good02 LIKE '%' || :query || '%' "
            + "OR good03 LIKE '%' || :query || '%') "
            + "ORDER BY date DESC")
    List<Diary> searchDiaries(String query);

    /**
     * 指定された親 ID に紐づく 3GT の詳細を取得します。
     *
     * @param diaryId 親エントリの ID
     * @return 紐づく ThreeGoodThings データ
     */
    @Query("SELECT * FROM three_good_things WHERE diaryId = :diaryId")
    ThreeGoodThings get3GTByDiaryId(int diaryId);

    /**
     * 指定された親 ID に紐づく 影問答（Shadow Work）の詳細を取得します。
     *
     * @param diaryId 親エントリの ID
     * @return 紐づく ShadowWork データ
     */
    @Query("SELECT * FROM shadow_works WHERE diaryId = :diaryId")
    ShadowWork getShadowWorkByDiaryId(int diaryId);

    /**
     * 指定された親 ID に紐づく CBT ログの詳細を取得します。
     *
     * @param diaryId 親エントリの ID
     * @return 紐づく CbtLog データ
     */
    @Query("SELECT * FROM cbt_logs WHERE diaryId = :diaryId")
    CbtLog getCbtByDiaryId(int diaryId);

    /**
     * 本日の特定ワークの完了回数をカウントします。
     * * @param type ワーク種別（Constantsクラスで定義されたもの）
     * @param startOfDay 本日の 00:00:00 のタイムスタンプ（ミリ秒）
     * @return 完了件数
     */
    @Query("SELECT COUNT(*) FROM diaries WHERE formatType = :type AND date >= :startOfDay")
    int getCountForToday(String type, long startOfDay);
}
