<template>

  <div class="add-page">

    <!-- HEADER -->
    <div class="page-header">

      <div>
        <h1>社員追加</h1>
        <p>Employee Registration</p>
      </div>

      <button
        class="back-button"
        @click="goBack"
      >
        ← 一覧に戻る
      </button>

    </div>


    <!-- FORM -->
    <div class="form-card">

      <form @submit.prevent="handleSubmit">

        <div class="form-grid">

          <!-- 社員コード -->
          <div class="form-group">

            <label>社員コード *</label>

            <input
              v-model="form.employeeCode"
              type="text"
              placeholder="EMP011"
              required
            />

          </div>


          <!-- 氏名 -->
          <div class="form-group">

            <label>氏名 *</label>

            <input
              v-model="form.name"
              type="text"
              placeholder="山田 太郎"
              required
            />

          </div>


          <!-- メール -->
          <div class="form-group">

            <label>メール *</label>

            <input
              v-model="form.email"
              type="email"
              placeholder="yamada@example.com"
              required
            />

          </div>


          <!-- 電話番号 -->
          <div class="form-group">

            <label>電話番号</label>

            <input
              v-model="form.phone"
              type="text"
              placeholder="090-1234-5678"
            />

          </div>


          <!-- 部署ID -->
          <div class="form-group">

            <label>部署ID</label>

            <input
              v-model.number="form.departmentId"
              type="number"
              min="1"
              placeholder="1"
            />

          </div>


          <!-- 役職ID -->
          <div class="form-group">

            <label>役職ID</label>

            <input
              v-model.number="form.positionId"
              type="number"
              min="1"
              placeholder="1"
            />

          </div>


          <!-- 雇用形態 -->
          <div class="form-group">

            <label>雇用形態 *</label>

            <select
              v-model="form.employmentType"
              required
            >

              <option value="">
                選択してください
              </option>

              <option value="正社員">
                正社員
              </option>

              <option value="契約社員">
                契約社員
              </option>

              <option value="パート">
                パート
              </option>

            </select>

          </div>


          <!-- ステータス -->
          <div class="form-group">

            <label>ステータス *</label>

            <select
              v-model="form.status"
              required
            >

              <option value="ACTIVE">
                ACTIVE
              </option>

              <option value="ON_LEAVE">
                ON_LEAVE
              </option>

              <option value="RESIGNED">
                RESIGNED
              </option>

            </select>

          </div>


          <!-- 入社日 -->
          <div class="form-group">

            <label>入社日 *</label>

            <input
              v-model="form.hireDate"
              type="date"
              required
            />

          </div>


          <!-- 退職日 -->
          <div class="form-group">

            <label>退職日</label>

            <input
              v-model="form.resignationDate"
              type="date"
            />

          </div>


          <!-- 上司ID -->
          <div class="form-group">

            <label>上司ID</label>

            <input
              v-model.number="form.managerId"
              type="number"
              min="1"
              placeholder="1"
            />

          </div>


          <!-- 給与 -->
          <div class="form-group">

            <label>給与 *</label>

            <input
              v-model.number="form.salary"
              type="number"
              min="0"
              step="0.01"
              placeholder="500000"
              required
            />

          </div>

        </div>


        <!-- ERROR -->
        <div
          v-if="errorMessage"
          class="error-message"
        >
          {{ errorMessage }}
        </div>


        <!-- BUTTONS -->
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

  </div>

</template>


<script setup>

import { ref } from 'vue'

import { useRouter } from 'vue-router'

import { createEmployee }
  from '../services/employeeApi'


// =========================
// ROUTER
// =========================

const router = useRouter()


// =========================
// FORM
// =========================

const form = ref({

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


// =========================
// STATE
// =========================

const saving = ref(false)

const errorMessage = ref('')


// =========================
// SUBMIT
// =========================

const handleSubmit = async () => {

  try {

    saving.value = true

    errorMessage.value = ''


    // =========================
    // REQUEST DATA
    // =========================

    const employee = {

      employeeCode:
        form.value.employeeCode,

      name:
        form.value.name,

      email:
        form.value.email,

      phone:
        form.value.phone || null,

      departmentId:
        form.value.departmentId || null,

      positionId:
        form.value.positionId || null,

      employmentType:
        form.value.employmentType,

      status:
        form.value.status,

      hireDate:
        form.value.hireDate,

      resignationDate:
        form.value.resignationDate || null,

      managerId:
        form.value.managerId || null,

      salary:
        form.value.salary

    }


    // =========================
    // API
    // =========================

    await createEmployee(employee)


    // =========================
    // SUCCESS
    // =========================

    alert('社員を登録しました。')

    router.push('/employees')


  } catch (error) {

    console.error(
      'Failed to create employee:',
      error
    )


    // =========================
    // ERROR MESSAGE
    // =========================

    if (
      error.response &&
      error.response.data
    ) {

      if (
        typeof error.response.data === 'string'
      ) {

        errorMessage.value =
          error.response.data

      } else {

        errorMessage.value =
          '社員の登録に失敗しました。'

      }

    } else {

      errorMessage.value =
        'サーバーに接続できませんでした。'

    }

  } finally {

    saving.value = false

  }

}


// =========================
// BACK
// =========================

const goBack = () => {

  router.push('/employees')

}

</script>


<style scoped>

.add-page {

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
   BUTTON
========================= */

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


/* =========================
   CARD
========================= */

.form-card {

  background: white;

  border-radius: 8px;

  padding: 30px;

  box-shadow:
    0 2px 8px rgba(0, 0, 0, 0.08);

}


/* =========================
   GRID
========================= */

.form-grid {

  display: grid;

  grid-template-columns:
    repeat(2, 1fr);

  gap: 20px 30px;

}


/* =========================
   FORM
========================= */

.form-group {

  display: flex;

  flex-direction: column;

  gap: 7px;

}

.form-group label {

  font-size: 13px;

  color: #555;

  font-weight: 600;

}

.form-group input,
.form-group select {

  padding: 10px 12px;

  border: 1px solid #ccc;

  border-radius: 6px;

  font-size: 14px;

  outline: none;

}

.form-group input:focus,
.form-group select:focus {

  border-color: #1976d2;

}


/* =========================
   ERROR
========================= */

.error-message {

  margin-top: 20px;

  padding: 12px;

  background: #ffebee;

  color: #c62828;

  border-radius: 6px;

}


/* =========================
   ACTIONS
========================= */

.form-actions {

  display: flex;

  justify-content: flex-end;

  gap: 10px;

  margin-top: 30px;

  padding-top: 20px;

  border-top: 1px solid #eee;

}

.cancel-button,
.save-button {

  padding: 10px 22px;

  border: none;

  border-radius: 6px;

  color: white;

  cursor: pointer;

  font-size: 14px;

}

.cancel-button {

  background: #777;

}

.cancel-button:hover {

  background: #666;

}

.save-button {

  background: #1976d2;

}

.save-button:hover {

  background: #1565c0;

}

.save-button:disabled {

  background: #aaa;

  cursor: not-allowed;

}


/* =========================
   RESPONSIVE
========================= */

@media (max-width: 768px) {

  .form-grid {

    grid-template-columns: 1fr;

  }

  .page-header {

    align-items: flex-start;

    gap: 15px;

  }

  .form-card {

    padding: 20px;

  }

}

</style>

