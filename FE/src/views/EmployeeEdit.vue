<template>
  <div class="edit-page">

    <div class="page-header">
      <div>
        <h1>社員編集</h1>
        <p>Employee Edit</p>
      </div>

      <button class="back-button" @click="goBack">
        ← 詳細に戻る
      </button>
    </div>

    <div v-if="loading" class="loading">
      データを読み込んでいます...
    </div>

    <form v-else class="edit-card" @submit.prevent="saveEmployee">

      <div class="form-grid">

        <div class="form-item">
          <label>ID</label>
          <input
            v-model="form.id"
            type="text"
            disabled
          />
        </div>

        <div class="form-item">
          <label>社員コード <span>*</span></label>
          <input
            v-model="form.employeeCode"
            type="text"
            required
          />
        </div>

        <div class="form-item">
          <label>氏名 <span>*</span></label>
          <input
            v-model="form.name"
            type="text"
            required
          />
        </div>

        <div class="form-item">
          <label>メール <span>*</span></label>
          <input
            v-model="form.email"
            type="email"
            required
          />
        </div>

        <div class="form-item">
          <label>電話番号</label>
          <input
            v-model="form.phone"
            type="text"
          />
        </div>

        <div class="form-item">
          <label>部署ID</label>
          <input
            v-model.number="form.departmentId"
            type="number"
            min="1"
          />
        </div>

        <div class="form-item">
          <label>役職ID</label>
          <input
            v-model.number="form.positionId"
            type="number"
            min="1"
          />
        </div>

        <div class="form-item">
          <label>雇用形態</label>
          <select v-model="form.employmentType">
            <option value="">選択してください</option>
            <option value="正社員">正社員</option>
            <option value="契約社員">契約社員</option>
            <option value="パート">パート</option>
            <option value="アルバイト">アルバイト</option>
          </select>
        </div>

        <div class="form-item">
          <label>ステータス</label>
          <select v-model="form.status">
            <option value="ACTIVE">ACTIVE</option>
            <option value="RESIGNED">RESIGNED</option>
          </select>
        </div>

        <div class="form-item">
          <label>入社日</label>
          <input
            v-model="form.hireDate"
            type="date"
          />
        </div>

        <div class="form-item">
          <label>退職日</label>
          <input
            v-model="form.resignationDate"
            type="date"
          />
        </div>

        <div class="form-item">
          <label>上司ID</label>
          <input
            v-model.number="form.managerId"
            type="number"
            min="1"
          />
        </div>

        <div class="form-item">
          <label>給与</label>
          <input
            v-model.number="form.salary"
            type="number"
            min="0"
          />
        </div>

      </div>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <div v-if="successMessage" class="success-message">
        {{ successMessage }}
      </div>

      <div class="form-actions">

        <button
          type="button"
          class="cancel-button"
          @click="goBack"
        >
          キャンセル
        </button>

        <button
          type="submit"
          class="save-button"
          :disabled="saving"
        >
          {{ saving ? '保存中...' : '保存' }}
        </button>

      </div>

    </form>

  </div>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

import {
  getEmployeeById,
  updateEmployee
} from '../services/employeeApi'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const saving = ref(false)

const errorMessage = ref('')
const successMessage = ref('')

const form = ref({
  id: null,
  employeeCode: '',
  name: '',
  email: '',
  phone: '',
  departmentId: null,
  positionId: null,
  employmentType: '',
  status: 'ACTIVE',
  hireDate: '',
  resignationDate: '',
  managerId: null,
  salary: null
})

const loadEmployee = async () => {

  try {

    loading.value = true
    errorMessage.value = ''

    const result =
      await getEmployeeById(route.params.id)

    if (!result) {

      errorMessage.value =
        '社員情報が見つかりません。'

      return
    }

    form.value = {
      id: result.id ?? null,
      employeeCode: result.employeeCode ?? '',
      name: result.name ?? '',
      email: result.email ?? '',
      phone: result.phone ?? '',
      departmentId: result.departmentId ?? null,
      positionId: result.positionId ?? null,
      employmentType: result.employmentType ?? '',
      status: result.status ?? 'ACTIVE',
      hireDate: result.hireDate ?? '',
      resignationDate: result.resignationDate ?? '',
      managerId: result.managerId ?? null,
      salary: result.salary ?? null
    }

  } catch (error) {

    console.error(
      'Failed to load employee:',
      error
    )

    errorMessage.value =
      '社員情報の取得に失敗しました。'

  } finally {

    loading.value = false

  }

}

const saveEmployee = async () => {

  try {

    saving.value = true
    errorMessage.value = ''
    successMessage.value = ''

    const employeeData = {
      employeeCode: form.value.employeeCode,
      name: form.value.name,
      email: form.value.email,
      phone: form.value.phone || null,
      departmentId: form.value.departmentId || null,
      positionId: form.value.positionId || null,
      employmentType:
        form.value.employmentType || null,
      status: form.value.status || null,
      hireDate: form.value.hireDate || null,
      resignationDate:
        form.value.resignationDate || null,
      managerId: form.value.managerId || null,
      salary: form.value.salary || null
    }

    await updateEmployee(
      route.params.id,
      employeeData
    )

    successMessage.value =
      '社員情報を更新しました。'

    setTimeout(() => {
      router.push(
        `/employees/${route.params.id}`
      )
    }, 700)

  } catch (error) {

    console.error(
      'Failed to update employee:',
      error
    )

    if (
      error.response?.data?.message
    ) {
      errorMessage.value =
        error.response.data.message
    } else {
      errorMessage.value =
        '社員情報の更新に失敗しました。'
    }

  } finally {

    saving.value = false

  }

}

const goBack = () => {

  router.push(
    `/employees/${route.params.id}`
  )

}

onMounted(() => {
  loadEmployee()
})

</script>

<style scoped>

.edit-page {
  padding: 30px;
  max-width: 1200px;
  margin: 0 auto;
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

.back-button {
  padding: 10px 18px;
  border: none;
  border-radius: 6px;
  background: #666;
  color: white;
  cursor: pointer;
}

.back-button:hover {
  background: #555;
}

.edit-card {
  background: white;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px 30px;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 7px;
}

.form-item label {
  font-size: 13px;
  color: #555;
  font-weight: 600;
}

.form-item label span {
  color: #d32f2f;
}

.form-item input,
.form-item select {
  width: 100%;
  box-sizing: border-box;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  background: white;
}

.form-item input:focus,
.form-item select:focus {
  outline: none;
  border-color: #1976d2;
}

.form-item input:disabled {
  background: #f5f5f5;
  color: #777;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #eee;
}

.cancel-button,
.save-button {
  padding: 11px 24px;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.cancel-button {
  background: #757575;
}

.cancel-button:hover {
  background: #616161;
}

.save-button {
  background: #1976d2;
}

.save-button:hover {
  background: #1565c0;
}

.save-button:disabled {
  background: #90caf9;
  cursor: not-allowed;
}

.loading {
  padding: 40px;
  text-align: center;
}

.error-message {
  margin-top: 20px;
  padding: 12px 15px;
  border-radius: 5px;
  background: #ffebee;
  color: #c62828;
}

.success-message {
  margin-top: 20px;
  padding: 12px 15px;
  border-radius: 5px;
  background: #e8f5e9;
  color: #2e7d32;
}

@media (max-width: 768px) {

  .edit-page {
    padding: 15px;
  }

  .page-header {
    align-items: flex-start;
    gap: 15px;
  }

  .form-grid {
    grid-template-columns: 1fr;
  }

  .edit-card {
    padding: 20px;
  }

}

</style>

