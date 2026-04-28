import request from '@/utils/request'

// 查询要素目录树
export function eleCatalogTree(query) {
  return request({
    url: '/experimental/eleCatalog/catalogTree',
    method: 'get',
    params: query
  })
}

// 查询要素目录列表
export function eleCatalogList(query) {
  return request({
    url: '/experimental/eleCatalog/catalogList',
    method: 'get',
    params: query
  })
}

// 新增要素目录
export function addEleCatalog(data) {
  return request({
    url: '/experimental/eleCatalog/catalogAdd',
    method: 'post',
    data: data
  })
}

// 修改要素目录
export function updateEleCatalog(data) {
  return request({
    url: '/experimental/eleCatalog/updateCatalog',
    method: 'put',
    data: data
  })
}

// 删除要素目录
export function delEleCatalog(catalogId) {
  return request({
    url: '/experimental/eleCatalog/delCatalog/' + catalogId,
    method: 'delete'
  })
}

// 根据ID查询要素目录
export function getEleCatalog(catalogId) {
  return request({
    url: '/experimental/eleCatalog/queryCatalogById/' + catalogId,
    method: 'get'
  })
}

// 查询要素列表
export function listElement(query) {
  return request({
    url: '/experimental/element/list',
    method: 'get',
    params: query
  })
}

// 新增要素
export function addElement(data) {
  return request({
    url: '/experimental/element',
    method: 'post',
    data: data
  })
}

// 修改要素
export function updateElement(data) {
  return request({
    url: '/experimental/element',
    method: 'put',
    data: data
  })
}

// 删除要素
export function delElement(eleId) {
  return request({
    url: '/experimental/element/' + eleId,
    method: 'delete'
  })
}
