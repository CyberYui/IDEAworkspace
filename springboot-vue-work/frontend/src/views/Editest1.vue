<template>
    <!-- model='article' 表示整个表单和下面的 article 对象绑定-->
    <el-form
        label-position="right"
        ref="form"
        :rules="rules"
        :model="article"
        label-width="100px"
        style="width: 1000px">
        <!-- 使用 v-model 分别绑定数据 -->
        <el-form-item label="序号" prop="id" style="display: none;">
            <el-input v-model="article.id" readonly></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="title">
            <el-input v-model="article.title"></el-input>
        </el-form-item>
        <el-form-item label="所属主题" prop="major">
            <el-input v-model="article.major"></el-input>
        </el-form-item>
        <el-form-item label="文本内容" prop="content">
            <div class="mavonEditor">
                <mavon-editor
                    ref=md
                    @imgAdd="imgAdd"
                    :toolbars="markdownOption"
                    v-model="handbook"
                    style="height: 500px"
                >
                    <template slot="left-toolbar-before">
                        <button
                            type="button"
                            class="op-icon el-icon-video-camera-solid"
                            style="border:none;background:none;color:#555555;"
                            aria-hidden="true"
                            @click="getFile"
                            :title="`上传视频`">
                        </button>
                    </template>
                </mavon-editor>
                <input
                    class="op-icon el-icon-video-camera-solid"
                    type="file"
                    ref="filesVideo"
                    style="display:none;background:none;color:#555555;"
                    @change="uploadVideo"
                    accept="video/mp4">
            </div>
        </el-form-item>
        <el-form-item label="单图链接" prop="image">
            <el-input v-model="article.image"></el-input>
        </el-form-item>
        <el-form-item label="视频链接" prop="videos">
            <el-input v-model="article.videos"></el-input>
        </el-form-item>
        <div style="text-align: center">
            <el-button
                round
                type="success"
                @click="onSubmit('form')"
            >确认修改
            </el-button>
            <el-button
                round
                type="info"
                @click="cancelSubmit"
            >取消修改
            </el-button>
        </div>
    </el-form>
</template>

<script>

