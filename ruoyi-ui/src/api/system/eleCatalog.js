import request from '@/utils/request'

// 查询元素目录列表
export function listEleCatalog(query) {
  return request({
    url: '/system/eleCatalog/list',
    method: 'get',
    params: query
  })
}

// 查询元素目录树
export function treeEleCatalog(query) {
  return request({
    url: '/system/eleCatalog/tree',
    method: 'get',
    params: query
  })
}

// 查询元素目录详细
export function getEleCatalog(catalogId) {
  return request({
    url: '/system/eleCatalog/' + catalogId,
    method: 'get'
  })
}

// 新增元素目录
export function addEleCatalog(data) {
  return request({
    url: '/system/eleCatalog',
    method: 'post',
    data: data
  })
}

// 修改元素目录
export function updateEleCatalog(data) {
  return request({
    url: '/system/eleCatalog',
    method: 'put',
    data: data
  })
}

// 删除元素目录
export function delEleCatalog(catalogId) {
  return request({
    url: '/system/eleCatalog/' + catalogId,
    method: 'delete'
  })
}
