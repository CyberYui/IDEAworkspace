<template>
    <div>
        <!--这是用来测试内容的vue文件-->
        <!--现在正在测试的页面为 : Table.vue-->
        <div id="addItem">
            <el-row>
                <!--添加按钮-->
                <el-button
                        style="width: 27%"
                        type="success"
                        @click="addContent">添加
                </el-button>
                <!--刷新按钮-->
                <el-button
                        style="width: 27%"
                        type="warning"
                        @click="refContent">
                    <!--@click="handleClick(scope.row)"-->
                    刷新
                </el-button>
                <!--搜索框-->
                <!--起名searchData,将输入的数据进行绑定-->
                <!--placeholder 里面的内容是用于提示的-->
                <el-input
                        :data="searchData"
                        placeholder="请输入想要搜索的内容"
                        v-model="input"
                        style="width: 27%; margin-left: 12px;"
                        clearable>
                </el-input>
                <!--搜索按钮-->
                <el-button
                        style="width: 13%; margin-left: 12px;"
                        type="primary"
                        @click="seContent">搜索
                </el-button>
            </el-row>
        </div>
        <!--表格内容,起名 tableData 进行数据绑定-->
        <el-table
                :data="tableData"
                border
                max-height="800"
                style="width: 100%;  margin-top: 12px;">
            <!-- 每一个列中 prop 表示对应的内容属性 -->
            <!-- 表头显示的内容在 label 中固定表示-->
            <!-- 最终不在表中显示 id -->
            <!--id 序号列-->
            <el-table-column
                    align="center"
                    prop="id"
                    label="序号"
                    width="200">
            </el-table-column>
            <!--id 序号列结束-->
            <!--标题列-->
            <el-table-column
                    fixed
                    prop="title"
                    label="标题"
                    width="200">
            </el-table-column>
            <!--标题列结束-->
            <!--主题列-->
            <el-table-column
                    align="center"
                    prop="major"
                    label="主题"
                    width="120">
            </el-table-column>
            <!--主题列结束-->
            <!--内容列-->
            <el-table-column
                    align="center"
                    prop="content"
                    label="内容"
                    width="300">
            </el-table-column>
            <!--内容列结束-->
            <!--图片地址列-->
            <el-table-column
                    align="center"
                    prop="image"
                    label="图片地址"
                    width="300">
                <!-- 下面注释掉的内容用于在网页上显示图片 -->
                <!--<template slot-scope="scope">-->
                <!--    <img :src="scope.row.image" style="height: 70px"/>-->
                <!--</template>-->
            </el-table-column>
            <!--图片地址列结束-->
            <!--添加查看图片的按钮-->
            <!--这里的主要技术问题并没有解决-->
            <el-table-column
                    align="center"
                    label="查看图片"
                    width="120px">
                <!--图片弹出框-->
                <el-popover
                        placement="top-start"
                        title="查看图片"
                        width="200"
                        trigger="click">
                    <!--设定弹出框的 div 大小,并传输需要的图片路径-->
                    <!--技术问题:这里的图片无法显示-->
                    <div style="width:200px;height:200px;">
                        <img
                                style="height:75px;"
                                :src='"http://file06.16sucai.com/2016/0806/fe48f9d41b21a269ba9446c11308a0ec.jpg"'
                                alt
                        />
                    </div>
                    <el-button
                            slot="reference"
                            size="mini"
                            style="width: 95px"
                            type="success"
                            @click="getImage">查看图片
                    </el-button>
                </el-popover>
                <!--图片弹出框结束-->
            </el-table-column>
            <!--查看图片按钮结束-->
            <!--视频地址列-->
            <el-table-column
                    align="center"
                    prop="videos"
                    label="视频地址"
                    width="300">
            </el-table-column>
            <!--视频地址列结束-->
            <!--添加查看视频的按钮-->
            <el-table-column
                    align="center"
                    label="查看视频"
                    width="120px">
                <el-button
                        size="mini"
                        style="width: 95px"
                        type="success"
                        @click="videosCheck">查看视频
                </el-button>
            </el-table-column>
            <!--查看视频按钮结束-->
            <!--网页地址列-->
            <el-table-column
                    align="center"
                    prop="address"
                    label="网页地址"
                    width="300">
            </el-table-column>
            <!--网页地址列结束-->
            <!--添加查看网页二维码的按钮-->
            <el-table-column
                    align="center"
                    label="查看二维码"
                    width="140px">
                <!--二维码弹出框-->
                <el-popover
                        placement="top-end"
                        title="查看二维码"
                        width="200px"
                        trigger="click">
                    <div style="width:200px;height:200px;">
                        <img
                                style="height:75px;"
                                :src='"http://file06.16sucai.com/2016/0806/fe48f9d41b21a269ba9446c11308a0ec.jpg"'
                                alt
                        />
                    </div>
                    <el-button
                            slot="reference"
                            size="mini"
                            style="width: 110px"
                            type="success"
                            @click="qrCodeCheck">查看二维码
                    </el-button>
                </el-popover>
                <!--二维码弹出框结束-->
            </el-table-column>
            <!--查看网页二维码按钮结束-->
            <el-table-column
                    align="center"
                    fixed="right"
                    label="操作"
                    width="160px">
                <el-table-column
                        align="center"
                        label="修改"
                        width="80px">
                    <!--修改按钮-->
                    <el-button
                            size="mini"
                            type="info"
                            @click="upContent">修改
                    </el-button>
                </el-table-column>
                <!--删除按钮-->
                <el-table-column
                        align="center"
                        label="删除"
                        width="80px">
                    <!-- 删除按钮的弹出确认框 -->
                    <el-popover
                            placement="bottom"
                            width="160"
                            v-model="visible">
                        <p>确定删除吗？</p>
                        <div style="text-align: right; margin: 0">
                            <el-button
                                    size="mini"
                                    type="primary"
                                    @click="visible = false">确定
                            </el-button>
                            <el-button
                                    size="mini"
                                    type="info"
                                    @click="visible = false">取消
                            </el-button>
                        </div>
                        <el-button
                                size="mini"
                                type="danger"
                                slot="reference"
                                @click="delContent">删除
                        </el-button>
                    </el-popover>
                    <!-- 删除按钮的弹出框结束 -->
                </el-table-column>
            </el-table-column>
            <!--<el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button type="text" size="small">编辑</el-button>
                </template>
            </el-table-column>-->
        </el-table>
        <!--分页-->
        <el-pagination
                small
                layout="prev, pager, next"
                :total="500">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: 'Test',
        // methods 开始
        methods: {
            handleClick(row) {
                console.log(row);
            },
            // 添加按钮的 MessageBox 方法
            addContent() {
                // 重定义 this 防止之后无法使用
                let _this = this;
                // 看看是否调用
                console.log('成功进入添加方法');
                // 开始提示 MessageBox
                _this.$confirm('请输入添加的内容,带*号的为必填项', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    _this.$message({
                        type: 'success',
                        message: '添加成功!'
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消添加'
                    });
                });
            },
            // 刷新内容方法
            refContent() {
                // 看看是否调用
                console.log('成功进入刷新方法');
            },
            // 查看图片方法
            getImage() {
                // 看看是否调用
                console.log('成功进入查看图片方法');
            },
            // 修改内容的 MessageBox 方法
            upContent() {
                // 重定义 this 防止之后无法使用
                let _this = this;
                // 看看是否调用
                console.log('成功进入修改方法');
                // 开始提示 MessageBox
                _this.$confirm('请输入需要修改的内容,带*号的为必填项', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    _this.$message({
                        type: 'success',
                        message: '修改成功!'
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'warning',
                        message: '已取消修改'
                    });
                });
            },
            // 查找内容方法
            seContent() {
                // 看看是否调用
                console.log('成功进入查询方法');
            },
            // 删除内容方法
            delContent() {
                // 看看是否调用
                console.log('成功进入删除方法');
            },
            // 查看二维码方法
            qrCodeCheck() {
                // 看看是否调用
                console.log('成功进入查看二维码方法');
            },
            // 查看视频方法
            videosCheck() {
                // 看看是否调用
                console.log('成功进入查看视频方法');
            }
        },
        // methods 结束

        // 使用 axios 链接后台数据库,刷新表格
        created() {
            let _this = this;
            axios.get('http://localhost:8181/qrcodedb/list').then(function (response) {
                _this.tableData = response.data;
            })
        },

        // data 内容
        data() {
            return {
                tableData: [
                    // tableData 是一个数组,里面分条存放各种属性
                    {
                        id: '1',
                        title: '王小虎',
                        major: '上海',
                        content: '上海市普陀区金沙江路 1518 弄',
                        image: '上海市普陀区金沙江路 1519 弄',
                        videos: '上海市普陀区金沙江路 1520 弄',
                        address: '上海市普陀区金沙江路 1521 弄'
                    }],
                input: '',
                searchData: [{
                    // 查询输入内容
                }],
                visible: false,
            };
        }
    }
</script>

<style scoped>

</style>