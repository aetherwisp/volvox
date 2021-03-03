package com.github.aetherwisp.volvox.presentation;

public interface MessageKeys {

    /**
     * header
     */
    public static final class Header {
        private Header() {
            throw new UnsupportedOperationException();
        }

        private static final String PREFIX = PresentationConfiguration.PREFIX + ".header";

        /**
         * menu
         */
        public static final class Menu {
            private Menu() {
                throw new UnsupportedOperationException();
            }

            private static final String PREFIX = MessageKeys.Header.PREFIX + ".menu";

            /** 設定 */
            public static final String SETTINGS = PREFIX + ".settings";

            /** 全般 */
            public static final String SETTINGS_GENERAL = SETTINGS + ".general";

            /** 言語 */
            public static final String SETTINGS_LANGUAGE = SETTINGS + ".language";

            /** タイムゾーン */
            public static final String SETTINGS_TIMEZONE = SETTINGS + ".timezone";

        }

    }

}
