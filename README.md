# 工程使用说明 
---



工程使用说明可参见码云的wiki(coding的wiki不支持导出PDF和HTML)：[https://gitee.com/quanwenz/newframe/wikis](https://gitee.com/quanwenz/newframe/wikis)



版本更新日志
===================================================
- 2.1.0-20220426-2

  - 1、springboot升级至2.6.7
-----------------------------------------------
- 2.1.0-20220426-1

  - 1、优化登录和权限验证用到的缓存
  - 2、SpringBoot版本升级至2.6.6
  - 3、添加接口签名验证工具类
  - 4、暂时关闭common-docker-tools、common-etcd-tools、common-jgit-tools、common-minio-tools
    、biz-neo4j、common-lua-tools、common-zeromq-tools、log-expansion模块，如果还想使用在setting.xml中打开对应模块即可
  - 5、删除workflow、webflow-app半成品模块
  - 6、添加flowable工作流(目前不能与Atomikos共用)
  - 7、规范了autoconfig配置类命名并在spring.factories中作配置
  - 8、修改了java版本配置方式(1.11改为11)
  - 9、跨域origin配置改为originPattern
  - 10、修改Mybatis-Plus分页插件的问题
  - 11、xxtable插件中插入数据自动返回主键ID
  - 12、修复druid配置的若干BUG
  - 13、添加quartz定时任务引擎
  - 14、添加LocalDateTimeUtils工作
  - 15、mybatis-plus中添加动态表名切换功能
  - 16、优化多个模块的源码

-----------------------------------------------
- 2.0.2-20211206-1

    - 1、反射工具类中添加函数句柄
    - 2、更新etcd-tools模块功能
    - 3、更新docker-tools模块功能
    - 4、harbor-tools丰富工具函数
    - 5、添加common-jgit-tools模块功能
    - 6、添加phantomjs生成echarts图表功能
    - 7、添加文本比对功能
    - 8、添加通过配置文件和注解跳过权限认证的功能
    - 9、修改xxtable下拉菜单类型的BUG
    - 10、springboot升级至2.6.1
    - 11、修改xxtable的sql调用方式，动态适配多种数据库，当前支持mysql、orcale、神通通用
    - 12、添加了一些请求参数校验
    - 13、更新了k8s-tools模块功能
    - 14、修改了已知的一些BUG
    - 15、整体优化了代码
-----------------------------------------------
- 2.0.2-20211213-1
    - 1、添加了一些请求参数校验
    - 2、修复了K8s-tools中的BUG
    - 3、修复了代码中存在的一些安全漏洞
    - 4、升级log4j2版本至2.15.0

-----------------------------------------------
- 2.0.2-20211221-1
  - 1、log4j2升级至2.17.0
  - 2、修复了一部分能改的安全漏洞
  - 3、AES加密模式由ECD改为CBC
  - 4、配置文件内数据库密码加密
  - 5、默认gradle版本修改为7.3.2

-----------------------------------------------
- 2.0.2-20211221-2
  - 1、优化mybatis配置

-----------------------------------------------
- 2.0.2-20211231-1
  - 1、添加OpenAuth客户端
  - 2、在@ComponentScan注解中排除了测试包的扫描
  - 3、移动了DynamicMapper类的位置，删除了DynamicMapper中的弃用方法
  - 4、DynamicMapper中修改了insert、delete、select等直接运行SQL的方法，为了避免冲突，添加了BySql后缀
  - 5、扩展了Mybatis-Plus的通用方法，添加了fetchByStream和truncate方法，可在Mapper层继承DynamicMapper使用(替换继承BaseMapper)
  - 6、修改配置文件内mapper.xml路径配置多个只有一个生效的BUG
  - 7、添加执行初始化schema脚本的功能，在数据源处spring.datasource.druid.xxx.schema配置脚本和开关
  - 8、修复了一些配置文件内Mybatis-Plus的配置项不生效的BUG，Mybatis-Plus配置名称做了统一
  - 9、JWT生成Token方式添加了rsa的实现(为了以后兼容Istio网关)，通过配置文件内的配置选项切换生成方式
  - 10、升级了Mybatis-Plus等一些第三方依赖的版本
  - 11、添加了RedisJson操作依赖和测试
  - 12、将权限相关的Mapper.XML实现改为了Mybatis-Plus方式
  - 13、添加数据配置Validation等配置，防止mysql8小时断开连接
  - 14、修改数据源默认切面表达式支持切到配置的子包
  - 15、去除了CustomCorsFilter中无用的配置
  - 16、修改的数据源切面，使第一个数据源的包也注册到aop中

-----------------------------------------------
- 2.0.2-20220126-1
  - 1、添加Mybatis-Plus的MetaObjectHandler
  - 2、添加p6spy，在使用atomikos时会有冲突，在不使用atomikos时可以使用，默认不启用
  - 3、修复了StatFilter和数据库相关的一些布尔值配置不生效的BUG
  - 4、修改了CaffineUtils默认容量到10000
  - 5、添加了jredisearch依赖并添加了其全文检索的测试
  - 6、修改了一些gradle参数，替换了一些过时的gradle配置
  - 7、修改登陆时密码错误提示不准确的BUG
  - 8、调整sqlSessionFactoryCustomizers扩展接口的调用位置，保证扩展配置不被覆盖
  - 9、添加大文件断点续传功能(需前端配置，resources下有前端demo代码)
  - 10、添加prometheus监控依赖
  - 11、升级oshi-core版本至5.8.7，兼容windows11
  - 12、修改构建docker并推送至镜像私服的配置
  - 13、springboot版本升级至2.6.3
  - 14、修复了其他已知的BUG
