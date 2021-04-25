<template>
    <!-- 本页面为编辑页面的测试页面 -->
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
                }
            }
        },
        created() {
            // console.log(this.$router.currentRoute.query.id)
            let _this = this;
            axios.get('http://localhost:8181/qrcodedb/find/1').then(function (response) {
                _this.article = response.data;
            });
        },
        mounted() {
            const editor = new Editor({
                el: document.querySelector('#editor')
            });
        }
    }
</script>

<style scoped>

</style>