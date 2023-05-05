<!--
* @description 
* @fileName article.vue
* @author cyx
* @date 2023/03/22 19:34:39
!-->
<template>
  <div class="subbox">
    <div class="count">
      <div>{{ content.commentCount }}</div>
    </div>
    <div class="main">
      <div class="mhead">
        <div @click="toComment">{{ content.text }}</div>
        <div @click="visit">{{ member.name }}</div>
      </div>
      <div class="mcontent">
        <img
          v-if="content.urls != null && content.urls.length > 0"
          src="content.urls[0]"
        />
        <img
          v-if="content.urls != null && content.urls.length > 1"
          src="content.urls[1]"
        />
        <img
          v-if="content.urls != null && content.urls.length > 2"
          src="content.urls[2]"
        />
      </div>
      <div class="mbottom" v-if="content.publishTime != undefined">
        {{ content.publishTime.substring(0, 10) }}
      </div>
    </div>
    <div class="reason">
      <h5>类型：帖子</h5>
      <h5>举报原因</h5>
      <div v-for="item,index in reason" :key="index">
        {{ bans[item].description }}
    </div>
    </div>
    <div class="result">
      <button @click="refuse">拒绝</button>
      <button @click="accept">同意</button>
    </div>
  </div>
</template>
  
  <script>
import type from "../classes/type";
import http from "../http/request";
export default {
  name: "",
  props: {
    contentId: "",
  },
  data() {
    return {
      member: {},
      content: {},
      bans: [],
      inspect: {},
      reason:[],
    };
  },
  methods: {
    getBans() {
      http
        .get("/main/ban/list")
        .then((res) => {
          this.bans = res.data.object;

        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 进入content空间
    toComment() {
      this.$router.push({
        path: "/main/bar/commentL1",
        query: {
          barId: this.content.id,
          contentId: this.content.barId,
        },
      });
    },
    // 获取用户
    getMember() {
      http
        .get("/main/member/get", {
          params: {
            id: this.content.memberId,
          },
        })
        .then((res) => {
          this.member = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 访问用户
    visit() {
      this.$router.push({
        path: "/main/person",
        query: {
          memberId: this.member.id,
        },
      });
    },

    getContent(id) {
      http
        .get("/main/content/one", {
          params: {
            id,
          },
        })
        .then((res) => {
          this.content = res.data.object;
          this.getMember();
        })
        .catch((err) => {
          console.log(err);
        });
    },

    getInspect() {
      http
        .get("/main/inspect/content/one", {
          params: {
            contentId: this.contentId,
            type: type.CONTENT,
          },
        })
        .then((res) => {
            this.inspect=res.data.object
            this.reason= this.inspect.bans.split(',');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    accept(){
      
        http.post("/main/inspect/content/accept",{
            contentId:this.contentId,
        }).then((res)=>{
          if(res.data.object==true){
            this.$emit("refreshBan")
          }
        }).catch((err)=>{
            console.log(err)
        })
    },
    refuse(){
        http.post("/main/inspect/content/refuse",{
            contentId:this.contentId,
        }).then((res)=>{
          if(res.data.object==true){
            this.$emit("refreshBan")
          }
        }).catch((err)=>{
            console.log(err)
        })
    },

  },

  mounted() {
    // console.log(this.content)
    this.getContent(this.contentId);
    this.getBans();
    this.getInspect();
  },
};
</script>
  <style scoped>
.subbox {
  /* width: 100%; */
  border: 1px solid rgba(125, 125, 125, 0.2);
  box-sizing: content-box;
}
.subbox:hover {
  background-color: rgba(125, 125, 125, 0.05);
}
.subbox > div {
  display: inline-block;
}
.count {
  width: 10%;
  vertical-align: middle;
  text-align: center;
  /* background-color: red; */
}
.count > div {
  display: inline-block;
  margin-top: 10px;
  padding: 5px;
  background-color: rgba(125, 125, 125, 0.1);
  vertical-align: left;
  text-align: center;
}
.main {
  vertical-align: top;
  width: 90%;
}
.mhead {
  height: 40px;
  /* background-color: red; */
}
.mhead > div {
  height: 100%;
  line-height: 40px;
  display: inline-block;
  vertical-align: top;
}
.mhead div:nth-of-type(1) {
  box-sizing: border-box;
  width: 85%;
  /* background-color: red; */
  color: #2d64b3;
  font-size: 18px;
  text-align: left;
  padding-left: 10px;
  text-overflow: ellipsis;
}
.mhead div:nth-of-type(1):hover {
  text-decoration: underline;
  cursor: pointer;
}
.mhead div:nth-of-type(2):hover {
  cursor: pointer;
  color: #2d64b3;
}
.mhead div:nth-of-type(2) {
  /* background-color: green; */
  width: 15%;
  font-size: 14px;
  text-overflow: ellipsis;
}
.mbottom {
  height: 20px;
  line-height: 20px;
  text-align: right;
  padding-right: 10px;
  font-size: 12px;
}
.result {
  width: 100%;
  height: 30px;
  line-height: 30px;
  margin-bottom: 10px;
}
.result button {
  height: 30px;
  line-height: 30px;
  border: 0;
  position: relative;
  padding: 0 10px;
  border-radius: 5px;
  color: white;
}
.result button:hover {
  cursor: pointer;
}
.result button:nth-of-type(1) {
  background-color: red;
  position: absolute;
  left: 40%;
}
.result button:nth-of-type(2) {
  background-color: green;
  position: absolute;
  right: 40%;
}
.reason{
    width: 90%;
    margin-left: 5%;
    border-top: 1px solid ;
    padding: 0 10px;
    box-sizing: content-box;
}
h5{
    font-weight: 500;
    font-size: 16px;
    line-height: 30px;
}
.reason>div{
    line-height: 25px;
    font-size: 14px;
    color: red;
}

h5:nth-of-type(1){
  color: #38F;
}
h5:nth-of-type(2){
  color: gray;
}
</style>