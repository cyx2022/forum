<!--
* @description 
* @fileName barcontent.vue
* @author cyx
* @date 2023/03/22 20:36:13
!-->
<template>
  <div class="body">
    <div class="mid">
      <div
        class="item"
        v-for="(item, index) in subLabel"
        :key="index"
        @click="changeLabel(index)"
        :class="{ click: activeItem == index }"
      >
        {{ check(index)?item.text:"" }}
      </div>
      <div class="search">
        <input type="input" v-model="word" /><span @click="barSearch()"
          >搜索</span
        >
      </div>
    </div>
    <router-view :key="key"></router-view>
  </div>
</template>

<script>
import http from '../http/request'
export default {
  name: "",
  data() {
    return {
      subLabel: [
        { text: "看帖", url: "/main/bar/barcontent/barcontent1" },
        { text: "推荐", url: "/main/bar/barcontent/recommend" },
        { text: "发布", url: "/main/bar/barcontent/inputcontent" },
        { text: "审核", url: "/main/bar/barcontent/ban" },
      ],
      activeItem: 0,
      word: "",
      bar: {},
      barId: this.$route.query.barId,
    };
  },
  computed: {
    key() {
      return this.$route.name !== undefined
        ? this.$route.name + +new Date()
        : this.$route + +new Date();
    },

  },

  methods: {
    check(index){
      let id = localStorage.getItem("mId");
      if(index!=this.subLabel.length) return true;
      else return this.bar.builderId==id;
    },
    // 更换标签
    changeLabel(index) {
      if (this.activeItem == index) return;
      if (index == this.subLabel.length) {
        let id = localStorage.getItem("mId");
        if (!id == this.bar.builderId) {
          return;
        }
      }
      this.activeItem = index;
      this.$router.push({
        path: this.subLabel[index].url,
        query: {
          barId: this.$route.query.barId,
          activeId: this.activeItem,
        },
      });
    },
    getBar() {
      http
        .get("/main/bar/one", {
          params: {
            id: this.barId,
          },
        })
        .then((res) => {
          this.bar = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 吧内搜索
    barSearch() {
      if (
        this.word == undefined ||
        this.word == "" ||
        this.word == this.$route.query.word
      )
        return;
      this.$router.push({
        path: "/main/bar/barcontent/barcontent1",
        query: {
          word: this.word,
          id: this.$route.query.barId,
          activeIndex: 0,
          barId: this.$route.query.barId,
        },
      });
    },
    doBinding() {
      this.word = this.$route.query.word || "";
      this.activeItem = this.$route.query.activeId || 0;
    },
  },
  components: {},
  mounted() {
    this.doBinding();
    this.getBar();
  },
};
</script>
<style scoped>
.body {
  width: 100%;
  border: 1px solid rgba(1, 0, 0, 0.2);
}
.mid {
  width: 100%;
  height: 50px;
  background-color: rgba(125, 125, 125, 0.25);
  position: relative;
}
.item {
  float: left;
  width: 80px;
  line-height: 50px;
  text-align: center;
}
.item:hover {
  cursor: pointer;
}
.click {
  background-color: rgba(125, 125, 125, 0.3);
  color: #38f;
}
.search {
  height: 50px;
  line-height: 50px;
  position: absolute;
  right: 30px;
}
.search input {
  width: 300px;
  border: 0;
  line-height: 30px;
  font-size: 16px;
  text-indent: 10px;
  outline: none;
}
.search span {
  width: 60px;
  background-color: #38f;
  line-height: 30px;
  text-align: center;
}
.search span:hover {
  cursor: pointer;
}
.search input,
.search span {
  height: 30px;
  display: inline-block;
  vertical-align: middle;
}
</style>