package com.wyf.designcreate.ai.aiserver.tools;

import com.wyf.designcreate.constant.AppConstant;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriteTool {

    @Tool("写入文件到指定位置")
    public String writeFile(@P("文件相对路径")String relativePath,
                                   @P("文件内容") String content,
                                   @ToolMemoryId Long appId) {
        try {
            Path path = Paths.get(relativePath);
            String finalPath;

            if (path.isAbsolute()) {
                finalPath = relativePath;
            } else {
                String dirName = "vue_" + appId;
                String projectDir = AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + dirName;
                finalPath = projectDir + File.separator + relativePath;
            }

            Path fullPath = Paths.get(finalPath);

            Path parentDir = fullPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            Files.writeString(fullPath, content);
            return "文件写入成功: " + relativePath;
        } catch (IOException e) {
            return "文件写入失败: " + e.getMessage();
        }
    }

    @Tool("读取文件内容")
    public String readFile(@P("文件相对路径")String relativePath,
                                  @ToolMemoryId Long appId) {
        try {
            Path path = Paths.get(relativePath);
            String finalPath;

            if (path.isAbsolute()) {
                finalPath = relativePath;
            } else {
                String dirName = "vue_" + appId;
                String projectDir = AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + dirName;
                finalPath = projectDir + File.separator + relativePath;
            }

            Path fullPath = Paths.get(finalPath);

            if (!Files.exists(fullPath)) {
                return "文件不存在: " + finalPath;
            }

            return Files.readString(fullPath);
        } catch (IOException e) {
            return "文件读取失败: " + e.getMessage();
        }
    }

    @Tool("列出指定目录下的所有文件")
    public String listFiles(@P("目录相对路径或绝对路径，默认为空表示项目根目录")String relativeDir,
                                   @ToolMemoryId Long appId) {
        try {
            String dirName = "vue_" + appId;
            String projectDir = AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + dirName;

            String finalDir;
            if (relativeDir == null || relativeDir.isEmpty()) {
                finalDir = projectDir;
            } else {
                Path path = Paths.get(relativeDir);
                if (path.isAbsolute()) {
                    finalDir = relativeDir;
                } else {
                    finalDir = projectDir + File.separator + relativeDir;
                }
            }

            Path dirPath = Paths.get(finalDir);

            if (!Files.exists(dirPath)) {
                return "目录不存在: " + finalDir;
            }

            if (!Files.isDirectory(dirPath)) {
                return "不是目录: " + finalDir;
            }

            StringBuilder sb = new StringBuilder();
            try (var entries = Files.list(dirPath)) {
                entries.forEach(entry -> {
                    String type = Files.isDirectory(entry) ? "[DIR]" : "[FILE]";
                    sb.append(type).append(" ").append(entry.getFileName()).append("\n");
                });
            }

            return sb.toString();
        } catch (IOException e) {
            return "列出文件失败: " + e.getMessage();
        }
    }
}
