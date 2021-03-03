package com.github.aetherwisp.volvox.application.error;

import java.net.URI;
import java.util.Objects;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * エラーレスポンスの JSON を表すクラスです。
 * 
 * <p>
 * このクラスのレイアウトは {@link https://tools.ietf.org/html/rfc7807 RFC-7807} に基づいています。
 * 
 * @author NIS
 * @see https://tools.ietf.org/html/rfc7807 RFC-7807
 */
@JsonPropertyOrder(alphabetic = true)
public class FailureJson {
    // ======================================================================
    // Fields
    /**
     * 問題の種類を識別する URI 参照です。
     * 
     * <p>
     * 問題の種類について人間が読めるドキュメントを提示する際に設定します。(マニュアルやトラブルシューティングガイドなど) このメンバが存在しない場合、クライアントは about:blank
     * と解釈します。
     */
    private URI type;

    /**
     * 問題の短い要約です。
     */
    private final String title;

    /**
     * 問題の発生に対してオリジンサーバが指定した HTTP ステータスコードです。
     */
    private int status;

    /**
     * この問題の発生に固有の、人間が読み取れる説明です。
     * 
     * <p>
     * デバッグ情報を提供するのではなく、クライアントが問題を修正するのを支援する説明を設定してください。
     */
    private String detail;

    /**
     * 問題発生箇所を識別する URI 参照です。
     */
    private String instance;

    // ======================================================================
    // Constructors
    /**
     * 指定された問題の短い要約で {@link FailureJson} を構築します。
     * 
     * @param _title 問題の短い要約
     */
    public FailureJson(@NonNull final String _title) {
        this.title = Objects.requireNonNull(_title);
    }

    public FailureJson(@NonNull final String _title, int _status) {
        this.title = Objects.requireNonNull(_title);
        this.status = _status;
    }

    // ======================================================================
    // Getters
    public URI getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getInstance() {
        return this.instance;
    }

    // ======================================================================
    // Setters
    public void setType(URI _type) {
        this.type = _type;
    }

    public void setStatus(int _status) {
        this.status = _status;
    }

    public void setDetail(String _detail) {
        this.detail = _detail;
    }

    public void setInstance(String _instance) {
        this.instance = _instance;
    }

}
