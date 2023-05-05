<!--
* @description 
* @fileName inputComment.vue
* @author cyx
* @date 2023/04/02 18:36:41
!-->
<template>
  <div class="icontainer">
    <div class="ihead">
      <div>新帖发布</div>
      <input type="input" placeholder="请输入标题" v-model="title" />
    </div>
    <div class="ibody">
      <div class="letter">
        <textarea placeholder="请输入内容主体" v-model="text"></textarea>
      </div>
      <div class="img">
        <p>插入图片</p>
        <input type="file" ref="photo" @change="insertImg" />
      </div>
      <div class="imgs">
        <!-- <img  /> -->
        <div class="item" v-for="(item, index) in urls" :key="index" >
          <span @click="delPhoto(index)">+</span>
          <img :src="item" />
        </div>
      </div>
    </div>
    <div class="ibottom">
      <span @click="submit">提交</span>
    </div>
  </div>
</template>

<script>
import SubmitButton from "../components/submitButton.vue";
import http from "../http/request";
export default {
  name: "",
  components: {
    SubmitButton,
  },
  data() {
    return {
      title: "",
      text: "",
      barId: "",
      urls: [],
    };
  },
  props: {},
  methods: {
    doBinding() {
      this.barId = this.$route.query.barId;
    },
    // 插入图片
    insertImg() {
      let file = this.$refs.photo.files[0];
      let formData = new FormData();
      formData.append("photo", file);
      http
        .post("/upload/img/save", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res.data.object);
          this.urls.push(res.data.object);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    submit() {
      let formData = new FormData();
      formData.append("urls", this.urls);
      formData.append("title", this.title);
      formData.append("text", this.text);
      formData.append("barId", this.barId);
      http
        .post("/main/content/add", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    delPhoto(index){
      this.urls.splice(index,1)
    }
  },
  mounted() {
    this.doBinding();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.icontainer {
  /* margin-bottom: 10px; */
}
.ihead {
  font-size: 16px;
  text-align: left;
}
.ihead div {
  text-align: center;
  margin: 10px 0 10px 0;
  font-size: 16px;
  color: #38f;
}
.ihead input {
  display: inline-block;
  margin: 5px 0 10px 5px;
  height: 30px;
  width: 90%;
  outline: none;
  text-decoration: none;
  font-size: 15px;
  text-indent: 8px;
  border: 1px solid #38f;
}
.ibody {
  position: relative;
  box-sizing: content-box;
}
.ibody>div {
  margin: 5px;
  border: 1px solid rgba(125, 125, 125, 0.2);
  padding: 8px;
  box-sizing: border-box;
}
.ibody .letter {
  text-align: left;
}

.img:hover {
  cursor: pointer;
}
textarea {
  width: 100%;
  min-height: 100px;
  border: 0;
  outline: none;
  resize: none;
  font-size: 15px;
}
.img {
  height: 40px;
  position: relative;
  color: white;
  text-align: center;
  overflow: hidden;
  width: calc(100% - 10px);
  text-align: center;
  background-color: #38f;
}
.img input {
  opacity: 0;
  width: 80%;
  vertical-align: top;
}
.img p {
  position: absolute;
  left: 0;
  top: 0;
  height: 40px;
  line-height: 40px;
  text-align: center;
  border-radius: 2px;
  width: 80%;
}
.imgs {
  text-align: left;
}
.item {
  height: 200px;
  width: 200px;
  /* padding-left: 10px; */
  /* background-color: red; */
  position: relative;
  border: 0 !important;
  display: inline-block;
  margin-left: 10px;
}
.item img {
  height: 200px;
  width: 200px;
  border: 0 !important;
}
.item span{
  display: inline-block;
  top: 1px;
  right: 1px;
  position: absolute;
  width: 20px;
  height: 20px;
}
.item span{
  background-color: white;
  text-align: center;
  line-height: 20px;
}
.item span:hover{
  cursor: pointer;
}
.ibottom {
  height: 30px;
  width: 100%;
  position: relative;
  padding-bottom: 10px;
}
.ibottom span {
  display: inline-block;
  position: absolute;
  right: 10px;
  background-color: red;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
}
.ibottom span:hover {
  cursor: pointer;
}
</style>
