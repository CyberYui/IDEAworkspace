package backend.controller;

import backend.entity.Qrcodedb;
import backend.service.QrcodedbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    /**
     * @description:
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:51
     * @param:
     * @return: java.util.List<backend.entity.Qrcodedb>
     */
    @GetMapping("/list")
    public List<Qrcodedb> list() {
        return this.qrcodedbService.list();
    }

    /**
     * @description:
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:52
     * @param: id
     * @return: boolean
     */
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return this.qrcodedbService.removeById(id);
    }

    /**
     * @description:
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:52
     * @param: id
     * @return: backend.entity.Qrcodedb
     */
    @GetMapping("/find/{id}")
    public Qrcodedb find(@PathVariable("id") Integer id) {
        return this.qrcodedbService.getById(id);
    }

    /**
     * @description:
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:52
     * @param: qrcodedb
     * @return: boolean
     */
    @PutMapping("/update")
    public boolean update(@RequestBody Qrcodedb qrcodedb) {
        return this.qrcodedbService.updateById(qrcodedb);
    }
    
    /**
     * @description: 表单的添加操作实现
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:52
     * @param: qrcodedb
     * @return: boolean
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Qrcodedb qrcodedb) {
        return this.qrcodedbService.save(qrcodedb);
    }

    /**
     * @description: 上传图片的后端实现
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/13 8:52
     * @param: file
     * @return: 返回图片的 URL
     */
    @PostMapping("/uploadImg")
    public String uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        // 基本思路 : 将获取到的文件先存储给一个对象,之后对这个对象进行操作
        // 1. 给对象重命名,通过获取时间等内容命名新名称
        // 2. 将对象复制到最终要保存图片的路径中,然后以新名称命名文件
        // 3. 返回最终的图片路径
        // 现阶段的问题:
        // 1. npm run serve 是跑在一个虚拟环境下的,无法实现相对路径的取图
        // 2. 要想跑在服务器上,就要在这边的低层代码里确定好服务器的存图路径
        // 解决方案:
        // 1. 了解服务器路径,然后直接在这边写死,然后部署进行测试和修改
        //
        // 本项目只是个单表的小项目,要想使用企业类项目还得再找
        // 对于视频的思路:
        // 1. 限定视频的上传大小
        // 2. 通过断点续传来控制视频的上传(选做)
        // 断点续传的网址:https://www.bilibili.com/video/BV16i4y1A7EN?p=4&spm_id_from=pageDriver
        // 理应也有个视频的上传路径
        // String vidUploadPath = "\\resources\\videoDir";

        // 由于前端发送的是 FormData 类型内容,所以这里用
        // RequestParam 注解获取内容
        if (!file.isEmpty()) {
            // 文件大小
            // System.out.println(file.getSize());
            // 提示文件获取成功
            System.out.println("Receive file well !");
            // 开始对获取到的文件进行操作
            // 注意的是这里获取到的是 MultipartFile 对象,需要先转为 File 对象进行操作
            // 首先创建一个 file 对象,用来当最终操作的对象
            File f = null;
            // 输出文件的新 name,即上传传输过来的文件名
            System.out.println("getName : " + file.getName());
            // 输出源文件的名称,即上传之前文件叫啥
            System.out.println("originalName : " + file.getOriginalFilename());
            // 切分获取到的文件源名,拆解出格式
            String srcFileName = file.getOriginalFilename().toString();
            String[] srcFileNameArr = srcFileName.split("\\.");
            // 输出获取到的文件名称和格式名
            System.out.println(srcFileNameArr[0]);
            System.out.println(srcFileNameArr[1]);
            // 数组的第二项为格式
            String fileFormat = srcFileNameArr[1];

            // 定义输出路径,拼凑出适应当前系统的路径
            // 为了防止浏览器阻止访问本地路径,直接放到前端目录下,使用相对路径保存
            String imgUploadPath = "..\\frontend\\resources\\uploadFiles\\uploadImgs\\";

            // 创建随机日期
            Date localDate = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy-hh-mm-ss");
            String createFdate =dateFormatter.format(localDate);

            // 创建随机数
            Random ran1 = new Random();
            // 生成 0~10000 中的随机数
            int numRan = ran1.nextInt(10000);
            String numRanS = String.valueOf(numRan);

            // 开始创建文件,确保文件名不是空的
            // 这里修改了代码,实现将文件复制到可选的路径
            // 文件的正确性确定了,将这个文件保存到相应的路径中,这个文件不会被删除
            File f1 = new File(imgUploadPath+createFdate+numRanS+"."+fileFormat);
            // 写入文件
            // 创建用于计算的循环数
            int i;
            try (
                    InputStream in1 = file.getInputStream();
                    OutputStream os1 = new FileOutputStream(f1)) {
                // 得到文件流.以文件流的方式输出到新文件
                // 给一个缓存空间值,给予文件用于传输
                byte[] buffer = new byte[4096];
                while ((i = in1.read(buffer, 0, 4096)) != -1) {
                    os1.write(buffer, 0, i);
                }
                // 读取文件第一行
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f1));
                // 输出一下,这里可能是乱码,乱码就代表读取到内容了
                // System.out.println(bufferedReader.readLine());
                // 关闭该流并释放与之关联的所有资源
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 输出 file 的URI
            System.out.println(f.toURI().toString());
            // 输出 file 的URL
            System.out.println(f.toURI().toURL().toString());
            // 输出文件的绝对路径,这里放断点的话就能看到在项目根目录下的文件了
            System.out.println(f.getAbsolutePath());
            // 操作完文件之后 删除在根目录下生成的文件
            File file1 = new File(f.toURI());
            if (file1.delete()) {
                System.out.println("Delete complete!");
            } else {
                System.out.println("Delete failed");
            }

            // 在这里获取一下新生成的文件的路径,看看长啥样
            String realPath = f1.getPath().toString();
            System.out.println("realPath = "+realPath);
            // 设置将文件放在固定路径,并重新命名
            System.out.println("realURL = "+f1.toURI().toURL().toString());
            return f1.toURI().toURL().toString();
        }
        System.out.println("Can't receive the file.");
        return "false";
    }

    /**
     * @description: 上传视频的后端实现
     * @className: QrcodedbController
     * @author: Cyber
     * @date: 2021/5/18 17:44
     * @param: file1
     * @return: 返回视频的 URL
     */
    @PostMapping("/uploadVideo")
    public String uploadVideo(@RequestParam("file") MultipartFile file1) throws IOException{
        System.out.println("这是上传视频的后端实现");
        return "testing complete";
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

