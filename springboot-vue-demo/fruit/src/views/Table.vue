<template>
    <el-table
            :data="tableData"
            border
            style="width: 100%">
        <el-table-column
                fixed
                prop="id"
                label="数据库id"
                align="center"
                width="120">
        </el-table-column>
        <el-table-column
                prop="name"
                label="水果名称"
                align="center"
                width="160">
        </el-table-column>
        <el-table-column
                prop="sale"
                label="销售情况"
                align="center"
                width="160">
        </el-table-column>
        <!-- 给图片嵌入显示 -->
        <el-table-column
                prop="icon"
                label="水果图片"
                align="center"
                width="160">
            <!-- scope 映射当前对象,也就是上面的 el-table-column 整体-->
            <!-- scope 并不是固定的,它只是个变量名 -->
            <!-- scope.row.icon 表示提取的是这一行的 icon 值,也就是图片的路径 -->
            <!-- 这样就实现了在表格里面显示图片内容,而不是图片路径的效果 -->
            <template slot-scope="scope">
                <img :src="scope.row.icon" style="height: 70px"/>
            </template>
        </el-table-column>
        <!-- fixed 表示浮动,我们这里删除掉向右浮动-->
        <!--<el-table-column-->
        <!--        fixed="right"-->
        <!--        label="操作"-->
        <!--        width="160">-->
        <el-table-column
                label="操作"
                width="160">
            <template slot-scope="scope">
                <!-- 当点击删除按钮的时候,会调用 fruitDelete 函数,并传入 scope.row 对象 -->
                <el-button @click="fruitDelete(scope.row)" type="text" size="small">删除</el-button>
                <el-button @click="fruitUpdate(scope.row)" type="text" size="small">修改</el-button>
                <el-button @click="fruitFind(scope.row)" type="text" size="small">查找本项</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        name: "Table",
        methods: {
            // 写出 fruitDelete 函数
            fruitDelete(row) {
                // 删除的时候只需要找到唯一标识即可,即找到数据库里面的 id 即可
                // console.log(row);
                // alert(row.id); // 提示id,看看能不能选中

                // 弹框的js代码,从elementUI里面直接找即可
                // 还是一样,在外部声明 this
                let _this = this;
                //开始执行
                this.$confirm('此操作将永久删除'+row.name+'行内容, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 真正的删除函数,具体如何实现的看后面注释,那里的删除函数更为简洁
                    axios.delete('http://localhost:8181/fruit/delete/'+row.id).then(function(response){
                        if (response.data){
                            _this.$alert(row.name+'行删除成功!','删除数据',{
                                confirmButtonText: '确定',
                                callback: action => {
                                    // 跳转到 /table ,也就是刷新当前页面
                                    location.reload()
                                }
                            });
                        }
                    })
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

                // 从后台编写删除接口,提供给这里,然后这里去进行删除的 request
                // 后端的 DeleteMapping 为 ("/delete/{id}") ,所以这里的/delete/两个/缺一不可
                //     axios.delete('http://localhost:8181/fruit/delete/'+row.id).then(function (response) {
                //         if(response.data){
                //             alert('delete success!');
                //         }
                //     })
            },

            // 写出 fruitUpdate 函数
            fruitUpdate(row){
                // 我们需要先引导用户进入一个表单页面,这个页面的 mapping 是 edit
                // 然后这个表单需要先在后台拿到现有的数据,然后才能说修改
                this.$router.push('/edit?id='+row.id)
            },

            // 写出 fruitFind 函数
            fruitFind(row){
                // 还是一样,在外部声明 this
                let _this = this;
                // 查找本项的数据库内容就直接返回当前 id 对应的 url 即可
                axios.get('http://localhost:8181/fruit/find/'+row.id).then(function (response) {
                    // 控制台输出
                    console.log(response.data);
                    // 提示查找本项成功
                    alert('本项存在!');
                })
            }
        },

        // 修改假数据为真数据,即连接数据库,让前端和后端交互
        // 但是由于前端和后端的域名不一样,一个是8080,一个是8181,所以需要跨域
        created(){
            // 创建this关键字,因为无法在axios中直接调用this,这个this是vue对象
            // 而在then里面使用this的话,指的是response这个函数
            let _this = this;
            // 使用axios链接前后端,把从后端数据库提取到数据的url传给get
            axios({
                method:'get',
                url:'http://localhost:8181/fruit/list'
            })// 响应对象为response
                .then(function (response) {
                // 控制台输出
                console.log(response);
                _this.tableData = response.data
                // 把收取到的数据放到表单视图中
                // 我们需要嵌入一个图片标签给表格,也就是在 element 里面嵌入
            })
        },

        // data中的数据是假数据,内容都是死的,我们需要在交互之后覆盖内容,这里只保留一条
        data() {
            return {
                tableData: [{
                    id: '2016-05-02',
                    name: '王小虎',
                    sale: '上海',
                    icon: '普陀区'
                }]
            }
        }
    }
</script>