import request from '@/utils/request'

// 查询资产列表
export function listExpAssets(query) {
  return request({
    url: '/system/expAssets/list',
    method: 'get',
    params: query
  })
}

// 查询资产详细
export function getExpAssets(assetsId) {
  return request({
    url: '/system/expAssets/' + assetsId,
    method: 'get'
  })
}

// 新增资产（入库）
export function addExpAssets(data) {
  return request({
    url: '/system/expAssets',
    method: 'post',
    data: data
  })
}

// 修改资产
export function updateExpAssets(data) {
  return request({
    url: '/system/expAssets',
    method: 'put',
    data: data
  })
}

// 删除资产
export function delExpAssets(assetsIds) {
  return request({
    url: '/system/expAssets/' + assetsIds,
    method: 'delete'
  })
}

// 资产出库
export function useExpAssets(data) {
  return request({
    url: '/system/expAssets/use',
    method: 'post',
    data: data
  })
}
