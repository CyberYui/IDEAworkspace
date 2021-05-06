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
            <div id="editor"></div>
            <!-- 创建添加图片按钮的响应框,即 html 自带的图片上传框
             只有当点击上传按钮之后才会显示图片上传框
             @change 表示其响应的函数为 uploadFile
             ref=files 标志着可以通过 $ref.files 获取到上传的文件名
             accept=image/* 表示接受所有格式的图像文件
             假如只支持某些格式的图片,可以这样写
             accept=image/png, image/jpeg -->
            <input
                type="file"
                ref="files"
                style="opacity:0"
                @change="uploadFile"
                accept="image/png, image/jpeg">
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

            editor.eventManager.addEventType('uploadImageButton');
            editor.eventManager.listen('uploadImageButton', function () {
                fileDom.click();
                // this.getqiniuToken();
            });

            // 添加按钮到工具栏上
            toolbar.insertItem(15, {
                type: 'button',
                options: {
                    className: 'tui-image',
                    event: 'uploadImageButton',
                    tooltip: 'upload image,only png & jpg available',
                    style: 'color:black;'
                }
            });
        },
        //新增图片的图片上传 input 响应的函数
        uploadFile(imageGet) {
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
                    url: "http://localhost:8181/qrcodedb/upload",
                    method: "post",
                    // data 表示上传的对象,这里就是上传之前创建的 FormData 对象
                    data: formData,
                    config: config,
                    // 不需要缓存
                    // cache: false,
                    // 不需要进行数据转换
                    processData: false,
                }).then(function (data) {
                    // data 是后端 return 的对象
                    console.log('response.data : ' + data.data);
                    console.log('response.data.key : ' + data.data.key);
                    //上传成功开始拼接地址
                    // let imgUrl = "" + response.data.key;
                    // 添加这个地址到 markdown 编辑器
                    // _this.addImgToMd(imgUrl);
                }).catch(error => {
                    console.error(error);
                })
            } else {
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
        // ---------uploadFile 上传图片函数结束---------end---------

        //添加图片到markdown
        addImgToMd(data) {
            // 传入的就是图片的地址 url
            // API---getCodeMirror() ==> Get current editor mode name
            // 首先获取编辑器的名字
            let editor = this.editor.getCodeMirror();
            console.log(editor);
            // API--getCurrentModeEditor() ==> Get current editor mode name
            // 获取编辑器的格式,应该是 markdown
            let editorHtml = this.editor.getCurrentModeEditor();
            console.log(editorHtml);
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