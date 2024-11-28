//页面获取投诉列表
function getComplainList(params){
    return $axios({
        url: '/spz/complaint/clerk/list',
        method: 'get',
        params
    })
}
function treatment(data) {
    return $axios({
        url: '/spz/complaint/clerk/complainant',
        method: 'put',
        data
    })
}

function result(data) {
    return $axios({
        url: '/spz/complaint/clerk/respondent',
        method: 'put',
        data
    })
}