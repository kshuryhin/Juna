<template>
  <router-link to="/createVacancy" class="create-vacancy-btn">Create Vacancy</router-link>
</template>

<script>
export default {
  name: "MyVacancies"
}
</script>

<!-- YourComponent.vue -->
<style scoped>
.create-vacancy-btn {
  display: inline-block;
  background-color: #168FF0;
  color: white;
  text-decoration: none;
  padding: 10px 20px;
  font-weight: bold;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
}

.create-vacancy-btn:hover {
  background-color: #0E6CB3;
}
</style>
