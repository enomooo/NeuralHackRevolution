package com.example.neuralhackrevolution.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.neuralhackrevolution.data.entity.*;

import java.util.List;

/**
 * データベースへのアクセスを管理するインターフェース（注文票）です。
 * 脳のデバッグ情報を長期記憶ストレージへ出し入れする役割を担います。
 */
@Dao
public interface DiaryDao {
    // 日記の親データを保存し、生成されたIDを返す
    @Insert
    long insertDiary(Diary diary);

    // 各フォーマットを保存する
    @Insert
    void insert3GT(ThreeGoodThings threeGoodThings);

    @Insert
    void insertCbtLog(CbtLog cbtLog);

    @Insert
    void insertShadowWork(ShadowWork shadowWork);

    @Insert
    void insertMagicTherapy(MagicTherapy magicTherapy);

    @Insert
    void insertFreeSpace(FreeSpace freeSpace);


    // 全ての日記データを新しい順で取得する
    @Query("SELECT * FROM diaries ORDER BY date DESC")
    List<Diary> getAllDiaries();

    // 過去のdiaryを単語検索
    @Query("SELECT * FROM diaries WHERE id IN (SELECT diaryId FROM three_good_things WHERE good01 LIKE '%' || :query || '%' OR good02 LIKE '%' || :query || '%' OR good03 LIKE '%' || :query || '%') ORDER BY date DESC")
    List<Diary> searchDiaries(String query);

    // 指定したdiaryIdに紐づくThreeGoodThingsの詳細を取得する
    @Query("SELECT * FROM three_good_things WHERE diaryId = :diaryId")
    ThreeGoodThings get3GTByDiaryId(int diaryId);

    // 指定したdiaryIdに紐づくShadowWorkの詳細を取得する
    @Query("SELECT * FROM shadow_works WHERE diaryId = :diaryId")
    ShadowWork getShadowWorkByDiaryId(long diaryId);

    @Query("SELECT * FROM diaries WHERE id = :id")
    Diary getDiaryById(int id);
}
