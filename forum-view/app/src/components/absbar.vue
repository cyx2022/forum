<!--
* @description 
* @fileName absbar.vue
* @author cyx
* @date 2023/04/08 12:25:48
!-->
<template>
  <div class="sbox">
    <img bar.img />
    <div class="three">
      <div @click="toBar(bar.id)">{{ bar.name }}</div>
      <div>{{ bar.number }}</div>
      <div>{{ bar.commentCount }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  props:{
    bar:{}
  },
  data() {
    return {
    };
  },
};
</script>
<style scoped>
.sbox {
}
</style>