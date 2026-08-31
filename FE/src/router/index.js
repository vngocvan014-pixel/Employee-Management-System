import { createRouter, createWebHistory } from 'vue-router'

import EmployeeList from '../views/EmployeeList.vue'
import EmployeeDetail from '../views/EmployeeDetail.vue'
import EmployeeEdit from '../views/EmployeeEdit.vue'
import EmployeeAdd from '../views/EmployeeAdd.vue'

const routes = [

  {
    path: '/',
    redirect: '/employees'
  },

  {
    path: '/employees',
    name: 'EmployeeList',
    component: EmployeeList
  },

  {
    path: '/employees/new',
    name: 'EmployeeAdd',
    component: EmployeeAdd
  },

  {
    path: '/employees/:id/edit',
    name: 'EmployeeEdit',
    component: EmployeeEdit
  },

  {
    path: '/employees/:id',
    name: 'EmployeeDetail',
    component: EmployeeDetail
  }

]

const router = createRouter({

  history: createWebHistory(),

  routes

})

export default router

