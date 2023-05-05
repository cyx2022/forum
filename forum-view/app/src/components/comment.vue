<!--
* @description 
* @fileName comment.vue
* @author cyx
* @date 2023/03/22 22:02:55
!-->

<!-- 二级评论体 -->
<template>
  <div class="commentbox">
    <div class="commenttop">
      <img src="../img/1.jpg" />
      <div class="fromandto">
        <span class="from" @click="toMember(from.id)">{{ from.name }}</span
        ><span class="mid" v-if="to >= 0">回复</span><span class="to" v-if=" to>= 0" @click="toMember(to.id)">{{ to.name }}</span>
        <span class="comment">{{ comment.content }}</span>
      </div>
    </div>
    <div class="commentbottom">
      <span class="ban" @click="ban">举报</span
      ><span class="date">{{ comment.time.substring(0, 10) }}</span
      ><span class="return" @click="show">回复</span>
    </div>
  </div>
</template>

<script>
import type from '../classes/type'
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      comment: this.subComment,
      from: {},
      to: {},
    };
  },
  props: {
    subComment: {},
  },
  methods: {
    toMember(id){
      this.$router.push({
        path:"/main/person/show",query:{
          memberId:id,
      }})
    },
    getMember() {
      // 请求发送者
      http
        .get("/main/member/get", {
          params: {
            id: this.comment.fromMemberId,
          },
        })
        .then((res) => {
          this.from = res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
        if(this.to>=0)
      // 请求接收者
      http
        .get("/main/member/get", {
          params: {
            id: this.comment.toMemberId,
          },
        })
        .then((res) => {
          this.to = res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    show(){
      this.$emit("show")
    },
    ban(){
      this.$emit("ban",type.SUBCOMMENT,this.subComment.id)
    }
  },
  mounted() {
    this.getMember();
  },
};
</script>
<style scoped>
.commentbox {
  /* background-color: red; */

  /* background-color: red; */
  width: 100%;
  box-sizing: content-box;
}
.commenttop {
  /* height: 50px; */
  text-align: left;
  padding-left: 10px;
}
.commenttop img {
  display: inline-block;
  width: 40px;
  height: 40px;
  margin-top: 10px;
}
.fromandto {
  box-sizing: border-box;
  vertical-align: top;
  display: inline-block;
  margin-left: 10px;
  /* background-color: green; */
  padding-top: 10px;
}
.from,
.mid,
.to {
  font-size: 12px;
}
.from,
.to {
  margin: 0 3px 0 3px;
  color: #38f;
}
.from,.to{
  cursor: pointer;
}
.comment {
  font-size: 14px;
  margin-left: 10px;
}
.commentbottom {
  box-sizing: border-box;
  height: 20px;
  font-size: 12px;
  /* background-color: aqua; */
  text-align: right;
  padding-right: 10px;
}
.commentbottom span {
  margin-left: 10px;
}
.ban {
  color: red;
}
.ban:hover{
  cursor: pointer;
}
.return {
  color: #38f;
}
.return:hover {
  cursor: pointer;
}
</style>