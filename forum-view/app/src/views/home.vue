<!--
* @description 
* @fileName home.vue
* @author cyx
* @date 2023/03/21 16:31:53
!-->
<template>
  <div class="box">
    <el-carousel trigger="click" height="17vw">
      <el-carousel-item v-for="(i, t) in hotContent" :key="t">
        <div class="hot">
          <ul>
            <li v-for="(item, k) in i" :key="k">
              <img :src="item.url" />
              <span @click="toContent(item.barId,item.id)">{{ item.text }}</span>
            </li>
          </ul>
        </div>
      </el-carousel-item>
    </el-carousel>

    <div class="container">
      <div class="left">
        <div class="me">
          <div class="title">我的关注</div>
          <div class="point" v-for="(item, index) in focusBar" :key="index" @click="toBar(item.id)">
            {{ item.name }}
          </div>
        </div>
        <div class="label">
          <div class="head">贴吧分类</div>
          <div class="line"></div>
          <ul>
            <li
              v-for="(item, index) in label" @mouseover="changeLabel(index)" @mouseout="outLabel"
              :key="index"
            >
              <div class="text"  >{{ item.name }}</div>
              <div class="float"  v-if="index === labelId"  >
                <div>推荐</div>
                <ul>
                  <li>那你</li>
                  <li>那你</li>
                  <li>那你</li>
                  <li>那你</li>
                  <li>那你</li>
                  <li>那你</li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
        <div class="toall" @click="toAll">查看全部</div>
      </div>
      <div class="right">
        <div class="top-hot">
          <div class="hot-head">推荐</div>
          <ul>
            <li v-for="item,index in rmdBar" :key=index>
              <img :src="item.img"/>
              <div class="three">
                <div @click="toBar(item.id)">{{ item.name }}</div>
                <div>{{ item.number }}</div>
                <div>{{ item.commentCount }}</div>
              </div>
            </li>
          </ul>
        </div>
        <div class="hot-text">
          <div class="subhead">热门动态</div>
        </div>
        <div class="hotcontent" v-for="item,index in content" :key="index">
          <div class="subbar" @click="toBar(item.barId)">{{ item.barName }}</div>
          <div class="subcontent" @click=toContent(item.barId,item.id)>{{ item.text }}</div>
          <span>{{ item.commentCount }}</span>
          <div class="subbottom">
            <!-- <img src="" alt=""> -->
            <img v-for="item,index in item.urls" :key="index" :src="item">
          </div>
        </div>
        <div class="more" @click="toMore" :class="{'disable':disable}">{{this.disable?'加载完成':'更多精彩'}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      hotContent: [
      ],
      // 关注列表
      focusBar: [],
      // 标签类型
      label: [],
      // 鼠标经过label id
      labelId: -1,
      subLabelId:-1,
      flag:false,
      // 每个label的推荐
      rmdBar:[],
      content:[],
      current:1,
      disable:false,
    };
  },
  methods: {
    outLabel(){
      this.labelId=-1;
    },
    changeLabel(index){
      this.labelId=index;
    },
    // 获取关注列表
    getFocusBar() {
      let id = localStorage.getItem("mId");
      http
        .get("/main/focus/bar/member", {
          params: {
            id,
            current:1,
            size:5,
          },
        })
        .then((res) => {
          this.focusBar = res.data.object;
        })
        .catch(() => {
          this.$message.error("服务端异常");
        });
    },

    // 获取所有标签的类型
    getLabel() {
      http
        .get("/main/label/all")
        .then((res) => {
          this.label = res.data.object;
        })
        .catch((errr) => {
          this.$message.error("服务端异常");
        });
    },
    // 获取首页推荐bar信息
    getHotBar(){
      http.get("/main/bar/get",{
        params:{
          current:1,
          size:8,
          order:1
        }
      }).then((res)=>{
        this.rmdBar=res.data.object.records;
      }).catch((err)=>{
        console.log(err);
      })
    },

    // 获取热门内容
    getHotContent(){
      http.get("/main/content/list",{
        params:{
          current:1,
          size:12,
          order:1
        }
      }).then((res)=>{
        this.hotContent=new Array(3)
        let temp=res.data.object.records;
        let i=0,k=-1;
        while(i<temp.length){
          if(i%4==0) {
            k++;
            this.hotContent[k]=new Array(4)
          }
          i++;
          this.hotContent[k][i % 4]=temp[i-1];
        }
      }).catch((err)=>{
        console.log(err)
      })
    },
    // 获取列表推荐content
    getRmdContent(){
      http.get("/main/content/list",{
        params:{
          current:this.current,
          size:10,
          order:1
        }
      }).then((res)=>{
        this.content=[...this.content,...res.data.object.records];
        if(this.content.length==res.data.object.total){
          this.disable=true;
        }
      }).catch((err)=>{
        console.log(err)
      })
    },

    // 进入bar空间
    toBar(id){
      this.$router.push({
        path:"/main/bar",
        query:{
          barId:id,
        }
      })
    },

    // 进入content空间
    toContent(barId,contentId){
      this.$router.push({
        path:"/main/bar/commentL1",
        query:{
          barId:barId,
          contentId:contentId,
        }
      })
    },
    toAll(){
      this.$router.push("/main/list")
    },
    toMore(){
      this.current=this.current+1;
          this.getRmdContent();
  }
  },
  mounted() {
    this.getLabel();
    this.getHotBar();
    this.getFocusBar();
    this.getHotContent();
    this.getRmdContent();
  },

};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  list-style: none;
}
.box {
  width: 70vw;
  /* background-color: green; */
  margin-left: 15vw;
}
.hot {
  height: 17vw;
}
.hot ul li {
  float: left;
  height: 17vw;
  width: 17.401vw;
  margin-left: 0.1vw;
}
.hot li span {
  display: block;
  transform: translateY(-40px);
  height: 30px;
  line-height: 30px;
  font-size: 16px;
  color: rgba(125,125,125,1);
  text-align: center !important;
}
.hot li span:hover{
  cursor: pointer;
  color: green;
}
.hot img {
  width: 100%;
  height: 100%;
  /* background-color: red; */
}
.container {
  width: 100%;
  margin-top: 30px;
  vertical-align: top;
}

