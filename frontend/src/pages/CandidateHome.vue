<template>
  <div>
    <h2>VACANCIES</h2>

    <LogoutButton></LogoutButton>
  </div>
</template>

<script>
import LogoutButton from "@/components/LogoutButton.vue";

export default {
  components: {LogoutButton},

  created: function (){
    const role = localStorage.getItem('role')
    if (role !== 'CANDIDATES') {
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss" scoped>
h2 {
  background-color: aqua;
  color: #2c3e50;
}
</style>