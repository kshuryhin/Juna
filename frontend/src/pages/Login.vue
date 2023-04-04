<template>
  <div class="logo-container">
    <img src="../assets/logo.png" alt="Juna" class="logo" @click="this.$router.push('/')">
  </div>
  <div class="login-box">
    <h2 style="color:#168FF0">Sign In</h2>
    <form>
      <div class="user-box">
        <input type="text" name="" required="" v-model="user.email">
        <label>Email</label>
      </div>
      <div class="user-box">
        <input type="password" name="" required="" v-model="user.password">
        <label>Пароль</label>
      </div>

      <a @click="login()">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        Продовжити

      </a>
      <p class="or" @click="this.$router.push('/signup')">Або зареєструватись</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
import roles from "@/roles";
import statuses from "@/statuses";
export default {
  data() {
    return {
      user: {
        email: '',
        password: '',
      }
    }
  },

  methods: {
    login() {
      this.$emit('create', this.user)

      axios.post('http://localhost:8085/authenticate', {
        email: this.user.email,
        password: this.user.password
      },)
          .then((response) => {
            localStorage.token = response.data.token
            localStorage.role = response.data.role
            const role = localStorage.getItem('role')
            switch (role) {
              case roles.CANDIDATE:
                this.$router.push('/vacancies')
                break
              case roles.EMPLOYER:
                console.log(role)
                this.$router.push('/candidates')
                break
              case roles.MENTOR:
                this.$router.push('/students')
                break
              default:
                alert("Unknown role")
            }

          })
          .catch(reason => {
            if (reason.response.status === statuses.FORBIDDEN) {
              alert("You have wrong data")
            }

          })

      }

    },
}
</script>

<style lang="scss" scoped>

.or {
  font-size: 15px;

  &:hover {
    cursor: pointer;
  }
}


a:hover {
  cursor: pointer;
}



input:invalid {
  animation: shake 300ms;
  color: red;
}

@keyframes shake {
  25% {transform: translateX(4px);}
  50% {transform: translateX(-4px);}
  75% {transform: translateX(4px);}
}


.logo-container {
  display: inline-block;
  height: 51%;
  width: 44%; margin-top: -5%;
  margin-left: -40%;

}

.logo {
  height: 100%;
  width: 100%;
  margin-top: 40%;
  margin-left: -10%;

  &:hover {
    cursor:pointer;
  }
}

.inputs {
  padding: 2%;
}


//-------------------------------------------------


.login-box {
  position: absolute;
  top: 51%;
  left: 70%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: transparent;
  box-sizing: border-box;
  box-shadow: 0 15px 25px #168FF0;
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #168FF0;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid  lightgray;
  outline: none;
  background: transparent;
}

.login-box .user-box input:focus {
  border-bottom: 1px solid #168FF0;
}


.login-box .user-box label {
  position: absolute;
  top:0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: grey;
  pointer-events: none;
  transition: .5s;


}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #168FF0;
  font-size: 12px;
}

.login-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #168FF0;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
  letter-spacing: 4px
}

.login-box a:hover {
  background: #168FF0;
  color: white;
  border-radius: 5px;
  box-shadow: 0 0 5px #168FF0,
  0 0 25px #168FF0,
  0 0 50px #168FF0,
  0 0 100px #168FF0;
}

.login-box a span {
  position: absolute;
  display: block;
}


@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%,100% {
    left: 100%;
  }
}


@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%,100% {
    top: 100%;
  }
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%,100% {
    right: 100%;
  }
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%,100% {
    bottom: 100%;
  }
}

</style>