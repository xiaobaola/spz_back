function getManagerList(params) {
    return $axios({
        url: '/spz/manager/page',
        method: 'get',
        params
    })
}

// 修改---启用禁用接口
function enableOrDisableManager(params) {
    return $axios({
        url: '/spz/manager',
        method: 'put',
        data: {...params}
    })
}

// 新增---添加员工
function addManager(params) {
    return $axios({
        url: '/spz/manager',
        method: 'post',
        data: {...params}
    })
}

// 修改---添加员工
function editManager(params) {
    return $axios({
        url: '/spz/manager',
        method: 'put',
        data: {...params}
    })
}

// 修改页面反查详情接口
function queryManagerById(id) {
    return $axios({
        url: `/spz/manager/${id}`,
        method: 'get'
    })
}