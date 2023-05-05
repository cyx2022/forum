<!--
* @description 
* @fileName person.vue
* @author cyx
* @date 2023/03/23 10:07:05
!-->
<template>
  <div class="box">
    <div class="head">
      <div class="img">
        <img src="../img/1.jpg" />
        <div @click="doFocus" :class="{'focus':flag}">{{flag?"已关注":"关注"}}</div>
        <div @click="chat">私信</div>
      </div>
      <div class="subhead">
        <div class="subhead1">
          <div>{{ member.name }}</div>
        </div>
        <div class="subhead2">
          <div class="formargin">
            <span>吧龄:</span
            ><span>{{
              getYear() -
              parseInt((member.registerTime + "").substring(0, 4)) +
              1
            }}</span>
          </div>
          <div>
            <span>发帖:</span><span>{{ contentCount }}</span>
          </div>
        </div>
        <div class="subhead3">
          <div>禁言</div>
          <div>举报</div>
        </div>
      </div>
    </div>
    <div class="body">
      <div class="thead">
        <div
          v-for="(item, index) in subLabel"
          :key="index"
          @click="changeLabel(index)"
          :class="{ click: activeItem == index }"
        >
          {{ item.text }}
        </div>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  components: {},
  data() {
    return {
      subLabel: [
        { path: "/main/person/show", text: "主页" },
        { path: "/main/person/contentstore", text: "帖子" },
      ],
      activeItem: this.$route.query.activeIndex||0,
      member: {},
      contentCount: 0,
      flag:false,
    };
  },
  methods: {
    // 更换标签
    changeLabel(index) {
      if (this.activeItem == index) {
        return;
      }
      this.activeItem = index;
      if (this.$route.query.memberId == undefined) {
        this.$router.push({path:this.subLabel[index].path,query:{
          activeIndex:this.activeItem,}
        });
      } else {
        this.$router.push({
          path: this.subLabel[index].path,
          query: {
            memberId: this.$route.query.memberId,
            activeIndex:this.activeItem
          },
        });
      }
    },
    // 获取用户信息
    getMember() {
      let mId;
      // 用户自己访问
      if (this.$route.query.memberId == undefined) {
        mId = localStorage.getItem("mId")
          ? JSON.parse(localStorage.getItem("mId"))
          : null;
      } else {
        // 其他用户访问
        mId = this.$route.query.memberId;
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
    // 获取内容条数
    getContentCount() {
      let mId = localStorage.getItem("mId")
        ? JSON.parse(localStorage.getItem("mId"))
        : null;
      http
        .get("/main/content/count", {
          params: {
            memberId: mId,
          },
        })
        .then((res) => {
          this.contentCount = res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    // 获取当前年份
    getYear() {
      let date = new Date();
      let year = date.getFullYear();
      return year;
    },
    doFocus(){
      http.post("/main/focus/member/add",{
        id:this.member.id,
      }).then((res)=>{
        this.flag=res.data.object
      }).catch((err)=>{
        console.log(err)
      })
    },
    checkFocus(){
      http.get("/main/focus/member/check",{
        params:{
          id:this.member.id
        }
      }).then((res)=>{
        this.flag=res.data.object
      }).catch((err)=>{
        console.log(err)
      })
    },
    chat(){
      this.$router.push({
            path: "/main/msg",
            query: {
              memberId: this.member.id,
              activeIndex:1
            },
          })
    }
  },
  mounted() {
    this.getMember();
    this.getContentCount();
    this.checkFocus();
  },
};
</script>
<style scoped>
.box {
  box-sizing: border-box;
  width: 70vw;
  /* background-color: green; */
  margin-left: 15vw;
  margin-top: 5.5vw;
}
.head {
  height: 10.5vw;
  background-color: rgba(125, 125, 125, 0.1);
  text-align: left;
}
.img {
  display: inline-block;
  width: 10vw;
  height: 10vw;
  /* background-color: red; */
}
.img img {
  width: 9vw;
  height: 9vw;
  transform: translateX(7.5px) translateY(-5vw);
}
.img > div {
  height: 2vw;
  line-height: 2vw;
  /* padding: 7px 10px 7px 10px; */
  transform: translateY(-4.2vw);
  background-color: red;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 20px;
  text-align: center;
  color: white;
  margin-bottom: 10px;
}
.img > div:hover {
  cursor: pointer;
}
.img > div:nth-of-type(1) {
  /* background-color: #d93030; */
}
.img > div:nth-of-type(2) {
  background-color: #2a7dfa;
}

.subhead {
  display: inline-block;
  width: 58vw;
  height: 10vw;
  /* background-color: blanchedalmond; */
  vertical-align: top;
  margin-left: 2vw;
}
.subhead1 {
  height: 5vw;
  /* background-color: red; */
}
.subhead2,
.subhead3 {
  height: 2.5vw;
  /* background-color: green; */
}
.subhead1 > div {
  height: 5vw;
  line-height: 5vw;
  text-align: left;
  /* background-color: firebrick; */
  float: left;
  margin-left: 10px;
}
.subhead2 div:nth-of-type(1) {
  float: left;
  margin-left: 10px;
}
.subhead2 div:nth-of-type(2) {
  float: left;
  margin-left: 50px;
}

.subhead2 > div,
.subhead3 > div {
  font-size: 12px;
  height: 2.5vw;
  line-height: 2.5vw;
  color: rgba(125, 125, 125);
  /* background-color: firebrick; */
}
.subhead2 {
  /* background-color: green; */
}
.subhead3 {
  /* background-color: yellow; */
}
.subhead1 div:nth-of-type(1) {
  font-size: 20px;
  font-weight: 700;
  font-family: STHeiti, "Microsoft Yahei", Arial, SimSun;
  /* vertical-align: text-bottom; */
}

.subhead2 > div {
  /* font-weight: 700; */
  text-align: left;
  /* font-family: STHeiti, "Microsoft Yahei", Arial, SimSun; */
}
.subhead3 > div {
  /* background-color: yellow; */
  float: right;
  margin-right: 10px;
}
.subhead3 div:nth-of-type(2):hover {
  cursor: pointer;
  color: red;
}
.subhead3 div:nth-of-type(1):hover {
  cursor: pointer;
  color: black;
}

.body {
  width: 100%;
  background-color: rgba(125, 125, 125, 0.07);
  margin-top: 10px;
  border-top: 1px solid rgba(125, 125, 125, 0.2);
}

.thead {
  overflow: hidden;
}
.thead > div {
  float: left;
  height: 50px;
  line-height: 50px;
  width: 80px;
  text-align: center;
}

.thead > div:hover {
  /* color: #38f; */
  cursor: pointer;
}
.click {
  background-color: rgba(125, 125, 125, 0.3);
  color: #38f;
}
.focus{
  background-color: green !important;
}
</style>