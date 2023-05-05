<!--
* @description 
* @fileName main.vue
* @author cyx
* @date 2023/03/21 15:57:04
!-->
<template>
  <div>
    <header>
      <div>
        <ul class="lbar">
          <li>
            <span>欢迎 :&nbsp&nbsp</span><span>{{ member.name }}</span>
          </li>
          <li>
            <span>等级 :&nbsp&nbsp</span><span>LV{{ level }}</span>
          </li>
          <li>
            <span>积分 :&nbsp&nbsp</span><span>{{ point }}</span>
          </li>
        </ul>
      </div>

      <div>
        <ul class="rbar">
          <li
            v-for="(item, index) in rbar"
            :key="index"
            @click="changeScene(index)"
          >
            {{ item.text }}
          </li>
        </ul>
      </div>
    </header>
    <div class="search" :class="{ hidden: !inputFlag }" @mouseout="outWord">
      <input
        type="input"
        v-model="word"
        @change="fetchData()"
        @mouseenter="inWord"
      />
      <div @click="search" class="input">搜索</div>
      <div class="reminder" :class="{ out: reminder.length == 0 }">
        <ul>
          <li
            v-for="(item, index) in reminder"
            :key="index"
            @click="toItem(index)"
          >
            {{ item.type == 1 ? item.title : item.text }}
          </li>
        </ul>
      </div>
    </div>
    <main><router-view :key="key"></router-view></main>
    <div class="welfare">
      <div class="tip">每日积分赠送</div>
      <img :src="defaultImg" />
      <div class="button" v-if="flag" @click="getWelfare">{{ msg[0] }}</div>
      <div class="msg" v-if="!flag">{{ msg[1] }}</div>
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
      member: {},
      level: "",
      point: "",
      word: "",
      rbar: [
        { text: "退出", url: "/auth/logout" },
        { text: "消息", url: "/main/msg" },
        { text: "我的", url: "/main/person" },
        { text: "首页", url: "/main/home" },
      ],
      reminder: [],
      inputFlag: false,
      defaultImg: "",
      flag: true,
      msg: ["获取", "今日活动结束"],
    };
  },

  computed: {
    key() {
      return this.$route.name !== undefined
        ? this.$route.name + +new Date()
        : this.$route + +new Date();
    },
  },

  methods: {
    changeScene(index) {
      if (index == 0) {
        // 退出登录
        http
          .get(this.rbar[index].url)
          .then((res) => {
            if (res.data.code === 200) {
              this.$router.replace("/login");
            } else {
            }
          })
          .catch((err) => {
            fn.serverError();
          });
      } else {
        // 改变场景
        this.$router.push(this.rbar[index].url);
      }
    },
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
          // 获取等级信息
          this.getLevel(res.data.object.levelId);
          // 获取积分信息
          this.getPoint(res.data.object.id);
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    getLevel(mid) {
      http
        .get("/main/level/id", {
          params: {
            id: mid,
          },
        })
        .then((res) => {
          this.level = res.data.object.value;
        })
        .catch(() => {
          this.$message.error("服务端异常");
        });
    },
    getPoint(mid) {
      http
        .get("/point/get/mid", {
          params: {
            mid: mid,
          },
        })
        .then((res) => {
          this.point = res.data.object.hold;
        })
        .catch((err) => {
          console.log("point");
          console.log(err);
          this.$message.error("服务端异常");
        });
    },
    search() {
      let tword = this.word;
      this.word = "";

      if (this.$route.query.word == tword) return;
      this.$router.push({
        path: "/main/search/bar",
        query: {
          word: tword,
        },
      });
    },
    fetchData() {
      http
        .get("/search/get/word", {
          params: {
            word: this.word,
          },
        })
        .then((res) => {
          this.reminder = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    inWord() {
      this.inputFlag = true;
    },
    outWord() {
      this.inputFlag = false;
      // this.reminder=[]
    },
    toItem(index) {
      let id = this.reminder[index].id;
      if (this.reminder[index].type == 1) {
        this.$router.push(
          this.$router.push({
            path: "/main/bar",
            query: {
              barId: id,
            },
          })
        );
      } else {
        let barId;
        http
          .get("/main/bar/content", {
            params: {
              contentId: id,
            },
          })
          .then((res) => {
            barId = res.data.object;
          })
          .catch((err) => {
            console.log(err);
          });
        this.$router.push({
          path: "/main/bar/commentL1",
          query: {
            barId: barId,
            contentId: contentId,
          },
        });
      }
    },
    getDefaultImg() {
      http
        .get("/upload/img/default")
        .then((res) => {
          this.defaultImg = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getWelfare() {
      http
        .get("/point/welfare/url")
        .then((res) => {
          if (res.data.code != 6001) {
            http
              .get("/point/welfare/point/pass/" + res.data.object)
              .then((res) => {
                console.log(res)
                if (res.data.code == 200) {
                  this.flag = false;
                  this.msg[1]="一天仅限制一次";
                  this.$message({
                    showClose: true,
                    message: res.data.object,
                    type: "success",
                  });
                } else if (res.data.code == 6001) {
                  this.$message({
                    showClose: true,
                    message: res.data.object,
                  });
                } else {
                  this.msg[1] = res.data.object;
                }
              })
              .catch((err) => {
                console.log(err);
              });
          } else {
            this.flag = false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getMember();
    this.getDefaultImg();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.lbar li {
  float: left;
  margin-left: 30px;
}
.rbar li {
  float: right;
  margin-left: 30px;
}
.rbar li:hover {
  cursor: pointer;
  color: #38f;
}
li {
  list-style: none;
}
header {
  height: 50px;
  font-size: 17px;
  line-height: 50px;
  position: relative;
  border-bottom: 1px solid rgba(125, 125, 125, 0.5);
}
header > div {
  display: inline-block;
}

header > div:nth-of-type(1) {
  position: absolute;
  left: 15vw;
}
header > div:nth-of-type(2) {
  position: absolute;
  right: 15vw;
}
.search {
  width: 50vw;
  margin-left: 25vw;
  margin-top: 10px;
  height: 6vh;
  position: relative;
  border: 1px solid #38f;
  margin-bottom: 10px;
  box-sizing: border-box;
  color: red !important;
}
.hidden {
  overflow: hidden;
}
.search:hover {
  cursor: pointer;
}
.search input {
  outline: none;
  border: none;
  height: 6vh;
  line-height: 6vh;
  width: 85%;
  position: absolute;
  left: 0;
  text-indent: 10px;
  font-size: 20px;
  font-weight: 50;
  color: #53545e;
}
.search .input {
  display: inline-block;
  font-size: 17px;
  background-color: #38f;
  color: white;
  height: 100%;
  line-height: 6vh;
  width: 15%;
  position: absolute;
  top: 0;
  right: 0;
  text-align: center;
}
.reminder {
  /* height: 200px !important; */
  width: 85% !important;
  background-color: white !important;
  z-index: 999;
  position: absolute;
  left: 0;
  top: 6vh !important;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
  border: 1px solid #38f;
  border-top: 0;
  margin-top: 1px;
  padding-bottom: 4px;
}
.search li {
  text-align: left;
  padding-left: 10px;
  height: 25px;
  line-height: 25px;
  text-overflow: ellipsis;
  color: black;
}
.search li:hover {
  color: #38f;
}
.out {
  height: 0;
  padding: 0;
  border: 0;
}
.welfare {
  width: 10vw;
  height: calc(10vw + 60px);
  position: absolute;
  top: 20vh;
  right: 10px;
  /* border: 0.1px solid #38F; */
}
.welfare img {
  width: 10vw;
  height: 10vw;
  vertical-align: middle;
}
.tip,
.msg,
.button {
  height: 30px;
  line-height: 30px;
  text-align: center;
}
.tip {
  color: #38f;
}
.button {
  background-color: red;
  color: white;
}
.button:hover {
  cursor: pointer;
}
.msg {
  color: rgba(0, 0, 0, 0.3);
  background-color: rgba(125, 125, 125, 0.2) !important;
}
</style>