<!--
* @description 
* @fileName contentList.vue
* @author cyx
* @date 2023/04/07 09:55:37
!-->
<template>
  <div class="rbody">
    <div class="rbhead">
        <div v-for="item,index in type" :key="index" @click=changeType(index) :class="{'type':typeIndex==index}">{{ item }}</div>
    </div>
    <div class="rbbody">
      <Rmdcontent v-for="item,index in contents" :key="index" :content=item></Rmdcontent>
    </div>
  </div>
</template>

<script>
import Rmdcontent from '../components/rmdcontent.vue'
import http from '../http/request'
export default {
  name:"",
  components:{
    Rmdcontent,
  },
  data(){
    return {
        type:["热度","时间"],
        // 排序方式 热度 时间
        typeIndex:0,
        label:'',
        index:'',
        contents:[]
    }
  },
  watch: {
    "$route.query.index": {
      handler(newVal, oldVal) {
        this.label = newVal;
        this.getContents(this.label);
      },
    },
  },
  methods:{
    changeType(index){
        this.typeIndex=index;
        this.getContents(this.label);
    },
    getContents(label){
        http.get("/main/content/label",{
            params:{
            label: label,
             current:1,
             size:20,
             order:this.typeIndex+1,
            }
        }).then((res)=>{
          this.contents=res.data.object.records;
        }).catch((err)=>{
          console.log(err)
        })
    },
    doBinding(){
      this.label=this.$route.query.index
    }
  },
  mounted(){
    this.doBinding();
    this.getContents(this.label);
  }
}

</script>
<style scoped>
.rbody{
    width: 100%;
    min-height: 170px;
    background-color: rgba(125,125,125,0.05);
}
.rbhead{
    text-align: left;
}

.rbhead>div{
    display: inline-block;
    padding: 5px 10px;
    font-size: 14px;
}
.rbhead>div:hover{
  cursor: pointer;
}
.type{
    background-color: rgba(125,125,125,0.3);
}
</style>