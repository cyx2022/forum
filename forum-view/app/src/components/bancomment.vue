<!--
* @description 
* @fileName bancomment.vue
* @author cyx
* @date 2023/05/03 14:42:34
!-->
<template>
  <div>
    <div class="content">{{comment.content}}</div>
    <div class="reason">
      <h5>类型：评论</h5>
      <h5>举报原因</h5>
      <div v-for="(item, index) in reason" :key="index">
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
import http from "../http/request";
export default {
  name: "",
  props: {
    commentId: "",
  },
  data() {
    return {
      bans: [],
      reason: [],
      comment: {},
    };
  },
  methods: {
    getComment() {
      http
        .get("/main/comment/get", {
          params: {
            id: this.commentId,
          },
        })
        .then((res) => {
          this.comment=res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getInspect() {
      http
        .get("/main/inspect/content/one", {
          params: {
            commentId: this.commentId,
            type: type.COMMENT,
          },
        })
        .then((res) => {
          this.inspect = res.data.object;
          this.reason = this.inspect.bans.split(",");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    accept() {
      http
        .post("/main/inspect/comment/accept", {
          commentId: this.commentId,
        })
        .then((res) => {
          if (res.data.object == true) {
            this.$emit("refreshBan");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    refuse() {
      http
        .post("/main/inspect/comment/refuse", {
          commentId: this.commentId,
        })
        .then((res) => {
          if (res.data.object == true) {
            this.$emit("refreshBan");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
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
  },
  mounted() {
    this.getBans();
    this.getComment();
  },
};
</script>
<style scoped>
.reason {
  width: 90%;
  margin-left: 5%;
  border-top: 1px solid;
  padding: 0 10px;
  box-sizing: content-box;
}
h5 {
  font-weight: 500;
  font-size: 16px;
  line-height: 30px;
}
.reason > div {
  line-height: 25px;
  font-size: 14px;
  color: red;
}

h5:nth-of-type(1) {
  color: #38f;
}
h5:nth-of-type(2) {
  color: gray;
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
.content{
  padding: 0 30px;
  color: #38F;
  line-height: 30px;

}
</style>