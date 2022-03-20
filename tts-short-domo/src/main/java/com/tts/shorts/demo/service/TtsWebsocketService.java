package com.tts.shorts.demo.service;

import com.tts.shorts.demo.util.SignCheck;
import org.springframework.stereotype.Service;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 类功能
 *
 * @author unisound
 * @date 2020/9/25
 */
@Service
public class TtsWebsocketService {

    static String host = "wss://ws-stts.hivoice.cn/v1/tts?";

    static String appkey = "45gn7md5n44aak7a57rdjud3b5l4xdgv75saomys";
    static String secret = "ba24a917a38e11e49c6fb82a72e0d896";

    public  void ttsWebsocket() throws Exception {
        String fileName = System.currentTimeMillis() + ".mp3";

        String text = "刘先生，你好，你好你好，有什么事情我能帮到你。";
//        String text = "我要说的是，你千万别害怕";
//        String text = "刘先生，你好，你好你好，有什么事情我能帮到你。";
//        String text = "刘先生，你好，你好你好，有什么事情我能帮到你。";

        long time = System.currentTimeMillis();
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append(appkey).append(time).append(secret);
        String sign = SignCheck.getSHA256Digest(paramBuilder.toString());

        StringBuilder param = new StringBuilder();
        param.append("appkey=").append(appkey).append("&")
                .append("time=").append(time).append("&")
                .append("sign=").append(sign);
        String str = host + param.toString();
        System.out.println(str);
        URI uri = new URI(str);

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        Session session = container.connectToServer(new TtsClientEndpoint(fileName, text), uri);
        //TODO  客户端要设置
        session.setMaxBinaryMessageBufferSize(1024 * 1024 * 10);

        while (true) {
            Thread.sleep(1000);
            if (!session.isOpen()) {
                break;
            }
        }
    }


}
