import request from '@/utils/request'

// 查询部门下拉树结构
export function deptTreeSelect(query) {
  return request({
    url: '/system/user/deptTree',
    method: 'get',
    params: query
  })
}

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 查询所有模板
export function listAllTem(query) {
  return request({
    url: '/experimental/common/listAllTem',
    method: 'get',
    params: query
  })
}

// 查询所有SOP模板
export function listAllSopTem(query) {
  return request({
    url: '/experimental/common/listAllSopTem',
    method: 'get',
    params: query
  })
}
