// 连接
function connect({uri: uri, onMessage: onMessage, onOpen: onOpen, onError: onError, onClose: onClose}) {
    var ws = new WebSocket(uri)
    ws.onmessage = onMessage
    ws.onopen = onOpen
    ws.onerror = onError || function (e) {console.log('ws通讯错误')}
    ws.onclose = onClose || function (e) {console.log('ws通道关闭')}
    return ws
}

