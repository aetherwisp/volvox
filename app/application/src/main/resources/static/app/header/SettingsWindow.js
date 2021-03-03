window.volvox = window.volvox || {};

window.volvox.VolvoxSettingsWindow = isc.defineClass('VolvoxSettingsWindow', 'ModalWindow').addProperties({
    alwaysShowScrollbars: false,
    autoCenter: true,
    autoDraw: false,
    canDragResize: false,
    title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings} ]]*/'Settings',
    maxWidth: '100%',
    width: '75%',
    height: '75%',
    initWidget: function () {
        this.Super("initWidget", arguments);

        this.addMember(isc.HLayout.create({
            autoDraw: false,
            height: '100%',
            width: '100%',
            canDragResize: false,
            members: [
                isc.TreeGrid.create({
                    autoDraw: false,
                    height: '100%',
                    maxWidth: '50%',
                    minWidth: 200,
                    width: '30%',
                    showHeader: false,
                    showNodeIcons: false,
                    showResizeBar: true,
                    showOpener: true,
                    data: isc.Tree.create({
                        modelType: 'children',
                        nameProperty: 'title',
                        childrenProperty: 'children',
                        root: {
                            title: null,
                            children: [{
                                title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.general} ]]*/'General',
                                children: [{
                                    title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.timezone} + ' / ' + #{aetherwisp.volvox.presentation.header.menu.settings.language} ]]*/'Timezone / Language',
                                }]
                            }]
                        }
                    }),
                    fields: [
                        { name: 'title', title: 'Title' },
                    ],
                    nodeClick: function (_viewer, _node, _recordNum) {
                        let tabset = _viewer.getParentCanvas().getMembers()[1];
                        tabset.selectTab(null != _node ? _node.title : 0);
                        tabset.redraw();
                    }
                }),
                //======================================================================
                // 右フォーム
                // TODO: TabSet で切り替える（参考：https://www.smartclient.com/smartclient-release/showcase/?id=closeableTabs）
                isc.TabSet.create({
                    alwaysShowScrollbars: true,
                    autoDraw: false,
                    height: '100%',
                    showTabBar: false,
                    showTabScroller: false,
                    shrinkElementOnHide: true,
                    tabBarPosition: 'top',
                    tabs: [{
                        pane: isc.DynamicForm.create({
                            alwaysShowScrollbars: true,
                            autoDraw: false,
                            autoFocus: true,
                            autoFocusOnError: false,
                            canSubmit: false,
                            groupBorderCSS: '1px solid blue',
                            groupTitle: /*[[ '<span style="font: bold large/100% monospace;">' + #{aetherwisp.volvox.presentation.header.menu.settings.general} + '</span>' ]]*/'General',
                            isGroup: true,
                            padding: 10,
                            fields: [{
                                canSelectText: true,
                                escapeHTML: true,
                                showTitle: false,
                                type: 'header',
                                defaultValue: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.timezone} + ' / ' + #{aetherwisp.volvox.presentation.header.menu.settings.language} ]]*/''
                            }, {
                                name: 'timezone',
                                displayField: 'name',
                                editorType: 'PickTreeItem',
                                showTitle: true,
                                title: /*[[ #{aetherwisp.volvox.presentation.header.menu.settings.timezone} ]]*/'Timezone',
                                valueField: 'value',
                                valueTree: isc.Tree.create({
                                    modelType: 'children',
                                    root: /*[[ ${timezoneTree} ]]*/null
                                })
                            }],
                            height: '100%',
                            width: '100%'
                        })
                    }],
                    // width: '70%',
                })
            ]
        }));
    }

});
