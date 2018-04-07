# JFinalOA
本项目根据：https://gitee.com/glorylion/JFinalOA.git 重构，作者开发的项目真的不错，而且能够分享出来。但由于数据库建表语句出现很多错误，
我修改了好长时间，终于部署成功。并把新的sql语句提交上去。

    idea: 2017
    jdk:1.8
    maven: 3.5.9
运行方法：
    1、clone项目导入idea
    2、导入idea，ctrl+alt+shift+s,设置里面选中facets,选中web，若没有请自行创建，配置项目下面的web.xml位置
    3、配置到本地tomcat,tomcat用的7.0
    4、导入doc目录下数据库下的sql脚本，首先导入jfinaloa+v.sql
    5、启动项目访问即可。
后续开发计划：
    1.修改用户信息，使其更加完善
    2.添加岗位管理
    3.添加任务管理
    4.添加项目管理
    5.添加考勤管理
    6.完善流程细节
后续使用的技术：Redis，zookeeper,dubbo等。
