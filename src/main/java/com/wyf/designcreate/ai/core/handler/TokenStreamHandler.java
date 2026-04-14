package com.wyf.designcreate.ai.core.handler;

import cn.hutool.json.JSONUtil;
import com.wyf.designcreate.ai.tokenstream.*;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenStreamHandler {

//    public Flux<String> handle(TokenStream stream) {
//        return Flux.create(sink -> {
//            stream.onPartialResponse((String partialResponse) -> {
//                PartialResponseMessage message = new PartialResponseMessage(partialResponse);
//                sink.next(JSONUtil.toJsonStr(message));
//            }).onPartialThinking(partialThinking -> {
//                PartialThinkingMessage message = new PartialThinkingMessage(partialThinking.text());
//                sink.next(JSONUtil.toJsonStr(message));
//            }).onRetrieved(contents -> {
//                RetrievedMessage message = new RetrievedMessage(contents.stream().map(c -> c.textSegment().text()).toList());
//                sink.next(JSONUtil.toJsonStr(message));
//            }).onIntermediateResponse((dev.langchain4j.model.chat.response.ChatResponse intermediateResponse) -> {
//                IntermediateResponseMessage message = new IntermediateResponseMessage(intermediateResponse);
//                sink.next(JSONUtil.toJsonStr(message));
//            }).beforeToolExecution(beforeToolExecution -> {
//                ToolExecutionRequest request = beforeToolExecution.request();
//                Map<String, Object> arguments = JSONUtil.toBean(request.arguments(), Map.class);
//                BeforeToolExecutionMessage message = new BeforeToolExecutionMessage(
//                        request.name(),
//                        request.id(),
//                        arguments
//                );
//                sink.next(JSONUtil.toJsonStr(message));
//            }).onToolExecuted(toolExecution -> {
//                ToolExecutionRequest request = toolExecution.request();
//                Map<String, Object> arguments = JSONUtil.toBean(request.arguments(), Map.class);
//                ToolExecutedMessage message = new ToolExecutedMessage(
//                        request.name(),
//                        request.id(),
//                        arguments,
//                        toolExecution.result()
//                );
//                sink.next(JSONUtil.toJsonStr(message));
//            }).onCompleteResponse((dev.langchain4j.model.chat.response.ChatResponse response) -> {
//                CompleteResponseMessage message = new CompleteResponseMessage(response);
//                sink.next(JSONUtil.toJsonStr(message));
//                sink.complete();
//            }).onError((Throwable error) -> {
//                ErrorMessage message = new ErrorMessage(error.getMessage(), error.getClass().getSimpleName());
//                sink.next(JSONUtil.toJsonStr(message));
//                sink.complete();
//            }).start();
//        });
//    }

    /**
     * 处理 TokenStream 流，支持工具执行回调。
     * 将流中的各个事件（部分响应、思考过程、工具执行前/后、完整响应、错误等）转换为 JSON 字符串并发射。
     *
     * @param stream LangChain4j 的 TokenStream 对象
     * @return 包含 JSON 格式消息的 Flux 流
     */
    public Flux<String> handleWithToolExecution(TokenStream stream) {
        return Flux.create(sink -> {
            // 处理部分响应内容
            stream.onPartialResponse((String partialResponse) -> {
                PartialResponseMessage message = new PartialResponseMessage(partialResponse);
                log.info("partialResponse: {}", message);
                sink.next(JSONUtil.toJsonStr(message));
            })
            // 处理部分思考过程
            .onPartialThinking(partialThinking -> {
                PartialThinkingMessage message = new PartialThinkingMessage(partialThinking.text());
                sink.next(JSONUtil.toJsonStr(message));
            })
            // 处理工具执行前的请求
            .beforeToolExecution(beforeToolExecution -> {
                ToolExecutionRequest request = beforeToolExecution.request();
                Map<String, Object> arguments = parseArguments(request.arguments());
                BeforeToolExecutionMessage message = new BeforeToolExecutionMessage(
                        request.name(),
                        request.id(),
                        arguments
                );
                log.info("beforeToolExecution: {}", message);
                sink.next(JSONUtil.toJsonStr(message));
            })
            // 处理工具执行后的结果
            .onToolExecuted(toolExecution -> {
                ToolExecutionRequest request = toolExecution.request();
                Map<String, Object> arguments = parseArguments(request.arguments());
                ToolExecutedMessage message = new ToolExecutedMessage(
                        request.name(),
                        request.id(),
                        toolExecution.result()
                );
                sink.next(JSONUtil.toJsonStr(message));
            })
//            // 处理完整响应
//            .onCompleteResponse((dev.langchain4j.model.chat.response.ChatResponse response) -> {
//                CompleteResponseMessage message = new CompleteResponseMessage(response);
//                sink.next(JSONUtil.toJsonStr(message));
//                sink.complete();
//            })
            // 处理错误
            .onError((Throwable error) -> {
                ErrorMessage message = new ErrorMessage(error.getMessage(), error.getClass().getSimpleName());
                sink.next(JSONUtil.toJsonStr(message));
                sink.complete();
            })
            // 启动流
            .start();
        });
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseArguments(String argumentsJson) {
        try {
            return JSONUtil.toBean(argumentsJson, Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
