<template>
  <div class="app-container">
    <el-card style="height: 86vh;">
      <div class="header">
        <el-row>
          <el-col>
            <el-form :inline="true">
              <el-form-item label="服务器名称">
                <el-input
                    v-model="input"
                    style="width: 240px"
                    placeholder="请输入服务器名称"
                    clearable
                />
              </el-form-item>
              <el-form-item label="服务器类型">
                <el-select v-model="value" placeholder="Select" style="width: 240px">
                  <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="选择时间">
                <el-date-picker
                    v-model="value1"
                    type="datetimerange"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    :default-time="defaultTime1"
                />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" icon="Search">搜索</el-button>
                <el-button icon="Refresh">重置</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
      <div class="body">
        <el-table border :data="tableData" style="width: 100%">
          <el-table-column align="center" center prop="date" label="服务器名称" width="180"/>
          <el-table-column align="center" prop="name" label="CPU核数" width="180"/>
          <el-table-column align="center" prop="address" label="类型"/>
          <el-table-column align="center" prop="address" label="开始时间"/>
          <el-table-column align="center" prop="address" label="结束时间"/>
          <el-table-column align="center" prop="address" label="状态" width="200">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handlerDetailView">查看详情</el-button>
              <el-button type="danger" size="small">取消</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="foot">
        <!--  分页器-->
        <pagination
            v-show="total > 0"
            :total="total"
            v-model:page="queryParams.pageNum"
            v-model:limit="queryParams.pageSize"
            @pagination="getList"
        />
      </div>
    </el-card>
    <el-drawer v-model="drawer" :direction="direction">
      <template #header>
        <h1>标题</h1>
      </template>
      <template #default>
        <form>
          <el-form-item label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>服务器编号</span>
              </div>
            </template>
            xxx
          </el-form-item>
          <el-form-item label="服务器名称" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>服务器编号</span>
              </div>
            </template>
          </el-form-item>
          <el-form-item label="CPU核数" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>CPU核数</span>
              </div>
            </template>
          </el-form-item>
          <el-form-item label="内存" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>内存</span>
              </div>
            </template>
          </el-form-item>
          <el-form-item label="硬盘" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>硬盘</span>
              </div>
            </template>
          </el-form-item>
          <el-form-item label="用途" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>开发</span>
              </div>
            </template>
          </el-form-item>
          <el-form-item label="实时空闲资源" label-width="120" label-position="left">
            <template #label>
              <div style="display: flex;align-items: center;">
                <el-icon size="24"><MostlyCloudy /></el-icon>
                <span>5</span>
              </div>
            </template>
          </el-form-item>

        </form>
      </template>
    </el-drawer>
  </div>
</template>

<script lang="ts" setup>

import {ref} from 'vue'


const value = ref('')
const input = ref('')
//控制抽屉打开
const drawer = ref(false)


//查看详情
const handlerDetailView = () => {
  console.log('查看行数据:')
  drawer.value = true
}
//分页器数据
const total = ref(10)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    keyword: '',

  }
})
//结构赋值
const {queryParams, form, rules} = toRefs(data)

//取消抽屉
function cancelClick() {
  drawer.value = false
}

//确定
function confirmClick() {
  drawer.value = false
}

//测试数据
const options = [
  {
    value: 'Option1',
    label: 'Option1',
  },
  {
    value: 'Option2',
    label: 'Option2',
  },
  {
    value: 'Option3',
    label: 'Option3',
  },
  {
    value: 'Option4',
    label: 'Option4',
  },
  {
    value: 'Option5',
    label: 'Option5',
  },
]
const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

const direction = ref<DrawerProps['direction']>('rtl')
const radio1 = ref('Option 1')


</script>

<style scoped lang="scss">
//main-container全局样式
.app-container {
  .header {
    margin: 15px;
  }
}


</style>