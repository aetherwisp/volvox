// language=JavaScript

window.app = window.app || {};

window.app.LoadMask = isc.defineClass('Modal', 'Window').addProperties({
    autoDraw: false,

    alwaysShowScrollbars: false,
    autoCenter: true,
    backgroundColor: 'transparent',
    backgroundImage: /*[[@{/images/loading.gif}]]*/'',
    bodyStyle: null,            // to remove 1px body border
    border: '1px solid silver',
    canDragReposition: false,
    canDragResize: false,
    canDropComponents: false,
    canFocus: false,
    isModal: true,
    layoutMargin: 0,            // to remove 4px gray border
    modalMaskOpacity: 75,
    showBody: false,
    showDragPlaceHolder: false,
    showDragShadow: false,
    showDropLines: false,
    showEdges: false,
    showFooter: false,
    showHeader: false,
    showHover: false,
    showMaximizeButton: false,
    showMinimizeButton: false,
    showModalMask: true,
    showResizer: false,
    showShadow: false,
    showSnapGrid: false,
    showStatusBar: false,
    showTitle: false,
    styleName: null,            // to remove 1px (rounded) outer border
    width: 102,
    height: 102
});
