<template>
    <div>
        <!--如果把 router-view 框起来,那么能对其进行一些上层操作-->
        <!--比如在其外面加一层内容,或者直接添加背景什么的-->
        <!--也就是说 App.vue 会在每个 views 的外面都有内容显示-->
        <!--比如这里添加了一个导航栏-->
        <!--为了让导航栏不在一些特定页面展示,给其添加了 v-if 属性-->
        <el-menu
            v-if="headerShow"
            id="app"
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect">
            <el-menu-item index="1">处理中心</el-menu-item>
            <el-submenu index="2">
                <template slot="title">我的工作台</template>
                <el-menu-item index="2-1">选项1</el-menu-item>
                <el-menu-item index="2-2">选项2</el-menu-item>
                <el-menu-item index="2-3">选项3</el-menu-item>
                <el-submenu index="2-4">
                    <template slot="title">选项4</template>
                    <el-menu-item index="2-4-1">选项1</el-menu-item>
                    <el-menu-item index="2-4-2">选项2</el-menu-item>
                    <el-menu-item index="2-4-3">选项3</el-menu-item>
                </el-submenu>
            </el-submenu>
            <el-menu-item index="3" disabled>消息中心</el-menu-item>
            <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
        </el-menu>
        <router-view/>
    </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'

export default {
    name: 'app',
    // components: {
    //     HelloWorld
    // }
    data() {
        return {
            activeIndex: '1',
            activeIndex2: '1',
            headerShow: true
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        }
    },
    watch: {
        $route(to, from) {
            // 设置当进入的是视频测试页面 videotest
            // 以及二维码显示页面 qrcodetest 时不显示导航栏
            if (this.$route.path === "/videotest" || "/qrcodetest") {
                this.headerShow = false;
            } else {
                this.headerShow = true;
            }
        }
    }
}
</script>

<style>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-bottom: 12px;
}
</style>
