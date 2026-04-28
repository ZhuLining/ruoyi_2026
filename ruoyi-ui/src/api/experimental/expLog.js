import request from '@/utils/request'

// 查询操作日志列表
export function listExpLog(query) {
  return request({
    url: '/experimental/expLog/list',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delExpLog(id) {
  return request({
    url: '/experimental/expLog/' + id,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanExpLog() {
  return request({
    url: '/experimental/expLog/clean',
    method: 'delete'
  })
}

// 还原实验
export function recoverExpLog(data) {
  return request({
    url: '/experimental/expLog/recover',
    method: 'put',
    data: data
  })
}
