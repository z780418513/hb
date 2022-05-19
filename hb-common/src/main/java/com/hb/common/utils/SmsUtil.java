package com.hb.common.utils;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 阿里短信服务工具类
 *
 * @Author hanbaolaoba
 * @Date 2022/05/19  15:30
 **/
@Component
public class SmsUtil {

    @Value("${ali.accessKeyId}")
    private String accessKeyId;
    @Value("${ali.accessKeySecret}")
    private String accessKeySecret;

    public static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(this.accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(this.accessKeySecret);
        // 访问的域名
        config.endpoint = SmsUtil.ENDPOINT;
        return new Client(config);
    }

    public void senMsg() throws Exception {
        Client client = createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        RuntimeOptions runtime = new RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        client.sendSmsWithOptions(sendSmsRequest, runtime);
    }
}
