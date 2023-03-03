<template>
  <div>
    <h2>HomePage</h2>
    <router-link to="/signup">Register</router-link>
    <br>
    <router-link to="/login">Login</router-link>
  </div>
</template>

<script>
import roles from "@/roles";

export default {
  methods: {

  },

  created: function() {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    if (token != null) {
      switch (role) {
        case roles.CANDIDATE:
          this.$router.push('/vacancies')
          break
        case roles.EMPLOYER:
          this.$router.push('/candidates')
          break
        case roles.MENTOR:
          this.$router.push('/students')
          break
        default:
          alert("Unknown role")
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