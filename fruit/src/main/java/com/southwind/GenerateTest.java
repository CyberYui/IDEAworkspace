package com.southwind;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GenerateTest {
    public static void main(String[] args) {
        //创建generator对象
        AutoGenerator autoGenerator = new AutoGenerator();
        //数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);   //选择数据库类型为MySQL
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        //数据库的用户名密码以及url需要修改成自己的
        dataSourceConfig.setUsername("mybatisuser");
        dataSourceConfig.setPassword("ThePassword");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/db_example");
        //组装数据库

        autoGenerator.setDataSource(dataSourceConfig);
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //生成的文件会放到哪里,这里会放到当前项目的/src/main/java中
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        //设置各个内容的创建作者
        globalConfig.setAuthor("admin");
        //设置是否打开文件夹
        globalConfig.setOpen(false);
        //声明service层接口的写法,%sService的意思是把IService变成Service进行生成
        globalConfig.setServiceName("%sService");
        //组装全局配置
        autoGenerator.setGlobalConfig(globalConfig);

        //配置包信息
        PackageConfig packageConfig = new PackageConfig();
        //父包类
        packageConfig.setParent("com.southwind");
        //各个子包的类
        packageConfig.setEntity("entity");
        //mapper全放到mapper文件夹中
        packageConfig.setMapper("mapper");
        //service全放到service文件夹中
        packageConfig.setService("service");
        //service的实现类全放到service.impl文件夹中
        packageConfig.setServiceImpl("service.impl");
        //controller全放到controller文件夹中
        packageConfig.setController("controller");
        //组装包配置
        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //设置控制哪一张表,如果要控制多张表,这么写即可
        //strategyConfig.setInclude("fruit","name");
        strategyConfig.setInclude("fruit");
        //由于java和数据库名称的区别,要将数据库中有下划线的名称改为驼峰式名称
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //字段也进行相同操作
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //实体类创建好之后会加入lombok注解,也就是自动生成set和get方法
        strategyConfig.setEntityLombokModel(true);
        //组装策略配置
        autoGenerator.setStrategy(strategyConfig);

        //全部配置完成
        //运行,本文件右键运行main方法即可,快捷键 ctrl+shift+f10
        autoGenerator.execute();
    }
}