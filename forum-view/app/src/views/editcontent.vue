<!--
* @description 
* @fileName editcontent.vue
* @author cyx
* @date 2023/04/08 19:59:31
!-->
<template>
  <div class="cbox">
    <h5>修改帖子</h5>
    <div class="msg">
      <div>
        <span>空间：</span
        ><span class="bar" @click="toBar()">{{ bar.name }}</span>
      </div>

      <div>
        <span>评论：</span><span>{{ content.commentCount }}</span>
      </div>

      <div>
        <span>分享：</span><span>{{ content.shareCount }}</span>
      </div>
      <div>
        <span>收藏：</span><span>{{ content.storeCount }}</span>
      </div>
    </div>
    <div class="text">
      <div class="title">
        <input v-model="title" />
      </div>
      <div>
        <textarea v-model="text"></textarea>
      </div>
    </div>
    <div class="img" v-for="(item, index) in img" :key="index">
      <div>
        <span @click="del(index)">+</span>
        <img :src="item" />
      </div>
    </div>
    <div class="time">
      <span v-if="content.publishTime != undefined">{{
        content.publishTime.substring(0, 10)
      }}</span>
    </div>
    <div class="submit">
      <span @click="submit">提交</span>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      content: {},
      bar: {},
      title: "",
      text: "",
      img: [],
    };
  },
  methods: {
    getContent(id) {
      http
        .get("/main/content/one", {
          params: {
            id,
          },
        })
        .then((res) => {
          this.content = res.data.object;
          (this.title = this.content.title), (this.text = this.content.text);
          if (this.content.url != null) {
            this.img = this.content.split("^");
          }
          this.getBar(this.content.barId);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    getBar(id) {
      http
        .get("/main/bar/one", {
          params: {
            id,
          },
        })
        .then((res) => {
          this.bar = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 进入bar空间
    toBar() {
      this.$router.push({
        path: "/main/bar",
        query: {
          barId: this.bar.id,
        },
      });
    },
    del(index) {
      // 删除图片
      this.img.splice(index, 1);
    },

    submit() {
      http
        .post(
          "/main/content/update",
          {
            title: this.title,
            text: this.text,
            urls: this.urls,
            contentId: this.content.id,
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        )
        .then((res) => {
          if (res.data.code == 200) {
            this.$router.push("/main/person/show", {
              query: {
                activeIndex: 0,
              },
            });
          } else {
            this.$message.error("修改失败");
          }
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getContent(this.$route.query.contentId);
  },
};
</script>
<style scoped>
.cbox {
  border: 1px solid rgba(125, 125, 125, 0.3);
  width: 54vw;
  margin-left: 23vw;
  box-sizing: border-box;
  margin-bottom: 20px;
}
h5 {
  height: 70px;
  line-height: 70px;
  font-weight: 500;
  font-size: 20px;
  padding-left: 20px;
}
.msg {
  text-align: left;
  padding-left: 20px;
  height: 30px;
  line-height: 30px;
}
.cbox .msg > div {
  display: inline-block;
  margin-right: 30px;
  color: rgba(125, 125, 125, 1);
}
.cbox .msg > div span {
  margin-right: 5px;
}
.bar {
  color: #38f;
}
.bar:hover {
  cursor: pointer;
}
.time {
  height: 30px;
  line-height: 30px;
  font-size: 14px;
  text-align: right;
  padding-right: 20px;
  color: rgba(125, 125, 125, 1);
}
.title input {
  outline: none;
  border: 0;
  border-bottom: 1px solid rgba(125, 125, 125, 0.2);
  height: 30px;
  width: 30vw;
  font-size: 18px;
  letter-spacing: 3px;
  font-family: "Microsoft YaHei";
  padding: 0 40px;
}
.text {
  box-sizing: border-box;
}
textarea {
  border: none;
  outline: none; /*边线不显示*/
  resize: none; /*禁止拉伸*/
  appearance: none;
  width: 100%;
  background-color: rgba(125, 125, 125, 0.05);
  padding: 5px 20px;
  color: black;
  font-size: 18px;
  box-sizing: border-box;
  min-height: 150px;
}
.text > div:nth-of-type(1) {
  height: 50px;
  line-height: 50px;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.7);
}
.text > div:nth-of-type(2) {
}
.img {
  text-align: left;
  box-sizing: border-box;
  margin-top: 20px;
  padding-left: 20px;
}
.img > div {
  position: relative;
}
.img span {
  display: inline-block;
  position: absolute;
  right: 0;
  top: 0;
  font-size: 18px;
  background-color: white;
}
.img > div {
  width: 15vw;
  height: 15vw;
}
img {
  width: 15vw;
  height: 15vw;
  margin-right: 10px;
}
.submit {
  text-align: right;
  margin-top: 20px;
  margin-right: 20px;
  margin-bottom: 20px;
  position: relative;
  height: 30px;
}

.submit span {
  display: inline-block;
  position: absolute;
  right: 10px;
  background-color: red;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
}
.submit span:hover {
  cursor: pointer;
}
</style>