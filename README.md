Agile-demo
==
本项目旨在为大家，提供一个快速搭建系统的demo，Spring boot与Mybatis-plus的整合，其中的应用业务，是一个简单的权限系统，同时使用了Shiro框架，让大家熟悉Shiro在实际中的应用。

项目启动步骤
==
1、下载项目源码  
2、安装mysql，安装步骤(网络上有很多方法，略)，使用源码文件夹/sql下的sql初始化数据库，启动mysql服务。  
3、下载redis，启动redis服务。  
4、安装nginx，根据自己的电脑系统，上网查找，我的电脑是mac的，从网上找了一个安装即可；修改nginx下的配置，打开nginx目录，/nginx/nginx.conf

       ...
       server {
           ....
           #静态页面目录
           root  E:\github\X-SpringBoot\x-springboot-ui;
           #默认首页
           index  login.html;
           ....
           
           location ^~// {
                proxy_pass   http://127.0.0.1:9001; #这里为后端服务地址
           }
       }
       ...
配置nginx指定的login.html地址和后端服务的地址。  
5、注意nginx中的前端配置指定login.html文件的路径、后端服务url和应用保持一致，
源码项目中的mysql配置、redis配置改为本地服务地址，启动后端应用程序、启动nginx服务，然后访问nginx服务地址即可看到登录页。

项目说明
==
1、spring boot与mybatis-plus整合，采用mybatis-plus 的代码生成插件

2、mybatis-plus常用操作

3、登陆使用shiro整合

4、自定义日志注解、缓存注解

5、采用mybatis-plus动态数据源配置，读写分离

![数据库表设计](https://github.com/lyin226/agile-demo/tree/master/img/agile-demo.jpg)

![登陆页面](https://github.com/lyin226/agile-demo/tree/master/img/login.jpg)

![首页](https://github.com/lyin226/agile-demo/tree/master/img/index.jpg)

![管理员管理页面](https://github.com/lyin226/agile-demo/tree/master/img/admin.jpg)

![角色管理页面](https://github.com/lyin226/agile-demo/tree/master/img/role.jpg)

![菜单管理页面](https://github.com/lyin226/agile-demo/tree/master/img/menu.jpg)

![参数管理页面](https://github.com/lyin226/agile-demo/tree/master/img/param.jpg)

![日志管理页面](https://github.com/lyin226/agile-demo/tree/master/img/log.jpg)

![项目文档博客入门教程](https://blog.csdn.net/u011915230/article/details/90578777)
