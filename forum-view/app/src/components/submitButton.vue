<!--
* @description 
* @fileName submitButton.vue
* @author cyx
* @date 2023/04/10 19:59:59
!-->
<template>
  <div>
    <span>提交</span>
  </div>
</template>

<script>
export default {
  name:""
}

</script>
<style scoped>
div{
    margin-right: 10px;
    width: 100%;
    position: relative;
    padding-bottom: 10px;
    height: 30px;
}


div span{
  display: inline-block;
  position: absolute;
  right: 10px;
  background-color: red;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
}
div span:hover{
  cursor: pointer;
}
</style>