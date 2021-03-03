// language=JavaScript

window.volvox = window.volvox || {};

/**
 * volvox における通信ルールに基づいたリクエスト送信処理の実装です。
 */
isc.defineClass('Ajax').addProperties({
    /**
     * @param {Object} _scope コールバック内で this として使用する値
     * @param {String} _url リクエスト URL
     * @param {Function} _success 成功時のコールバック
     * @param {Function} _failure 失敗時のコールバック
     */
    get: function(_scope, _url, _success, _failure) {
        app.Ajax.request({
            scope: _scope,
            method: 'GET',
            url: _url,
            data: {},
            headers: {
                'Content-Type': /*[[ ${T(org.springframework.http.MediaType).APPLICATION_JSON_UTF8_VALUE} ]]*/'application/json;charset=UTF-8'
            }
        }, _success, _failure);
    },

    /**
     * POST パラメータを JSON 形式で送信します。
     * 
     * @param {Object} _scope コールバック内で this として使用する値
     * @param {String} url リクエスト URL
     * @param {Object} entity リクエストボディ
     * @param {Function} success 成功時のコールバック
     * @param {Function} failure 失敗時のコールバック
     */
    postAsJson: function(_scope, _url, _entity, _success, _failure) {
        app.Ajax.request({
            scope: _scope,
            method: 'POST',
            headers: {
                'Content-Type': /*[[ ${T(org.springframework.http.MediaType).APPLICATION_JSON_UTF8_VALUE} ]]*/'application/json;charset=UTF-8'
            },
            url: _url,
            data: _entity
        }, _success, _failure);
    },

    /**
     * POST パラメータを FORM 形式で送信します。
     * 
     * @param {Object} _scope コールバック内で this として使用する値
     * @param {String} url リクエスト URL
     * @param {Object} entity リクエストボディ
     * @param {Function} success 成功時のコールバック
     * @param {Function} failure 失敗時のコールバック
     */
    postAsForm: function(_scope, _url, _entity, _success, _failure) {
        const params = new URLSearchParams();
        for (let key in _entity) {
            if (_entity.hasOwnProperty(key)) {
                params.append(key, _entity[key]);
            }
        }

        app.Ajax.request({
            scope: _scope,
            method: 'POST',
            headers: {
                'Content-Type': /*[[ ${T(org.springframework.http.MediaType).APPLICATION_FORM_URLENCODED_VALUE} ]]*/'application/x-www-form-urlencoded'
            },
            url: _url,
            data: params
        }, _success, _failure);
    },

    delete: function(_scope, _url, _success, _failure) {
        app.Ajax.request({
            scope: _scope,
            method: 'DELETE',
            url: _url,
            data: {},
            headers: {
                'Content-Type': /*[[ ${T(org.springframework.http.MediaType).APPLICATION_JSON_UTF8_VALUE} ]]*/'application/json;charset=UTF-8'
            }
        }, _success, _failure);
    },

    /**
     * @param {Object} _config リクエスト設定
     * @param {Function} _success 成功時のコールバック
     * @param {Function} _failure 失敗時のコールバック
     */
    request: function(_config, _success, _failure) {
        _config = _config || { scope: this };
        _success = _success || function(_common, _result) {
        };
        _failure = _failure || function(_common, _result) {
        };

        axios.request(_config)
        .then(function(_response) {
            let config = _response.config;
            let common = _response.data.common;
            let result = _response.data.result;

            try {
                _success.call(config.scope, common, result);
            } finally {
                if (null != common.location) {
                    let contextPath = /*[[ ${@environment.getProperty('server.servlet.context-path')} ]]*/'';
                    window.location.replace(contextPath + common.location);
                }
            }
        })
        .catch(function(_error) {
            let config = _error.config;
            let common = _error.response.data.common;
            let result = _error.response.data.result;
            let message = '<h3>' + common.message + '</h3>'
            if (null != common.solutions && 0 != common.solutions.length) {
                message += '<ul>';
                for (let i = 0; i < common.solutions.length; i++) {
                    message += '<li>';
                    message += common.solutions[i];
                    message += '</li>';
                }
                message += '</ul>';
            }
            isc.Dialog.create({
                title: common.status.code + ' - ' + common.status.phrase,
                message: message,
                icon: '[SKIN]warn.png',
                width: '480',
                buttons: [
                    isc.Button.create({ title: 'OK' })
                ],
                buttonClick: function(_button, _index) {
                    try {
                        _failure.call(config.scope, common, result);
                    } finally {
                        this.destroy();
                    }
                }
            });
        });
    }
});
volvox.Ajax = isc.Ajax.create();

