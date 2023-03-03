<template>
  <div>
    <h2>CANDIDATES</h2>
<LogoutButton></LogoutButton>
  </div>
</template>

<script>
import LogoutButton from "@/components/LogoutButton.vue";
import roles from "@/roles";

export default {
  components: {LogoutButton},
  created: function (){
    const role = localStorage.getItem('role')
    if (role !== roles.EMPLOYER) {
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