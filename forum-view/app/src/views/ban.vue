<!--
* @description 
* @fileName ban.vue
* @author cyx
* @date 2023/05/03 14:15:54
!-->
<template>
  <div>
    <div v-for="item,index in items" :key="index">
        <BanComment v-if="item.type==type.COMMENT" :commentId="item.itemId" @refreshBan="refreshBan"></BanComment>
        <BanContent v-if="item.type==type.CONTENT" :contentId="item.itemId"  @refreshBan="refreshBan">
        </BanContent>
    </div>
  </div>
</template>

<script>
import BanContent from '../components/bancontent.vue'
import BanComment from '../components/bancomment.vue'
import type from '../classes/type'
import http from '../http/request'
export default {
  name: "",
  components:{
    BanComment,
    BanContent,
  },
  data() {
    return {
      barId: this.$route.query.barId,
      items:[],
      type:type,
    };
  },
  methods: {
    getBan(barId){
        http.get("/main/inspect/list",{
            params:{
                barId,
                current:1,
                size:8,
            }
        }).then((res)=>{
            this.items=res.data.object.records;
        }).catch((err)=>{
            console.log(err)
        })
    },
    refreshBan(){
    this.getBan(this.barId);
    }
  },
  mounted(){
    this.getBan(this.barId);
  }
}
</script>
<style scoped>
</style>