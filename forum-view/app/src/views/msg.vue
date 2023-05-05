<!--
* @description 
* @fileName msg.vue
* @author cyx
* @date 2023/04/21 09:59:47
!-->
<template>
  <div class="mcontainer">
    <div class="left">
      <div class="mhead">消息中心</div>
      <div
        v-for="(item, index) in msgUrl"
        :key="index"
        class="imsg"
        @click="changeUrl(index)"
        :class="{ active: activeIndex == index }"
      >
        {{ item.text }}
      </div>
    </div>
    <div class="right">
      <div class="rhead">{{ msgUrl[activeIndex].text }}</div>
      <el-scrollbar style="height: 537px;position: relative;"  v-if="activeIndex == 0">
        <div class="system">
          <Systemmsgcpt
            v-for="(item, index) in systemMsgUnreadList"
            :key="index"
            :msg="item"
            :flag="true"
          ></Systemmsgcpt>
          <Systemmsgcpt
            v-for="(item, index) in systemMsgLatestList"
            :key="index"
            :msg="item"
            :flag="false"
          ></Systemmsgcpt>
        </div>
      </el-scrollbar>
      <div class="person" v-if="activeIndex == 1">
        <div class="phead" v-if="personMsgUnreadHead.length > 0">
          {{ senderMember.name }}
        </div>
        <el-scrollbar style="height: 100%"  class="pleft">
          <div>
            <Membercell
              v-for="(item, index) in personMsgUnreadHead"
              :key="index"
              :absMsg="item"
              @click="changeMember(index)"
            ></Membercell>
          </div>
        </el-scrollbar>

        <div class="pright">
          <el-scrollbar style="height: calc(100% - 120px);position: relative;">
            <div v-for="(item, index) in memberMsg" :key="index">
              <lmsgcontent
                v-if="item.senderId == senderId"
                :member="senderMember"
                :msg="item"
              ></lmsgcontent>
              <Rmsgcontent
                v-if="item.senderId == receiverId"
                :member="receiverMember"
                :msg="item"
              ></Rmsgcontent>
            </div>
          </el-scrollbar>
          <div class="input">
            <textarea v-model="text"></textarea>
            <div class="psubmit" @click="submitMsg">发送</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http/request";
