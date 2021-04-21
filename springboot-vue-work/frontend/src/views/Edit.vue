<template>
    <!-- model='article' 表示整个表单和下面的 article 对象绑定-->
    <el-form
            ref="form"
            :rules="rules"
            :model="article"
            label-width="100px"
            style="width: 600px">
        <!-- 使用 v-model 分别绑定数据 -->
        <el-form-item label="序号" prop="id" style="visibility: hidden;">
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
            <el-input v-model="article.content"></el-input>
        </el-form-item>
        <!-- 条目结束 -->
        <el-form-item label="单图链接" prop="image">
            <el-input v-model="article.image"></el-input>
        </el-form-item>
        <el-form-item label="视频链接" prop="videos">
            <el-input v-model="article.videos"></el-input>
        </el-form-item>
        <el-button
                type="success"
                @click="onSubmit('form')"
        >确认修改
        </el-button>
        <el-button
                type="info"
                @click="cancelSubmit"
        >取消修改
        </el-button>
    </el-form>
</template>

<script>
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
            // console.log(this.$router.currentRoute.query.id)
            let id = this.$router.currentRoute.query.id;
            let _this = this;
            axios.get('http://localhost:8181/qrcodedb/find/' + id).then(function (response) {
                _this.article = response.data;
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
                                        _this.$router.push('/test');
                                    }
                                })
                            }
                        })
                    }
                })
            },
            cancelSubmit() {
                this.$alert('确定要取消修改吗?','取消修改', {
                    confirmButtonText: '确定',
                    type:'error',
                    callback: action => {
                        this.$message({
                            type: 'warning',
                            message: '已取消修改'
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