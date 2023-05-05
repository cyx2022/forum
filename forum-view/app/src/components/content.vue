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
      <div class="mbottom">
        {{ content.publishTime.substring(0, 10) }}
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  props: {
    content: {},
  },
  data() {
    return {
      member: {},
    };
  },
  methods: {
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
  },

  mounted() {
    // console.log(this.content)
    this.getMember();
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
</style>