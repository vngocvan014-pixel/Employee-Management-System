<template>
  <div class="detail-page">

    <div class="page-header">

      <div>
        <h1>社員詳細</h1>
        <p>Employee Detail</p>
      </div>

      <div class="header-buttons">

        <button
          class="back-button"
          @click="goBack"
        >
          ← 一覧に戻る
        </button>

        <button
          class="edit-button"
          @click="goEdit"
        >
          編集
        </button>

      </div>

    </div>

    <div
      v-if="loading"
      class="loading"
    >
      データを読み込んでいます...
    </div>

    <div
      v-else-if="employee"
      class="detail-card"
    >

      <div class="detail-grid">

        <div class="detail-item">
          <label>ID</label>
          <span>{{ employee.id }}</span>
        </div>

        <div class="detail-item">
          <label>社員コード</label>
          <span>{{ employee.employeeCode }}</span>
        </div>

        <div class="detail-item">
          <label>氏名</label>
          <span>{{ employee.name }}</span>
        </div>

        <div class="detail-item">
          <label>メール</label>
          <span>{{ employee.email }}</span>
        </div>

        <div class="detail-item">
          <label>電話番号</label>
          <span>{{ employee.phone || '-' }}</span>
        </div>

        <div class="detail-item">
          <label>部署ID</label>
          <span>{{ employee.departmentId ?? '-' }}</span>
        </div>

        <div class="detail-item">
          <label>役職ID</label>
          <span>{{ employee.positionId ?? '-' }}</span>
        </div>

        <div class="detail-item">
          <label>雇用形態</label>
          <span>{{ employee.employmentType || '-' }}</span>
        </div>

        <div class="detail-item">
          <label>ステータス</label>

          <span
            :class="[
              'status',
              employee.status?.toLowerCase()
            ]"
          >
            {{ employee.status || '-' }}
          </span>

        </div>

        <div class="detail-item">
          <label>入社日</label>
          <span>{{ employee.hireDate || '-' }}</span>
        </div>

        <div class="detail-item">
          <label>退職日</label>
          <span>{{ employee.resignationDate || '-' }}</span>
        </div>

        <div class="detail-item">
          <label>上司ID</label>
          <span>{{ employee.managerId ?? '-' }}</span>
        </div>

        <div class="detail-item">
          <label>給与</label>
          <span>{{ formatSalary(employee.salary) }}</span>
        </div>

        <div class="detail-item">
          <label>作成日時</label>
          <span>{{ employee.createdAt || '-' }}</span>
        </div>

        <div class="detail-item">
          <label>更新日時</label>
          <span>{{ employee.updatedAt || '-' }}</span>
        </div>

      </div>

    </div>

    <div
      v-else
      class="error"
    >
      社員情報が見つかりません。
    </div>

  </div>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import { getEmployeeById } from '../services/employeeApi'

const route = useRoute()
const router = useRouter()

const employee = ref(null)
const loading = ref(false)

const loadEmployee = async () => {

  try {

    loading.value = true

    const result = await getEmployeeById(
      route.params.id
    )

    employee.value = result

  } catch (error) {

    console.error(
      'Failed to load employee:',
      error
    )

  } finally {

    loading.value = false

  }

}

const formatSalary = (salary) => {

  if (
    salary === null ||
    salary === undefined
  ) {
    return '-'
  }

  return Number(salary).toLocaleString('ja-JP') + ' 円'

}

const goBack = () => {

  router.push('/employees')

}

const goEdit = () => {

  router.push(
    `/employees/${route.params.id}/edit`
  )

}

onMounted(() => {

  loadEmployee()

})

</script>

<style scoped>

.detail-page {
  padding: 30px;
}

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

.header-buttons {
  display: flex;
  gap: 10px;
}

.back-button,
.edit-button {
  padding: 10px 18px;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
}

.back-button {
  background: #666;
}

.back-button:hover {
  background: #555;
}

.edit-button {
  background: #1976d2;
}

.edit-button:hover {
  background: #1565c0;
}

.detail-card {
  background: white;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px 30px;
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.detail-item label {
  font-size: 13px;
  color: #777;
  font-weight: 600;
}

.detail-item span {
  font-size: 16px;
}

.status {
  display: inline-block;
  width: fit-content;
  padding: 4px 10px;
  border-radius: 4px;
  font-size: 12px !important;
}

.status.active {
  background: #e8f5e9;
  color: #2e7d32;
}

.status.resigned {
  background: #ffebee;
  color: #c62828;
}

.status.on_leave {
  background: #fff3e0;
  color: #ef6c00;
}

.loading {
  padding: 40px;
  text-align: center;
}

.error {
  padding: 40px;
  text-align: center;
  color: #c62828;
}

@media (max-width: 768px) {

  .detail-grid {
    grid-template-columns: 1fr;
  }

}

</style>
