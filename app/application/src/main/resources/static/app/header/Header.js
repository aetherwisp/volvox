window.volvox = window.volvox || {};

window.volvox.VolvoxHeader = isc.defineClass('VolvoxHeader', 'HLayout').addProperties({
    autoDraw: false,
    alwaysShowScrollbars: false,
    //border: '1px solid black',
    height: 50,
    layoutMargin: 0,
    leaveScrollbarGap: true,
    showResizeBar: false,
    canDragResize: false,
    settingsWindow: volvox.VolvoxSettingsWindow.create({
        ID: 'volvox-settings-window'
    }),
    logoutForm: isc.DynamicForm.create({
        ID: 'volvox-header-form',
        action: /*[[ @{/logout} ]]*/'',
        autoDraw: true,
        height: 1,
        method: 'POST',
        width: 1,
        canSubmit: true,
        hidden: true,
        visible: false,
        fields: [
            { type: 'hidden', name: /*[[ ${_csrf.parameterName} ]]*/'', value: /*[[ ${_csrf.token} ]]*/null }
        ]
    }),
    initWidget: function () {
        this.Super("initWidget", arguments);

        //======================================================================
        // ヘッダ左の余白
        this.addMember(isc.LayoutSpacer.create({
            width: '*',
            height: '100%'
        }));

        //======================================================================
        // ヘッダ右のメニューボタン
        this.addMember(isc.IconMenuButton.create({
            autoDraw: false,
            align: 'right',
            autoFit: true,
            canHover: false,
            height: '100%',
            icon: null,
            minWidth: 100,
            showMenuOnClick: true,
            showFocusedAsOver: false,
            title: /*[[ ${T(com.github.aetherwisp.volvox.application.user.Users).currentUser().getUsername()} ]]*/'',
            valign: 'center',
            wrap: false,
            menu: isc.Menu.create({
                ID: 'menu',
                autoDraw: false,
                showShadow: true,
                shadowDepth: 10,
                data: [{
                    isSeparator: true
                }, {
                    title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings} ]]*/'Settings',
                    submenu: [{
                        title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.timezone} + ' / ' + #{aetherwisp.volvox.presentation.header.menu.settings.language} ]]*/'Timezone / Language',
                        click: function (_target, _item, _menu, _colNum) {
                            isc.say('FIXME: 設定ウィンドウを表示して言語を選択状態にする');
                        }
                    }, {
                        isSeparator: true
                    }, {
                        title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.general} ]]*/'General',
                        // click: 'volvox-settings-window.show();'
                        click: function (_target, _item, _menu, _colNum) {
                            let window = isc.ViewLoader.getById('volvox-settings-window');
                            window.show();
                        }
                    }]
                }, {
                    title: /*[[ #{aetherwisp.volvox.presentation.header.menu.logout} ]]*/'Logout',
                    click: function (_target, _item, _menu, _colNum) {
                        let form = isc.ViewLoader.getById('volvox-header-form');
                        form.submit();
                    },
                }]
            })
        }));
    },
});
