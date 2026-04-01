package com.wyf.designcreate.controller;

import cn.hutool.json.JSONUtil;
import com.wyf.designcreate.ai.core.aiserver.codegen.AiCodeGeneratorService;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiServiceController {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @PostMapping("/stream")
    public Flux<ServerSentEvent<String>> generateCode(String userMessage) {
        Flux<String> stringFlux = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        // 发送SSE事件
        return stringFlux.map(check -> {
            Map<String, String> data = Map.of("d", check);
            String jsonStr = JSONUtil.toJsonStr(data);
            return ServerSentEvent.<String>builder()
                    .data(jsonStr)
                    .build();
        });
    }
}
