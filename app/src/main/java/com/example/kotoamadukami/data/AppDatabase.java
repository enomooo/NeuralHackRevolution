package com.example.kotoamadukami.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kotoamadukami.data.dao.DiaryDao;
import com.example.kotoamadukami.data.entity.CbtLog;
import com.example.kotoamadukami.data.entity.Diary;
import com.example.kotoamadukami.data.entity.FreeSpace;
import com.example.kotoamadukami.data.entity.MagicTherapy;
import com.example.kotoamadukami.data.entity.ShadowWork;
import com.example.kotoamadukami.data.entity.ThreeGoodThings;

/**
 * アプリケーション全体のデータベース管理を行う抽象クラスです。
 * 脳のOSにおける「長期記憶ストレージ」の役割を果たします。
 * ここに作成した全てのEntityを登録し、データを一元管理します。
 */
@Database(entities = {
        Diary.class,
        ThreeGoodThings.class,
        CbtLog.class,
        ShadowWork.class,
        MagicTherapy.class,
        FreeSpace.class
        },
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * データベース操作のための DAO (Data Access Object) を取得します。
     *
     * @return {@link DiaryDao} の実装
     */
    public abstract DiaryDao diaryDao();

    /**
     * データベースの唯一のインスタンスを保持する変数（シングルトン）。
     * volatileキーワードにより、複数スレッドからの同時アクセス時も整合性を保ち、
     * パケットロス（メモリの不一致）を防ぎます。
     */
    private static volatile AppDatabase INSTANCE;

    /**
     * データベースのインスタンスを生成、または取得します。
     *
     * <p>「Double-Checked Locking」パターンを用いて、スレッドセーフかつ
     * 高速なインスタンス提供を実現しています。</p>
     *
     * @param context アプリケーションコンテキスト
     * @return {@link AppDatabase} のシングルトンインスタンス
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "neural_hack_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}