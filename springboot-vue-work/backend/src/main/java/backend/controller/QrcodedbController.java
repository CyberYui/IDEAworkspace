package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
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
    // 模仿通过主题删除内容
}

