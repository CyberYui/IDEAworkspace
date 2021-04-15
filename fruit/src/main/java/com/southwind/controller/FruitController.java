package com.southwind.controller;


import com.southwind.entity.Fruit;
import com.southwind.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-04-15
 */
@RestController
@RequestMapping("//fruit")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping("/list")
    public List<Fruit> list(){
        return this.fruitService.list();
    }

    // 编写删除接口,返回一个布尔值
    // DeleteMapping 中的 /delete/ 表示所有的删除都在 delete 这个 mapping 中进行
    // id 就很简单了,我们通过 id 进行删除操作, id 是我们找到内容的标记
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        return this.fruitService.removeById(id);
    }

    // 编写一个查找接口,也是通过 id 进行比对查找,只不过这个对象返回的直接是一个 Fruit 对象
    // 启动本项目,通过在浏览器地址栏输入 http://localhost:8181/fruit/find/4
    // 即可得到 id=4 的数据库内容
    @GetMapping("/find/{id}")
    public Fruit find(@PathVariable("id") Integer id){
        return this.fruitService.getById(id);
    }

    // 对于修改,我们不仅需要从数据库查找内容,而且需要给数据库的内容修改之后填写新内容
    // 所以编写一个更新接口,修改的话我们需要发送一个put请求
    // 前端表单填写完毕后回传过来的是一个 JSON 数据,所以这里我们需要进行 JSON 数据解析
    // 所以需要使用 @RequestBody 注解,内容会保存在 fruit 对象中
    @PutMapping("/update")
    public boolean update(@RequestBody Fruit fruit){
        // 函数还是使用布尔类型,是因为 updateById 函数的返回值为 布尔类型
        return this.fruitService.updateById(fruit);
    }

    // 对于添加,和修改基本差不多,甚至比修改要更加简单
    // 我们只需要把表单的内容填给数据库即可,甚至不需要先从数据库拿数据
    @PostMapping("/add")
    public boolean add(@RequestBody Fruit fruit){
        return this.fruitService.save(fruit);
    }
}

