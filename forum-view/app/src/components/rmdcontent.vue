<!--
* @description 
* @fileName rmdcontent.vue
* @author cyx
* @date 2023/04/07 20:41:48
!-->
<template>
  <div class="hotcontent">
    <div class="subbar" @click="toBar(content.barId)">
      {{ content.barName }}
    </div>
    <div class="subcontent" @click="toContent(content.barId, content.id)">
      {{ content.text }}
    </div>
    <span>{{ content.commentCount }}</span>
    <div class="subbottom">
      <img
        v-if="content.urls != null && content.urls.length > 0"
        src="content.urls[0]"
      />
      <img
        v-if="content.urls != null && content.urls.length > 1"
        src="content.urls[1]"
      />
      <img
        v-if="content.urls != null && content.urls.length > 2"
        src="content.urls[2]"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  props: {
    content: {},
  },
  methods: {
    toBar(id) {
      this.$router.push({
        path: "/main/bar",
        query: {
          barId: id,
        },
      });
    },
    toContent(barId, contentId) {
      this.$router.push({
        path: "/main/bar/commentL1",
        query: {
          barId: barId,
          contentId: contentId,
        },
      });
    },
  },
};
</script>
<style scoped>
.hotcontent {
  box-sizing: border-box;
  width: 100%;
  text-align: left;
  padding-left: 20px;
  border-bottom: 1px solid rgba(125, 125, 125, 0.2);
}
.hotcontent:hover {
  background-color: rgba(125, 125, 125, 0.05);
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
  line-height: 40px;
  padding-left: 5px;
  border: 1px solid rgba(15, 125, 125, 0.2);
}
.subbottom {
  /* height: 20px; */
  /* line-height: 20px; */
  /* font-size: 13px; */
}
.subbottom img {
  width: 20vw;
  height: 20vw;
}
</style>