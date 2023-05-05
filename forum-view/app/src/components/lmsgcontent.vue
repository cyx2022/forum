<!--
* @description 
* @fileName msgcontent.vue
* @author cyx
* @date 2023/04/18 12:49:28
!-->
<template>
  <div class="lmsg">
    <img :src="member.avatar"/>
    <div>{{ msg.content }}</div>
  </div>
</template>

<script>
export default {
  name:"",
  props:{
    msg:{},
    member:{}
  }
}

</script>
<style scoped>
img{
  height: 50px;
  width: 50px;
  border-radius: 25px;
}
.lmsg>div{
  display: inline-block;
  background-color: white;
  padding: 5px;
  box-sizing: border-box;
  border-radius: 5px;
  margin-left: 10px;
}
.lmsg>div,img{
  vertical-align: top;
}
</style>