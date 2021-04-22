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
        <el-form-item label="序号" prop="id">
            <el-input v-model="article.id" readonly></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="title">
            <el-input v-model="article.title"></el-input>
        </el-form-item>
        <el-form-item label="所属主题" prop="major">
            <el-input v-model="article.major"></el-input>
        </el-form-item>
        <!-- 测试时仅仅先使用下面这个条目 -->
        <el-form-item label="文本内容" prop="content">
            <div id="editor" style="height: 500px"></div>
        </el-form-item>
        <!-- 条目结束 -->
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
                    @click="onAdd('form')"
            >确认添加
            </el-button>
            <el-button
                    round
                    type="info"
                    @click="cancelAdd"
            >取消添加
            </el-button>
        </div>
    </el-form>
</template>

<script>
    import Editor from '@toast-ui/editor';
    import 'codemirror/lib/codemirror.css';
    import '@toast-ui/editor/dist/toastui-editor.css';

    export default {
        name: "Add",
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
                rules: {
                    title: [
                        {required: true, message: '请输入标题', trigger: 'blur'}
                    ],
                    major: [
                        {required: true, message: '请输入主题', trigger: 'blur'}
                    ],
                    content: [
                        {required: true, message: '请编辑内容', trigger: 'blur'}
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

        },
        mounted() {
            // 当网页解析完毕后生成富文本编辑器
            const editor = new Editor({
                el: document.querySelector('#editor'),
                previewStyle: 'vertical'
            });
        },
        methods: {
            cancelAdd() {
                this.$alert('确定要取消添加吗?', '取消添加', {
                    confirmButtonText: '确定',
                    type: 'error',
                    callback: action => {
                        this.$message({
                            type: 'warning',
                            message: '已取消添加'
                        });
                        this.$router.push('/test');
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>