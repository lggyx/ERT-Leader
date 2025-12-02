---
░█▀▀░█▀▄░▀█▀░░░░░█░░░█▀▀░█▀█░█▀▄░█▀▀░█▀▄
░█▀▀░█▀▄░░█░░▄▄▄░█░░░█▀▀░█▀█░█░█░█▀▀░█▀▄
░▀▀▀░▀░▀░░▀░░░░░░▀▀▀░▀▀▀░▀░▀░▀▀░░▀▀▀░▀░▀
---
> 简介
> 1. 应用场景
> 2. 技术采用
> 3. 部署运维
## 应用场景
用于人格领导力分析画像，方便工作分流

## 技术采用
---
**前端**:
1. vue
2. vite
3. element-plus
---
**后端**
1. springboot3
2. mybatis-plus
3. mysql
4. openapi3

## 部署运维
1. 克隆本仓库
```bash
git clone https://github.com/lggyx/ERT-Leader.git
```
2. 配置环境变量<br/>
- 数据库文件在docs/db目录下，请先导入数据库文件
- 复制一份.env.sample文件，并命名为.env<br/>
  修改.env文件本地服务的用户名、密码、数据库名称等配置
- 再复制一份.env.sample文件，并命名为.env.properties<br/>
  修改ErtLeaderApplicationTests.java注解参数，将.env.properties文件路径添加到注解参数里
3. 启动后端服务<br/>
在项目根目录下执行cmd命令启动服务
```bash
mvnw clean spring-boot:run
```
或者使用IDE工具启动，IDE工具启动时需要配置环境变量把配置文件添加到启动项里<br/>
访问 https://localhost:8912/

4. 启动前端服务<br/>
```bash
cd ert-leader-web
npm install
npm run dev
```
访问 http://localhost:8913/
