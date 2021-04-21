<template>
    <div>
        <!--这是用来测试内容的vue文件-->
        <!--现在正在测试的页面为 : Table.vue-->
        <div id="addItem">
            <el-row>
                <!--添加按钮-->
                <el-button
                        style="width: 27%; margin-right: 12px;"
                        type="success"
                        @click="dialogFormVisible = true">添加
                </el-button>
                <!-- 弹出表单内容 -->
                <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                    <el-form>
                        <template slot-scope="scope">
                            <el-form-item label="标题" :label-width="formLabelWidth">
                                <el-input></el-input>
                            </el-form-item>
                            <el-form-item label="所属主题" :label-width="formLabelWidth">
                                <el-input></el-input>
                            </el-form-item>
                            <!-- 测试时仅仅先使用下面这个条目 -->
                            <el-form-item label="请在以下处输入内容,右侧会实时显示效果" :label-width="formLabelWidth">
                                <el-input></el-input>
                            </el-form-item>
                            <!-- 条目结束 -->
                            <el-form-item label="图片链接" :label-width="formLabelWidth">
                                <el-input></el-input>
                            </el-form-item>
                            <el-form-item label="视频链接" :label-width="formLabelWidth">
                                <el-input></el-input>
                            </el-form-item>
                            <el-button
                                    type="success"
                                    @click="addContent">确认添加
                            </el-button>
                            <el-button
                                    type="info"
                                    @click="calAdd">取消添加
                            </el-button>
                        </template>
                    </el-form>
                </el-dialog>
                <!-- FormEnd -->
                <!--刷新按钮-->
                <el-button
                        style="width: 27%"
                        type="warning"
                        @click="refContent">
                    <!--@click="handleClick(scope.row)"-->
                    刷新
                </el-button>
                <!--搜索框-->
                <!--placeholder 里面的内容是用于提示的-->
                <el-input
                        v-model="searchData"
                        placeholder="请输入标题,现仅支持标题的搜索"
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
                    width="50">
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
                    width="450"
                    :show-overflow-tooltip="true">
                <!-- 为了保证不让内容过长占用表格单格 -->
                <!-- :show-overflow-tooltip="true" 使得一行的内容会在鼠标 hover 之后显示 -->
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
                    <!--技术问题:这里的图片无法实时调取数据库内容然后显示-->
                    <div style="height:200px;padding: 5px">
                        <img
                                style="height:190px;"
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
                    width="200px">
                <el-table-column
                        align="center"
                        label="修改"
                        width="100px">
                    <!--修改按钮-->
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="info"
                                icon="el-icon-edit-outline"
                                @click="findContent(scope.row)">修改
                        </el-button>
                    </template>
                </el-table-column>
                <!--删除按钮-->
                <el-table-column
                        align="center"
                        label="删除"
                        width="100px">
                    <template slot-scope="scope">
                        <el-button
                                slot="reference"
                                size="mini"
                                type="danger"
                                icon="el-icon-delete"
                                @click="delContent(scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table-column>
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
            // 添加内容方法
            addContent() {
                // 重定义 this 防止之后无法使用
                let _this = this;
                // 看看是否调用
                console.log('成功进入添加方法');
            },
            // 取消添加按钮的响应函数
            calAdd() {
                // 直接提示取消即可
                this.$message({
                    type: 'error',
                    message: '已取消添加'
                });
                // 取消后关闭表单
                this.dialogFormVisible = false;
            },
            // 刷新内容方法
            refContent() {
                // 看看是否调用
                // console.log('成功进入刷新方法');
                this.$message({
                    type: 'success',
                    message: '已刷新页面'
                });
                this.reload();
            },
            // 查看图片方法
            getImage() {
                // 看看是否调用
                console.log('成功进入查看图片方法');
            },
            // 修改内容方法
            // 实际上是通过 id 在数据库查找内容的方法
            findContent(row) {
                // 看看是否调用
                // console.log('成功进入修改方法');
                // 跳转到修改页面进行修改
                console.log(row.id);
                // this.$router.push('/edit?id='+row.id);
                this.$router.push({
                    path: '/edit',
                    query: {
                        id: row.id,
                    }
                })
            },
            // 查找内容方法
            seContent() {
                // 看看是否调用
                // console.log('成功进入查询方法');
                let searchItem = searchData;
            },
            // 删除内容方法
            delContent(row) {
                // 重定义 this 防止之后无法使用
                let _this = this;
                // 看看是否调用
                // alert(row.id);
                // 开始提示 MessageBox
                _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 点击确认之后会删除本行内容
                    // 通过 axios 跨域删除数据库相关内容
                    axios.delete('http://localhost:8181/qrcodedb/delete/' + row.id).then(function (response) {
                        if (response.data) {
                            // 提示成功通知
                            _this.$message({
                                type: 'success',
                                // 带着标题提示删除成功
                                message: row.title + '删除成功!'
                            });
                            // 删除成功后自动刷新页面
                            location.reload();
                        } else {
                            // 删除失败自动刷新页面显示结果
                            alert('删除失败 ! 请联系管理员 ! ');
                            location.reload();
                        }
                    });
                }).catch(() => {
                    // 当用户取消时的提示
                    _this.$message({
                        type: 'error',
                        message: '已取消删除'
                    });
                });
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
            // 修改表格行的内容
        },
        // methods 结束

        // 使用 axios 链接后台数据库,刷新表格
        created() {
            let _this = this;
            axios.get('http://localhost:8181/qrcodedb/list').then(function (response) {
                _this.tableData = response.data;
            });
        },

        // data 内容
        data() {
            return {
                tableData: [
                    // tableData 是一个数组,里面分条存放各种属性
                    // 这里只展示一条内容,其实可以完全不用这则内容
                    {
                        id: '1',
                        title: '王小虎',
                        major: '上海',
                        content: '上海市普陀区金沙江路 1518 弄',
                        image: '上海市普陀区金沙江路 1519 弄',
                        videos: '上海市普陀区金沙江路 1520 弄',
                        address: '上海市普陀区金沙江路 1521 弄'
                    }],
                searchData: '',
                dialogFormVisible: false,
                formLabelWidth: '120px',
                visible: false,
            };
        }
    }
</script>

<style lang="css">

</style>