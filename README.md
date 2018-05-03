# 嘟嘟
本项目根据：https://gitee.com/glorylion/JFinalOA.git 重构，作者开发的项目真的不错，而且能够分享出来。但由于数据库建表语句出现很多错误，
我修改了好长时间，终于部署成功。并把新的sql语句提交上去。

    idea: 2017
    jdk:1.8
    maven: 3.5.9
## 为什么选择JFinal
这个项目是以自己做测试和平时练手之用，而如何快速和稳定的搭建测试平台是主要目的，业务逻辑和技术难点，都可以嵌入进去，而JFinal刚好符合这个标准吧。
## 运行方法：
1. clone项目导入idea；
2. 导入idea，ctrl+alt+shift+s,设置里面选中facets,选中web，若没有请自行创建，配置项目下面的web.xml位置；
3. 配置到本地tomcat,tomcat用的8.0；
4. 导入doc目录下数据库下的sql脚本，首先导入jfinaloa+v.sql；
5. 启动项目访问即可。

## 后续开发计划：
- 修改用户信息，使其更加完善；
- 添加岗位管理；
- 创建团队项目，实现工作日期，任务分配，周报和月报管理等，实现分布式架构搭建；
- 创建考勤项目，实现考勤申请，考勤汇总，考勤录入等功能，完善分布式架构；
- 完善流程细节；

## 后续使用的技术：
    Redis，zookeeper,dubbo等。
    
    
    
## 图片展示
![image](https://github.com/AllenLeeY/JFinalOA/blob/master/src/main/webapp/common/img/readme/login.png?raw=true)
![image](https://raw.githubusercontent.com/AllenLeeY/JFinalOA/master/src/main/webapp/common/img/readme/home.png)
![image](https://github.com/AllenLeeY/JFinalOA/blob/master/src/main/webapp/common/img/readme/frient.png?raw=true)
![image](https://github.com/AllenLeeY/JFinalOA/blob/master/src/main/webapp/common/img/readme/clum.png?raw=true)