<template>
  <div>
    <h2>HomePage</h2>
    <router-link to="/signup">Register</router-link>
    <br>
    <router-link to="/login">Login</router-link>
  </div>
</template>

<script>
export default {
  methods: {

  },

  created: function() {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    if (token != null) {
      if (role === 'CANDIDATES') {
        this.$router.push('/vacancies')
      } else if (role === 'EMPLOYERS') {
        this.$router.push('/candidates')
      } else if (role === 'MENTORS') {
        this.$router.push('/students')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
h2 {
  background-color: #168FF0;
  color: white;
}
</style>