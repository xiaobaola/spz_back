// 查询二手商品列表接口
const getSecondHandTradeListRecycler = (params) => {
    return $axios({
        url: '/spz/scrapTrade/list/recycler',
        method: 'get',
        params
    })
}

// 查看商品详情接口
const querySecondHandItemDetailById = (id) => {
    return $axios({
        url: `/spz/secondHand/item/${id}`,
        method: 'get'
    })
}

// 通过审核接口
const approveSecondHandItem = (params) => {
    return $axios({
        url: '/spz/secondHand/item/productReview/approve',
        method: 'put',
        data: {...params}
    })
}

// 不通过审核接口
const rejectSecondHandItem = (params) => {
    return $axios({
        url: '/spz/secondHand/item/reject',
        method: 'put',
        data: {...params}
    })
}

// 更新商品状态接口
const updateSecondHandItemStatus = (params) => {
    return $axios({
        url: '/spz/productReview/item/status',
        method: 'put',
        data: {...params}
    })
}
