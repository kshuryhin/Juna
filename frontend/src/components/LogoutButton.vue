<template>
  <div>
    <button @click="logout()">LogOUT</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'logout-button',
  methods: {
    logout() {
      axios.put('http://localhost:8085/logout', {}, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      })
      localStorage.clear()
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>