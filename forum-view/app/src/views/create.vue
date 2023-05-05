<!--
* @description 
* @fileName editmsg.vue
* @author cyx
* @date 2023/04/08 00:08:22
!-->
<template>
  <div class="box">
    <h5>
      创建<span>{{ this.$route.query.name }}吧</span>
    </h5>
    <div class="msg">
      <span>名称</span>
      <input type="input" v-model="name" disabled />
    </div>
    <div class="select">
      <el-select v-model="labelId" placeholder="请选择标签">
        <el-option
          v-for="(item, index) in label"
          :key="index"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>
    </div>
    <div class="msg">
      <span>简介</span>
      <input type="input" v-model="tip" />
    </div>

    <div class="img">
      <p class="value">更换头像</p>
      <input
        ref="photo"
        type="file"
        style="opacity: 0"
        name="file"
        @change="updateImg"
      />
    </div>
    <div class="show">
      <img :src="url" />
    </div>
    <div class="submit">
      <div @click="create">创建</div>
    </div>
  </div>
</template>
  
  <script>
import http from "../http/request";
export default {
  name: "",
  data() {
    return {
      member: {},
      name: "",
      tip: "",
      label: [],
      barId: "",
      url: "",
      labelId: "",
    };
  },
  methods: {
    doBinding() {
      this.name = this.$route.query.name;
    },
    // 更换头像
    updateImg() {
      let file = this.$refs.photo.files[0];
      let formData = new FormData();
      formData.append("photo", file);
      http
        .post("/upload/img/save", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          this.url = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    // 获取所有标签的类型
    getLabel() {
      http
        .get("/main/label/all")
        .then((res) => {
          this.label = res.data.object;
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },

    // 创建
    create() {
      http
        .post("/main/bar/add", {
          labelId: this.labelId,
          name: this.name,
          tip: this.tip,
          url: this.url,
        })
        .then((res) => {
          if (res.data.code == 200) {
            this.$router.push({
              path: "/main/bar/barcontent/barcontent1",
              query: {
                barId: res.data.object,
              },
            });
          } else {
            this.$message.error("创建失败");
          }
        })
        .catch((err) => {
          this.$message.error("服务端异常");
        });
    },
  },
  mounted() {
    this.getLabel();
    this.doBinding();
  },
};
</script>
  <style scoped>
* {
  margin: 0;
  padding: 0;
}
input {
  outline: none;
  text-decoration: none;
  border: 0;
  height: 30px;
  line-height: 30px;
  width: 50%;
  letter-spacing: 2px;
  text-indent: 5px;
  border-bottom: 1px solid rgba(125, 125, 125, 0.5);
  font-size: 16px;
  font-family: "Microsoft YaHei";
}
.box {
  border: 1px solid rgba(125, 125, 125, 0.3);
  width: 54vw;
  margin-left: 23vw;
  box-sizing: border-box;
  margin-bottom: 20px;
}
.box .msg {
  text-align: left;
  height: 50px;
  font-size: 16px;
}
h5 {
  height: 70px;
  line-height: 70px;
  font-weight: 500;
  font-size: 20px;
}
h5 span {
  margin-left: 10px;
}
.box > div > span {
  padding: 0 20px;
  letter-spacing: 5px;
}

.img {
  position: relative;
  padding: 5px 10px;
  overflow: hidden;
  margin-left: 20px;
  text-align: left;
}

.value {
  position: absolute;
  left: 0;
  top: 0;
  height: 30px;
  width: 80px;
  line-height: 30px;
  text-align: center;
  background-color: #38f;
  border-radius: 2px;
}
.img input {
  height: 30px;
  width: 80px;
}
.show {
  text-align: left;
  margin: 30px 0 20px 20px;
}
.show img {
  width: 20vw;
  height: 20vw;
}
.select {
  text-align: left;
  margin-left: 20px;
  margin-bottom: 10px;
}
.submit {
  position: relative;
  height: 30px;
  line-height: 30px;
  margin-bottom: 20px;
}
.submit div {
  background-color: red;
  color: white;
  position: absolute;
  right: 30px;
  height: 30px;
  line-height: 30px;
  padding: 3px 10px;
}
.submit div:hover {
  cursor: pointer;
}
</style>