```vue
<script setup>
import { onMounted, ref } from 'vue'
import { getEmployees } from '../api/employeeApi'
import EmployeeForm from '../components/EmployeeForm.vue'
import EmployeeTable from '../components/EmployeeTable.vue'

const employees = ref([])
const loading = ref(false)
const error = ref('')

const loadEmployees = async () => {
  loading.value = true
  error.value = ''

  try {
    const response = await getEmployees()
    employees.value = response.data
  } catch (err) {
    console.error(err)
    error.value = 'Failed to load employees.'
  } finally {
    loading.value = false
  }
}

const handleEmployeeCreated = (employee) => {
  employees.value.push(employee)
}

onMounted(() => {
  loadEmployees()
})
</script>

<template>
  <main class="employee-list">
    <h1>Employee Management System</h1>

    <EmployeeForm @created="handleEmployeeCreated" />

    <p v-if="loading">Loading...</p>

    <p v-if="error" class="error">
      {{ error }}
    </p>

    <EmployeeTable
      v-if="!loading && !error"
      :employees="employees"
    />

    <p v-if="!loading && !error && employees.length === 0">
      No employees found.
    </p>
  </main>
</template>

<style scoped>
.employee-list {
  max-width: 900px;
  margin: 40px auto;
  padding: 20px;
}

.error {
  color: red;
}
</style>
```
