package com.github.aetherwisp.volvox.application;

public interface SessionKeys {

    public static final class Index {
        private Index() {
            throw new UnsupportedOperationException();
        }

        private static final String PREFIX = ApplicationConfiguration.PREFIX + ".index";

        public static final class Login {
            private Login() {
                throw new UnsupportedOperationException();
            }

            private static final String PREFIX = Index.PREFIX + ".index";

            public static final String ERROR = PREFIX + ".error";
        }

    }
}
