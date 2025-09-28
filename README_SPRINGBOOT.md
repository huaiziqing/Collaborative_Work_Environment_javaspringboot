# 项目重构为Spring Boot架构说明

## 重构内容

本项目已从原来的Maven多模块传统Java Web项目重构为基于Spring Boot的单体架构，主要变更如下：

1. 项目结构优化：将原来的四个模块(common, book-service, server-service, main-application)合并为统一的Spring Boot项目结构
2. 依赖管理简化：使用Spring Boot Starter Parent统一管理依赖版本
3. 配置文件优化：使用application.yml替代原来的mybatis-config.xml
4. Web层重构：将原生Servlet转换为Spring MVC Controller
5. 数据访问层优化：使用Spring Boot MyBatis Starter自动配置

## 项目结构

```
src/main/java
├── org.example
│   ├── Application.java (Spring Boot启动类)
│   ├── book
│   │   ├── controller (控制器层)
│   │   ├── model (实体类)
│   │   ├── repository (数据访问层)
│   │   ├── service (业务逻辑层)
│   │   └── cache (缓存相关)
│   ├── server
│   │   ├── controller (控制器层)
│   │   ├── model (实体类)
│   │   ├── repository (数据访问层)
│   │   └── service (业务逻辑层)
│   └── common (通用工具类)
└── resources
    └── application.yml (Spring Boot配置文件)
```

## 运行项目

1. 确保安装了JDK 17
2. 使用Maven构建项目：
   ```bash
   mvn clean install
   ```
3. 运行Spring Boot应用：
   ```bash
   mvn spring-boot:run
   ```
   或
   ```bash
   java -jar target/Collaborative_Work_Environment-1.0-SNAPSHOT.jar
   ```

## API接口

项目提供RESTful API接口：

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

## 配置说明

项目配置文件为`src/main/resources/application.yml`，包含以下主要配置：

- 数据库连接信息
- MyBatis配置
- 服务器端口配置
- 日志级别配置

## 技术栈

- Spring Boot 3.1.0
- MyBatis 3.0.2
- MySQL 8.0.33
- Java 17