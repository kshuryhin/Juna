<template>
  <div>
    <button @click="logout()" id="butt">Вийти</button>
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
#butt {
  font-size: 17px;
  background-color: #168FF0;
  color: white;
  border: 1px solid white;
  width: 4%;
  padding-top: 0.3%;
  padding-bottom: 0.3%;
  border-radius: 40px;
}


#butt:hover {
  color: #168FF0;
  background-color: white;
  border-color: #168FF0;
  cursor: pointer;

}





</style>