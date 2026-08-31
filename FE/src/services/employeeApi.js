import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

// =========================
// GET EMPLOYEES
// =========================

export const getEmployees = async (
  page = 0,
  size = 10
) => {
  const response = await api.get(
    '/employees/search',
    {
      params: {
        page,
        size
      }
    }
  )

  return response.data
}

// =========================
// GET EMPLOYEE BY ID
// =========================

export const getEmployeeById = async (id) => {

  const response = await api.get(
    `/employees/${id}`
  )

  return response.data
}

// =========================
// CREATE EMPLOYEE
// =========================

export const createEmployee = async (
  employee
) => {

  const response = await api.post(
    '/employees',
    employee
  )

  return response.data
}

// =========================
// UPDATE EMPLOYEE
// =========================

export const updateEmployee = async (
  id,
  employee
) => {

  const response = await api.put(
    `/employees/${id}`,
    employee
  )

  return response.data
}

// =========================
// DELETE EMPLOYEE
// =========================

export const deleteEmployee = async (id) => {

  const response = await api.delete(
    `/employees/${id}`
  )

  return response.data
}

// =========================
// SEARCH EMPLOYEES
// =========================

export const searchEmployees = async ({
  keyword = '',
  departmentId = null,
  status = null,
  page = 0,
  size = 10
} = {}) => {

  const response = await api.get(
    '/employees/search',
    {
      params: {
        keyword: keyword || undefined,
        departmentId:
          departmentId || undefined,
        status: status || undefined,
        page,
        size
      }
    }
  )

  return response.data
}

export default api

