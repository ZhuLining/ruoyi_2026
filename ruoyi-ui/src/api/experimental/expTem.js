import request from '@/utils/request'

// 查询模板列表
export function listTem(query) {
  return request({
    url: '/experimental/expTem/list',
    method: 'get',
    params: query
  })
}

// 新增模板
export function addTem(data) {
  return request({
    url: '/experimental/expTem/temAdd',
    method: 'post',
    data: data
  })
}

// 修改模板
export function updateTem(data) {
  return request({
    url: '/experimental/expTem/updateTem',
    method: 'put',
    data: data
  })
}

// 获取模板详情
export function getTem(temId) {
  return request({
    url: '/experimental/expTem/' + temId,
    method: 'get'
  })
}

// 删除模板
export function delTem(temId) {
  return request({
    url: '/experimental/expTem/' + temId,
    method: 'delete'
  })
}
