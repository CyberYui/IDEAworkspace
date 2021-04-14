package com.example.mybatisplusdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 在一个 SpringBoot 应用中,只需要配置 @MapperScan 注解到 mapper 目录即可
@MapperScan("com.example.mybatisplusdemo.mapper")
public class MybatisPlusDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusDemoApplication.class, args);
	}
	// 项目主要是使用 mybatis-plus 链接 H2 数据库完成数据的调用,H2 数据库的好处是它会在 resources/db 中保存
	// db是自己创建的,这一种数据库的好处是可以在打包的时候嵌入 jar 包,完成对数据的自给自足式的调用

	/*本项目的结果是会在控制台输出以下内容,这些内容是写在 H2 数据库中的
	* User(id=1, name=Jone, age=18, email=test1@baomidou.com)
	* User(id=2, name=Jack, age=20, email=test2@baomidou.com)
	* User(id=3, name=Tom, age=28, email=test3@baomidou.com)
	* User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
	* User(id=5, name=Billie, age=24, email=test5@baomidou.com)  */

	// 我们可以看到集成MyBatis-Plus非常的简单，只需要引入 starter 工程，并配置 mapper 扫描路径即可
	// 我甚至为本项目添加了一个 mysql 数据库 db_testmybatis,显然现在用不到了

	/* 关于 mybatis-plus 的使用:
	* <dependency>
   * 	<groupId>com.baomidou</groupId>
   * 	<artifactId>mybatis-plus</artifactId>
   * 	<version>3.4.2</version>
   * </dependency>
	* 引入 MyBatis-Plus 之后请不要再次引入 MyBatis 以及 MyBatis-Spring，以避免因版本差异导致的问题
	* 快照 SNAPSHOT 版本需要添加仓库，且版本号为快照版本
	* <repository>
	* 		<id>snapshots</id>
	* 		<url>https://oss.sonatype.org/content/repositories/snapshots/</url>
	* </repository>
	*  */
}
