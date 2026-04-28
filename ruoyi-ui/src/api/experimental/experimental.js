import request from '@/utils/request'

// 查询实验列表（待审核）
export function listExp(query) {
  return request({
    url: '/experimental/expManage/list',
    method: 'get',
    params: query
  })
}

// 查询待处理列表
export function backlogList(query) {
  return request({
    url: '/experimental/expManage/backlog',
    method: 'get',
    params: query
  })
}

// 查询已完成列表
export function finishList(query) {
  return request({
    url: '/experimental/expManage/finishList',
    method: 'get',
    params: query
  })
}

// 查询实验下拉列表
export function getExpList(query) {
  return request({
    url: '/experimental/expManage/expList',
    method: 'get',
    params: query
  })
}

// 新增实验
export function addExp(data) {
  return request({
    url: '/experimental/expManage/expAdd',
    method: 'post',
    data: data
  })
}

// 修改实验
export function updateExp(data) {
  return request({
    url: '/experimental/expManage/updateExp',
    method: 'put',
    data: data
  })
}

// 获取实验详情
export function getExp(expId) {
  return request({
    url: '/experimental/expManage/' + expId,
    method: 'get'
  })
}

// 删除实验
export function delExp(data) {
  return request({
    url: '/experimental/expManage/del',
    method: 'post',
    data: data
  })
}

// 审核实验
export function checkExp(data) {
  return request({
    url: '/experimental/expManage/check',
    method: 'put',
    data: data
  })
}

// 实验结果
export function resultExp(data) {
  return request({
    url: '/experimental/expManage/result',
    method: 'put',
    data: data
  })
}

// 结果详情
export function getExpDetail(expId) {
  return request({
    url: '/experimental/expManage/detail/' + expId,
    method: 'get'
  })
}

// 导出实验
export function exportExp(data) {
  return request({
    url: '/experimental/expManage/export',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}
