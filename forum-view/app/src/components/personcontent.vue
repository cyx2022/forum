<!--
* @description 
* @fileName personarticle.vue
* @author cyx
* @date 2023/03/23 12:16:01
!-->
<template>
  <div class="pbox">
    <div class="phead">
      <div
        title="删除"
        class="delFlag"
        v-if="check(content.memberId, myMemberId)"
        @click="del(content.id)"
      >
        删除
      </div>
      <div
        title="修改"
        class="checkFlag"
        v-if="check(content.memberId, myMemberId)"
        @click="toEdit(myMemberId, content.id)"
      >
        修改
      </div>
      <div title="查看" @click="toContent">{{ "标题："+content.title }}</div>
      <div title="进入吧空间" @click="toBar()" class="bar">
        {{ "空间："+content.barName }}
      </div>
      <div>{{ content.publishTime.substring(0, 10) }}</div>
    </div>
    <div class="pcontent">{{ content.text }}</div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "personarticle",
  props: {
    content: {},
  },
  data() {
    return {
      myMemberId: localStorage.getItem("mId")
        ? JSON.parse(localStorage.getItem("mId"))
        : null,
    };
  },
  methods: {
    check(sourceId, resId) {
      return sourceId == resId;
    },
    // 进入bar空间
    toBar() {
      this.$router.push({
        path: "/main/bar",
        query: {
          barId: this.content.barId,
        },
      });
    },

    // 进入content空间
    toContent() {
      this.$router.push({
        path: "/main/bar/commentL1",
        query: {
          barId: this.content.barId,
          contentId: this.content.id,
        },
      });
    },
    // 修改content
    toEdit(memberId, contentId) {
      this.$router.push({
        path: "/main/editcontent",
        query: {
          memberId,
          contentId,
        },
      });
    },
    del(id) {
      http
        .delete("/main/content/del", {
          params: {
            contentId: id,
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    console.log(this.content);
  },
};
</script>
<style scoped>
.pbox {
  width: 100%;
  border-bottom: 1px solid rgba(125, 125, 125, 0.1);
}
.phead {
  height: 40px;
  line-height: 40px;
  /* background-color: red; */
  text-align: left;
  position: relative;
  padding-left: 20px;
}
.phead div {
  display: inline-block;
  margin-right: 15px;
  font-size: 12px;
  color: rgba(125, 125, 125);
}
.phead div:nth-of-type(2) {
  color: #38f;
  font-size: 14px;
}

.phead div:nth-of-type(2):hover,
.phead div:nth-of-type(3):hover {
  cursor: pointer;
}
.phead div:nth-of-type(3){
  font-size: 14px;
  color: black;
}
.phead div:last-of-type {
  position: absolute;
  right: 10px;
}

.pcontent {
  width: 100%;
  min-height: 30px;
  padding: 0 20px;
}
.checkFlag {
  color: #38f;
}
.checkFlag:hover {
  cursor: pointer;
}

.delFlag {
  color: red !important;
}
.delFlag:hover {
  cursor: pointer;
}

.bar {
  font-size: 13px !important;
}
</style>