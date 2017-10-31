

window.onerror = function(message, url, lineNumber,columnNumber,errorObj) {
    nativeHook.showMessage("Error  "+message);
    return true;
};