<!--
* @description 
* @fileName barList.vue
* @author cyx
* @date 2023/04/07 09:06:19
!-->
<template>
  <div class="rbody">
    <ul>
      <li v-for="(item, index) in bars" :key="index" @click="toBar(item.id)">{{ item.name }}</li>
    </ul>
  </div>
</template>

<script>
import http from '../http/request'
export default {
  name: "",
  data() {
    return {
      bars: [],
      index: 1,
    };
  },
  methods: {
    // 获取bar列表
    getBars(index) {
      http
        .get("/main/bar/get", {
          params: {
            labelId: index,
            current: -1,
            size: -1,
          },
        })
        .then((res) => {
          this.bars = res.data.object.records;
        })
        .catch((err) => {
          console.log(err);
        });
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
  },
  watch: {
    "$route.query.index": {
      handler(newVal, oldVal) {
        this.index = newVal;
        this.getBars(this.index);
      },
    },
  },
  mounted() {
    this.index = this.$route.query.index;
    this.getBars(this.index);
  },
};
</script>
<style scoped>
li{
    list-style: none;
}
.rbody {
  width: 100%;
  min-height: 170px;
  background-color: rgba(125, 125, 125, 0.1);
  overflow: hidden;
}
.rbody li {
  width: 20%;
  height: 40px;
  line-height: 40px;
  float: left;
  text-overflow: ellipsis;
  text-align: center;
}
li:hover{
  cursor: pointer;
  color: #38F;
}
</style>