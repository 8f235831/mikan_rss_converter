# 【Deprecated】Mikan Rss Converter
为解决qBittorrent客户端访问蜜柑计划（ https://mikanani.me/ ）的RSS源时，访问不稳定、难以并发访问的问题，建立此项目。

<del>项目完成后不到一周，</del>蜜柑计划已无法访问，本项目已失效。

## 项目概况
项目基于SpringBoot建立了服务后端，基于Vue3设计了前端管理页面。前端资源合并在后端中，可一并部署。

项目目前实现的功能包括：
* 对RSS源访问的失败自动重试
* 对RSS源的缓存
* 图形化管理页面
* 对RSS项的正则过滤

## 项目使用
作为SpringBoot项目编译打包，使用Docker部署。 

* 控制页面： `http://<hostname>:40401/`
* RSS源：`http://<hostname>:40401/rss/source/list`
