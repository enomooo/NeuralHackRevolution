package com.example.kotoamadukami.utils;

/**
 * アプリ全体で使用する定数を管理するクラス。
 * Google Java Style Guideに基づき、定数は大文字のアンダースコア区切りで命名します。
 */
public final class Constants {
    private Constants() {
        // インスタンス化を防止
    }

    /** ワーク種別: 3GT */
    public static final String FORMAT_3GT = "3GT";

    /** ワーク種別: シャドウワーク */
    public static final String FORMAT_SHADOW = "SHADOW_WORK";

    /** ワーク種別: CBT */
    public static final String FORMAT_CBT = "CBT";
}
