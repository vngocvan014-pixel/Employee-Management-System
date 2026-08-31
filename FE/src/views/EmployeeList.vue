<template>

  <div class="employee-page">

    <!-- =========================
         HEADER
    ========================== -->

    <div class="page-header">

      <div>
        <h1>社員管理</h1>
        <p>Employee Management System</p>
      </div>

      <button
        class="add-button"
        @click="goAdd"
      >
        + 社員追加
      </button>

    </div>


    <!-- =========================
         LOADING
    ========================== -->

    <div
      v-if="loading"
      class="loading"
    >
      データを読み込んでいます...
    </div>


    <!-- =========================
         EMPLOYEE TABLE
    ========================== -->

    <div v-else>

      <EmployeeTable
        :employees="employees"
      />

    </div>

  </div>

</template>


<script setup>

import { ref, onMounted } from 'vue'

import { useRouter } from 'vue-router'

import EmployeeTable
  from '../components/EmployeeTable.vue'

import { getEmployees }
  from '../services/employeeApi'


// =========================
// ROUTER
// =========================

const router = useRouter()


// =========================
// DATA
// =========================

const employees = ref([])

const loading = ref(false)


// =========================
// LOAD EMPLOYEES
// =========================

const loadEmployees = async () => {

  try {

    loading.value = true

    const result =
      await getEmployees(0, 10)

    employees.value =
      result.data

  } catch (error) {

    console.error(
      'Failed to load employees:',
      error
    )

  } finally {

    loading.value = false

  }

}


// =========================
// GO TO ADD PAGE
// =========================

const goAdd = () => {

  router.push('/employees/new')

}


// =========================
// INITIAL LOAD
// =========================

onMounted(() => {

  loadEmployees()

})

</script>


<style scoped>

.employee-page {
  padding: 30px;
}


/* =========================
   HEADER
========================= */

.page-header {

  display: flex;

  justify-content: space-between;

  align-items: center;

  margin-bottom: 25px;

}

.page-header h1 {

  margin: 0;

  font-size: 28px;

}

.page-header p {

  margin-top: 5px;

  color: #777;

}


/* =========================
   ADD BUTTON
========================= */

.add-button {

  padding: 10px 18px;

  border: none;

  border-radius: 6px;

  background: #1976d2;

  color: white;

  cursor: pointer;

  font-size: 14px;

}

.add-button:hover {

  background: #1565c0;

}


/* =========================
   LOADING
========================= */

.loading {

  padding: 40px;

  text-align: center;

}

</style>
