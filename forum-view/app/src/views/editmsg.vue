<!--
* @description 
* @fileName editmsg.vue
* @author cyx
* @date 2023/04/08 00:08:22
!-->
<template>
  <div class="box">
    <h5>编辑个人信息</h5>
    <div class="msg">
      <span>昵称</span>
      <input type="input" v-model="member.name" />
    </div>
    <div class="msg">
      <span>原密</span>
      <input type="input" placeholder="请输入原始密码" v-model="member.password" />
    </div>

    <div class="msg">
      <span>新密</span>
      <input type="input" v-model="newpassword" />
    </div>

    <div class="msg">
      <span>性别</span>
      <div class="sex">
        <span>男</span
        ><input
          type="radio"
          checked="true"
          value="1"
          v-model="member.sex"
          name="sex"
        /><span>女</span
        ><input type="radio" value="0" v-model="member.sex" name="sex" />
      </div>
    </div>

    <div class="msg">
      <span>电话</span>
      <input type="input" v-model="member.phone" />
    </div>

    <div class="msg">
      <span>邮箱</span>
      <input type="input" v-model="member.email" />
    </div>
    <div class="img">
      <p class="value">更换头像</p>
      <input
        ref="photo"
        type="file"
        style="opacity: 0"
        name="file"
        @change="updateImg"
      />
    </div>
    <div class="bottom">
      <img :src="member.avatar" />
    </div>
    <div class="submit">
    <div @click="submit">确定</div>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
let data;
export default {
  name: "",
  data() {
    return {
      member: {},
      newpassword: "",
      avatar:"",
      oldName:'',
    };
  },
  computed: {
    headers() {
      const access_token = localStorage.getItem("access_token");
      return {
        access_token,
      };
    },
  },
  methods: {
    getMember() {
      let id = localStorage.getItem("mId");
      http
        .get("/main/member/get", {
          params: {
            id: id,
          },
        })
        .then((res) => {
          this.member = res.data.object;
          this.oldName=this.member.name;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    // 更换头像
    updateImg() {
      let file = this.$refs.photo.files[0];
      let formData = new FormData();
      formData.append("photo", file);
      http
        .post("/upload/img/save", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          this.member.avatar=res.data.object
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 确定修改
    submit(){
      http.post("/main/member/update",{
        newName:this.member.name,
        oldName:this.oldName,
        oldPassword:'',
        newPassword:this.newpassword,
        sex:this.member.sex,
        phone:this.member.phone,
        email:this.member.email,
        avatar:this.member.avatar,
      }).then((res)=>{
        if(res.data.object.code==200){
          // 登录成功,调整登录页
          this.$router.push("/login")
        }else{
        console.log(res.data.object)
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  mounted() {
    this.getMember();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
input {
  outline: none;
  text-decoration: none;
  border: 0;
  height: 30px;
  line-height: 30px;
  width: 50%;
  letter-spacing: 2px;
  text-indent: 5px;
  border-bottom: 1px solid rgba(125, 125, 125, 0.5);
  font-size: 16px;
  font-family: "Microsoft YaHei";
}
.box {
  border: 1px solid rgba(125, 125, 125, 0.3);
  width: 54vw;
  margin-left: 23vw;
  box-sizing: border-box;
  margin-bottom: 40px;
}
.box .msg {
  text-align: left;
  height: 50px;
  font-size: 16px;
}
h5 {
  height: 70px;
  line-height: 70px;
  font-weight: 500;
  font-size: 20px;
}
.box > div > span {
  padding: 0 20px;
  letter-spacing: 5px;
}
.sex {
  display: inline-block;
}
.sex input {
  width: 20px;
  height: 20px;
}
.sex span {
  margin-right: 10px;
}
.sex input {
  margin-right: 20px;
}
.img {
  position: relative;
  padding: 5px 10px;
  overflow: hidden;
  margin-left: 20px;
  text-align: left;
}

.value {
  position: absolute;
  left: 0;
  top: 0;
  height: 30px;
  width: 80px;
  line-height: 30px;
  text-align: center;
  background-color: #38f;
  border-radius: 2px;
}
.img input {
  height: 30px;
  width: 80px;
}
.bottom {
  text-align: left;
  margin-left: 20px;
}
img {
  width: 20vw;
  height: 20vw;
}
.submit{
  text-align: right;
  margin-right: 20px;
  margin-bottom: 20px;
}
.submit:hover{
  cursor: pointer;
}
.submit>div{
  padding: 5px 10px ;
  box-sizing: content-box;
  background-color: red;
  display: inline-block;
  color: white;

}
</style>