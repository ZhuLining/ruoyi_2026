import request from '@/utils/request'

// 查询元素列表
export function listEleTable(query) {
  return request({
    url: '/system/eleTable/list',
    method: 'get',
    params: query
  })
}

// 查询元素详细
export function getEleTable(eleId) {
  return request({
    url: '/system/eleTable/' + eleId,
    method: 'get'
  })
}

// 新增元素
export function addEleTable(data) {
  return request({
    url: '/system/eleTable',
    method: 'post',
    data: data
  })
}

// 修改元素
export function updateEleTable(data) {
  return request({
    url: '/system/eleTable',
    method: 'put',
    data: data
  })
}

// 删除元素
export function delEleTable(eleIds) {
  return request({
    url: '/system/eleTable/' + eleIds,
    method: 'delete'
  })
}
