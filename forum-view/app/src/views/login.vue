<!--
* @description 
* @fileName login.vue
* @author cyx
* @date 2023/03/20 21:32:12
!-->
<template>
  <div class="box">
    <div class="login-container">
      <div class="left-container">
        <div class="title"><span>登录</span></div>
        <div class="input-container">
          <input
            type="text"
            name="username"
            placeholder="用户名"
            v-model="username"
          /><span>{{ usernameInfo }}</span>
          <input
            type="password"
            name="password"
            placeholder="密码"
            v-model="password"
          /><span>{{ passwordInfo }}</span>
          <input
            type="input"
            name="code"
            placeholder="验证码"
            v-model="code"
          /><span>{{ codeInfo }}</span
          ><img :src="'data:image/gif;base64,' + codeSrc" @click="getCode" />
        </div>
        <div class="third-login">
          第三方登录
          <span class="iconfont">#icon-disanfangdenglu;</span>
        </div>
        <div class="message-container">
          <span>忘记密码</span>
        </div>
        <h4 v-if="msgVisibled">{{ msgdata }}</h4>
      </div>

      <div class="right-container">
        <div class="regist-container">
          <span class="regist" @click="toRegister">注册</span>
        </div>
        <div class="action-container">
          <span @click="submit">提交</span>
        </div>
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
      username: "cyx",
      password: "123456",
      code: "",

      //   错误提示
      usernameInfo: "",
      passwordInfo: "",
      codeInfo: "",
      msgVisibled: false,
      //   验证码流
      codeSrc: "",
      // 后端提示信息
      msgdata: "",
      imgUUID: "",
    };
  },
  methods: {
    // 登录
    submit() {
      let userNameFlag = fn.checkUserName(this.username);
      let passwordFlag = fn.checkPassword(this.password);
      let codeFlag = fn.checkCode(this.code);
      if (userNameFlag.flag && passwordFlag.flag && codeFlag.flag) {
        // 清空信息
        http
          .post("/auth/login", {
            username: this.username,
            password: this.password,
            code: this.code,
          })
          .then((res) => {
            this.username = "";
            this.password = "";
            this.code = "";
            // 保存token,用户id
            localStorage.setItem("mId",JSON.stringify(res.data.object.mId))
            localStorage.setItem("access_token",JSON.stringify(res.data.object.access_token))
            localStorage.setItem("refresh_token",JSON.stringify(res.data.object.refresh_token))
            this.$router.push("/main/home")
          })
          .catch((err) => {
            this.$message.error("服务端异常");
          });
      } else {
        // 验证失败，显示失败信息
        this.usernameInfo = userNameFlag.msg;
        this.passwordInfo = passwordFlag.msg;
        this.codeInfo = codeFlag.msg;
        console.log(this.usernameInfo);
        console.log(this.passwordInfo);
        console.log(this.codeInfo);
      }
    },
    // 跳转注册页
    toRegister() {
      this.$router.push("/register");
    },
    // 获取验证码
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
  padding: 0;
  margin: 0;
}

.box {
  height: 100vh;
  background-image: linear-gradient(
    to bottom right,
    rgb(114, 135, 254),
    rgb(130, 88, 186)
  );
  overflow: hidden;
}

.login-container {
  overflow: hidden;
  position: relative;
  width: 600px;
  height: 475px;
  margin: 0 auto;
  margin-top: 10%;
  border-radius: 15px;
  box-shadow: 0 10px 50px 0px rbg(59, 45, 159);
  background-color: rgb(95, 76, 194);
}
h4 {
  text-align: center;
  display: inline-block;
  color: red;
  font-weight: 200;
  margin-top: 20px;
}
.left-container {
  display: inline-block;
  width: 330px;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  padding: 60px;
  background-image: linear-gradient(
    to bottom right,
    rgb(118, 76, 163),
    rgb(92, 103, 211)
  );
}
.title {
  color: #fff;
  font-size: 18px;
  font-weight: 200;
}
.title span {
  border-bottom: 3px solid rgb(237, 221, 22);
}
.input-container {
  padding: 20px 0;
  position: relative;
}
.input-container input {
  height: 40px;
  line-height: 40px;
  /* background-color: red; */
  font-size: 16px;
}
.input-container input:last-of-type {
  width: 60%;
}
.input-container img {
  position: absolute;
  bottom: 40px;
  width: 35%;
  right: 0;
  height: 50px;
}
.input-container img:hover {
  cursor: pointer;
}
.input-container span {
  position: absolute;
  left: 0;
  transform: translateY(-18px);
  color: red;
  font-size: 13px;
}

input {
  border: 0;
  background: none;
  outline: none;
  color: #fff;
  margin: 20px 0;
  display: block;
  width: 100%;
  padding: 5px 0;
  transition: 0.2s;
  border-bottom: 1px solid rgb(199, 191, 219);
}
input:hover {
  border-bottom-color: #fff;
}
::-webkit-input-placeholder {
  color: rgb(199, 191, 219);
}

.third-login {
  width: 100%;
  height: 40px;
  background-color: red;
  transform: translateY(-20px);
  line-height: 40px;
}
.message-container {
  font-size: 14px;
  transition: 0.2s;
  color: rgb(199, 191, 219);
  cursor: pointer;
}
.message-container:hover {
  color: #fff;
}
.right-container {
  width: 145px;
  display: inline-block;
  height: calc(100% - 120px);
  vertical-align: top;
  padding: 60px 0;
}
.regist-container {
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: 200;
}
.regist-container span {
  border-bottom: 3px solid rgb(237, 221, 22);
  margin-top: 100px;
}
.regist:hover {
  cursor: pointer;
}
.action-container {
  font-size: 16px;
  color: #fff;
  text-align: center;
  position: relative;
  top: 260px;
}
.action-container span {
  border: 1px solid rgb(237, 221, 22);
  padding: 10px;
  display: inline;
  line-height: 32px;
  border-radius: 32px;
  position: absolute;
  bottom: 30px;
  left: calc(72px - 20px);
  transition: 0.2s;
  cursor: pointer;
}
.action-container span:hover {
  background-color: rgb(237, 221, 22);
  color: rgb(95, 76, 194);
}
</style>

<!DOCTYPE html>
