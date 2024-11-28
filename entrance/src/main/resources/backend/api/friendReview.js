function getFriendReview(params) {
    return $axios({
        url: '/spz/user/clerk/list',
        method: 'get',
        params
    })
}
// rejectFriend
function rejectFriend(params) {
    return $axios({
        url: '/spz/user/clerk/disagree',
        method: 'put',
        data: {...params}
    })
}

//agree
function approveFriend(params) {
    return $axios({
        url: '/spz/user/clerk/agree',
        method: 'put',
        data: {...params}
    })
}


