// 获取商品列表接口（价格审核用）
const getSecondHandItemsForPriceReview = (params) => {
    return $axios({
        url: '/spz/secondHand/item/list/priceReview',
        method: 'get',
        params
    });
}

// 获取商品详情接口（价格审核用）
const querySecondHandItemPriceDetailById = (id) => {
    return $axios({
        url: `/spz/priceReview/item/detail/${id}`,
        method: 'get'
    });
}

// 通过价格审核接口
const approveSecondHandItemPrice = (params) => {
    return $axios({
        url: '/spz/secondHand/item/priceReview/approve',
        method: 'post',
        data: { ...params }
    });
}

// 不通过价格审核接口
const rejectSecondHandItemPrice = (params) => {
    return $axios({
        url: '/spz/secondHand/item/reject',
        method: 'post',
        data: { ...params }
    });
}