.left {
  width: 17%;
  background-color: rgba(125, 125, 125, 0.2);
  border: 0.2px solid rgba(125, 125, 125, 0.2);
  display: inline-block;
  margin-right: 2%;
}
.right {
  width: 80%;
  display: inline-block;
  vertical-align: top;
  border: 0.2px solid rgba(125, 125, 125, 0.2);
  margin-bottom: 10px;
}

.me .title {
  height: 40px;
  line-height: 40px;
  text-align: left;
  padding-left: 5px;
  font-weight: 700;
  /* background-color: yellow; */
}
.me .title {
  cursor: pointer;
}
.me .point {
  height: 30px;
  line-height: 30px;
  padding-left: 10px;
  /* text-align: center; */
}
.me .point:hover {
  cursor: pointer;
  background-color: white;
}
.label {
  position: relative;
}
.label .head {
  height: 30px;
  font-size: 13px;
  text-align: left;
  padding-left: 5px;
  line-height: 30px;
  letter-spacing: 1px;
}
.line {
  height: 1px;
  width: 65%;
  background-color: rgba(125, 125, 125, 0.7);
  position: absolute;
  top: 15px;
  right: 0;
}
.label > ul{
  z-index: 1000;
  box-sizing:content-box
}
.label > ul > li {
  height: 50px;
  line-height: 50px;
  position: relative;
  /* background-color: red; */
  box-sizing: content-box;
  text-align: center;
}
.label > ul > li:hover {
  cursor: pointer;
  background-color: white;
}

