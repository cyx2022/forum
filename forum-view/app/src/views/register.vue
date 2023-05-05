<!--
* @description 
* @fileName register.vue
* @author cyx
* @date 2023/03/20 23:52:04
!-->
<template>
  <div class="box">
    <div class="container">
      <div class="login-wrapper">
        <div class="header">注册</div>
        <div class="form-wrapper">
          <input
            type="text"
            name="username"
            placeholder="用户名"
            class="input-item"
            v-model="username"
            @mousedown="input"
          /><span>{{ usernameInfo }}</span>
          <input
            type="password"
            name="password"
            placeholder="密码"
            class="input-item"
            v-model="password"
            @mousedown="input"
          /><span>{{ passwordInfo }}</span>
          <input
            type="input"
            name="code"
            placeholder="验证码"
            class="input-item"
            v-model="code"
            @mousedown="input"
          />
          <span>{{ codeInfo }}</span
          ><img :src="'data:image/gif;base64,' + codeSrc" @click="getCode" />
          <div class="btn" @click="submit">注册</div>
        </div>
        <h4 v-if="msgVisibled">{{ msgdata }}</h4>
      </div>
    </div>
  </div>
</template>

<script>
import fn from "../utils/fn";
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      username: "",
      password: "",
      code: "",
      msgVisibled: false,
      //   错误提示
      usernameInfo: "",
      passwordInfo: "",
      codeInfo: "",
      //   验证码流
      codeSrc: "",
      // 后端提示信息
      msgdata: "",
      imgUUID: "",
    };
  },
  methods: {
    submit() {
      let userNameFlag = fn.checkUserName(this.username);
      let passwordFlag = fn.checkPassword(this.password);
      let codeFlag = fn.checkCode(this.code);
      if (userNameFlag.flag && passwordFlag.flag && codeFlag.flag) {
        // 验证成功
        http
          .post("/auth/sign", {
            username: this.username,
            password: this.password,
            code: this.code,
          })
          .then((res) => {
            localStorage.setItem("userId",JSON.stringify(res.data.object))
            this.$router.push("/login")
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        // 验证失败，显示失败信息
        this.usernameInfo = userNameFlag.msg;
        this.passwordInfo = passwordFlag.msg;
        this.codeInfo = codeFlag.msg;
      }
    },
    input() {
      this.usernameInfo = "";
      this.passwordInfo = "";
      this.codeInfo = "";
    },
    getCode() {
      http.get("/auth/captcha").then(
        (res) => {
          if (res.data.code != 200) {
            this.msgVisibled = true;
            this.msgdata = res.data.message;
            setTimeout(() => {
              this.msgVisibled = false;
              this.msgdata = "";
            }, 2000);
          } else {
            this.codeSrc = res.data.object.img;
            this.imgUUID = res.data.object.imgUUID;
          }
        },
        () => {}
      );
    },
  },
  mounted() {
    this.getCode();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.box {
  height: 100%;
}
.container {
  height: 100vh;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}
h4 {
  margin-top: 20px;
  text-align: center;
  display: inline-block;
  color: red;
  font-weight: 200;
}
.login-wrapper {
  position: relative;
  background-color: #fff;
  width: 358px;
  height: 530px;
  border-radius: 15px;
  padding: 0 50px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  position: relative;
}
.header {
  font-size: 38px;
  font-weight: bold;
  text-align: center;
  line-height: 200px;
}

.input-item {
  display: block;
  width: 100%;
  margin-bottom: 20px;
  border: 0;
  padding: 10px;
  border-bottom: 1px solid rgb(128, 125, 125);
  font-size: 15px;
  outline: none;
}
.input-item:placeholder {
  text-transform: uppercase;
}
.form-wrapper {
  position: relative;
}
.form-wrapper span {
  position: absolute;
  left: 0;
  color: red;
  transform: translateY(-15px);
  font-size: 13px;
}

.form-wrapper input:last-of-type {
  width: 60%;
}
.form-wrapper img {
  position: absolute;
  bottom: 78px;
  width: 35%;
  right: -20px;
  height: 50px;
}
.form-wrapper img:hover {
  cursor: pointer;
}
.btn {
  text-align: center;
  padding: 10px;
  width: 100%;
  margin-top: 40px;
  background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
  color: #fff;
}

.btn:hover {
  cursor: pointer;
}
</style>


<!DOCTYPE html>


