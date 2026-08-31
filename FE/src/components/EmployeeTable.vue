<template>

  <div class="employee-table-container">

    <table class="employee-table">

      <thead>
        <tr>
          <th>ID</th>
          <th>社員コード</th>
          <th>氏名</th>
          <th>メール</th>
          <th>電話番号</th>
          <th>部署ID</th>
          <th>役職ID</th>
          <th>雇用形態</th>
          <th>ステータス</th>
          <th>入社日</th>
          <th>給与</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>

        <tr
          v-for="employee in employees"
          :key="employee.id"
        >

          <td>{{ employee.id }}</td>

          <td>{{ employee.employeeCode }}</td>

          <td>{{ employee.name }}</td>

          <td>{{ employee.email }}</td>

          <td>{{ employee.phone }}</td>

          <td>{{ employee.departmentId }}</td>

          <td>{{ employee.positionId }}</td>

          <td>{{ employee.employmentType }}</td>

          <td>
            <span
              :class="[
                'status',
                employee.status?.toLowerCase()
              ]"
            >
              {{ employee.status }}
            </span>
          </td>

          <td>{{ employee.hireDate }}</td>

          <td>
            {{ formatSalary(employee.salary) }}
          </td>

          <td>

            <button
              class="detail-button"
              @click="goToDetail(employee.id)"
            >
              詳細
            </button>

          </td>

        </tr>

        <tr v-if="employees.length === 0">

          <td
            colspan="12"
            class="empty"
          >
            社員データがありません。
          </td>

        </tr>

      </tbody>

    </table>

  </div>

</template>


<script setup>

import { useRouter } from 'vue-router'


defineProps({
  employees: {
    type: Array,
    required: true
  }
})


const router = useRouter()


const goToDetail = (id) => {

  router.push(`/employees/${id}`)

}


const formatSalary = (salary) => {

  if (
    salary === null ||
    salary === undefined
  ) {
    return '-'
  }

  return Number(salary)
    .toLocaleString('ja-JP') + ' 円'

}

</script>


<style scoped>

.employee-table-container {
  width: 100%;
  overflow-x: auto;
}

.employee-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
}

.employee-table th,
.employee-table td {
  padding: 12px;
  border-bottom: 1px solid #ddd;
  text-align: left;
  white-space: nowrap;
}

.employee-table th {
  background: #f5f5f5;
  font-weight: 600;
}

.employee-table tbody tr:hover {
  background: #fafafa;
}


/* =========================
   STATUS
========================= */

.status {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.status.active {
  background: #e8f5e9;
  color: #2e7d32;
}

.status.resigned {
  background: #ffebee;
  color: #c62828;
}


/* =========================
   DETAIL BUTTON
========================= */

.detail-button {

  padding: 6px 12px;

  border: 1px solid #1976d2;

  border-radius: 5px;

  background: white;

  color: #1976d2;

  cursor: pointer;

}

.detail-button:hover {

  background: #1976d2;

  color: white;

}


/* =========================
   EMPTY
========================= */

.empty {

  text-align: center !important;

  padding: 30px !important;

}

</style>
