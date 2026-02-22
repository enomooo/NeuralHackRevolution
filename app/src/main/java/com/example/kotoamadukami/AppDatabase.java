package com.example.kotoamadukami;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.kotoamadukami.data.dao.DiaryDao;
import com.example.kotoamadukami.data.entity.*;

/**
 * アプリケーションのメインデータベースクラス。
 * Roomライブラリを使用して、SQLiteデータベースのインスタンス管理と
 * データアクセスオブジェクト（DAO）への橋渡しを行います。
 * * <p>このクラスはシングルトンパターンを適用しており、アプリ全体で
 * 同一のデータベースインスタンスを共有することを保証します。</p>
 * * @Database entities: データベースに含める全テーブル（Entityクラス）を定義
 * * @Database version: スキーマ変更時に上げるバージョン番号（初期は1）
 */
@Database(entities = {
        Diary.class,
        ThreeGoodThings.class,
        ShadowWork.class,
        CbtLog.class,
        MagicTherapy.class,
        FreeSpace.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * DiaryDao（司書）へのアクセスを提供します。
     * 各Activityはこのメソッドを通じてデータベース操作を行います。
     * @return {@link DiaryDao} の実装インスタンス
     */
    public abstract DiaryDao diaryDao();

    /**
     * データベースの唯一のインスタンス。
     * volatileキーワードにより、複数スレッドからの可視性を保証します。
     */
    private static volatile AppDatabase INSTANCE;

    /**
     * データベースインスタンスを取得します。
     * インスタンスが存在しない場合は、スレッドセーフな方法で新規作成します。
     * * @param context アプリケーションコンテキスト（メモリリーク防止のため）
     * @return 作成済み、または既存の {@link AppDatabase} インスタンス
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