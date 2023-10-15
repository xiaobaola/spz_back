function loginApi(data) {
    return $axios({
        'url': 'spz/manager/login',
        'method': 'post',
        data
    })
}

function logoutApi() {
    return $axios({
        'url': 'spz/manager/logout',
        'method': 'post',
    })
}
