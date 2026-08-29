```vue
<script setup>
import { ref } from 'vue'
import { createEmployee } from '../api/employeeApi'

const emit = defineEmits(['created'])

const name = ref('')
const email = ref('')
const error = ref('')
const loading = ref(false)

const submitForm = async () => {
  error.value = ''

  if (!name.value.trim() || !email.value.trim()) {
    error.value = 'Name and Email are required.'
    return
  }

  loading.value = true

  try {
    const response = await createEmployee({
      name: name.value,
      email: email.value,
    })

    emit('created', response.data)

    name.value = ''
    email.value = ''
  } catch (err) {
    console.error(err)
    error.value = 'Failed to create employee.'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <form class="employee-form" @submit.prevent="submitForm">
    <h2>Add Employee</h2>

    <div class="form-group">
      <label for="name">Name</label>
      <input
        id="name"
        v-model="name"
        type="text"
        placeholder="Enter name"
      />
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input
        id="email"
        v-model="email"
        type="email"
        placeholder="Enter email"
      />
    </div>

    <p v-if="error" class="error">
      {{ error }}
    </p>

    <button type="submit" :disabled="loading">
      {{ loading ? 'Adding...' : 'Add Employee' }}
    </button>
  </form>
</template>

<style scoped>
.employee-form {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
}

.error {
  color: red;
}
</style>
```
