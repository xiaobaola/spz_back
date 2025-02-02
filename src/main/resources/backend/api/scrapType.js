// 查询列表接口
const getScrapTypeList = (params) => {
    return $axios({
        url: '/spz/scrapType/list',
        method: 'get',
        params
    })
}

// 编辑页面反查详情接口
const queryCategoryById = (id) => {
    return $axios({
        url: `/spz/scrapType/${id}`,
        method: 'get'
    })
}

// 删除当前列的接口
const deleteScrapType = (ids) => {
    return $axios({
        url: '/spz/scrapType',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editScrapType = (params) => {
    return $axios({
        url: '/spz/scrapType',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addScrapType = (params) => {
    return $axios({
        url: '/spz/scrapType',
        method: 'post',
        data: {...params}
    })
}