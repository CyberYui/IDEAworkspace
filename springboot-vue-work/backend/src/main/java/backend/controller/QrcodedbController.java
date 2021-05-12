package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

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
    public boolean upload(@RequestParam("file")MultipartFile file) throws IOException {
        // 由于前端发送的是 FormData 类型内容,所以这里用
        // RequestParam 注解获取内容
        if (!file.isEmpty()){
            // 文件大小
            System.out.println(file.getSize());
            // 提示文件获取成功
            System.out.println("Receive file well !");
            // 开始对获取到的文件进行操作
            // 注意的是这里获取到的是 MultipartFile 对象,需要先转为 File 对象进行操作
            // 首先创建一个 file 对象,用来当最终操作的对象
            File f = null;
            // 输出文件的新 name,即上传传输过来的文件名
            System.out.println("getName : "+file.getName());
            // 输出源文件的名称,即上传之前文件叫啥
            System.out.println("originalName : "+file.getOriginalFilename());
            // 开始创建文件,确保文件名不是空的
            f = new File(Objects.requireNonNull(file.getOriginalFilename()));
            try(InputStream in = file.getInputStream(); OutputStream os=new FileOutputStream(f)){
                // 得到文件流.以文件流的方式输出到新文件
                // 可以使用byte[] ss = file.getBytes();代替while循环
                byte[] buffer = new byte[4096];
                byte[] ss = file.getBytes();
            }

            // 基本思路 : 将获取到的文件先存储给一个对象,之后对这个对象进行操作
            // 1. 给对象重命名,通过获取时间等内容命名新名称
            // 2. 将对象复制到最终要保存图片的路径中,然后以新名称命名文件
            // 3. 返回最终的图片路径
            // 最终要控制的就是 java 的 file 类使用,这个还得再看
            // 本项目只是个单表的小项目,要想使用企业类项目还得再找

            // 设置将文件放在固定路径,并重新命名
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

