# 部署指南

本文档提供 Design Create 项目的详细部署说明，包括开发环境和生产环境。

## 目录

- [开发环境部署](#开发环境部署)
- [生产环境部署](#生产环境部署)
- [环境变量说明](#环境变量说明)
- [数据库迁移](#数据库迁移)
- [常见问题](#常见问题)

---

## 开发环境部署

### 1. 安装基础依赖

#### JDK 21

```bash
# macOS
brew install openjdk@21

# Ubuntu/Debian
sudo apt install openjdk-21-jdk

# 验证安装
java -version
# 输出应包含 openjdk version "21.x.x"
```

#### MySQL 8.x

```bash
# macOS
brew install mysql
brew services start mysql

# Ubuntu/Debian
sudo apt install mysql-server
sudo systemctl start mysql

# 创建数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS design_create DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

#### Redis

```bash
# macOS
brew install redis
brew services start redis

# Ubuntu/Debian
sudo apt install redis-server
sudo systemctl start redis

# 验证连接
redis-cli ping
# 应返回 PONG
```

### 2. 克隆项目并初始化数据库

```bash
git clone https://github.com/WenYFmu/design-create.git
cd design-create

# 按顺序执行建表脚本
mysql -u root -p design_create < sql/user.sql
mysql -u root -p design_create < sql/app.sql
mysql -u root -p design_create < sql/message.sql
mysql -u root -p design_create < sql/message_feedback.sql
mysql -u root -p design_create < sql/message_summary.sql
```

### 3. 配置本地环境

创建本地配置文件 `src/main/resources/application-local.yml`：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/design_create?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
    username: root
    password: your_password
    type: com.zaxxer.hikari.HikariDataSource
  data:
    redis:
      host: localhost
      port: 6379
      timeout: 3600
  servlet:
    multipart:
      max-file-size: 10MB
server:
  port: 18080
  servlet:
    context-path: /api
    session:
      cookie:
        max-age: 2592000
      timeout: 43200

langchain4j:
  open-ai:
    chat-model:
      model-name: qwen3-max-2026-01-23
      api-key: ${AI_API_KEY:your_api_key_here}
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1
    streaming-chat-model:
      model-name: qwen3-max-2026-01-23
      api-key: ${AI_API_KEY:your_api_key_here}
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1

mycors: http://localhost:5173
```

> ⚠️ `application-local.yml` 已在 `.gitignore` 中排除，不会被提交到仓库。

### 4. 编译与运行

```bash
# 编译项目（跳过测试）
./mvnw clean package -DskipTests

# 方式一：直接运行 JAR
java -jar target/designcreate-0.0.1-SNAPSHOT.jar

# 方式二：使用 Maven 运行
./mvnw spring-boot:run

# 方式三：指定 Profile 运行
java -jar target/designcreate-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

### 5. 验证部署

| 服务 | 地址 |
|------|------|
| API 基地址 | http://localhost:18080/api |
| API 文档 (Knife4j) | http://localhost:18080/api/swagger-ui.html |
| OpenAPI 规范 | http://localhost:18080/api/v3/api-docs |

---

## 生产环境部署

<!-- 🎨 PLACEHOLDER: Docker 部署方案 — 需编写 Dockerfile 和 docker-compose.yml -->

### 方式一：直接部署（推荐入门）

#### 1. 服务器准备

```bash
# 安装 JDK 21
sudo apt install openjdk-21-jdk

# 安装 MySQL
sudo apt install mysql-server
sudo mysql_secure_installation

# 安装 Redis
sudo apt install redis-server
```

#### 2. 构建与部署

```bash
# 在开发机构建
./mvnw clean package -DskipTests

# 上传 JAR 到服务器
scp target/designcreate-0.0.1-SNAPSHOT.jar user@server:/opt/design-create/

# 在服务器上运行
java -jar /opt/design-create/designcreate-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --spring.datasource.password=${DB_PASSWORD} \
  --langchain4j.open-ai.chat-model.api-key=${AI_API_KEY} \
  --langchain4j.open-ai.streaming-chat-model.api-key=${AI_API_KEY}
```

#### 3. 使用 Systemd 管理服务

创建 `/etc/systemd/system/design-create.service`：

```ini
[Unit]
Description=Design Create Application
After=network.target mysql.service redis.service

[Service]
Type=simple
User=design-create
WorkingDirectory=/opt/design-create
ExecStart=/usr/bin/java -jar /opt/design-create/designcreate-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl daemon-reload
sudo systemctl enable design-create
sudo systemctl start design-create
```

### 方式二：Docker 部署（占位符）

<!-- 🎨 PLACEHOLDER: 以下 Docker 配置需要根据项目实际情况编写 -->

> Docker 部署方案待完善。需要编写 `Dockerfile` 和 `docker-compose.yml`。

```dockerfile
# Dockerfile 示例（待完善）
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/designcreate-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```yaml
# docker-compose.yml 示例（待完善）
version: '3.8'
services:
  app:
    build: .
    ports:
      - "18080:18080"
    depends_on:
      - mysql
      - redis
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      # 更多环境变量...

  mysql:
    image: mysql:8.0
    # 配置...

  redis:
    image: redis:7-alpine
    # 配置...
```

### Nginx 反向代理配置

```nginx
server {
    listen 80;
    server_name your-domain.com;

    location /api/ {
        proxy_pass http://127.0.0.1:18080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        # SSE 支持
        proxy_buffering off;
        proxy_cache off;
        proxy_read_timeout 86400s;
        proxy_send_timeout 86400s;
        chunked_transfer_encoding on;
    }

    # 静态资源（部署的应用）
    location /deploy/ {
        proxy_pass http://127.0.0.1:18080/api/static/;
    }
}
```

---

## 环境变量说明

为避免敏感信息硬编码，推荐通过环境变量或启动参数覆盖配置：

| 环境变量 | 对应配置 | 说明 |
|----------|---------|------|
| `AI_API_KEY` | `langchain4j.open-ai.chat-model.api-key` | AI 模型 API Key |
| `DB_PASSWORD` | `spring.datasource.password` | MySQL 密码 |
| `DB_USERNAME` | `spring.datasource.username` | MySQL 用户名 |
| `DB_URL` | `spring.datasource.url` | MySQL 连接地址 |
| `REDIS_HOST` | `spring.data.redis.host` | Redis 地址 |
| `REDIS_PORT` | `spring.data.redis.port` | Redis 端口 |

### 使用方式

```bash
# 方式一：环境变量
export AI_API_KEY=sk-your-key
export DB_PASSWORD=your-password
java -jar app.jar

# 方式二：启动参数
java -jar app.jar \
  --langchain4j.open-ai.chat-model.api-key=sk-your-key \
  --spring.datasource.password=your-password

# 方式三：.env 文件（配合 docker-compose）
```

---

## 数据库迁移

### 建表脚本执行顺序

按以下顺序执行 `sql/` 目录下的脚本：

```
1. user.sql              # 用户表（基础依赖，最先创建）
2. app.sql               # 应用表（依赖 user 表的 userId）
3. message.sql           # 消息表（依赖 app 表的 appId）
4. message_feedback.sql  # 消息反馈表（依赖 message 表）
5. message_summary.sql   # 消息摘要表（依赖 message 表）
```

### 数据库表关系

```
user (1) ──── (N) app (1) ──── (N) message (1) ──── (N) message_feedback
                                   │
                                   └──── (1) ──── (N) message_summary
```

### 注意事项

- 所有表使用 `utf8mb4_unicode_ci` 排序规则
- `app` 表的 `deployKey` 字段有唯一约束
- `message` 表的 `id` 字段为自增主键
- `app` 表的 `id` 字段使用雪花算法（`ASSIGN_ID`）
- 逻辑删除字段为 `isDelete` / `isDeleted`（0=正常，1=已删除）

---

## 常见问题

<!-- 🎨 PLACEHOLDER: FAQ — 需根据实际用户反馈持续补充 -->

### Q: 启动时报 `Unknown column` 错误

**A**: 检查是否已正确执行所有 SQL 建表脚本，确保数据库表结构与代码实体类一致。

### Q: AI 生成代码时返回 401 错误

**A**: 检查 `langchain4j.open-ai.chat-model.api-key` 配置是否正确，API Key 是否有效。

### Q: Redis 连接超时

**A**:
1. 确认 Redis 服务已启动：`redis-cli ping`
2. 检查 `spring.data.redis.host` 和 `port` 配置
3. 如有密码认证，添加 `spring.data.redis.password` 配置

### Q: SSE 流式接口返回 406 Not Acceptable

**A**: 确保请求头包含 `Accept: text/event-stream`，前端使用 `EventSource` 或 `fetch` + `ReadableStream` 处理 SSE。

### Q: 生成的代码文件在哪里？

**A**: 默认保存在项目根目录的 `tmp/code_output/` 下：
- HTML 生成：`tmp/code_output/html_{appId}/index.html`
- 多文件生成：`tmp/code_output/multi_file_{appId}/`
- Vue 项目生成：`tmp/code_output/vue_{appId}/`

### Q: 如何修改 AI 模型？

**A**: 修改 `application-local.yml` 中的 `langchain4j.open-ai` 配置：
- `model-name`: 更换模型名称
- `base-url`: 更换 API 地址（支持所有 OpenAI 兼容接口）
- `api-key`: 更换对应的 API Key

### Q: 跨域问题如何解决？

**A**: 修改 `application-local.yml` 中的 `mycors` 配置为前端实际地址：
```yaml
mycors: http://localhost:5173  # 前端开发服务器地址
```
