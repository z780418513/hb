const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false, // false:关闭语法检查

    assetsDir: "static",
    runtimeCompiler: true,

    devServer: {
        host: "0.0.0.0",
        // 端口号
        port: 8080,
        https: false,
        // https:{type:Boolean}
        //配置自动启动浏览器
        open: true,
        //热更新
        hot: true,
        //配置多个跨域
        proxy: { }
        // proxy: proxyConfig.proxyList,
    },
})
