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
                        @click="handleClick(scope.row)">添加
                </el-button>
                <!--刷新按钮-->
                <el-button
                        style="width: 27%"
                        type="warning"
                        @click="handleClick(scope.row)">刷新
                </el-button>
                <!--搜索框-->
                <el-input
                        :data="searchData"
                        placeholder="请输入内容"
                        v-model="input"
                        style="width: 27%; margin-left: 12px;"
                        clearable>
                </el-input>
                <!--搜索按钮-->
                <el-button
                        style="width: 13%; margin-left: 12px;"
                        type="primary"
                        @click="handleClick(scope.row)">搜索
                </el-button>
            </el-row>
        </div>
        <el-table
                :data="tableData"
                border
                style="width: 100%;  margin-top: 12px;">
            <el-table-column
                    fixed
                    prop="id"
                    label="序号"
                    width="150">
            </el-table-column>
            <el-table-column
                    fixed
                    prop="title"
                    label="标题"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="major"
                    label="主题"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="content"
                    label="内容"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="image"
                    label="图片地址"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="videos"
                    label="视频地址"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="网页地址"
                    width="300">
            </el-table-column>
            <el-table-column
                    label="操作"
                    width="200">
                <el-table-column
                        label="修改"
                        width="100">
                    <template slot-scope="scope">
                        <!--修改按钮-->
                        <el-button
                                fixed="fixed"
                                size="mini"
                                type="info"
                                @click="handleClick(scope.row)">修改
                        </el-button>
                    </template>
                </el-table-column>
                <!--删除按钮-->
                <el-table-column
                        label="删除"
                        width="100">
                    <template slot-scope="scope">
                        <el-button
                                fixed="fixed"
                                size="mini"
                                type="danger"
                                @click="handleClick(scope.row)">删除
                        </el-button>
                    </template>
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
        methods: {
            handleClick(row) {
                console.log(row);
            }
        },

        data() {
            return {
                tableData: [{
                    id: '1',
                    title: '王小虎',
                    major: '上海',
                    content: '上海市普陀区金沙江路 1518 弄',
                    image: '上海市普陀区金沙江路 1519 弄',
                    videos: '上海市普陀区金沙江路 1520 弄',
                    address: '上海市普陀区金沙江路 1521 弄'
                }],
                input: ''
            }
        }
    }
</script>

<style scoped>

</style>