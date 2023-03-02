<template>
    <img src="../assets/logo.png" alt="Juna" class="logo">
  <div class="login-box">
    <h2 style="color:#168FF0">Sign Up</h2>
    <form>
      <div class="user-box">
        <input type="text" name="" required="" v-model="user.firstName">
        <label>Ім'я</label>
      </div>
      <div class="user-box">
        <input type="text" name="" required="" v-model="user.lastName">
        <label>Прізвище</label>
      </div><div class="user-box">
        <input type="email" name="" required="" v-model="user.email">
        <label>Email</label>
      </div><div class="user-box">
        <input type="password" name="" required="" v-model="user.password">
        <label>Пароль</label>
      </div>

      <div class="radio">
        <div class="radio__1">
          <input id="radio-1" type="radio"  name="radio" value="CANDIDATES" checked v-model="user.role">
          <label for="radio-1">Я шукаю роботу</label>
        </div>

        <div class="radio__2">
          <input id="radio-2" type="radio" name="radio" value="EMPLOYERS" v-model="user.role">
          <label for="radio-2">Я роботодавець</label>
        </div>

        <div class="radio__3">
          <input id="radio-3" type="radio" name="radio" value="MENTORS" v-model="user.role">
          <label for="radio-3">Я ментор</label>
        </div>

      </div>

      <a @click="createUser()">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        Продовжити
      </a>
    </form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      user: {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        role: 'CANDIDATES',
      }
    }
  },

  methods: {
    createUser() {
      this.$emit('create', this.user)

      axios.post('http://localhost:8085/register', {
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email,
        password: this.user.password,
        role:this.user.role
      },)
          .then((response) => {
            localStorage.token = response.data.token
            localStorage.setItem(this.user.email, this.user.role)
          })

      if (this.user.role === 'CANDIDATES') {
        this.$router.push('/vacancies')
      } else if (this.user.role === 'EMPLOYERS') {
        this.$router.push('/candidates')
      } else if (this.user.role === 'MENTORS') {
        this.$router.push('/students')
      }

    },
  }
}
</script>

<style lang="scss" scoped>

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


.logo {
  height: 51%;
  width: 44%;
  padding-top: 18%;
  padding-right: 6%;
  margin-top: -5%;
  margin-left: -40%;
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