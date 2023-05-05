<!--
* @description 
* @fileName show.vue
* @author cyx
* @date 2023/03/23 23:33:13
!-->
<template>
  <div class="sbox">
    <!-- 是用户自身才可以修改 -->
    <h5 v-if="getLocalMemberId()===this.memberId" @click="toEditMsg()">修改个人信息</h5>
    <div>
      <span>性别 :</span>
      <span>{{ member.sex == 1 ? "男" : "女" }}</span>
    </div>
    <div>
      <span>电话：</span>
      <span>{{ member.phone }}</span>
    </div>
    <div>
      <span>邮箱：</span>
      <span>{{ member.eamil }}</span>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      member: {},
      memberId:this.$route.query.memberId||localStorage.getItem("mId")
          ? JSON.parse(localStorage.getItem("mId"))
          : null,
    };
  },
  methods: {
    // 获取用户信息 判断是用户自己还是访问用户
    getMember() {
      let mId;
      if (this.$route.query.memberId != undefined) {
        mId = this.$route.query.memberId;
      } else {
        mId=this.getLocalMemberId();
      }
      http
        .get("/main/member/get", {
          params: {
            id: mId,
          },
        })
        .then((res) => {
          this.member = res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },

    getLocalMemberId(){
      let mId = localStorage.getItem("mId")
          ? JSON.parse(localStorage.getItem("mId"))
          : null;
          return mId;
    },
    // 跳转修改个人信息页
    toEditMsg(){
      this.$router.push("/main/editmsg")
    }
  },
  mounted() {
    this.getMember();
  },
};
</script>
<style scoped>
.sbox {
  width: 100%;
}
.sbox > div {
  line-height: 40px;
  text-align: left;
  box-sizing: border-box;
  padding-left: 20px;
}
.sbox > div span:nth-of-type(1) {
  color: #38f;
}
.sbox > div span:nth-of-type(2) {
  color: rgba(125, 125, 125, 1);
  padding-left: 20px;
}
h5 {
  text-align: right;
  font-weight: 500;
  font-size: 12px;
  padding-right: 10px;
}
h5:hover {
  color: green;
  cursor: pointer;
}
</style>