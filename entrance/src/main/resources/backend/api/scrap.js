// 查询列表接口
const getScrapPage = (params) => {
    return $axios({
        url: '/spz/scrap/page',
        method: 'get',
        params
    })
}

// 删除接口
const deleteScrap = (ids) => {
    return $axios({
        url: '/spz/scrap',
        method: 'delete',
        params: {ids}
    })
}

// 修改接口
const editScrap = (params) => {
    return $axios({
        url: '/spz/scrap',
        method: 'put',
        data: {...params}
    })
}

// 新增接口
const addScrap = (params) => {
    return $axios({
        url: '/spz/scrap',
        method: 'post',
        data: {...params}
    })
}

// 查询详情
const queryScrapById = (id) => {
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
const queryScrapList = (params) => {
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
        url: '/spz/common/download',
        method: 'get',
        params
    })
}