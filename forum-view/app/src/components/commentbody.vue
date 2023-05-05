<!--
* @description 
* @fileName subcommentL1.vue
* @author cyx
* @date 2023/03/22 20:56:51
!-->

<!-- 一级评论体 -->
<template>
  <div class="subbody">
    <div class="lL1">
      <img src="../img/1.jpg" />
      <div @click="toMember(member.id)" class="member">{{ member.name }}</div>
      <div>平民</div>
    </div>
    <div class="rL1">
      <div class="content">{{ comment.content }}</div>
      <div class="bottom">
        <span @click="ban( type.COMMENT, comment.id)">举报</span>
        <span>{{ comment.time.substring(0, 10) }}</span>
        <span @click="toComment" class="commentflag">查看评论</span>
      </div>
      <div class="container">
        <div class="commentlist" v-if="commentFlag">
          <Comment
            v-for="(item, index) in subComments"
            :key="index"
            :subComment="item"
            @show="show"
            @ban="ban"
          ></Comment>
          <div class="bottombar" @click="show">发言</div>
          <div class="input" v-if="inputFlag">
            <textarea v-model="content" placeholder="请评论"></textarea>
            <div @click="submit">提交</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import type from "../classes/type";
import http from "../http/request";
import Comment from "../components/comment.vue";
export default {
  name: "",
  props: {
    comment: {},
    bans: [],
  },
  components: { Comment },
  data() {
    return {
      commentFlag: false,
      member: {},
      subComments: [],
      content: "",
      inputFlag: false,
      type:type
    };
  },
  methods: {
    showDialog(type, itemId) {
      this.dialogVisible = true;
      this.type = type;
      this.itemId = itemId;
    },
    confirmSelection() {
      http
        .post("/main/inspect/add", {
          banArr: this.selectedItems,
          type: this.type,
          itemId: this.itemId,
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
      this.dialogVisible = false;
      this.itemId = "";
      this.type = "";
    },

    toMember(id) {
      this.$router.push({
        path: "/main/person/show",
        query: {
          memberId: id,
        },
      });
    },
    toComment() {
      this.commentFlag = !this.commentFlag;
    },
    getMember() {
      http
        .get("/main/member/get", {
          params: {
            id: this.comment.memberId,
          },
        })
        .then((res) => {
          this.member = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 子组件控制输入框显示
    show() {
      this.inputFlag = !this.inputFlag;
    },
    // 获取二级评论
    getSubComment() {
      http
        .get("/main/subComment/comment", {
          params: {
            commentId: this.comment.id,
            current: 1,
            size: 8,
          },
        })
        .then((res) => {
          this.subComments = res.data.object.records;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 提交二级评论
    submit() {
      http
        .post("/main/subComment/add", {
          commentId: this.comment.id,
          content: this.content,
        })
        .then((res) => {
          this.subComments.unshift(res.data.object);
          this.content=""
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ban(type,itemId) {
      this.$emit("ban",type,itemId);
    },
  },
  mounted() {
    this.getMember();
    this.getSubComment();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.subbody {
  width: 70vw;
  overflow: hidden;
  box-sizing: content-box;
  border-bottom: 1px solid rgba(125, 125, 125, 0.2);
}
.subbody > div {
  float: left;
}
.lL1 {
  width: 10vw;
  padding: 20px 0 20px 0;
}
.lL1 img {
  width: 6vw;
  height: 6vw;
  margin-left: 2vw;
}
.lL1 div:nth-of-type(1) {
  text-align: center;
  margin: 10px 0 10px 0;
  color: #38f;
  font-size: 13px;
}
.lL1 div:nth-of-type(2) {
  padding: 5px 10px 5px 10px;
  font-size: 12px;
  border: 1px solid rgba(125, 125, 125, 0.2);
  display: inline-block;
  color: #4c4c4c;
  margin-left: calc(3vw + 11px);
}
.rL1 {
  box-sizing: content-box;
  width: 60vw;
  vertical-align: top;
  position: relative;
  /* background-color: green; */
  /* overflow: hidden; */
  /* height: 1000px; */
  /* background-color: yellow; */
  /* height: 1000px; */
}
.content {
  text-align: left;
  padding-top: 20px;
}
.bottom {
  height: 30px;
  line-height: 30px;
  text-align: right;
  padding-right: 20px;
  font-size: 12px;
}
.bottom span {
  margin: 0 10px 0 10px;
}
.bottom span:nth-of-type(1) {
  color: red;
}
.bottom span:nth-of-type(1):hover {
  cursor: pointer;
}
.bottom span:nth-of-type(2) {
  color: rgba(125, 125, 125, 0.8);
}
.bottom span:nth-of-type(3) {
  display: inline-block;
  color: #38f;
  background-color: rgba(125, 125, 125, 0.1);
  height: 100%;
  padding: 0 10px 0 10px;
}
.commentlist {
  position: relative;
  min-height: 130px;
  /* background-color: red; */
  width: calc(60vw - 30px);
  background-color: rgba(125, 125, 125, 0.05);
  margin-top: 0;
  /* position: absolute; */
  /* right: 30px; */
  box-sizing: border-box;
  /* transform: translateX(30px); */
  /* 不生效 */
  /* transition: all 1s linear; */
}
.commentflag:hover {
  cursor: pointer;
}

.input {
  box-sizing: border-box;
  margin: 10px 0;
  width: 100%;
  height: 100px;
  position: relative;
}
textarea {
  border: none;
  outline: none; /*边线不显示*/
  resize: none; /*禁止拉伸*/
  appearance: none;
  background-color: rgba(125, 125, 125, 0.05);
  height: 70px;
  width: 100%;
  padding: 10px 10px;
  font-size: 16px;
  box-sizing: border-box;
}
.input > div {
  height: 26px;
  line-height: 26px;
  background-color: green;
  color: white;
  position: absolute;
  right: 10px;
  bottom: 0;
  box-sizing: content-box;
  display: inline-block;
  margin: 2px 0;
  color: white;
  background-color: red;
  font-size: 14px;
  width: 40px;
  text-align: center;
}
.input > div:hover{
  cursor: pointer;
}
.bottombar {
  width: 40px;
  font-size: 14px;
  text-align: center;
  height: 30px;
  line-height: 30px;
  background-color: #38f;
  color: white;
  transform: translateX(calc(60vw - 80px));
}
.bottombar:hover{
  cursor: pointer;
}
.member:hover {
  cursor: pointer;
}
</style>