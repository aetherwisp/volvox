package com.github.aetherwisp.volvox.domain;

import java.util.List;
import com.github.aetherwisp.volvox.domain.Repository.Finder;

/**
 * リポジトリインタフェースです。
 * 
 * @param <ENTITY> エンティティの型
 * @param <ID> エンティティ識別子の型
 * @param <FINDER> {@link Finder} 実装の型
 */
public interface Repository<ENTITY, ID, FINDER extends Finder<ENTITY, ID>> {

    FINDER finder();

    public static interface Finder<ENTITY, ID> {

        /**
         * 指定された識別子のエンティティを取得します。
         * 
         * <p>
         * 該当するエンティティが存在しない場合は null を返します。
         * 
         * @param firstId 第１エンティティ識別子
         * @param remainingIds 第２以降のエンティティ識別子
         * @return エンティティ、または null
         */
        @SuppressWarnings({"unchecked"})
        ENTITY get(ID firstId, ID... remainingIds);

        /**
         * @return エンティティのリスト、または空のリスト
         */
        List<ENTITY> find();
    }

}
