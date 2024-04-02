// 查询列表接口
const getMessageTradeList = (params) => {
    return $axios({
        url: '/spz/messageTrade/list',
        method: 'get',
        params
    })
}

// 编辑页面反查详情接口
const queryMessageTradeById = (id) => {
    return $axios({
        url: `/spz/messageTrade/${id}`,
        method: 'get'
    })
}

// 删除当前列的接口
const deleteMessageTrade = (ids) => {
    return $axios({
        url: '/spz/messageTrade',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editMessageTrade = (params) => {
    return $axios({
        url: '/spz/messageTrade',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addMessageTrade = (params) => {
    return $axios({
        url: '/spz/messageTrade',
        method: 'post',
        data: {...params}
    })
}