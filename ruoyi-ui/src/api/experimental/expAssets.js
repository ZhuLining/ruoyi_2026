import request from '@/utils/request'

// 查询资产列表
export function listAssets(query) {
  return request({
    url: '/experimental/assets/list',
    method: 'get',
    params: query
  })
}

// 查询全部资产（不分页）
export function allAssets(query) {
  return request({
    url: '/experimental/assets/allList',
    method: 'get',
    params: query
  })
}

// 查询资产目录树
export function assetsCatalogTree(query) {
  return request({
    url: '/experimental/assets/catalogTree',
    method: 'get',
    params: query
  })
}

// 查询资产目录列表
export function assetsCatalogList(query) {
  return request({
    url: '/experimental/assets/catalogList',
    method: 'get',
    params: query
  })
}

// 新增资产目录
export function addAssetsCatalog(data) {
  return request({
    url: '/experimental/assets/catalogAdd',
    method: 'post',
    data: data
  })
}

// 修改资产目录
export function updateAssetsCatalog(data) {
  return request({
    url: '/experimental/assets/updateCatalog',
    method: 'put',
    data: data
  })
}

// 删除资产目录
export function delAssetsCatalog(catalogId) {
  return request({
    url: '/experimental/assets/delCatalog/' + catalogId,
    method: 'delete'
  })
}

// 根据ID查询资产目录
export function getAssetsCatalog(catalogId) {
  return request({
    url: '/experimental/assets/queryCatalogById/' + catalogId,
    method: 'get'
  })
}

// 新增资产
export function addAssets(data) {
  return request({
    url: '/experimental/assets',
    method: 'post',
    data: data
  })
}

// 修改资产
export function updateAssets(data) {
  return request({
    url: '/experimental/assets',
    method: 'put',
    data: data
  })
}

// 获取资产详情
export function getAssets(assetsId) {
  return request({
    url: '/experimental/assets/' + assetsId,
    method: 'get'
  })
}

// 删除资产
export function delAssets(assetsId) {
  return request({
    url: '/experimental/assets/' + assetsId,
    method: 'delete'
  })
}

// 资产领用
export function useAssets(data) {
  return request({
    url: '/experimental/assets/use',
    method: 'post',
    data: data
  })
}

// 资产归还
export function returnAssets(data) {
  return request({
    url: '/experimental/assets/return',
    method: 'post',
    data: data
  })
}

// 查询资产使用记录
export function listAssetsUse(query) {
  return request({
    url: '/experimental/assets/useList',
    method: 'get',
    params: query
  })
}
