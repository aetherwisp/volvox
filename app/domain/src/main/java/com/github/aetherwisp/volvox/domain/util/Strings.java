package com.github.aetherwisp.volvox.domain.util;

public final class Strings {
    private Strings() {
        throw new UnsupportedOperationException("Do no create.");
    }

    //======================================================================
    // Methods
    /**
     * @param _str 検査する文字列
     * @return 指定された文字列が null または空文字列なら true、そうでないなら false
     */
    public static boolean isNullOrEmpty(String _str) {
        return null == _str || _str.isEmpty();
    }
}
