package com.southwind.controller;


import com.southwind.entity.Fruit;
import com.southwind.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

