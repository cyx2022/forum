<!--
* @description 
* @fileName search.vue
* @author cyx
* @date 2023/04/08 10:25:24
!-->
<template>
  <div class="box">
    <div class="bhead">
      <div v-if="ban"><span>已展示与</span><span class="word">{{ this.$route.query.word||'' }}</span
      ><span>有关的所有主题</span><span class="createBar">创建</span><span class="bar" @click="create">{{ word.endsWith('吧')?this.word:this.word+'吧' }}</span>
    </div>
    <div v-if="!ban">包含敏感词,请重新查询</div>
    </div>

    <div class="bbody">
      <div class="left">
        <ul>
          <li
            v-for="(item, index) in resultType"
            :key="index"
            @click="toResult(index)"
          >
            {{ item.text }}
          </li>
        </ul>
      </div>
      <div class="right"><router-view></router-view></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      resultType: [
      { text: "贴吧", url: "/main/search/bar" },
        { text: "帖子", url: "/main/search/content" },
      ],
      word:this.$route.query.word||'',
      ban:true,
      activeIndex:0
    };
  },
  methods: {
    toResult(index) {
      if(this.activeIndex==index) return;
      let to = this.resultType[index].url;
      this.$router.push({
        path: to,
        query: {
          word: this.word,
          activeIndex:index
        },
      });
    },
    create(){
      this.$router.push({path:'/main/create',query:{
        name:this.word,
      }})
    },
    doBinding(){
      this.activeIndex=this.$route.query.activeIndex||0;
    }
  },
  created() {
    this.doBinding();
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
.word {
  padding: 0 10px;
  color: #38f;
}
.box {
  width: 70vw;
  margin-left: 15vw;
}
.bhead {
  height: 30px;
  line-height: 30px;
  font-size: 14px;
}
.bbody {
  width: 100%;
}
.left {
  display: inline-block;
  width: 10%;
}
li {
  height: 50px;
  line-height: 50px;
  font-size: 18px;
  background-color: #38f;
  color: white;
  text-align: center;
}
li:hover {
  cursor: pointer;
}
.right {
  width: 90%;
  display: inline-block;
  vertical-align: top;
  min-height: 100px;
  background-color: rgba(125, 125, 125, 0.1);
}
.createBar{
  margin-left: 10px;
}
.bar{
  margin-left: 5px;
  color: #38f;
}
.bar:hover{
  cursor: pointer;
}
</style>