// 查询列表页接口
const getTradeDetailPage = (params) => {
    return $axios({
        url: '/spz/scrapTrade/page',
        method: 'get',
        params
    })
}

// 查看接口
const queryTradeDetailById = (id) => {
    return $axios({
        url: `/spz/scrapTradeDetail/${id}`,
        method: 'get'
    })
}

// 取消，派送，完成接口
const editTradeDetail = (params) => {
    return $axios({
        url: '/spz/scrapTrade',
        method: 'put',
        data: {...params}
    })
}
