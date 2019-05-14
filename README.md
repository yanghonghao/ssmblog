# ssmblog
演示地址：http://152.136.147.167/front/index.html
测试账密：test:test
项目部分页面还未实现（参考、账号设置）
## 项目简介
个人博客网站，支持markdown编辑查看博客
## 后端技术选型
- ssm框架：主要用到于MVC、IOC

- shiro：权限管理

- mysql8：数据库

- redis：缓存权限信息

## 前端技术选型
- bootstrap3：整体样式

- dataTables插件：用于个人文章管理页面展示文章信息

- editor.md插件：用于实现markdown编辑器

- jquey.ajax：用于前后端异步通信（鉴于目前axios更流行，后面可能会使用axios将ajax替换掉）

- vue.js：用于导航条登录状态的渲染；主页文章列表的渲染

