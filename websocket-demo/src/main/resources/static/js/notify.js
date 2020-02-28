/**
 *  浏览器通知
 */
function checkPermission() {
    if (!('Notification' in window)) {
        console.warn("当前浏览器不支持通知功能，请使用Chrome内核等浏览器");
    } else if (Notification.permission !== 'granted') {
        Notification.requestPermission(function (permission) {
            if (permission !== 'granted') {
                console.log('用户禁止了浏览器通知功能')
            }
        });
    }
}

function notify({title: title, message: message, data: data, click: callback}) {
    checkPermission()
    var option = {
        body: message,
        tag: '',
        icon: '',
        data: data
    }
    var notify = new Notification(title, option)

    notify.onclick = function(e) {
        callback(e.target.data)
    }
}
