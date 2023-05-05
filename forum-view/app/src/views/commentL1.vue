<!--
* @description 
* @fileName commentL1
* @author cyx
* @date 2023/03/22 20:35:01
!-->
<template>
  <div class="subbox">
    <div class="title">
      <div>{{ content.text }}</div>
      <div>
        <span @click="getOfOwner" :class="{'special':ownerFlag}">只看楼主</span>
        <span @click="changeClick" :class="{'special':clickFlag}">点赞</span>
        <span @click="changeStore" :class="{'special':storeFlag}">收藏</span>
        <span @click="addShare">分享</span>
        <span class="return" @click="edit">回复</span>
        <span class="report" @click="showDialog(choiceType.CONTENT,contentId)">举报</span>
      </div>
    </div>
    <div class="body">
      <!-- 渲染时机有问题 -->
      <Commentbody :bans="bans"
        v-for="(item, index) in comments"
        :key="index"
        :comment="item"
        :choiceType="choiceType"
        @ban="showDialog"
      ></Commentbody>
    </div>
    <div class="inputbox" v-if="inputFlag">
      <div>请尽情发言</div>
      <div><textarea v-model="text"></textarea></div>
      <div>
        <div class="p">
          <p>插入图片</p>
          <input type="file" ref="photo" @change="insertImg" />
        </div>
        <div class="img">
          <img :src="item" v-for="(item, index) in urls" :key="index" />
        </div>
      </div>
      <div><span @click="submit">发布</span></div>
    </div>
    <el-dialog :visible.sync="dialogVisible" title="选择多项" width="50%" center>
      <el-checkbox-group v-model="selectedItems">
        <el-checkbox v-for="(item, index) in bans" :key="index" :label="item.id">{{ item.description }}</el-checkbox>
      </el-checkbox-group>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmSelection">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import TYPE from "../classes/type"
