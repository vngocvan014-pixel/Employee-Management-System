<script setup>
defineProps({
  employees: {
    type: Array,
    required: true,
  },
})
</script>

<template>
  <table class="employee-table">
    <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="employee in employees" :key="employee.id">
        <td>{{ employee.id }}</td>
        <td>{{ employee.name }}</td>
        <td>{{ employee.email }}</td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped>
.employee-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.employee-table th,
.employee-table td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
}

.employee-table th {
  font-weight: bold;
}
</style>

