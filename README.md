<h1 style="text-align: center">E-Mall System</h1>

[![GitHub issues](https://img.shields.io/github/issues/mingtingouyang/ego-project)](https://github.com/mingtingouyang/ego-project/issues)
[![GitHub forks](https://img.shields.io/github/forks/mingtingouyang/ego-project)](https://github.com/mingtingouyang/ego-project/network)
[![GitHub stars](https://img.shields.io/github/stars/mingtingouyang/ego-project)](https://github.com/mingtingouyang/ego-project/stargazers)
[![GitHub license](https://img.shields.io/github/license/mingtingouyang/ego-project)](https://github.com/mingtingouyang/ego-project/blob/master/LICENSE)

> 以下内容针对 springcloud 分支，给分支为最终版本

该项目基于微服务构建，使用 Spring Cloud + Eureka 注册中心管理微服务。使用了 SSM 框架，数据库使用 MySQL，运用 Redis 作为缓存，搜索功能基于 Solr 服务实现，使用了 ActiveMQ 实现消息队列功能。功能上主要包含两个部分：后台管理系统以及商城 Web 客户端。

## 后台管理系统

后台管理系统的前端主要使用 EasyUI 构建，该系统负责对商城内容的管理。以下为目前实现了的功能介绍。

### 商品类别管理

商品类别可用于商城页面的菜单加载（类别列表），以及分类创建商品。类别的管理除了类别的增、删外，还包括了每个类别的商品介绍中的参数模板管理，包含两级菜单，例如：

- 手机类别的商品的商品详情包括：基本信息（品牌、型号、上市时间等）、硬件信息（CPU、摄像头、电池等）...
- 电视类别的商品的商品详情包括：基本信息（品牌、型号、上市时间等）、硬件信息（尺寸、分辨率、色域等）...

管理员可以为每种类别等商品设定固定的模板，这样在添加商品时，会根据选定的商品类别，提供相应的模板给添加者填写商品的具体参数信息，用于显示在商城的商品详情页面。

另外，由于为了加速商城首页的渲染速度，
