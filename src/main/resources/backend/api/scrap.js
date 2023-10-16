// 查询列表接口
const getDishPage = (params) => {
    return $axios({
        url: '/spz/scrap/page',
        method: 'get',
        params
    })
}

// 删除接口
const deleteDish = (ids) => {
    return $axios({
        url: '/spz/scrap',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editDish = (params) => {
    return $axios({
        url: '/spz/scrap',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addDish = (params) => {
    return $axios({
        url: '/spz/scrap',
        method: 'post',
        data: {...params}
    })
}

// 查询详情
const queryDishById = (id) => {
    return $axios({
        url: `/spz/scrap/${id}`,
        method: 'get'
    })
}

// 获取菜品分类列表
const getScrapTypeList = (params) => {
    return $axios({
        url: '/spz/scrapType/list',
        method: 'get',
        params
    })
}

// 查菜品列表的接口
const queryDishList = (params) => {
    return $axios({
        url: '/spz/scrap/list',
        method: 'get',
        params
    })
}

// 文件down预览
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}

// 起售停售---批量起售停售接口
const dishStatusByStatus = (params) => {
    return $axios({
        url: `/spz/scrap/status/${params.status}`,
        method: 'post',
        params: {ids: params.id}
    })
}