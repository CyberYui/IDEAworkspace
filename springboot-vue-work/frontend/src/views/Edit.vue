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

    var contentAll = '移动到此处单击后进行内容修改,右侧为浏览框';

    export default {
        name: "Edit",
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
            let id = this.$router.currentRoute.query.id;
            let _this = this;
            axios.get('http://localhost:8181/qrcodedb/find/' + id).then(function (response) {
                _this.article = response.data;
            });
        },
        mounted() {
            // 重新获取一次,并给全局变量配置内容
            axios.get('http://localhost:8181/qrcodedb/find/1').then(function (response) {
                _this.article = response.data;
                contentAll = response.data.content;
                console.log('mounted: ' + contentAll);
            });
            // 开始渲染 markdown 编辑器
            let _this = this;
            // 当网页解析完毕后生成富文本编辑器
            const editor = new Editor({
                el: document.querySelector('#editor'),
                previewStyle: 'vertical',
                height: "500px",
                initialValue:contentAll,
                viewer: true,
                events:{
                    focus: function () {
                        editor.setMarkdown(contentAll);
                    },
                    change: function () {
                        // 配置响应事件,让输入的内容动态存储给 article 的 content
                        _this.article.content = editor.getMarkdown();
                    }
                }
            });
        },
        methods: {
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