import request from '@/utils/request'

// 查询出库记录列表
export function listExpAssetsUse(query) {
  return request({
    url: '/system/expAssetsUse/list',
    method: 'get',
    params: query
  })
}

// 查询出库记录详细
export function getExpAssetsUse(useId) {
  return request({
    url: '/system/expAssetsUse/' + useId,
    method: 'get'
  })
}

// 删除出库记录
export function delExpAssetsUse(useIds) {
  return request({
    url: '/system/expAssetsUse/' + useIds,
    method: 'delete'
  })
}
