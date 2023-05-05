<!--
* @description 
* @fileName systemmsgcpt.vue
* @author cyx
* @date 2023/04/18 10:24:11
!-->
<template>
  <div id="sbox">
    <div class="shead">
        <span class="point" :class="{un:msg.sign==0}"></span>
        <span class="stitle">{{ msg.title }}</span>
        <span class="sdate">{{msg.time.substring(0,10)}}</span>
    </div>
    <div class="sbody">{{ msg.content }}</div>
  </div>
</template>

<script>
export default {
  name:"",
  props:{
    msg:{},
    flag:'',
  },
  methods:{
    sign(){
        this.$emit("sign",msg.id);
    }
  },
  mounted(){
  }
}

</script>
<style scoped>
*{
    margin: 0;
    padding: 0;
}
#sbox{
    width: calc(100% - 20px);
    padding: 20px 0;
    background-color: white;
    margin: 10px 10px;
    border-radius: 10px;
}
.shead{
    height: 30px;
    line-height: 30px;
    padding: 0 20px;
    box-sizing: border-box;
    position: relative;
}
.shead{
    font-size: 16px;
}
.sdate{
    color: rgba(125,125,125,0.8);
    margin-left: 20px;
    font-size: 13px;
}
.sbody{
    padding: 0 30px;
    font-size: 14px;
    color: gray;
}
.point{
    display: inline-block;
    width: 10px;
    height: 10px;
    background-color: white;
    position: absolute;
    top: 0;
    right: 10px;
    border-radius: 5px;
}
.un{
  background-color: green !important;
}
</style>