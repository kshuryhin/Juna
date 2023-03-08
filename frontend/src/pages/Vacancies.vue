<template>
<div>
      <div class="navbar">
        <p id="logo" @click="this.$router.push('/')">Juna</p>
        <LogoutButton id="logout"></LogoutButton>
      </div>

  <div class="vacanclist">
    <input type="text" class="search" placeholder="Пошук" autofocus>
    <br><br>
    <div class="vacancy" v-for="vacancy in vacancies" @click="fetchVacancies()">
      <svg class="star" width="26" height="24" viewBox="0 0 26 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M13 1.61804L15.4432 9.13729L15.5554 9.48278H15.9187H23.8249L17.4286 14.1299L17.1347 14.3435L17.247 14.689L19.6902 22.2082L13.2939 17.5611L13 17.3475L12.7061 17.5611L6.30985 22.2082L8.753 14.689L8.86526 14.3435L8.57137 14.1299L2.17511 9.48278H10.0813H10.4446L10.5568 9.13729L13 1.61804Z" fill="white" stroke="#168FF0"/>
      </svg>
      <div><p class="vac-name"> {{ vacancy.name }} </p> </div>
      <div class="vac-description">{{ vacancy.description }}</div>
      <div class="vac-company"></div>
    </div>
  </div>

</div>
</template>

<script>
import LogoutButton from "@/components/LogoutButton.vue";
import roles from "@/roles";
import axios from "axios";
import statuses from "@/statuses";

export default {
  components: {LogoutButton},
  data() {
    return {
      vacancies: []
    }
  },

  methods: {
    //  async fetchVacancies() {
    //   const token = localStorage.getItem('token')
    //   await axios.get("http://localhost:8085/vacancies", {
    //     headers: {
    //       "Authorization" : token
    //     }
    //   })
    //       .then(response => {
    //         this.vacancies = response.data
    //       })
    //       .catch(reason => {
    //         if (reason.response.status === statuses.TOKEN_EXPIRED) {
    //           this.refreshToken()
    //
    //         }
    //         Vue.$forceUpdate()
    //       })
    //
    // },
    //
    // refreshToken() {
    //   const token = localStorage.getItem('token')
    //   axios.put("http://localhost:8085/exchange", {
    //     email: localStorage.getItem('email')
    //   })
    //       .then(response => {
    //         localStorage.setItem('token', response.data.token)
    //       })
    // }

    async fetchVacancies() {
      try {
        const response = await axios.get("http://localhost:8085/vacancies", {
          headers: {
            "Authorization": localStorage.getItem('token')
          }
        });
        this.vacancies = response.data;
      } catch (error) {
        if (error.response.status === statuses.TOKEN_EXPIRED) {
          try {
            const response = await axios.put("http://localhost:8085/exchange", {
              email: localStorage.getItem('email')
            });
            localStorage.setItem('token', response.data.token);
            const response2 = await axios.get("http://localhost:8085/vacancies", {
              headers: {
                "Authorization": localStorage.getItem('token'),
              }
            });
            this.vacancies = response2.data;
          } catch (error2) {
            console.error(error2);
          }
        } else {
          console.error(error);
        }
      }
    },
  },

  mounted() {
    this.fetchVacancies();
  },
}

  // created: function (){
  //   const role = localStorage.getItem('role')
  //   if (role !== roles.CANDIDATE) {
  //     this.$router.push('/')
  //   }
  // }

</script>

<style lang="scss" scoped>
@import url('https://fonts.cdnfonts.com/css/poetsen-one');

.navbar {
  position: fixed;
  height: 9%;
  width: 110%;
  background-color: #168FF0;
  margin-top: -1%;
  margin-left: -1%;
  padding: 0;
}

#logo {
  position: absolute;
  color: white;
  font-family: 'Poetsen One', sans-serif;
  font-size: 70px;
  margin-left: 13%;
  margin-top: -0.5%;
}
#logo:hover {
  cursor: pointer;
}

#logout {
  position: relative;
  margin-top: 1.3%;
  margin-right: -70%;
}

.vacanclist {
  padding-top: 10%;
  margin-left: 40%;
  margin-right: 10%;
}

.vacancy {
  display: inline-block;
  padding: 15px;
  border: 1px solid #168FF0;
  border-radius: 10px;
  margin-top: 15px;
  height: 20%;
  width: 97%;
  box-shadow: #2c3e50;
}

.search {
  border: 1px solid #168FF0;
  border-radius: 10px;
  height: 45px;
  width: 100%;
}

.vac-name {
  color: #168FF0;
  display: flex;
  padding-left: 5%;
  padding-top: -50%;
  padding-bottom: -1%;
  font-size: 1.5em;

  &:hover {
    cursor: pointer;
  }
}

.vac-company {
  position: absolute;
  display: flex;
  color: #168FF0;
  margin-right: 5%;
  padding-left: 40%;
  padding-bottom: 20%;
}

.vac-description {
  height: 4.5em;
  padding-left: 5%;
  padding-bottom: 2%;
  text-align: start;
  overflow: hidden;
}

.star {
  margin-left: -99%;
  margin-top: -1%;
}
</style>