.float {
  /* transform: translateX(11vw) translateY(-50px); */
  position: absolute;
  top: -10px;
  left: 12vw;
  width: 20vw;
  background-color: rgba(125, 125, 125, 0.08);
  /* background-color: green; */
  box-sizing: content-box;
}
.float div {
  height: 30px;
  line-height: 30px;
  /* background-color: green; */
  text-align: left;
  padding-left: 5px;
  font-size: 13px;
  color: rgba(0, 0, 255, 0.6);
}
.float li {
  float: left;
  line-height: 30px;
  margin: 8px 0 0px 8px;
}
.float li:hover {
  cursor: pointer;
}
.toall {
  border: 1px solid rgba(125, 125, 125, 0.2);
  height: 30px;
  line-height: 30px;
  margin: 5px 5px 20px 5px;
  background-color: white;
  text-align: center;
}
.toall:hover {
  cursor: pointer;
}
.top-hot {
  width: 100T;
  /* background-color: red; */
  margin-bottom: 20px;
}
.hot-head {
  text-align: left;
  padding-left: 10px;
  height: 40px;
  line-height: 40px;
  font-size: 17px;
  font-weight: 700;
}
.top-hot ul {
  width: 100%;
  padding-left: 0.5vw;
  overflow: hidden;
}

.top-hot ul li {
  width: 13vw;
  height: 5vw;
  float: left;
  /* background-color: red; */
  overflow: hidden;
  margin-right: 1vw;
  margin-bottom: 10px;
}
.top-hot ul li img {
  width: 5vw;
  height: 5vw;
  margin-right: 0.5vw;
  border-radius: 10px;
}
.top-hot ul li > div {
  vertical-align: top;
  width: 7vw;
  height: 5vw;
  display: inline-block;
  /* background-color: green; */
  text-align: left;
  padding-left: 5px;
}
.hot-text {
  height: 100px;
  width: 100%;
  border-top: 0.2px solid rgba(125, 125, 125, 0.2);
  padding-top: 20px;
}
.three div {
  margin-bottom: 8px;
}
.three div:nth-of-type(1) {
  font-size: 16px;
  color: #38f;
}
.three div:nth-of-type(1):hover{
  cursor: pointer;
  color: black;
}
.three div:nth-of-type(2) {
  font-size: 13px;

  color: rgba(125,125,125,0.8);
}
.three div:nth-of-type(3) {
  font-size: 13px;
  color: rgba(125,125,125,0.8);
}
.subhead {
  height: 40px;
  line-height: 40px;
  text-align: left;
  margin: 5px;
  padding-left: 5px;
  /* background-color: red; */
  font-size: 14px;
  font-weight: 500;
  border: 1px solid rgba(125, 125, 125, 0.2);
}
.hotcontent {
  box-sizing: border-box;
  width: 100%;
  transform: translateY(-30px);
  text-align: left;
  padding-left: 20px;
}
.hotcontent:hover {
  background-color: rgba(125, 125, 125, 0.1);
}
.subbar {
  height: 40px;
  line-height: 40px;
}
.subbar:hover {
  cursor: pointer;
  text-decoration: underline;
}
.subcontent {
  height: 40px;
  width: 90%;
  line-height: 40px;
  color: #005097;
  /* background-color: red; */
}

.subcontent:hover {
  cursor: pointer;
  text-decoration: underline;
}
.hotcontent span {
  box-sizing: border-box;
  width: 9.9%;
  display: inline-block;
  height: 40px;
  transform: translateY(-40px) translateX(900%);
  /* background-color: green; */
  line-height: 40px;
  padding-left: 5px;
  border: 1px solid rgba(15, 125, 125, 0.2);
}
.subbottom {
  /* height: 20px; */
  /* line-height: 20px; */
  /* font-size: 13px; */
}
.subbottom img{
  width: 20vw;
  height: 20vw;
}
.more {
  height: 40px;
  line-height: 40px;
  color: rgba(125, 125, 125, 0.8);
  border: 1px solid rgba(125, 125, 125, 0.8);
  margin: 10px;
  background-color: rgba(125, 125, 125, 0.3);
  text-align: center;
}

.more:hover {
  cursor: pointer;
}
.disable:hover{
  cursor: not-allowed;
}
</style>