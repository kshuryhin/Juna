<template>
    <img src="../assets/logo.png" alt="Juna" class="logo">
  <div class="login-box">
    <h2 style="color:#168FF0">Sign Up</h2>
    <form>
      <div class="user-box">
        <input type="text" name="" required="" v-model="user.name">
        <label>Ім'я</label>
      </div>
      <div class="user-box">
        <input type="text" name="" required="" v-model="user.surname">
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
          <input id="radio-1" type="radio"  name="radio" value="1">
          <label for="radio-1">Я шукаю роботу</label>
        </div>

        <div class="radio__2">
          <input id="radio-2" type="radio" name="radio" value="2" checked>
          <label for="radio-2">Я роботодавець</label>
        </div>
      </div>

      <a href="" @click="createUser()">
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
        name: '',
        surname: '',
        email: '',
        password: '',
      }
    }
  },

  methods: {
    createUser() {
      this.$emit('create', this.user)
      this.user = {
        name: '',
        surname: '',
        email: '',
        password: '',
      }
      axios.post('http://localhost:8089/user', {
        name: this.user.name,
        surname: this.user.surname,
        email: this.user.email,
        password: this.user.password
      })

    },


  }
}
</script>

<style lang="scss" scoped>


///*  RADIO  */
//.radio {
//  grid-column: 1 / 2;
//  display: grid;
//  grid-template-columns: repeat(2, 1fr);
//  justify-items: center;
//  input { display: none; }
//
//  &__1, &__2 {
//    & input:checked {
//      & ~ label {
//        //box-shadow: $inner-shadow;
//        &::after {
//          background: var(--primary);}
//      }
//    }
//    label {
//      //box-shadow: $shadow;
//      position: relative;
//      display: flex;
//      justify-content: center;
//      align-items: center;
//      cursor: pointer;
//      width: 2.8rem;
//      height: 2.8rem;
//      border-radius: 50%;
//      &:hover {&::after{background: var(--primary);}}
//
//      &::after {
//        content: "";
//        position: absolute;
//        width: 1.4rem;
//        height: 1.4rem;
//        background: var(--greyDark);
//        border-radius: 50%;
//        transition: 0.3s ease;
//      }
//    }
//  }
//}



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

//.login-box a span:nth-child(1) {
//  top: 0;
//  left: -100%;
//  width: 100%;
//  height: 2px;
//  background: linear-gradient(90deg, transparent, #03e9f4);
//  animation: btn-anim1 1s linear infinite;
//}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%,100% {
    left: 100%;
  }
}

//.login-box a span:nth-child(2) {
//  top: -100%;
//  right: 0;
//  width: 2px;
//  height: 100%;
//  background: linear-gradient(180deg, transparent, #03e9f4);
//  animation: btn-anim2 1s linear infinite;
//  animation-delay: .25s
//}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%,100% {
    top: 100%;
  }
}

//.login-box a span:nth-child(3) {
//  bottom: 0;
//  right: -100%;
//  width: 100%;
//  height: 2px;
//  background: linear-gradient(270deg, transparent, #03e9f4);
//  animation: btn-anim3 1s linear infinite;
//  animation-delay: .5s
//}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%,100% {
    right: 100%;
  }
}
//
//.login-box a span:nth-child(4) {
//  bottom: -100%;
//  left: 0;
//  width: 2px;
//  height: 100%;
//  background: linear-gradient(360deg, transparent, #03e9f4);
//  animation: btn-anim4 1s linear infinite;
//  animation-delay: .75s
//}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%,100% {
    bottom: 100%;
  }
}

</style>