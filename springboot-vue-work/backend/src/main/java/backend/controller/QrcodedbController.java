package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private QrcodedbController(QrcodedbService qrcodedbService) {
        this.qrcodedbService = qrcodedbService;
    }

    @GetMapping("/list")
    public List<Qrcodedb> list() {
        return this.qrcodedbService.list();
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return this.qrcodedbService.removeById(id);
    }

    @GetMapping("/find/{id}")
    public Qrcodedb find(@PathVariable("id") Integer id) {
        return this.qrcodedbService.getById(id);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Qrcodedb qrcodedb) {
        return this.qrcodedbService.updateById(qrcodedb);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody Qrcodedb qrcodedb) {
        return this.qrcodedbService.save(qrcodedb);
    }

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file")MultipartFile file){
        // 由于前端发送的是 FormData 类型内容,所以这里用
        // RequestParam 注解获取内容
        if (!file.isEmpty()){
            // 传输的内容名-->即 file
            System.out.println(file.getName());
            // 文件大小
            System.out.println(file.getSize());
            // 文件名
            System.out.println(file.getOriginalFilename());
            // 提示文件获取成功
            System.out.println("Receive file well !");
            // 开始对获取到的文件进行操作
            // 基本思路 : 将获取到的文件先存储给一个对象,之后对这个对象进行操作
            // 1. 给对象重命名,通过获取时间等内容命名新名称
            // 2. 将对象复制到最终要保存图片的路径中,然后以新名称命名文件
            // 3. 返回最终的图片路径
            // 最终要控制的就是 java 的 file 类使用,这个还得再看
            // 本项目只是个单表的小项目,要想使用企业类项目还得再找
            return true;
        }
        System.out.println("Can't receive the file.");
        return false;
    }

    // 尝试通过传过来的 title 标签进行模糊查询,返回查询到的一条数据
    // 这个函数已经被废弃了,查询的效果直接从前端页面进行实现
    //@GetMapping("/search/{title}")
    //public Qrcodedb findTitle(@PathVariable("title") String title){
    //    QueryWrapper<Qrcodedb> queryWrapper = new QueryWrapper<>();
    //    queryWrapper.like("title",title);
    //    return qrcodedbService.getOne(queryWrapper);
    //}

    // 下面这个函数可行性并没有得到验证,仅仅放在这里看一看
    //@RequestMapping(value = "/upload",method = RequestMethod.POST)
    //public Map<String,Object> multiImport(@RequestParam("File")MultipartFile[] uploadFile){
    //    Map<String,Object> result = new HashMap<String,Object>(16);
    //    System.out.println(uploadFile.length);
    //    for (MultipartFile multipartFile:uploadFile){
    //        System.out.println("文件 : "+multipartFile.getOriginalFilename());
    //    }
    //    return result;
    //}
}

