package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
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
 * @author CyberKaka
 * @since 2021-04-19
 */
@RestController
@RequestMapping("//qrcodedb")
public class QrcodedbController {

    /*
     * 视频里用的是 field 注入,这里我改为了构造器注入
     * 原代码:
     * 艾特Autowired
     * private QrcodedbService qrcodedbService
     *
     * 艾特GetMapping("/list")
     * public List<Qrcodedb> list(){
     *     return this.qrcodedbService.list()
     * }
     *
     * 当有一个依赖有多个实现的使用时最好使用上述的 field 注入
     * */

    /**
     * 使用 final 关键字,保证依赖不可变
     * */
    private final QrcodedbService qrcodedbService;
    @Autowired
    public QrcodedbController(QrcodedbService qrcodedbService){
        this.qrcodedbService = qrcodedbService;
    }

    @GetMapping("/list")
    public List<Qrcodedb> list(){
        return this.qrcodedbService.list();
    }
}