export default {
    name: "Editest1",
    data() {
        return {
            // 文章内容对象
            markdownOption: {
                bold: true, // 粗体
                italic: true, // 斜体
                header: true, // 标题
                underline: true, // 下划线
                strikethrough: true, // 中划线
                mark: true, // 标记
                superscript: true, // 上角标
                subscript: true, // 下角标
                quote: true, // 引用
                ol: true, // 有序列表
                ul: true, // 无序列表
                link: true, // 链接
                imagelink: true, // 图片链接
                code: true, // code
                table: true, // 表格
                fullscreen: true, // 全屏编辑
                readmodel: true, // 沉浸式阅读
                htmlcode: true, // 展示html源码
                help: true, // 帮助
                /* 1.3.5 */
                undo: true, // 上一步
                redo: true, // 下一步
                trash: true, // 清空
                save: true, // 保存（触发events中的save事件）
                /* 1.4.2 */
                navigation: true, // 导航目录
                /* 2.1.8 */
                alignleft: true, // 左对齐
                aligncenter: true, // 居中
                alignright: true, // 右对齐
                /* 2.2.1 */
                subfield: true, // 单双栏模式
                preview: false, // 预览
                shortCut: false, // 禁用快捷键
            },
            article: {
                id: '',
                title: '',
                major: '',
                content: '',
                image: '',
                videos: '',
                address: ''
            },
            handbook: "#### how to use mavonEditor",
            rules: {
                title: [
                    {required: true, message: '请输入标题', trigger: 'blur'}
                ],
                major: [
                    {required: true, message: '请输入主题', trigger: 'blur'}
                ],
                image: [
                    {required: false, message: '请编辑单图链接', trigger: 'blur'}
                ],
                videos: [
                    {required: true, message: '请添加视频链接', trigger: 'blur'}
                ]
            },
        }
    },
    created() {
        let _this = this;
        axios.get('http://localhost:8181/qrcodedb/find/5').then(function (response) {
            _this.article = response.data;
            _this.handbook = response.data.content;
        });
    },
    mounted() {

    },
    methods: {
        getFile() {
            let a = this.$refs.filesVideo;
            a.click();
        },
        // 绑定@imgAdd event
        imgAdd(pos, $file) {
            // 第一步.将图片上传到服务器.
            let formData = new FormData();
            formData.append('file', $file);
            let config = {
                headers: {
                    // 设定上传内容类型
                    'Content-type': 'multipart/form-data',
                },
                timeout: 60000,
            };
            axios({
                url: 'http://localhost:8181/qrcodedb/uploadImg',
                method: 'post',
                data: formData,
                config: config,
                processData: false,
            }).then((url) => {
                //上传成功开始拆解地址信息
                let srcUrl = url.data;
                // 先输出一次看看有没有获取到上述的原始路径形式
                console.log('srcUrl = ' + srcUrl);
                // 获取成功,开始执行拆解操作
                // 拆解的关键字为 file:/
                let imgPath = srcUrl.split("file:/", 3);
                // 取出第二项,也就是拆解出来的绝对路径
                let imgAbPath = imgPath[1];
                // 再拆解一下,把图片的名称给拆出来
                // F:/IDEAworkspace/springboot-vue-work/backend/../frontend/resources/uploadFiles/uploadImgs/18-05-2021-02-05-122777.jpg
                let imgArr = srcUrl.split("/", 11);
                console.log('imgArr' + imgArr);
                // 根据绝对路径进行修改此内容,具体的文件名在数组的第 11 项
                // 对第 11 项内容再进行修改,提取出不带文件格式的文件名
                // 数组从 0 开始数,第 11 项下标为 10
                let imgName = imgArr[10].split(".", 2);
                let imgRealName = imgName[0];
                // 输出一次不带格式的文件名
                console.log('imgRealName = ' + imgRealName);
                // 保存一下文件的格式名
                let imgTypeName = imgName[1];
                // 拼接 URL 使其适应 markdown 编辑器的格式
                // md 格式 : ![图片alt](图片链接 "图片title")
                // 不论图片如何保存,这里都使用相对路径在 markdown 里面显示
                // 首先绝对路径为 F:\IDEAworkspace\springboot-vue-work\frontend\resources\uploadFiles\uploadImgs 这一目录
                // 本 vue 文件与上述目录的相对路径为 ../../resources/uploadFiles/uploadImgs
                let imgMdPath = "../../resources/uploadFiles/uploadImgs/" + imgRealName + "." + imgTypeName;
                console.log(imgMdPath);
                // 把这个相对路径插入到 markdown 编辑器里面
                this.$refs.md.$img2Url(pos, imgMdPath);
            })
        },


        uploadVideo(videoGet) {
            let _this = this;
            // 首先获取到 input 内容框返回的数据
            const target = videoGet.target;
            // console.log('获取到的dom内容为'+target.files);

            // 循环输出文件列表,我就不信你一直都是 object FileList
            // for(let i = 0, j = target.files.length; i < j; i++)
            // {
            //     console.log(target.files[i]);
            // }

            // 一般只允许上传一张图片
            // 提取出上传的内容(只有一条)
            let file = target.files[0];
            // 创建一个空的 FormData 对象,用来存储文件相关信息
            let formData = new FormData();
            // API : formData.append(键名: name, 键值: value, 文件名: filename);
            // 通过 append 向 FormData 对象添加数据
            // 存储文件,主要操作的就是文件了
            formData.append("file", file);
            // 提取出其中的文件名,添加给 formData 存储文件名
            formData.append("fileName", file.name);
            // 存储文件类型
            formData.append("type", file.type);
            // -------配置 formData 对象完成---------
            // 输出一下文件名
            console.log('name is : ' + formData.getAll("fileName"));
            // 创建一个类型数组,用来判断上传的文件是否满足要求
            const fileTypes = ['video/mp4'];

            // 定义判断类型函数,它的参数为一个 FormData
            function validFileType(formData) {
                return fileTypes.includes(formData.get('type'));
            }

            // 判断文件的合法性(上传文件到服务器)
            // if(validFileType(formData)){
            //     // 图片正确,开始上传到服务器
            //     _this.$axios({
            //         method:"get",
            //         url:"",
            //         data:formData
            //     }).then(res=>{
            //         console.log('res.data.key : '+res.data.key);
            //         //上传成功开始拼接地址
            //         let imgUrl = "" + res.data.key;
            //         // 添加这个地址到 markdown 编辑器
            //         _this.addImgToMd(imgUrl);
            //     }).catch(error=>{
            //         // console.error(error.response);
            //     })
            // }else{
            //     alert(formData.get('name') + '的格式为 : ' +formData.get('type') + ',这不是一个合法的图片格式');
            // }

            // 判断文件的合法性(上传文件到本地路径)
            if (validFileType(formData)) {
                // 图片正确,开始上传到本地,添加请求内容类型
                let config = {
                    headers: {
                        // 设定上传内容类型
                        'Content-type': 'multipart/form-data',
                    },
                    timeout: 60000,
                }
                // 上传内容
                _this.$axios({
                    url: "http://localhost:8181/qrcodedb/uploadVideo",
                    method: "post",
                    // data 表示上传的对象,这里就是上传之前创建的 FormData 对象
                    data: formData,
                    config: config,
                    // 不需要缓存
                    // cache: false,
                    // 不需要进行数据转换
                    processData: false,
                }).then(function (data) {
                    // 等后端写完之后再来这里修改
                    console.log(data);
                    // data 是后端 return 的对象,应该是个字符串
                    // 强制通过 json 形式获取到数据内容
                    console.log('response.data : ' + JSON.stringify(data));
                    /* data 的 JSON 内容很多,需要的是这一项内容
                    * data: "file:/F:/IDEAworkspace/springboot-vue-work/backend…oadFiles/uploadVideos/19-40-2021-10-40-274915.mp4",
                    * 后台返回的还是一个 formData 数据,这个数据说白了就是一个 JSON 格式的 map 而已
                    */
                    //上传成功开始拆解地址信息
                    let srcUrl = data.data;
                    // 先输出一次看看有没有获取到上述的原始路径形式
                    console.log(srcUrl);
                    // 获取成功,开始执行拆解操作
                    let videoPath = srcUrl.split("file:/", 3);
                    // 取出第二项,也就是拆解出来的绝对路径
                    let videoAbPath = videoPath[1];
                    // 输出一下,看看是否正确
                    console.log('videoAbPath = ' + videoAbPath);
                    // F:/IDEAworkspace/springboot-vue-work/backend/../frontend/resources/uploadFiles/uploadVideos/19-55-2021-10-55-214047.mp4
                    // 再拆解一下,把图片的名称给拆出来
                    let videoArr = videoAbPath.split("/", 10);
                    // 根据绝对路径进行修改此内容,具体的文件名在数组的第 9 项
                    // 对第9 项内容再进行修改,提取出不带文件格式的文件名
                    let videoName = videoArr[9].split(".", 2);
                    let videoRealName = videoName[0];
                    // videoRealName = 19-58-2021-10-58-017544
                    let videoTypeName = videoName[1];
                    // videoTypeName = mp4

                    // 分解正确,开始填入markdown 编辑器
                    // 拼接 URL 使其适应 markdown 编辑器的格式
                    // md 格式 : <iframe height=498 width=510 src="视频地址" frameborder=0 allowfullscreen></iframe>
                    let videoMdPath =
                        "<iframe src=" + "../../resources/uploadFiles/uploadImgs/" + videoRealName + "." + videoTypeName + " scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\"></iframe>";
                    console.log(videoMdPath);
                    // URL 拼接完毕,将其输入到 markdown 编辑器中

                    // 添加这个地址到 markdown 编辑器
                    // 首先获取编辑器的当前内容
                    let content = _this.$refs.md.$data.d_value;
                    // 输出一下,看看是不是获取到了
                    console.log(content);
                    // 获取成功,修改内容
                    _this.$refs.md.$data.d_value = content+videoMdPath;
                    // 添加成功,结束
                }).catch(error => {
                    console.error(error);
                })
            } else {
                // 上传格式有误的话会提示错误
                alert(formData.get('name') + '的格式为 : ' + formData.get('type') + ',这不是一个合法的视频格式');
            }
        },

        // 提交修改按钮的响应函数
        onSubmit(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let _this = this;
                    this.article.content = this.$refs.md.d_value;
                    axios.put('http://localhost:8181/qrcodedb/update', this.article).then(function (response) {
                        if (response.data) {
                            _this.$alert('修改成功!', '修改数据', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    _this.$router.push('/table');
                                }
                            })
                        }
                    })
                }
            })
        },
        cancelSubmit() {
            this.$alert('确定要取消修改吗?', '取消修改', {
                confirmButtonText: '确定',
                type: 'error',
                callback: action => {
                    this.$message({
                        type: 'warning',
                        message: '已取消修改'
                    });
                    // 为了测试,先转到 table 页面
                    this.$router.push('/table');
                    // this.$router.push('/test');
                }
            });
        }
    }
}
</script>

<style scoped>
body {
    background-color: darkcyan;
}

.mavonEditor {
    background-color: white;
    margin: 0;
    width: 900px;
    height: 500px;
}
</style>