<!--
* @description 
* @fileName ArticleStore.vue
* @author cyx
* @date 2023/03/23 23:23:27
!-->
<template>
  <div class="asbox">
    <div class="tbody">
      <PersonContent v-for="item,index in contents" :key="index" :content="item"></PersonContent>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
import PersonContent from "../components/personcontent.vue";
export default {
  name: "",
  data(){
    return {
      contents:[]
    }
  },
  components: {
    PersonContent,
  },

  methods: {
    getContent() {
      let mId;
      if (this.$route.query.memberId != undefined) {
        mId = this.$route.query.memberId;
      } else {
        mId=localStorage.getItem("mId");
      }
      http
        .get("/main/content/list",{
          params: {
            memberId:mId,
            current:1,
            size:10
          }
        })
        .then((res) => {
          this.contents=res.data.object.records;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getContent();
  },
};
</script>
<style scoped>
.asbox {
  widows: 100%;
}

.tbody {
  width: 100%;
  margin-bottom: 20px;
}
</style>