<template>
    <!-- model 表示整个表单与哪个对象绑定,可以看到这里是 fruit 对象 -->
    <el-form ref="form" :rules="rules" :model="fruit" label-width="80px" style="width: 600px">
        <el-form-item label="水果ID">
            <!-- 给每个 input 绑定对象的相应属性 -->
            <!-- v-model 双向绑定,具体可以去看 vue 的官方文档 -->
            <el-input v-model="fruit.id" readonly></el-input>
        </el-form-item>
        <!-- 使用 prop 绑定校验规则,比如这里是绑定的 name 的规则 -->
        <!-- 所以,v-model绑定数据,prop绑定校验规则 -->
        <el-form-item label="名称" prop="name">
            <el-input v-model="fruit.name"></el-input>
        </el-form-item>
        <el-form-item label="销量" prop="sale">
            <el-input v-model.number="fruit.sale"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="icon">
            <el-input v-model="fruit.icon"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">立即修改</el-button>
            <el-button @click="unSubmit()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "Edit",
        data() {
            return {
                // fruit 对象,从数据库中拿到的内容会填到这里
                fruit: {
                    name: '',
                    sale: '',
                    icon: ''
                },
                // 定义数据校验的对象 rules ,里面包含了3个子对象
                // trigger 表示什么时候触发这个规则,blur 的意思是当光标离开时触发
                // required 表示必填内容,并且销量这里规定了必须填数字 number
                rules:{
                    name:[
                        { required: true, message: '请输入水果名称', trigger: 'blur' }
                    ],
                    sale:[
                        { required: true, message: '请输入销量', trigger: 'blur' },
                        { type: 'number', message: '销量必须为数字值'}
                    ],
                    icon:[
                        { required: true, message: '请输入图片链接', trigger: 'blur' }
                    ]
                }
            }
        },
        // 用户看到的页面是经过回填数据之后显示的页面
        // 所以我们的回填逻辑要写在页面的 created 初始化函数中
        created() {
            // 首先获取到在 table 中点击一行的修改按钮之后,传回的 id
            // Table 中是 push('/edit?id='+row.id)
            // 也就是问号传参,就是带着 id 传参过来
            let id = this.$route.query.id;
            // 声明 this 就是让程序知道我们在操作的是 vue 对象,而不是一个函数对象
            let _this = this;
            // 获取数据库中当前 id 相对应的内容,后台的接口是 /find/{id}
            axios.get('http://localhost:8181/fruit/find/'+id).then(function (response) {
                // 将当前从数据库中拿到的内容回调给 fruit 填出当前的表格
                _this.fruit = response.data
            })
        },
        methods: {
            // 设置修改完成之后的修改数据库函数,也就是更新操作
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // 同样,首先声明我们要操作的是 vue 对象
                        let _this = this;
                        // 请求后台的 update 接口,传入修改之后的 fruit 对象,这个对象是一个 JSON 数据的聚合物
                        axios.put('http://localhost:8181/fruit/update', this.fruit).then(function (response) {
                            // response 的 data 返回的是布尔类型,可以在后台的 update 方法中看到
                            if (response.data) {
                                // 首先提示修改成功,用户需要确认
                                _this.$alert(_this.fruit.name + '修改成功！', '修改数据', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        // 跳转回 /table ,也就是重新刷新一下表格数据,从表单跳回到修改之后的表格
                                        _this.$router.push('/table');
                                    }
                                });
                            }
                        })
                    }
                });
            },

            // 设置当用户点击取消之后的操作
            // 很简单,用户不想修改内容,那就直接重新刷新一下表格即可
            unSubmit() {
                this.$router.push('/table')
            }
        }
    }
</script>

<style scoped>

</style>