import http from "../http/request";
import Commentbody from "../components/commentbody.vue";
export default {
  name: "",
  components: {
    Commentbody,
  },
  data() {
    return {
      // content所属bar id
      barId: "",
      contentId: "",
      content: "",
      // 一级评论列表
      comments: [],
      inputFlag: false,
      urls: [],
      text: "",
      clickFlag:false,
      storeFlag:false,
      ownerFlag:false,

      // 举报相关
      dialogVisible: false,
      bans: [],
      selectedItems: [],
      // 被举报项目的id
      itemId:'',
      banType:'',
      choiceType:TYPE
    };
  },
  methods: {
    showDialog(type,itemId) {
      console.log(type,itemId)
      this.dialogVisible = true;
      this.type=type;
      this.itemId=itemId;
    },
    confirmSelection() {
      http.post("/main/inspect/add",{
        barId:this.barId,
        banArr:this.selectedItems,
        type:this.type,
        itemId:this.itemId,
      }).then((res)=>{
        console.log(res)
      }).catch((err)=>{
        console.log(err)
      })
      this.dialogVisible = false;
      this.itemId='';
      this.type='';
    },


    // 获取禁止文案
    getBans(){
      http.get("/main/ban/list").then((res)=>{
        // console.log(res)
        this.bans=res.data.object;
      }).catch((err)=>{
        console.log(err)
      })
    },
    // 绑定query信息
    doBinding() {
      this.barId = this.$route.query.barId;
      this.contentId = this.$route.query.contentId;
    },
    // 获取content的内容
    getContent() {
      http
        .get("/main/content/one", {
          params: {
            id: this.contentId,
          },
        })
        .then((res) => {
          this.content = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 获取一级评论列表
    getComments() {
      http
        .get("/main/comment/content", {
          params: {
            contentId: this.contentId,
            current: 1,
            size: 10,
          },
        })
        .then((res) => {
          this.comments = res.data.object.records;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    edit() {
      window.scrollTo(0, document.documentElement.scrollHeight);
      this.inputFlag = true;
    },
    insertImg() {
      let file = this.$refs.photo.files[0];
      let formData = new FormData();
      formData.append("photo", file);
      http
        .post("/upload/img/save", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          this.urls.push(res.data.object);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 发布一级评论
    submit() {
      http
        .post("/main/comment/add", {
          contentId: this.contentId,
          content: this.text,
          urls: this.urls,
        })
        .then((res) => {
          if (res.data.code == 200) {
            this.text = "";
            this.comments.unshift(res.data.object);
            window.scrollTo(0, 0);
            this.inputFlag = false;
            this.$message({
              message: "发布成功",
              type: "success",
            });
          } else {
            this.$message.error("服务端异常");
          }
        })
        .catch((err) => {
          this.$message.error("服务端异常");
          console.log(err);
        });
    },
    // 获取吧主在空间的收藏
    getOfOwner() {
      if(this.ownerFlag) {
        this.ownerFlag=false;
        return ;
      }
      http
        .get("/main/content/owner", {
          params: {
            id: this.contentId,
            current: 1,
            size: 5,
          },
        })
        .then((res) => {
          this.ownerFlag=true;
          this.commentsres.data.object.records;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 查看点赞
    checkClick() {
      http
        .get("/main/favor/check",{
          params:{
            contentId:this.contentId,
          }
        })
        .then((res) => {
          this.clickFlag=res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 添加点赞
    addClick() {
      http
        .post("/main/favor/add",{
            contentId:this.contentId,
        })
        .then((res) => {
          if(res.data.code==200){
            
          this.clickFlag=true;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 取消点赞
    cancelClick() {
      http
        .post("/main/favor/cancel",{
            contentId:this.contentId,
        })
        .then((res) => {
          if(res.data.code==200){
            this.clickFlag=false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 查看收藏
    checkStore(){
      http
        .get("/main/store/check",{
          params:{
            contentId:this.contentId,
          }
        })
        .then((res) => {
          this.storeFlag=res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 添加收藏
    addStore() {
      http
        .post("/main/store/add",{
            contentId:this.contentId,
        })
        .then((res) => {
          if(res.data.code==200){
            this.storeFlag=true;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 取消收藏
    cancelStore(){
      http
        .post("/main/store/cancel",{
            contentId:this.contentId,
        })
        .then((res) => {
          if(res.data.code==200){
            this.storeFlag=false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 添加分享
    addShare() {
      http
        .post("/main/share/add",{
          contentId:this.contentId,
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    changeClick(){
      if(this.clickFlag){
        this.cancelClick();
      }else{
        this.addClick()
      }
    },
    changeStore(){
      if(this.storeFlag){
        this.cancelStore();
      }else{
        this.addStore();
      }
    }

  },
  mounted() {
    this.doBinding();
    this.getBans();
    this.getContent();
    this.getComments();
    this.checkClick();
    this.checkStore();
  },
};
</script>
<style scoped>
.subbox {
  overflow: hidden;
  width: 100%;
  margin-top: 10px;
  box-sizing: content-box;
}
.title {
  text-align: center;
  height: 50px;
  line-height: 50px;
  /* background-color: red; */
  position: relative;
  font-family: "microsoft yahei", simhei, sans-serif;
}
.title div {
  height: 50px;
  position: absolute;
  top: 0;
}
.title div:nth-of-type(1) {
  left: 10px;
}
.title div:nth-of-type(2) {
  right: 10px;
}
.title div:nth-of-type(2) span {
  box-sizing: border-box;
  border: 1px solid rgba(125, 125, 125, 0.2);
  padding: 5px;
  height: 25px;
  margin: 10px;
  border-radius: 5px;
}
.title div:nth-of-type(2) span:hover {
  cursor: pointer;
  color: white;
  background-color: #38f;
}

.title span {
  font-size: 13px;
}
.title span:hover {
  cursor: pointer;
}
.return {
  color: #38f;
}
.body {
  margin-top: 5px;
  box-sizing: content-box;
  border: 1px solid rgba(125, 125, 125, 0.2);
  border-top: 1px solid rgba(125, 125, 125, 0.4);
  margin-bottom: 20px;
}
.inputbox {
  border: 1px solid #38f;
  margin-bottom: 20px;
}
.inputbox > div:nth-of-type(1) {
  height: 40px;
  line-height: 40px;
  color: #38f;
}
textarea {
  border: none;
  outline: none; /*边线不显示*/
  resize: none; /*禁止拉伸*/
  background: rgba(125, 125, 125, 0.1); /*带点绿*/
  appearance: none;
  width: 98%;
  font-size: 16px;
  padding: 5px 10px;
  box-sizing: border-box;
  height: 150px;
}
.inputbox > div:nth-of-type(4) {
  position: relative;
  height: 30px;
  margin: 10px 0;
  overflow: hidden;
}
.inputbox > div:nth-of-type(4) span {
  position: absolute;
  right: 1%;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  background-color: red;
  color: white;
}
.inputbox > div:nth-of-type(4) span:hover {
  cursor: pointer;
}
.inputbox > div:nth-of-type(3) {
  margin-top: 10px;
}
.p {
  height: 30px;
  background-color: #38f;
  position: relative;
  overflow: hidden;
  margin: 0 10px;
}
.p p {
  height: 30px;
  line-height: 30px;
  color: white;
  position: absolute;
  top: 0;
  left: 0;
  font-size: 16px;
  text-align: center !important;
  margin-left: calc(50% - 32px);
}
.p input {
  width: 100%;
  height: 100%;
  opacity: 0;
}
.p:hover {
  cursor: pointer;
}
.img {
  margin-top: 10px;
  text-align: left;
  padding: 0 1%;
}
.inputbox > div:nth-of-type(3) img {
  height: 200px;
  width: 200px;
  margin-right: 10px;
}
.special{
  background-color: green;
  color: white;
}
.report{
  border: 0 !important;
  font-size: 10px !important;
  color: red;
}
.report:hover{
  background-color: white !important;
  color: red  !important;
}
</style>