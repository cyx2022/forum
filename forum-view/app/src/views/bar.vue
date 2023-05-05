<!--
* @description 
* @fileName bar.vue
* @author cyx
* @date 2023/03/21 23:56:26
!-->
<template>
  <div class="box">
    <div class="head">
      <div class="del" @click="del" v-if="!delFlag">注销</div>
      <div class="img">
        <img src="../img/1.jpg" />
        <div :class="{ focus: focusFlag }" @click="changeFocus">
          {{ focusFlag ? "已关注" : "关注" }}
        </div>
      </div>
      <div class="subhead">
        <div class="subhead1">
          <div>{{ bar.name }}</div>
          <div>
            <span class="span">关注:</span
            ><span class="number">{{ fans }}</span>
          </div>
          <div>
            <span class="span">帖子:</span
            ><span class="number">{{ bar.contentCount }}</span>
          </div>
        </div>
        <div class="subhead2">
          <div class="tip">{{ bar.tip }}</div>
          <div>
            <span class="label">label:</span
            ><span class="tolabel">{{ bar.labelName }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="dy"><router-view></router-view></div>
  </div>
</template>

<script>
import http from "../http/request";
export default {
  name: "",
  components: {},
  data() {
    return {
      member: {},
      barId: "",
      focusFlag: false,
      bar: {},
      fans: "",
    };
  },
  computed:{
    delFlag(){
      let id = localStorage.getItem("mId");
      return id==this.bar.builderId;
    }
  },
  methods: {
    getBar() {
      http
        .get("/main/bar/one", {
          params: {
            id: this.barId,
          },
        })
        .then((res) => {
          this.bar = res.data.object;
          this.getFansCount();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 检查是否关注
    checkFocus() {
      http
        .get(
          "/main/focus/bar/barid",
          {
            params: {
              barId: this.barId,
            },
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        )
        .then((res) => {
          this.focusFlag = res.data.object == 1 ? true : false;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 进行关注
    toFocus() {
      http
        .post(
          "/main/focus/bar/barid",
          {
            barId: this.barId,
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        )
        .then((res) => {
          this.focusFlag = true;
          this.getFansCount();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 取消关注
    cancelFocus() {
      http
        .delete(
          "/main/focus/bar/barid",
          {
            params: {
              barId: this.barId,
            },
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        )
        .then((res) => {
          this.focusFlag = false;
          this.getFansCount();
        })
        .catch(() => {
          this.$message.error("服务端异常");
        });
    },
    // 关注状态改变
    changeFocus() {
      if (this.focusFlag) {
        this.cancelFocus();
      } else {
        this.toFocus();
      }
    },
    getFansCount() {
      http
        .get("/main/focus/bar/count", {
          params: {
            barId: this.barId,
          },
        })
        .then((res) => {
          this.fans = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 进入页面对数据进行绑定
    doBonding() {
      this.barId = this.$route.query.barId;
    },
    del() {
      this.$confirm("此操作将永久注销该吧, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          http
            .post("/main/bar/del", {
              id: this.barId,
            })
            .then((res) => {
              console.log(res);
              if(res.data.object==true){
                this.$router.push("/main/home");
              }else{
                this.$message({
            type: "info",
            message: "注销失败",
          });
              }
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },

  mounted() {
    this.doBonding();
    // 查询是否进行关注
    this.checkFocus();
    this.getBar();
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.box {
  box-sizing: border-box;
  width: 70vw;
  /* background-color: green; */
  margin-left: 15vw;
  margin-top: 5.5vw;
}
.head {
  height: 10vw;
  background-color: rgba(125, 125, 125, 0.1);
  text-align: left;
  position: relative;
}
.img {
  display: inline-block;
  width: 10vw;
  height: 10vw;
  /* background-color: red; */
}
.img img {
  border-radius: 5vw;
  width: 10vw;
  height: 10vw;
  transform: translateY(-5vw);
}
.img > div {
  padding: 7px 10px 7px 10px;
  transform: translateY(-4vw);
  background-color: red;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 20px;
  text-align: center;
  color: white;
  box-sizing: border-box;
  z-index: 999;
}
.img > div:hover {
  cursor: pointer;
}
.focus {
  background-color: green !important;
}
.subhead {
  display: inline-block;
  width: 48vw;
  height: 10vw;
  /* background-color: blanchedalmond; */
  vertical-align: top;
  margin-left: 2vw;
}
.subhead1,
.subhead2 {
  height: 5vw;
  /* background-color: red; */
}
.subhead1 > div,
.subhead2 > div {
  height: 5vw;
  line-height: 5vw;
  text-align: left;
  /* background-color: firebrick; */
  float: left;
  margin-left: 10px;
}
.subhead1 div:nth-of-type(1) {
  font-size: 20px;
  font-weight: 700;
  font-family: STHeiti, "Microsoft Yahei", Arial, SimSun;
  /* vertical-align: text-bottom; */
}
.subhead1 div:nth-of-type(2),
.subhead1 div:nth-of-type(3) {
  margin-left: 30px;
  font-size: 14px;
}
.span {
  color: rgba(125, 125, 125, 0.8);
}
.number {
  color: #ff7f3e;
  margin-left: 10px;
}
.top {
  font-size: 22px;
}
.label,
.tolabel {
  font-size: 14px;
  margin-left: 10px;
  color: rgba(125, 125, 125, 0.8);
}

.dy {
  width: 100%;
  box-sizing: content-box;
}
.del {
  position: absolute;
  right: 10px;
  top: 10px;
  color: red;
  font-size: 12px;
}
.del:hover {
  cursor: pointer;
}
</style>