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
                <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                <el-button type="text" size="small">编辑</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        name: "Table",
        methods: {
            handleClick(row) {
                console.log(row);
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

        // data中的数据是假数据,内容都是死的,我们需要在交互之后覆盖这些数据
        data() {
            return {
                tableData: [{
                    id: '2016-05-02',
                    name: '王小虎',
                    sale: '上海',
                    icon: '普陀区'
                }, {
                    id: '2016-05-02',
                    name: '王小虎',
                    sale: '上海',
                    icon: '普陀区'
                }, {
                    id: '2016-05-02',
                    name: '王小虎',
                    sale: '上海',
                    icon: '普陀区'
                }, {
                    id: '2016-05-02',
                    name: '王小虎',
                    sale: '上海',
                    icon: '普陀区'
                }]
            }
        }
    }
</script>