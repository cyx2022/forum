const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: '/',
  outputDir: 'dist',
  assetsDir:'./',
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      // proxyon为代理前缀，如果请求中包含该前缀，就走代理
      '/api': {
        // 真实的后端服务器地址
        target: "http://localhost:8002",
        ws: true,
        // 控制请求头中的host
        changeOrigin: true,
        // 将代理前缀名称替换为后端的真实前缀名称
        pathRewrite: {
          '^/api': ""
        }
      },
    }
  }
})
