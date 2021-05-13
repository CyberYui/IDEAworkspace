<template>
    <!-- 本页面用来尝试使用国产 markdown 编辑器替代原有的编辑器 -->
    <div class="mavonEditor">
        <mavon-editor
            ref=md
            @imgAdd="imgAdd"
            :toolbars="markdownOption"
            v-model="handbook"
        />
    </div>
</template>

<script>
export default {
    name: "Editor",
    data() {
        return {
            markdownOption: {
                bold:true, // 粗体
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
            },
            // 导入不同组件
            handbook: "#### how to use mavonEditor In this profile"
        };
    },
    components: {},
    created() {

    },
    mounted() {

    },
    methods:{
        // 绑定@imgAdd event
        imgAdd(pos,$file){
            // 第一步.将图片上传到服务器.
            let formData = new FormData();
            formData.append('image', $file);
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
            }).then(function (url) {
                //上传成功开始拆解地址信息
                let srcUrl = url.data;
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
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                /**
                 * $vm 指为mavonEditor实例，可以通过如下两种方式获取
                 * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
                 * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
                 */
                $vm.$img2Url(imgArr[7], url);
            })
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
    margin: 60px;
    width: auto;
    height: 500px;
}
</style>