import Systemmsgcpt from "../components/systemmsgcpt.vue";
import data from "../classes/data";
import Membercell from "../components/membercell.vue";
import Lmsgcontent from "../components/lmsgcontent.vue";
import Rmsgcontent from "../components/rmsgcontent.vue";
export default {
  name: "",
  components: {
    Systemmsgcpt,
    Membercell,
    Lmsgcontent,
    Rmsgcontent,
  },
  data() {
    return {
      msgUrl: [
        { text: "系统消息", url: "/main/msg/system" },
        { text: "个人消息", url: "/main/msg/person" },
      ],
      activeIndex: this.$route.query.activeIndex||0,
      socket: null,
      systemMsgLatestList: [],
      systemMsgUnreadList: [],
      rs: "",
      // 未读消息的第一条消息
      personMsgUnreadHead: [],
      // 发送消息文本
      text: "",
      // 活跃的发送者id
      membercellId: 0,
      // 活跃的发送者消息
      memberMsg: [],
      receiverMember: {},
      senderMember: {},
      senderId: "",
      receiverId: "",
    };
  },
  methods: {
    // 接入当前会话
    initChat() {
      let msg=new data.ChatMsg();
      msg.senderId=localStorage.getItem("mId");;
      msg.receiverId=this.$route.query.memberId;
      msg.msg="";
      this.fetchPersonMsgHead();
      var index=-1;
      var value;
      for(let i=0;i<this.personMsgUnreadHead;i++){
        if(this.personMsgUnreadHead[i].receiverId==msg.receiverId){
          index=i;
          value=this.personMsgUnreadHead[i];
          this.personMsgUnreadHead[i].splice(i, 1);
          break;
        }
      }
      if(index!=-1){
        this.personMsgUnreadHead.unshift(value);
      }
    },
    changeUrl(index) {
      this.activeIndex = index;
      if (index == 1) {
        // 拉取用户消息
        this.fetchPersonMsgHead();
      }
    },
    initSocket() {
      //判断浏览器是否支持websocket
      if (window.WebSocket) {
        //如果当前WebSocket 状态已经连接，无需重复初始化WebSocket
        if (
          this.socket != null &&
          this.socket != undefined &&
          this.socket.readyState == WebSocket.OPEN
        ) {
          return false;
        }
        //创建websocket 对象
        try {
          let s = localStorage.getItem("access_token");
          let access_token = s.substring(1, s.length - 1);
          this.socket = new WebSocket("ws:127.0.0.1:8002/ws", [access_token]);
          this.socket.onopen = this.wsopen;
          this.socket.close = this.wsclose;
          this.socket.onerror = this.wserror;
          this.socket.onmessage = this.wsmessage;
        } catch (e) {
          console.log(e.message);
        }
      } else {
        console.log("您的手机系统版本过低，请升级设备");
      }
    },
    chat(msg) {
      //如果当前WebSocket 状态已经连接，无需重复初始化WebSocket
      if (
        this.socket != null &&
        this.socket != undefined &&
        this.socket.readyState == WebSocket.OPEN
      ) {
        //将客户输入的消息进行发送
        this.socket.send(msg);
      } else {
        // this.initSocket();
        // setTimeout(this.chat, "1000",msg);
      }
    },
    reChat(msg) {
      //socket 重连机制
      console.log("消息重新发送");
      // this.socket.send(msg);
    },
    wsopen() {
      console.log("websocket 已连接");
      //构建chatMsg
      var chatMsg = new data.ChatMsg(null, null, null, null);
      //构建DataContent
      var dataContent = new data.DataContent(data.CONNECT, chatMsg, null);

      this.chat(JSON.stringify(dataContent));

      //定时心跳发送
      setInterval(this.keepalive, 10000);
      this.fetchSystemMsg();
    },
    wsmessage(e) {
      console.log("接收到的消息：" + e.data);
      var dataConetent = JSON.parse(e.data);
      var action = dataConetent.action;
      if (action == 5) {
        // 非法用户
        this.$router.push("/login");
      } else if (action == 6) {
        // token异常，重新获取token
      } else if (action == 2) {
        // 聊天信息
        var chatMsg = dataConetent.chatMsg;
        var msg = chatMsg.msg; //聊天内容
        var friendUserId = chatMsg.senderId;
        if (friendUserId == 0) {
          // 系统消息
          if (this.activeIndex == 0) {
            // 在系统消息的界面
            this.signMsgSingle(chatMsg.msgId);
          }
        } else {
          // 用户消息
          if (this.activeIndex == 1) {
            // 当前是活跃发送者聊天
            if (this.membercellId == friendUserId) {
              // 签收消息
              this.signMsgSingle(chatMsg.msgId);
              // 拼接消息记录
              this.memberMsg.push(dataConetent);
              this.personMsgUnreadHead[this.membercellId] = dataConetent;
            } else {
              // 不在当前聊天页
              let index = -1;
              for (let i = 0; i < this.personMsgUnreadHead; i++) {
                if (
                  this.personMsgUnreadHead[i].senderId == dataConetent.senderId
                ) {
                  index = i;
                }
              }
              if (index == -1) {
                // 不在未读消息列表
                this.personMsgUnreadHead.unshift(dataConetent);
                this.membercellId = this.membercellId + 1;
              } else {
                // 在未读消息列表
                this.personMsgUnreadHead[index] = dataConetent;
              }
            }
          }
        }
        //接到到消息之后，对消息进行签收
        // var dataContentSign = new data.DataContent(
        //   data.SIGNED,
        //   null,
        //   chatMsg.msgId
        // );
        // this.chat(JSON.stringify(dataContentSign));
      }
    },
    wsclose() {
      console.log("连接关闭.......");
    },
    wserror() {
      console.log("发生错误.....");
    },
    //构建单个签收对象的模型
    signMsgSingle(msgId) {
      var dataContentSign = new data.DataContent(data.SIGNED, null, msgId);
      this.chat(JSON.stringify(dataContentSign));
    },
    //构建批量签收对象的模型
    signMsgList(unSignedMsgIds) {
      var dataContentSign = new data.DataContent(
        data.SIGNED,
        null,
        unSignedMsgIds
      );
      this.chat(JSON.stringify(dataContentSign));
    },
    keepalive() {
      //构建对象
      var dataConetent = new data.DataContent(data.KEEPALIVE, null, null);
      //发送心跳
      this.chat(JSON.stringify(dataConetent));
    },

    // 获取系统消息
    getSystemMsg() {
      const request1 = http.get("/msg/system/list/latest");
      const request2 = http.get("/msg/system/list/read");
      return Promise.all([request1, request2])
        .then(([res1, res2]) => {
          this.systemMsgLatestList = res1.data.object;
          this.systemMsgUnreadList = res2.data.object;
          for (let i = 0; i < this.systemMsgUnreadList.length; i++) {
            this.rs += this.systemMsgUnreadList[i].id + ",";
          }

          if (this.rs.length > 0)
            this.rs = this.rs.substring(0, this.rs.length - 1);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 获取每个用户的第一条未读消息
    getPersonMsgHead() {
      http
        .get("/msg/person/latest/head")
        .then((res) => {
          this.personMsgUnreadHead.push(...res.data.object);
          this.changeMember();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async fetchSystemMsg() {
      await this.getSystemMsg();
      this.signMsgList(this.rs);
      this.rs = "";
    },

    async fetchPersonMsgHead() {
      await this.getPersonMsgHead();
      // 获取第一个活跃发送者的消息
    },

    // 改变消息的发送者
    changeMember() {
      http
        .get("/msg/person/list/one", {
          params: {
            senderId: this.personMsgUnreadHead[this.membercellId].senderId,
          },
        })
        .then((res) => {
          this.senderId = "";
          this.receiverId = "";
          // 重新获取聊天记录
          this.memberMsg = res.data.object;
          if (this.memberMsg.length > 0) {
            this.receiverId = localStorage.getItem("mId");
            this.senderId =
              this.memberMsg[0].senderId != this.receiverId
                ? this.memberMsg[0].senderId
                : this.memberMsg[0].receiverId;
            this.getMember(this.senderId, 0);
            this.getMember(this.receiverId, 1);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 获取发送者，接收者的信息
    getMember(id, flag) {
      http
        .get("/main/member/get", {
          params: {
            id: id,
          },
        })
        .then((res) => {
          if (flag == 0) {
            this.senderMember = res.data.object;
          } else {
            this.receiverMember = res.data.object;
          }
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
    submitMsg() {
      //构建chatMsg
      var chatMsg = new data.ChatMsg(
        this.receiverId,
        this.senderId,
        this.text,
        null
      );
      //构建DataContent
      var dataContent = new data.DataContent(data.CHAT, chatMsg, null);
      this.chat(JSON.stringify(dataContent));
      console.log("发送消息", dataContent);
      this.memberMsg.push(chatMsg);
    },
  },
  mounted() {
    this.initChat();
    this.initSocket();
  },
};
</script>
<style scoped>
@import "../css/scroll.css";
.mcontainer {
  width: 75vw;
  margin-left: 12.5vw;
}
.left,
.right {
  display: inline-block;
  vertical-align: top;
  box-sizing: border-box;
}
.left {
  width: 15%;
  background-color: rgba(125, 125, 125, 0.2);
  border: 1px solid rgba(125, 125, 125, 0.1);
  box-sizing: border-box;
}
.right {
  width: 84%;
  margin-left: 1%;
  background-color: rgba(125, 125, 125, 0.2);
  border: 1px solid rgba(125, 125, 125, 0.1);
}
.mhead {
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 18px;
}
.imsg {
  height: 40px;
  line-height: 40px;
  text-align: center;
}
.imsg:hover {
  cursor: pointer;
  background-color: white;
  color: #38f;
}
.rhead {
  height: 50px;
  line-height: 50px;
  text-align: center;
  color: #38f;
  background-color: white;
}

.person {
  height: 100% !important;
}
.pleft,
.pright {
  display: inline-block;
  vertical-align: top;
}
.pleft {
  width: calc(25%);
  height: 506px;
}
.system {
  height: 527px;
}
.pright {
  position: relative;
  width: calc(75% - 0px);
  height: 507px;
  border: 1px solid rgba(125, 125, 125, 0.3);
  padding: 10px 10px;
  box-sizing: border-box;
}
.phead {
  text-align: center;
  height: 30px;
  line-height: 30px;
  /* border-bottom: 1px solid rgba(125,125,125,0.3); */
  color: #38f;
}
.input {
  background-color: white;
  width: 96%;
  height: 120px;
  position: absolute;
  bottom: 0;
  left: 2%;
  right: 2%;
}
.pright textarea {
  border: none;
  outline: none;
  resize: none;
  background: rgba(125, 125, 125, 0.05);
  height: 80%;
  width: 100%;
  padding: 5px 10px;
  font-size: 16px;
  box-sizing: border-box;
}
.psubmit {
  height: 20%;
  width: 10%;
  margin-left: 90%;
  background-color: red;
  line-height: 24px;
  text-align: center;
}
.psubmit:hover {
  cursor: pointer;
}
.active {
  background-color: white;
  color: #38f;
}
.scroll{
  position: absolute;
  right: 0;
}
</style>






