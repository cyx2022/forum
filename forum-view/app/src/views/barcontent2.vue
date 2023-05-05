<!-- 看帖子 -->
<!--
* @description 
* @fileName barcontent1.vue
* @author cyx
* @date 2023/04/02 18:51:09
!-->
<template>
  <div>
    <Content v-for="(item, index) in contents" :key="index" :content="item" />
    <div class="more">加载更多</div>
  </div>
  
</template>

<script>
import Content from "../components/content.vue";
import http from "../http/request";
export default {
  name: "",
  components: {
    Content,
  },
  data() {
    return {
      barId: "",
      contents: [],
      word: "",
    };
  },
  methods: {
    doBinding() {
      this.barId = this.$route.query.barId;
    },
    getRmdContents() {
        // 展示bar主收藏的content
        http
          .get("/main/content/recommend", {
            params: {
              barId: this.barId,
              current:1,
              size:10,
            },
          })
          .then((res) => {
            this.contents = res.data.object.records;
          })
          .catch((err) => {
            console.log(err);
          });
    },
  },
  mounted() {
    this.doBinding();
    this.getRmdContents();
  },
};
</script>
<style scoped>
.more {
  width: 100%;
  height: 40px;
  line-height: 40px;
  color: rgba(125, 125, 125, 0.8);
  border: 1px solid rgba(125, 125, 125, 0.8);
  background-color: rgba(125, 125, 125, 0.3);
  text-align: center;
}
.more:hover {
  cursor: pointer;
}
</style>