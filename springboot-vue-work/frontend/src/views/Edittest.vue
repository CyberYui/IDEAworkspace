<template>
    <!-- 这个文件现已废弃 -->
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
            <div id="editor"></div>
            <!-- 创建添加图片按钮的响应框,即 html 自带的图片上传框
             只有当点击上传按钮之后才会显示图片上传框
             @change 表示其响应的函数为 uploadImg
             ref=files 标志着可以通过 $ref.files 获取到上传的文件名
             accept=image/* 表示接受所有格式的图像文件
             假如只支持某些格式的图片,可以这样写
             accept=image/png, image/jpeg -->
            <input
                type="file"
                ref="files"
                style="opacity:0"
                @change="uploadImg"
                accept="image/png, image/jpeg">

            <!-- 创建添加视频按钮的响应框,即 html 自带的 input 框
             只有当点击上传按钮之后才会显示图片上传框
             @change 表示其响应的函数为 uploadVideo
             ref=filesVideo 标志着可以通过 $ref.filesVideo 获取到上传的文件名
             accept=video/* 表示接受所有格式的视频文件
             假如只支持某些格式的视频,可以这样写
             accept=video/mp4 -->
            <input
                type="file"
                ref="filesVideo"
                style="opacity: 0"
                @change="uploadVideo"
                accept="video/mp4">
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
import Editor from '@toast-ui/editor';
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';

var contentAll = '移动到此处单击后进行内容修改';

export default {
    name: "Edittest",
    data() {
        return {
            // 文章内容对象
            article: {
                id: '',
                title: '',
                major: '',
                content: '',
                image: '',
                videos: '',
                address: ''
            },
            // 给 content 设定一个初始值
            content: "这里是文本编辑器的初始内容",
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
        // console.log(this.$router.currentRoute.query.id)
        // let id = this.$router.currentRoute.query.id;
        let _this = this;
        axios.get('http://localhost:8181/qrcodedb/find/5').then(function (response) {
            _this.article = response.data;
        });
    },
    mounted() {
        this.initEditor();
    },
    methods: {
      // 本文件仅仅用于测试,最终项目会修改成没有注释内容的项目,现在就尽量多注释了

        // 编辑器初始化函数
        initEditor() {
            // 重新获取一次,并给全局变量配置内容
            axios.get('http://localhost:8181/qrcodedb/find/5').then(function (response) {
                _this.article = response.data;
                contentAll = response.data.content;
                // console.log('mounted: ' + contentAll);
            });
            // 开始渲染 markdown 编辑器
            let _this = this;
            // 当网页解析完毕后生成富文本编辑器
            const editor = new Editor({
                el: document.querySelector('#editor'),
                previewStyle: 'vertical',
                height: "500px",
                // 为了自定义工具栏按钮,尝试修改其内容
                toolbarItems: [
                    'heading',
                    'bold',
                    'italic',
                    'strike',
                    'divider',
                    'hr',
                    'quote',
                    'divider',
                    'ul',
                    'ol',
                    'task',
                    'indent',
                    'outdent',
                    'divider',
                    'table',
                    // 注释掉 image,因为要用自定义的图片上传按钮
                    // 'image',
                    // 自定义上传视频按钮
                    // markdown 里面上传视频用 <iframe height=498 width=510 src="视频地址"></iframe> 实现
                    'video',
                    'link',
                    'divider',
                    'code',
                    'codeblock',
                    'divider'
                ],
                // 直接在初始化时使用内容
                initialValue: contentAll,
                viewer: true,
                events: {
                    // 聚焦时把全局变量的内容填充给编辑器
                    focus: function () {
                        editor.setMarkdown(contentAll);
                    },
                    // 配置响应事件,让输入的内容动态存储给 article 的 content
                    change: function () {
                        _this.article.content = editor.getMarkdown();
                    },
                    // 当不聚焦时保存一次编辑好的内容给全局变量
                    blur: function () {
                        contentAll = editor.getMarkdown();
                    },
                }
            });

            // 使用函数创建新的图片上传按钮
            let toolbar = editor.getUI().getToolbar();
            // 当点击上传按钮之后,打开的内容为 ref=files 的组件
            let fileDom = this.$refs.files;
            // 点击视频上传按钮后,打开的内容为 ref=filesVideo 的组件
            let fileDom1 = this.$refs.filesVideo;

            editor.eventManager.addEventType('uploadImageButton');
            editor.eventManager.listen('uploadImageButton', function () {
                fileDom.click();
                // this.getqiniuToken();
            });

            editor.eventManager.addEventType('uploadVideoButton');
            editor.eventManager.listen('uploadVideoButton', function () {
                fileDom1.click();
                // this.getqiniuToken();
            });

            // 添加按钮到工具栏上
            // 图片上传按钮
            toolbar.insertItem(15, {
                type: 'button',
                options: {
                    className: 'tui-image',
                    event: 'uploadImageButton',
                    tooltip: 'upload image,only png & jpg available',
                    style: 'color:black;'
                }
            });
            // 视频上传按钮
            toolbar.insertItem(16, {
                type: 'button',
                options: {
                    className: 'el-icon-video-camera-solid',
                    event: 'uploadVideoButton',
                    tooltip: 'upload video,only mp4 available',
                    style: 'background:none;color:#555555;'
                }
            });
        },
        //新增图片的图片上传 input 响应的函数
        uploadImg(imageGet) {
            let _this = this;
            // 首先获取到 input 内容框返回的数据
            const target = imageGet.target;
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
            const fileTypes = ['image/jpeg', 'image/png', 'image/pjpeg'];

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
                    url: "http://localhost:8181/qrcodedb/uploadImg",
                    method: "post",
                    // data 表示上传的对象,这里就是上传之前创建的 FormData 对象
                    data: formData,
                    config: config,
                    // 不需要缓存
                    // cache: false,
                    // 不需要进行数据转换
                    processData: false,
                }).then(function (data) {
                    // data 是后端 return 的对象,应该是个字符串
                    // 强制通过 json 形式获取到数据内容
                    console.log('response.data : ' + JSON.stringify(data));
                    /* data 的 JSON 内容很多,需要的是这一项内容
                    * "data":"file:/F:/IDEAworkspace/springboot-vue-work/backend/uploadFiles/uploadImgs/13-36-2021-09-36-558827.jpg",
                    * 后台返回的还是一个 formData 数据,这个数据说白了就是一个 JSON 格式的 map 而已
                    */
                    //上传成功开始拆解地址信息
                    let srcUrl = data.data;
                    // 先输出一次看看有没有获取到上述的原始路径形式
                    console.log(srcUrl);
                    // 获取成功,开始执行拆解操作
                    let imgPath = srcUrl.split("file:/",3);
                    // 取出第二项,也就是拆解出来的绝对路径
                    let imgAbPath = imgPath[1];
                    // 再拆解一下,把图片的名称给拆出来
                    let imgArr = srcUrl.split("/",8);
                    // 根据绝对路径进行修改此内容,具体的文件名在数组的第 7 项
                    // 对第 7 项内容再进行修改,提取出不带文件格式的文件名
                    let imgName = imgArr[7].split(".",2);
                    let imgRealName = imgName[0];
                    let imgTypeName = imgName[1];
                    // 拼接 URL 使其适应 markdown 编辑器的格式
                    // md 格式 : ![图片alt](图片链接 "图片title")
                    let imgMdPath = "!"+"[pic"+imgRealName+"]"+"("+imgAbPath+" \""+imgRealName+"."+imgTypeName+"\""+")";
                    console.log(imgMdPath);
                    // ![图片alt]
                    // ![pic13-49-2021-11-49-437409]
                    // 图片链接
                    // (F:/IDEAworkspace/springboot-vue-work/backend/uploadFiles/uploadImgs/13-49-2021-11-49-437409.jpg
                    // 图片title
                    // "13-49-2021-11-49-437409.jpg")
                    // URL 拼接完毕,将其输入到 markdown 编辑器中

                    // 添加这个地址到 markdown 编辑器
                    // 首先获取编辑器的当前内容
                    let content = _this.editor.getValue();
                    console.log(content);
                }).catch(error => {
                    console.error(error);
                })
            } else {
                // 上传格式有误的话会提示错误
                alert(formData.get('name') + '的格式为 : ' + formData.get('type') + ',这不是一个合法的图片格式');
            }


            // 原始代码
            // formData.append("token", this.qnToken);
            // this.$axios({
            //     method: "post",
            //     url: "https://upload.qiniup.com",
            //     data: formData
            // }).then(res => {
            //     //上传成功地址拼接
            //     let imgUrl = "https://qiniu.com/" + res.data.key;
            //     this.addImgToMd(imgUrl)
            // }).catch(error => {
            //     console.error(error.response);
            // });
        },
        // ---------uploadImg 上传图片函数结束---------end---------
        //添加图片到markdown
        // 控制台提示没有 getCodeMirror() 和 getCurrentModeEditor() 这些函数
        addImgToMd(data) {
            // 传入的就是图片的地址 url
            // API---getCodeMirror() ==> Get current editor mode name
            // 首先获取编辑器的当前内容
            // let content = this.editor.getMarkdown();
            // console.log(content);

            // let editor = this.editor;
            // console.log(editor);
            // API--getCurrentModeEditor() ==> Get current editor mode name
            // 获取编辑器的格式,应该是 markdown
            // let editorHtml = this.editor.getCurrentModeEditor();
            // console.log(editorHtml);
            // let isMarkdownMode = this.editor.isMarkdownMode();
            // if (isMarkdownMode) {
            //     editor.replaceSelection(`![img](${data})`);
            // } else {
            //     let range = editorHtml.getRange();
            //     let img = document.createElement('img');
            //     img.src = `${data}`;
            //     img.alt = "img";
            //     range.insertNode(img);
            // }
        },
        getqiniuToken() {
            // this.$axios({
            //     method: "get",
            //     url: getQiniuToken
            // })
            //     .then(res => {
            //         this.qnToken = res.data.upToken;
            //     })
            //     .catch(error => {
            //         console.error(error.response);
            //     });
        },
        //----------------新增图片

        // 新增视频的视频上传 input 响应的函数
        uploadVideo(videoGet){
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
                    let videoPath = srcUrl.split("file:/",3);
                    // 取出第二项,也就是拆解出来的绝对路径
                    let videoAbPath = videoPath[1];
                    // 输出一下,看看是否正确
                    console.log('videoAbPath = '+videoAbPath);
                    // F:/IDEAworkspace/springboot-vue-work/backend/../frontend/resources/uploadFiles/uploadVideos/19-55-2021-10-55-214047.mp4
                    // 再拆解一下,把图片的名称给拆出来
                    let videoArr = videoAbPath.split("/",10);
                    // 根据绝对路径进行修改此内容,具体的文件名在数组的第 9 项
                    // 对第9 项内容再进行修改,提取出不带文件格式的文件名
                    let videoName = videoArr[9].split(".",2);
                    let videoRealName = videoName[0];
                    // videoRealName = 19-58-2021-10-58-017544
                    let videoTypeName = videoName[1];
                    // videoTypeName = mp4

                    // 分解正确,开始填入markdown 编辑器
                    // 拼接 URL 使其适应 markdown 编辑器的格式
                    // md 格式 : <iframe height=498 width=510 src="视频地址" frameborder=0 allowfullscreen></iframe>
                    let videoMdPath =
                        "<iframe height=498 width=510 src="+"../../resources/uploadFiles/uploadImgs/"+videoRealName+"."+videoTypeName+" frameborder=0 allowfullscreen></iframe>";
                    console.log(videoMdPath);
                    // URL 拼接完毕,将其输入到 markdown 编辑器中

                    // 添加这个地址到 markdown 编辑器
                    // 首先获取编辑器的当前内容
                    let content = _this.editor.getValue();
                    console.log(content);
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

</style>