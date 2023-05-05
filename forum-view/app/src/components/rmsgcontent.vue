<!--
* @description 
* @fileName rmsgcontent.vue
* @author cyx
* @date 2023/04/18 12:50:10
!-->
<template>
  <div class="rmsg">
    <img :src="member.avatar" />
    <div>1{{ msg.content }}</div>
  </div>
</template>

<script>
export default {
  name: "",
  props:{
    msg:{},
    member:{}
  }
};
</script>
<style scoped>
.rmsg {
  overflow: hidden;
}
.rmsg > div {
  float: right;
  background-color: white;
  padding: 5px;
  box-sizing: border-box;
  border-radius: 5px;
  margin-right: 10px;
}
img {
  float: right;
  height: 50px;
  width: 50px;
  border-radius: 25px;
}
</style>