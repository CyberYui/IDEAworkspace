package backend;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GenerateTest {
    public static void main(String[] args) {
        // 创建generator对象
        AutoGenerator autoGenerator = new AutoGenerator();
        // 数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 选择数据库类型为MySQL
        dataSourceConfig.setDbType(DbType.MYSQL);
        // 引入驱动
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        // 数据库的用户名密码以及url需要修改成自己的
        dataSourceConfig.setUsername("mybatisuser");
        dataSourceConfig.setPassword("ThePassword");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/db_example");
        // 组装数据库

        autoGenerator.setDataSource(dataSourceConfig);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        // user.dir 表示读取当前路径,也就是读取 backend(项目路径)
        // 生成的文件会放到哪里,这里会放到当前项目的/src/main/java的backend中
        // 为什么会在 backend 中是因为后面有写到父包的包名为 backend
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        // 设置各个内容的创建作者
        globalConfig.setAuthor("CyberKaka");
        // 设置是否打开文件夹
        globalConfig.setOpen(false);
        // 声明service层接口的写法, %sService 的意思是把 IService 中大写的 I 去掉
        globalConfig.setServiceName("%sService");
        // 组装全局配置
        autoGenerator.setGlobalConfig(globalConfig);

        // 配置包信息
        PackageConfig packageConfig = new PackageConfig();
        // 父包类
        packageConfig.setParent("backend");
        // 各个子包的类
        // 实体类全放到entity文件夹中
        packageConfig.setEntity("entity");
        // mapper全放到mapper文件夹中
        packageConfig.setMapper("mapper");
        // service全放到service文件夹中
        packageConfig.setService("service");
        // service的实现类全放到service.impl文件夹中
        packageConfig.setServiceImpl("service.impl");
        // controller全放到controller文件夹中
        packageConfig.setController("controller");
        // 组装包配置
        // 由于既配置了路径又配置了包名,所以最终的文件会全部在这个路径生成
        // ("user.dir") + "/src/main/java"+"backend"
        // 也就是 F:/IDEAworkspace/springboot-vue-work/backend/src/main/java/backend
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 设置控制哪一张表,如果要控制多张表,这么写即可
        // strategyConfig.setInclude("table_name1","table_name2")
        strategyConfig.setInclude("qrcodedb");
        // setNaming 和 setColumnNaming 是用来映射表明和实体类
        // 以及字段名和成员变量名字的一个操作
        // 由于java和数据库名称的区别,不可能将数据库里面带下划线的名直接拿来用
        // 所以要将数据库中有下划线的名称改为驼峰式命名法
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 字段也进行相同操作
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 实体类创建好之后会加入lombok注解,也就是自动生成set和get方法
        strategyConfig.setEntityLombokModel(true);
        // 组装策略配置
        autoGenerator.setStrategy(strategyConfig);

        // 全部配置完成
        // 运行,本文件右键运行main方法即可,快捷键 ctrl+shift+f10
        autoGenerator.execute();
    }
}
