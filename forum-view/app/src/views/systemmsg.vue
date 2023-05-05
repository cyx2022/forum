<!--
* @description 
* @fileName systemmsg.vue
* @author cyx
* @date 2023/04/18 10:15:44
!-->
<template>
  <div>
    
  </div>
</template>

<script>
import data from "../classes/data";
import http from "../http/request";
export default {
  name: "",
  components: {
    Systemmsgcpt,
  },
  data() {
    return {

    };
  },
  methods: {
   

    sign(content) {
      let chatMsg = new data.ChatMsg(null, null, null, null);
      let dataContent = new data.DataContent(data.SIGNED, chatMsg, content);
      let msg = JSON.stringify(dataContent);
      console.log(this.$socket.readyState, this.$socket);
      this.foo();
      // this.$socket.send(msg);
      this.rs = "";
    },

    sleep(ms) {
      return new Promise((resolve) => setInterval(resolve, ms));
    },

    async foo() {
      console.log("ff");
      await sleep(1000);
    },
  },
  mounted() {
    this.fetch();
    this.sign(this.rs);
  },
};
</script>

<style scoped>
</style>