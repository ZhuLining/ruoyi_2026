import request from '@/utils/request'

// 查询SOP模板列表
export function listSopTem(query) {
  return request({
    url: '/experimental/expSopTem/list',
    method: 'get',
    params: query
  })
}

// 新增SOP模板
export function addSopTem(data) {
  return request({
    url: '/experimental/expSopTem/temAdd',
    method: 'post',
    data: data
  })
}

// 修改SOP模板
export function updateSopTem(data) {
  return request({
    url: '/experimental/expSopTem/updateTem',
    method: 'put',
    data: data
  })
}

// 获取SOP模板详情
export function getSopTem(temId) {
  return request({
    url: '/experimental/expSopTem/' + temId,
    method: 'get'
  })
}

// 删除SOP模板
export function delSopTem(temId) {
  return request({
    url: '/experimental/expSopTem/' + temId,
    method: 'delete'
  })
}

// 查询目录树
export function catalogTree(query) {
  return request({
    url: '/experimental/expSopTem/catalogTree',
    method: 'get',
    params: query
  })
}

// 查询目录列表
export function catalogList(query) {
  return request({
    url: '/experimental/expSopTem/catalogList',
    method: 'get',
    params: query
  })
}

// 新增目录
export function addCatalog(data) {
  return request({
    url: '/experimental/expSopTem/catalogAdd',
    method: 'post',
    data: data
  })
}

// 修改目录
export function updateCatalog(data) {
  return request({
    url: '/experimental/expSopTem/updateCatalog',
    method: 'put',
    data: data
  })
}

// 删除目录
export function delCatalog(catalogId) {
  return request({
    url: '/experimental/expSopTem/delCatalog/' + catalogId,
    method: 'delete'
  })
}

// 根据ID查询目录
export function getCatalog(catalogId) {
  return request({
    url: '/experimental/expSopTem/queryCatalogById/' + catalogId,
    method: 'get'
  })
}

// 检查目录下是否存在模板
export function checkExistTem(catalogId) {
  return request({
    url: '/experimental/expSopTem/checkExistTem/' + catalogId,
    method: 'get'
  })
}
