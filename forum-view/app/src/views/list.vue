<!--
* @description 
* @fileName barList.vue
* @author cyx
* @date 2023/04/06 21:54:45
!-->
<template>
  <div class="box">
    <div class="left">
      <div class="lhead">
        {{ $route.path.endsWith("bar") ? "全部贴吧分类" : "全部帖子分类" }}
      </div>
      <div class="lbody">
        <ul>
          <li
            v-for="(item, index) in label"
            :key="index"
            @click="changeLabel(index)"
            :class="{ active: activeIndex == index }"
          >
            {{ item.name }}
          </li>
        </ul>
      </div>
    </div>
    <div class="right">
      <div class="thead">
        <div
          v-for="(item, index) in types"
          :key="index"
          :class="{ 'type': type == index }"
          @click="changeType(index)"
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
  data() {
    return {
      label: [],
      activeIndex:this.$route.query.index||0,
      types: [
        { text: "吧", url: "/main/list/bar" },
        { text: "帖子", url: "/main/list/content" },
      ],
      // 接收跳转方的选择，避免
      type: this.$route.query.type||0,
    };
  },
  methods: {
    // 获取所有标签的类型
    getLabel() {
      http
        .get("/main/label/all")
        .then((res) => {
          this.label = res.data.object;
          // 页码主动刷新,无序跳转
          if (
            this.$route.path.indexOf("bar") != -1 ||
            this.$route.path.indexOf("content") != -1
          )
            return;
          //   开始展示
          // 跳转bar
          if (this.$route.path.indexOf("bar") != -1) {
            this.$router.push({
              path: "/main/list/bar",
              query: {
                index: this.label[0].id,
              },
            });
          }
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    // 点击改变label
    changeLabel(index) {
      if(this.activeIndex==index) return;
      this.$router.push({
        path: this.types[this.type].url,
        query: {
          index: index,
          type:this.type,
          label:this.label[this.activeIndex].id 
        },
      });
    },
    // 改变内容类型 bar  content
    changeType(index) {
      if(index==this.type) return;
      this.type = index;
      this.$router.push({
        path: this.types[index].url,
        query: {
          label: this.label[this.activeIndex].id,
          index:this.activeIndex,
          type:index
        },
      });
    },
  },
  mounted() {
    this.getLabel();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
li {
  list-style: none;
}
.box {
  width: 70vw;
  margin-left: 15vw;
}
.box > div {
  display: inline-block;
}
.left {
  width: 20%;
  border: 0.5px solid rgba(125, 125, 125, 0.3);
}
.right {
  width: 78%;
  margin-left: 1%;
  vertical-align: top;
}
.lhead {
  background-color: rgba(51, 136, 255, 1);
  color: white;
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
}
.lbody li {
  height: 40px;
  line-height: 40px;
  text-align: center;
}
.lbody li:hover{
  cursor: pointer;
}
.lbody ul {
  background-color: rgba(125, 125, 125, 0.2);
}
.active {
  background-color: white !important;
}
.thead {
  width: 100%;
  margin-bottom: 10px;
  text-align: left;
}
.thead > div {
  width: 10%;
  display: inline-block;
  height: 30px;
  line-height: 30px;
  background-color: rgba(125, 125, 125, 0.3);
  color: black !important;
  text-align: center;
}
.thead > div:hover{
  cursor: pointer;
}
.type {
  background-color: rgba(51, 136, 255, 1) !important;
  color: white !important;
}
</style>