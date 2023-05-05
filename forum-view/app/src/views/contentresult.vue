<!--
* @description 
* @fileName contentresult.vue
* @author cyx
* @date 2023/04/08 11:09:39
!-->
<template>
  <div>
    <ul>
      <li v-for="(item, index) in data" :key="index" @click="toContent(index)">
        <Content :content=item></Content>
      </li>
    </ul>
  </div>
</template>

<script>
import Content from '../components/content.vue'
import http from "../http/request";
export default {
  name: "",
  components:{
    Content
  },
  data() {
    return {
      word: this.$route.query.word,
      data: [],
      tBar:'',
    };
  },
  methods: {
    search() {
      http
        .get("/search/content/word", {
          params: {
            word: this.word,
          },
        })
        .then((res) => {
          this.data = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 获取该content的空间
    getBar(index) {
      http
        .get("/main/bar/one", {
          params: {
            id: this.data[index].barId,
          },
        })
        .then((res) => {
          this.tBar = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 进入content空间
    toContent(index) {
      this.getBar(index);
      this.$router.push({
        path: "/main/bar/commentL1",
        query: {
          barId: this.tBar,
          contentId: this.data[index].id,
        },
      });
    },
  },
  mounted() {
    this.search();
  },
};
</script>
<style scoped>
li{
  list-style: none;
}
</style>