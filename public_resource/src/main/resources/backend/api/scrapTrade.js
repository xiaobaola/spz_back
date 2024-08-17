// 查询列表接口
// const getScrapTypeList = (params) => {
//     return $axios({
//         url: '/spz/scrapType/list',
//         method: 'get',
//         params
//     })
// }

// 发送信息条与订单关联的接口
const addMessageScrapTrade = (params) => {
    return $axios({
        url: `/spz/messageScrapTrade`,
        method: 'post',
        params
    })
}