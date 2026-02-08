package com.example.neuralhackrevolution.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.neuralhackrevolution.data.dao.DiaryDao;
import com.example.neuralhackrevolution.data.entity.*;

/**
 * アプリケーション全体のデータベース管理を行う抽象クラスです。
 * 脳のOSにおける「長期記憶ストレージ」の役割を果たします。
 * ここに作成した全てのEntityを登録します
 */
@Database(entities = {
        Diary.class,
        ThreeGoodThings.class,
        CbtLog.class,
        ShadowWork.class,
        MagicTherapy.class,
        FreeSpace.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiaryDao diaryDao();

    /**
     * データベースの唯一のインスタンスを保持する変数。
     * volatileキーワードにより、複数スレッドからの同時アクセス時も整合性を保ち、
     * パケットロス（メモリの不一致）を防ぎます。
     */
    private static volatile AppDatabase INSTANCE;

    /**
     * データベースのインスタンスを生成・取得します（シングルトンパターン）。
     * * 脳のリソース消費を最小限に抑えるため、アプリ実行中に
     * インスタンスが重複して作られないように制御しています。
     *
     * @param context アプリケーションのコンテキスト
     * @return 生成されたAppDatabaseのインスタンス
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