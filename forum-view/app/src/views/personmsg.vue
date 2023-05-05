<!--
* @description 
* @fileName personmsg.vue
* @author cyx
* @date 2023/04/18 10:51:32
!-->
<template>
  <div class="person">
    <div class="phead">{{ memberList[activedIndex].name }}</div>
    <div class="pleft">
        <Membercell></Membercell>
        <Membercell></Membercell>
    </div>
    <div class="pright">
        <lmsgcontent></lmsgcontent>
        <Rmsgcontent></Rmsgcontent>
        <div class="input">
            <textarea v-model="text"></textarea>
            <div class="psubmit">发送</div>
        </div>
    </div>
  </div>
</template>

<script>

import Membercell from '../components/membercell.vue'
import Lmsgcontent from '../components/lmsgcontent.vue'
import Rmsgcontent from '../components/rmsgcontent.vue'
export default {
  name:"",
  components:{
    Membercell,
    Lmsgcontent,
    Rmsgcontent,
  },
  data(){
    return {
        memberList:[{name:'cyx'}],
        activedIndex:0,
        text:'',
        
    }
  }
}

</script>
<style scoped>
.person{
    height: 100%;
}
.pleft,.pright{
    display: inline-block;
    vertical-align: top;
}
.pleft{
    width: calc(25%);
    height:506px;
}
.pright{
    position: relative;
    width: calc(75% - 0px);
    height: 507px;
    border: 1px solid rgba(125,125,125,0.3);
    padding: 10px 10px;
    box-sizing: border-box;
}
.phead{
    text-align: center;
    height: 30px;
    line-height: 30px;
    /* border-bottom: 1px solid rgba(125,125,125,0.3); */
}
.input{
    background-color: white;
    width: 96%;
    height: 120px;
    position: absolute;
    bottom: 0;
    left: 2%;
    right: 2%;
}
.pright textarea{
	border:none;
	outline: none;
	resize: none;
	background:rgba(125,125,125,0.05);
    height: 80%;
    width: 100%;
    padding: 5px 10px;
    font-size: 16px;
    box-sizing: border-box;
}
.psubmit{
    height: 20%;
    width: 10%;
    margin-left: 90%;
    background-color: red;
    line-height: 24px;
    text-align: center;
}
.psubmit:hover{
    cursor: pointer;
}
</style>