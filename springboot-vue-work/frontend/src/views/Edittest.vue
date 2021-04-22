<template>
    <div id="editor"></div>
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