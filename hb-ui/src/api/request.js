/*axios二次封装*/
import axios from "axios";

const requests = axios.create({
    // 基础路径
    baseURL: process.env.VUE_APP_BASE_URL,
    // 请求响应超时时间
    timeout: 5000,
    headers:{
        "Content-type":"application/json"
    }
})

/* 请求拦截器：在请求之前可以检测到，可以对请求进行处理 */
requests.interceptors.request.use((config) => {
    // config：配置对象，里面有一个属性很重要，就是header请求头
    //          检查是否有token,有的话说明是已登录，没有就说明未登录
    //          如果登录了就在每个接口的headers里面增加token
    //         var token = localStorage.getItem("antToken");
    //         if (token) {
    //             config.headers.token = token;
    //         }
    return config;
})

requests.interceptors.response.use((res)=>{
    // 成功对回调函数：服务器响应数据回来之后，响应拦截器可以检测到，可以做一些事情
    return res.data;
},(error => {
    // 响应失败对回调函数
    return Promise.reject(error);
}))

// 对外暴露
export default requests;