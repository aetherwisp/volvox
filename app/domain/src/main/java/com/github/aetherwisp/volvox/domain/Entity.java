package com.github.aetherwisp.volvox.domain;

public interface Entity<E extends Entity<E>> {
    public static interface Name {
        String camelCase();

        String snakeCase();

        String kebabCase();
    }

    public static final Name EMPTY = new Name() {
        @Override
        public String snakeCase() {
            return "";
        }

        @Override
        public String kebabCase() {
            return "";
        }

        @Override
        public String camelCase() {
            return "";
        }
    };

    /**
     * @param other 比較するエンティティ
     * @return このエンティティが指定されたエンティティと同一（等価とは限らない）であるなら true、そうでないなら false
     */
    boolean sameAs(E other);
}
