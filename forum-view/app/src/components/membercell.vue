<!--
* @description 
* @fileName memberList.vue
* @author cyx
* @date 2023/04/18 10:54:45
!-->
<template>
  <div class="cbox">
    <div class="ban">+</div>
    <img :src="member.avatar" />
    <div class="common">
      <div>{{ member.name }}</div>
      <div>{{ absMsg.content }}</div>
    </div>
  </div>
</template>

<script>
import http from '../http/request'
export default {
  name: "",
  props: {
    absMsg: {},

  },
  data() {
    return {
      member:{}
    };
  },
  methods: {
    // 获取用户信息
    getMember() {
      http
        .get("/main/member/get", {
          params: {
            id: this.absMsg.senderId,
          },
        })
        .then((res) => {
          this.member=res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
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
img {
  vertical-align: middle;
  height: 50px;
  width: 50px;
  border-radius: 25px;
}
.cbox {
  width: 100%;
  line-height: 80px;
}
.cbox:hover {
  background-color: white;
  /* color: #38F; */
  cursor: pointer;
}
.cbox > div {
  vertical-align: top;
  height: 80px;
  display: inline-block;
}
.cbox > div:nth-of-type(1) {
  width: 15%;
  text-align: center;
}
.common {
  padding: 0 10px;
  box-sizing: border-box;
}
.common > div {
  line-height: 30px;
}
.common > div:nth-of-type(1) {
  margin-top: 10px;
}
.common > div:nth-of-type(2) {
  font-size: 13.5px;
  text-overflow: clip;
}
</style>