package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CyberKaka
 * @since 2021-04-19
 */
@RestController
@RequestMapping("//qrcodedb")
public class QrcodedbController {

    private final QrcodedbService qrcodedbService;

    @Autowired
    private QrcodedbController(QrcodedbService qrcodedbService){
        this.qrcodedbService = qrcodedbService;
    }

    @GetMapping("/list")
    public List<Qrcodedb> list() {
        return this.qrcodedbService.list();
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        return this.qrcodedbService.removeById(id);
    }

    @GetMapping("/find/{id}")
    public Qrcodedb find(@PathVariable("id") Integer id){
        return this.qrcodedbService.getById(id);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Qrcodedb qrcodedb){
        return this.qrcodedbService.updateById(qrcodedb);
    }

    // 尝试通过传过来的 title 标签进行模糊查询,返回查询到的一条数据
    @GetMapping("/search/{title}")
    public Qrcodedb findTitle(@PathVariable("title") String title){
        QueryWrapper<Qrcodedb> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",title);
        return qrcodedbService.getOne(queryWrapper);
    }
}

