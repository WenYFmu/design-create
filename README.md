<div align="center">

<!-- 🎨 PLACEHOLDER: 项目 Banner 图片 — 建议使用 Figma/Canva 设计，推荐尺寸 1200×300，文件路径 docs/images/banner.png -->
<!-- 替换下方链接为实际 Banner 图片地址 -->
<img src="https://via.placeholder.com/1200x300/4A90D9/FFFFFF?text=Design+Create" alt="Design Create Banner" width="100%"/>

# Design Create

**AI 驱动的智能设计与代码生成平台**

通过自然语言对话，一键生成 HTML 页面、多文件前端项目、Vue 应用和 Slidev 演示文稿

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green.svg)](https://spring.io/projects/spring-boot)
[![LangChain4j](https://img.shields.io/badge/LangChain4j-1.12.2-blue.svg)](https://github.com/langchain4j/langchain4j)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

[功能特性](#-功能特性) • [截图演示](#-截图演示) • [快速开始](#-快速开始) • [部署说明](#-部署说明) • [API 文档](#-api-接口概览)

</div>

---

## ✨ 功能特性

- 🤖 **AI 对话式代码生成** — 通过自然语言描述，AI 自动生成前端代码
  - 单文件 HTML 页面生成
  - 多文件模式（HTML + CSS + JS）生成
  - Vue 项目（含多目录多文件）生成
- 🔄 **流式实时输出** — 基于 SSE（Server-Sent Events）的流式代码生成，实时查看生成过程
- 🎨 **Slidev 幻灯片生成** — 支持多种主题的 AI 演示文稿生成
  - 内置 6 种主题：Academic / Default / Frankfurt / Miracle / Penguin / Vuetiful
  - 支持大纲生成、动画效果、布局定制
- 📦 **应用管理与一键部署** — 创建、编辑、部署你的 AI 生成应用
- 💬 **对话历史与智能摘要** — 自动保存对话记录，长对话自动摘要压缩上下文
- 🔐 **用户认证与权限管理** — 注册/登录、管理员权限控制、Session 会话管理

## 📸 截图演示

<!-- 🎨 PLACEHOLDER: 以下截图需要运行项目后实际截取替换 -->

| 主界面 | AI 代码生成 |
|:---:|:---:|
| ![主界面](https://via.placeholder.com/580x360/EEEEEE/999999?text=主界面截图) | ![AI代码生成](https://via.placeholder.com/580x360/EEEEEE/999999?text=AI代码生成演示) |
| *应用首页与对话界面* | *AI 实时流式生成代码* |

| HTML 生成效果 | Vue 项目生成效果 |
|:---:|:---:|
| ![HTML效果](https://via.placeholder.com/580x360/EEEEEE/999999?text=HTML生成效果) | ![Vue效果](https://via.placeholder.com/580x360/EEEEEE/999999?text=Vue项目生成效果) |
| *单文件 HTML 页面生成结果* | *Vue 多文件项目生成结果* |

| Slidev 幻灯片 | 应用部署 |
|:---:|:---:|
| ![Slidev](https://via.placeholder.com/580x360/EEEEEE/999999?text=Slidev幻灯片效果) | ![部署](https://via.placeholder.com/580x360/EEEEEE/999999?text=应用部署效果) |
| *AI 生成的 Slidev 演示文稿* | *一键部署后的在线访问效果* |

> ⚠️ 以上截图为占位符，实际截图请参考 [docs/images/README.md](docs/images/README.md) 中的截图规范进行替换。

## 🛠 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| **后端框架** | Spring Boot | 3.5.13 |
| **编程语言** | Java | 21 |
| **AI 框架** | LangChain4j | 1.12.2 |
| **大语言模型** | 通义千问 (Qwen) | qwen3-max |
| **ORM** | MyBatis Plus | 3.5.12 |
| **数据库** | MySQL | 8.x |
| **缓存/会话** | Redis + Spring Session | - |
| **分布式锁** | Redisson | 3.27.0 |
| **API 文档** | Knife4j (OpenAPI 3) | 4.5.0 |
| **工具库** | Hutool | 5.8.37 |
| **构建工具** | Maven | 3.x |

## 🏗 系统架构

<!-- 🎨 PLACEHOLDER: 架构图 — 建议使用 draw.io / Excalidraw 绘制，文件路径 docs/images/architecture.png -->
<!-- 替换下方链接为实际架构图地址 -->

```
┌─────────────────────────────────────────────────────────┐
│                      前端客户端                          │
│                  (浏览器 / SPA 应用)                     │
└──────────────────────┬──────────────────────────────────┘
                       │ HTTP / SSE
                       ▼
┌─────────────────────────────────────────────────────────┐
│                  Spring Boot 后端                        │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐              │
│  │ 用户管理  │  │ 应用管理  │  │ AI 服务   │              │
│  │Controller│  │Controller│  │Controller│              │
│  └──────────┘  └──────────┘  └──────────┘              │
│  ┌──────────────────────────────────────────┐           │
│  │            AiServiceFacade               │           │
│  │  ┌─────────┐ ┌──────────┐ ┌──────────┐  │           │
│  │  │代码生成  │ │标题提取   │ │消息摘要   │  │           │
│  │  │Service  │ │Service   │ │Service   │  │           │
│  │  └─────────┘ └──────────┘ └──────────┘  │           │
│  └──────────────────────────────────────────┘           │
│  ┌──────────────────────────────────────────┐           │
│  │         代码解析 & 文件保存               │           │
│  │  CodeParser → SaveCodeFileTemplate       │           │
│  └──────────────────────────────────────────┘           │
│  ┌──────────────────────────────────────────┐           │
│  │         聊天记忆 (Redis ChatMemory)       │           │
│  └──────────────────────────────────────────┘           │
└────────┬───────────────────────┬────────────────────────┘
         │                       │
    ┌────▼────┐            ┌─────▼─────┐
    │  MySQL  │            │   Redis   │
    │ 业务数据 │            │ 缓存/会话  │
    └─────────┘            └───────────┘
```

## 📁 项目结构

```
design-create/
├── sql/                          # 数据库建表脚本
│   ├── app.sql                   # 应用表
│   ├── user.sql                  # 用户表
│   ├── message.sql               # 消息表
│   ├── message_feedback.sql      # 消息反馈表
│   └── message_summary.sql       # 消息摘要表
├── src/main/java/com/wyf/designcreate/
│   ├── ai/                       # AI 核心模块
│   │   ├── aiserver/             # AI 服务层
│   │   │   ├── chatmodel/        # 聊天模型配置
│   │   │   ├── codegen/          # 代码生成服务（工厂模式）
│   │   │   ├── summary/          # 消息摘要服务
│   │   │   ├── title/            # 标题提取服务
│   │   │   └── tools/            # AI 工具（文件读写）
│   │   ├── core/                 # AI 核心处理
│   │   │   ├── handler/          # 流式处理处理器
│   │   │   ├── parser/           # 代码解析器（策略模式）
│   │   │   └── saver/            # 文件保存（模板方法模式）
│   │   ├── memory/               # 聊天记忆（Redis 存储）
│   │   ├── message/              # 消息持久化
│   │   ├── model/                # AI 模型与枚举
│   │   └── tokenstream/          # Token 流消息类型
│   ├── annotation/               # 自定义注解（权限校验）
│   ├── aop/                      # AOP 切面（认证拦截）
│   ├── common/                   # 通用返回与错误码
│   ├── config/                   # 配置类（CORS、MyBatis、线程池）
│   ├── constant/                 # 常量定义
│   ├── controller/               # 控制器层
│   ├── exception/                # 全局异常处理
│   ├── mapper/                   # MyBatis Mapper
│   ├── model/                    # 数据模型
│   │   ├── dto/                  # 请求 DTO
│   │   ├── entity/               # 数据库实体
│   │   ├── enums/                # 枚举类型
│   │   └── vo/                   # 视图对象
│   └── service/                  # 业务服务层
├── src/main/resources/
│   ├── mapper/                   # MyBatis XML 映射
│   ├── prompt/                   # AI 系统提示词模板
│   ├── skills/                   # Slidev 技能定义与参考文档
│   ├── application.yaml          # 主配置文件
│   └── application-local.yaml    # 本地环境配置（不提交）
├── docs/                         # 项目文档
│   ├── images/                   # 截图与演示资源
│   └── deployment.md             # 详细部署指南
└── pom.xml                       # Maven 项目配置
```

## 🚀 快速开始

### 环境要求

| 依赖 | 最低版本 | 说明 |
|------|---------|------|
| JDK | 21+ | 必须使用 Java 21 及以上版本 |
| MySQL | 8.0+ | 业务数据存储 |
| Redis | 6.0+ | 缓存、会话管理、聊天记忆 |
| Maven | 3.8+ | 项目构建工具 |

### 1. 克隆项目

```bash
git clone https://github.com/WenYFmu/design-create.git
cd design-create
```

### 2. 初始化数据库

创建 MySQL 数据库并执行建表脚本：

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS design_create DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p design_create < sql/user.sql
mysql -u root -p design_create < sql/app.sql
mysql -u root -p design_create < sql/message.sql
mysql -u root -p design_create < sql/message_feedback.sql
mysql -u root -p design_create < sql/message_summary.sql
```

### 3. 配置本地环境

复制并修改本地配置文件：

```bash
cp src/main/resources/application-local.yaml src/main/resources/application-local.yml
```

编辑 `application-local.yml`，填入你的实际配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/design_create?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
    username: your_mysql_username    # 修改为你的 MySQL 用户名
    password: your_mysql_password    # 修改为你的 MySQL 密码
  data:
    redis:
      host: localhost                # 修改为你的 Redis 地址
      port: 6379                     # 修改为你的 Redis 端口

langchain4j:
  open-ai:
    chat-model:
      model-name: your_model_name    # 如 qwen3-max-2026-01-23
      api-key: your_api_key          # 修改为你的 API Key
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1  # 或其他 OpenAI 兼容 API 地址
    streaming-chat-model:
      model-name: your_model_name
      api-key: your_api_key
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1

mycors: http://localhost:5173        # 前端地址，用于跨域配置
```

> ⚠️ **安全提示**：`application-local.yml` 已在 `.gitignore` 中排除，不会被提交到仓库。切勿将 API Key 等敏感信息硬编码在 `application.yaml` 中。

### 4. 编译运行

```bash
# 使用 Maven Wrapper 编译（无需安装 Maven）
./mvnw clean package -DskipTests

# 运行项目
java -jar target/designcreate-0.0.1-SNAPSHOT.jar

# 或直接使用 Maven 运行
./mvnw spring-boot:run
```

### 5. 验证启动

项目启动后，访问以下地址验证：

- **API 基地址**: http://localhost:18080/api
- **API 文档 (Knife4j)**: http://localhost:18080/api/swagger-ui.html
- **OpenAPI 规范**: http://localhost:18080/api/v3/api-docs

## 📖 部署说明

详细的部署指南请参考 [docs/deployment.md](docs/deployment.md)，包含：

- 开发环境搭建
- 生产环境部署方案
- 环境变量与安全配置
- 常见问题排查

## ⚙️ 配置说明

### AI 记忆配置

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `ai.memory.history-keep-turns` | 4 | 保留的最近对话轮数 |
| `ai.memory.summary-start-turns` | 8 | 触发摘要的对话轮数阈值 |
| `ai.memory.summary-enabled` | true | 是否启用自动摘要 |
| `ai.memory.summary-max-chars` | 700 | 摘要最大字符数 |
| `ai.memory.title-max-length` | 30 | 自动提取的应用名称最大长度 |

### 应用生成目录

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| 代码输出目录 | `./tmp/code_output` | AI 生成的代码文件存放路径 |
| 代码部署目录 | `./tmp/code_deploy` | 部署的应用文件存放路径 |
| 部署域名 | `http://localhost:18080` | 应用部署后的访问域名 |

## 📡 API 接口概览

### 用户模块 `/api/user`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/user/login` | 用户登录 | 公开 |
| POST | `/user/register` | 用户注册 | 公开 |
| GET | `/user/logout` | 退出登录 | 登录 |
| GET | `/user/get/login` | 获取当前登录用户 | 登录 |
| POST | `/user/update` | 更新用户信息 | 登录 |
| POST | `/user/delete` | 删除用户 | 管理员 |
| POST | `/user/get/list` | 获取用户列表 | 管理员 |

### 应用模块 `/api/app`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/app/add` | 创建应用 | 登录 |
| GET | `/app/get/vo` | 获取应用信息（视图） | 公开 |
| POST | `/app/update` | 更新应用 | 登录 |
| POST | `/app/delete` | 删除应用 | 登录 |
| POST | `/app/my/list/page/vo` | 我的应用列表 | 登录 |
| POST | `/app/good/list/page/vo` | 精选应用列表 | 公开 |
| POST | `/app/deploy` | 部署应用 | 登录 |
| GET | `/app/chat/gen/code` | AI 对话生成代码（SSE 流式） | 登录 |
| POST | `/app/get/history/message` | 获取聊天历史 | 登录 |

### AI 服务模块 `/api/ai`

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/ai/stream` | AI 流式代码生成（SSE） | 管理员 |

> 完整的 API 文档请访问 Knife4j UI：http://localhost:18080/api/swagger-ui.html

## 🤝 贡献指南

<!-- 🎨 PLACEHOLDER: 贡献指南 — 需根据项目实际情况编写 -->

欢迎对项目做出贡献！请遵循以下步骤：

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 提交 Pull Request

## 📄 许可证

<!-- 🎨 PLACEHOLDER: 许可证 — 需选择合适的开源协议并创建 LICENSE 文件 -->

本项目采用 MIT 许可证。详情请参阅 [LICENSE](LICENSE) 文件。

---

<div align="center">

**如果这个项目对你有帮助，请给个 ⭐ Star 支持一下！**

</div>
