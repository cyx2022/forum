<!--
* @description 
* @fileName barresult.vue
* @author cyx
* @date 2023/04/08 11:09:44
!-->
<template>
  <div>
    <ul>
      <li v-for="(item, index) in data" :key="index" @click="toBar(index)">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      word: this.$route.query.word,
      data: [],
    };
  },
  methods: {
    // 获取关键字的bar列表
    search() {
      http
        .get("/search/bar/word", {
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
    // 进入bar空间
    toBar(index) {
      this.$router.push({
        path: "/main/bar",
        query: {
          barId: this.data[index].id,
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
li {
  list-style: none;
  float: left;
  width: 20%;
  height: 30px;
  line-height: 30px;
  text-align: center;
}
li:hover {
  color: #38f;
  cursor: pointer;
}
</style>