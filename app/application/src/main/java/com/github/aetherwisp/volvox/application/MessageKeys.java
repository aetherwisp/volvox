package com.github.aetherwisp.volvox.application;

public interface MessageKeys {

    public static final class Index {
        private Index() {
            throw new UnsupportedOperationException();
        }

        private static final String PREFIX = ApplicationConfiguration.PREFIX + ".index";

        public static final class Login {
            private Login() {
                throw new UnsupportedOperationException();
            }

            private static final String PREFIX = MessageKeys.Index.PREFIX + ".login";

            /** ユーザ名またはパスワードが違う */
            public static final String ERROR_BAD_CREDENTIALS = PREFIX + ".bad.credentials";

            /** アカウント期限切れ */
            public static final String ERROR_ACCOUNT_EXPIRED = PREFIX + ".account.expired";

            /** アカウントロック */
            public static final String ERROR_ACCOUNT_LOCKED = PREFIX + ".account.locked";

            /** アカウント無効 */
            public static final String ERROR_ACCOUNT_DISABLED = PREFIX + ".account.disabled";

            /** パスワード期限切れ */
            public static final String ERROR_CREDENTIAL_EXPIRED = PREFIX + ".credential.expired";

            /** 予期しない失敗 */
            public static final String ERROR_FAILED = PREFIX + ".failed";
        }
    }

}
