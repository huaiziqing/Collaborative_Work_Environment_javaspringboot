# 协同办公系统

## 项目概述

本项目是一个基于Spring Boot后端和Vue3前端的协同办公系统，主要包含图书管理和服务器资源管理功能。

## 技术架构

### 后端技术栈
- Spring Boot 3.1.0
- MyBatis
- MySQL数据库
- Maven构建工具

### 前端技术栈
- Vue 3
- Vite
- Element Plus
- Axios

## 项目结构

```
项目根目录/
├── collaborative-office-user/     # 前端项目（Vue3 + Vite）
│   ├── src/                      # 前端源码
│   ├── package.json              # 前端依赖配置
│   └── vite.config.js            # 前端构建配置
├── src/                          # 后端源码
│   ├── main/java/org/example/    # 后端Java代码
│   └── main/resources/           # 后端资源配置
├── pom.xml                       # Maven配置
└── README.md                     # 项目说明文档
```

## 环境要求

- JDK 17+
- Maven 3.6+
- Node.js 16+
- MySQL 8.0+

## 快速开始

### 后端服务启动

```bash
# 克隆项目
git clone <项目地址>

# 进入项目目录
cd Collaborative_Work_Environment_javaspringboot

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

后端服务默认运行在 `http://localhost:8080`

### 前端服务启动

```bash
# 进入前端目录
cd collaborative-office-user

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务默认运行在 `http://localhost:8081`

## API接口

### 用户相关
- POST /api/login - 用户登录
- POST /api/register - 用户注册

### 图书相关
- GET /api/books - 获取所有图书
- GET /api/books/{id} - 根据ID获取图书
- POST /api/books - 添加图书
- PUT /api/books/{id} - 更新图书信息
- DELETE /api/books/{id} - 删除图书

### 服务器资源相关
- GET /api/resources - 获取所有服务器资源
- GET /api/resources/{id} - 根据ID获取服务器资源
- POST /api/resources - 添加服务器资源
- PUT /api/resources/{id} - 更新服务器资源信息
- DELETE /api/resources/{id} - 删除服务器资源

## 数据库配置

数据库连接信息配置在 `src/main/resources/application.yml` 文件中：

```yaml
spring:
  datasource:
    url: jdbc:mysql://数据库地址:3306/library_rental_system?useSSL=false&serverTimezone=UTC
    username: 用户名
    password: 密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## 开发规范

### 后端开发规范
1. 采用分层架构：controller、service、repository、model
2. 使用MyBatis进行数据访问
3. 遵循RESTful API设计原则
4. 统一异常处理

### 前端开发规范
1. 使用Vue 3 Composition API
2. 组件化开发
3. 遵循Element Plus设计规范
4. 使用Axios进行HTTP请求

## 部署说明

### 后端部署

```bash
# 打包项目
mvn clean package

# 运行打包后的jar文件
java -jar target/Collaborative_Work_Environment-1.0-SNAPSHOT.jar
```

### 前端部署

```bash
# 构建生产版本
npm run build:prod

# 部署dist目录中的文件到Web服务器
```

## 常见问题

### 1. 端口冲突
如果8080或8081端口被占用，可以修改配置文件中的端口设置。

### 2. 数据库连接问题
请确保MySQL服务正常运行，并且数据库连接配置正确。

### 3. 跨域问题
项目已配置CORS支持，如仍有跨域问题，请检查后端CorsConfig配置。

## 联系方式

如有问题，请联系项目维护人员。