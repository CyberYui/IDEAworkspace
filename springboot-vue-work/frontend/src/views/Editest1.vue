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
                <mavon-editor :toolbars="markdownOption" v-model="handbook"/>
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
        });
    },
    mounted() {

    },
    methods: {
        // 本文件仅仅用于测试,最终项目会修改成没有注释内容的项目,现在就尽量多注释了

        //新增图片的图片上传 input 响应的函数
        uploadImg(imageGet) {
            let _this = this;
            // 首先获取到 input 内容框返回的数据
            const target = imageGet.target;
            // 提取出上传的内容(只有一条)
            let file = target.files[0];
            // 创建一个空的 FormData 对象,用来存储文件相关信息
            let formData = new FormData();
            // 通过 append 向 FormData 对象添加数据
            formData.append("file", file);
            // 提取出其中的文件名,添加给 formData 存储文件名
            formData.append("fileName", file.name);
            // 存储文件类型
            formData.append("type", file.type);
            // -------配置 formData 对象完成---------
            // 创建一个类型数组,用来判断上传的文件是否满足要求
            const fileTypes = ['image/jpeg', 'image/png', 'image/pjpeg'];

            // 定义判断类型函数,它的参数为一个 FormData
            function validFileType(formData) {
                return fileTypes.includes(formData.get('type'));
            }

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
                    let imgPath = srcUrl.split("file:/", 3);
                    // 取出第二项,也就是拆解出来的绝对路径
                    let imgAbPath = imgPath[1];
                    // 再拆解一下,把图片的名称给拆出来
                    let imgArr = srcUrl.split("/", 8);
                    // 根据绝对路径进行修改此内容,具体的文件名在数组的第 7 项
                    // 对第 7 项内容再进行修改,提取出不带文件格式的文件名
                    let imgName = imgArr[7].split(".", 2);
                    let imgRealName = imgName[0];
                    let imgTypeName = imgName[1];
                    // 拼接 URL 使其适应 markdown 编辑器的格式
                    // md 格式 : ![图片alt](图片链接 "图片title")
                    let imgMdPath = "!" + "[pic" + imgRealName + "]" + "(" + imgAbPath + " \"" + imgRealName + "." + imgTypeName + "\"" + ")";
                    console.log(imgMdPath);
                    // ![图片alt]
                    // ![pic13-49-2021-11-49-437409]
                    // 图片链接
                    // (F:/IDEAworkspace/springboot-vue-work/backend/uploadFiles/uploadImgs/13-49-2021-11-49-437409.jpg
                    // 图片title
                    // "13-49-2021-11-49-437409.jpg")
                    // URL 拼接完毕,将其输入到 markdown 编辑器中

                    // 添加这个地址到 markdown 编辑器

                }).catch(error => {
                    console.error(error);
                })
            } else {
                // 上传格式有误的话会提示错误
                alert(formData.get('name') + '的格式为 : ' + formData.get('type') + ',这不是一个合法的图片格式');
            }
        },

        // ---------uploadImg 上传图片函数结束---------end---------

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
.mavonEditor {
    background-color: white;
    width: auto;
    height: 300px;
}
</style>