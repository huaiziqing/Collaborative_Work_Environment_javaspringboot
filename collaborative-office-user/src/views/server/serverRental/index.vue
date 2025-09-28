<template>
  <div class="app-container">
    <!-- 分页下拉框和搜索框 -->
    <div class="search-container">
      <el-select v-model="pageSize" style="width: 100px; margin-right: 20px" placeholder="请选择">
        <el-option
            v-for="item in pageSizeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

      <el-input
          v-model="searchInput"
          style="width: 240px"
          placeholder="输入关键字"
          :prefix-icon="Search"
          @input="handleSearch"
      />
    </div>

    <div class="big-table">
      <el-table :data="currentTableData" border style="width: 100%">
        <el-table-column prop="name" label="服务器名称" width="300" align="center" />
        <el-table-column prop="cpuCapacity" label="CPU核数" width="180" align="center"/>
        <el-table-column prop="memoryCapacity" label="内存(GB)" width="180" align="center"/>
        <el-table-column prop="purpose" label="用途" width="150" align="center">
          <template #default="{row}">
            <span :class="purposeClass(row.purpose)">{{ row.purpose }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="storageCapacity" label="存储容量" align="center"/>
        <el-table-column label="使用说明" align="center">
          <template #default="{row}">
            <div class="button-group" >
              <el-button
                  type="primary"
                  size="small"
                  @click="handleView(row)"
              >
                查看
              </el-button>

              <el-button
                  type="success"
                  size="small"
                  @click="handleBorrow(row)"
              >
                借阅
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <div class="pagination-container">
        <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="filteredData.length"
            :pager-count="11"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { Search } from '@element-plus/icons-vue'

const searchInput = ref('')
const currentPage = ref(1)
const pageSize = ref(10) // 默认每页显示10条数据
const pageSizeOptions = [
  { value: 5, label: '5 条/页' },
  { value: 10, label: '10 条/页' },
  { value: 20, label: '20 条/页' },
  { value: 50, label: '50 条/页' },
]

const tableData = [
  { name: '阿里云8核64g-1', cpuCapacity: '8', memoryCapacity: '64', purpose: '开发', storageCapacity: '1024' },
  { name: '阿里云16核128g-1', cpuCapacity: '16', memoryCapacity: '128', purpose: '生产', storageCapacity: '2048' },
  { name: '阿里云4核16g-1', cpuCapacity: '4', memoryCapacity: '16', purpose: '测试', storageCapacity: '512' },
  { name: '阿里云2核8g-1', cpuCapacity: '2', memoryCapacity: '8', purpose: '开发', storageCapacity: '256' },
  { name: '华为云8核32g-1', cpuCapacity: '8', memoryCapacity: '32', purpose: '实验', storageCapacity: '512' },
  { name: '华为云16核64g-1', cpuCapacity: '16', memoryCapacity: '64', purpose: '生产', storageCapacity: '1024' },
  { name: '华为云4核8g-1', cpuCapacity: '4', memoryCapacity: '8', purpose: '测试', storageCapacity: '256' },
  { name: '华为云2核4g-1', cpuCapacity: '2', memoryCapacity: '4', purpose: '开发', storageCapacity: '128' },
  { name: '腾讯云8核16g-1', cpuCapacity: '8', memoryCapacity: '16', purpose: '实验', storageCapacity: '256' },
  { name: '腾讯云32核128g-1', cpuCapacity: '32', memoryCapacity: '128', purpose: '大数据', storageCapacity: '4096' },
  { name: '腾讯云4核8g-1', cpuCapacity: '4', memoryCapacity: '8', purpose: '测试', storageCapacity: '128' },
  { name: '腾讯云1核2g-1', cpuCapacity: '1', memoryCapacity: '2', purpose: '开发', storageCapacity: '64' },
  { name: 'AWS8核32g-1', cpuCapacity: '8', memoryCapacity: '32', purpose: '生产', storageCapacity: '512' },
  { name: 'AWS16核64g-1', cpuCapacity: '16', memoryCapacity: '64', purpose: '实验', storageCapacity: '1024' },
  { name: 'AWS4核16g-1', cpuCapacity: '4', memoryCapacity: '16', purpose: '测试', storageCapacity: '256' },
  { name: 'AWS2核4g-1', cpuCapacity: '2', memoryCapacity: '4', purpose: '开发', storageCapacity: '128' },
  { name: 'GCP8核16g-1', cpuCapacity: '8', memoryCapacity: '16', purpose: '实验', storageCapacity: '256' },
  { name: 'GCP16核32g-1', cpuCapacity: '16', memoryCapacity: '32', purpose: '生产', storageCapacity: '512' },
  { name: 'GCP4核8g-1', cpuCapacity: '4', memoryCapacity: '8', purpose: '测试', storageCapacity: '128' },
  { name: 'GCP2核4g-1', cpuCapacity: '2', memoryCapacity: '4', purpose: '开发', storageCapacity: '64' },
  { name: '腾讯云4核8g-1', cpuCapacity: '4', memoryCapacity: '8', purpose: '测试', storageCapacity: '128' },
  { name: '腾讯云1核2g-1', cpuCapacity: '1', memoryCapacity: '2', purpose: '开发', storageCapacity: '64' },
  { name: 'AWS8核32g-1', cpuCapacity: '8', memoryCapacity: '32', purpose: '生产', storageCapacity: '512' },
  { name: 'AWS16核64g-1', cpuCapacity: '16', memoryCapacity: '64', purpose: '实验', storageCapacity: '1024' },
  { name: 'AWS4核16g-1', cpuCapacity: '4', memoryCapacity: '16', purpose: '测试', storageCapacity: '256' },
  { name: 'AWS2核4g-1', cpuCapacity: '2', memoryCapacity: '4', purpose: '开发', storageCapacity: '128' },
  { name: 'GCP8核16g-1', cpuCapacity: '8', memoryCapacity: '16', purpose: '实验', storageCapacity: '256' },
  { name: 'GCP16核32g-1', cpuCapacity: '16', memoryCapacity: '32', purpose: '生产', storageCapacity: '512' },
  { name: 'GCP4核8g-1', cpuCapacity: '4', memoryCapacity: '8', purpose: '测试', storageCapacity: '128' },
  { name: 'GCP2核4g-1', cpuCapacity: '2', memoryCapacity: '4', purpose: '开发', storageCapacity: '64' },
];

// 筛选后的数据
const filteredData = computed(() => {
  if (!searchInput.value) {
    return tableData
  }
  return tableData.filter(item =>
      item.name.toLowerCase().includes(searchInput.value.toLowerCase())
  )
})

// 计算当前页显示的数据
const currentTableData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredData.value.slice(start, end)
})

const handleCurrentChange = (val) => {
  currentPage.value = val
}

const handleSearch = () => {
  currentPage.value = 1 // 搜索时重置到第一页
}

const handleView = (row) => {
  console.log('查看行数据:', row)
  // 这里可以添加查看 详细信息 的逻辑
}

const handleBorrow = (row) => {
  console.log('借阅行数据:', row)
  // 这里可以添加 借阅 的逻辑
}

// 根据用途返回对应的 CSS 类名
const purposeClass = (purpose) => {
  switch (purpose) {
    case '开发':
      return 'purpose-dev';
    case '生产':
      return 'purpose-prod';
    case '测试':
      return 'purpose-test';
    case '实验':
      return 'purpose-exp';
    case '大数据':
      return 'purpose-bigdata';
    default:
      return '';
  }
}
</script>

<style scoped lang="scss">
.app-container {
  padding: 20px;
}

.big-table {
  margin-top: 20px;
}

.button-group {
  display: flex;
  justify-content: center; /* 水平居中 */
  gap: 8px; /* 按钮之间的间距 */
  width: 100%; /* 确保按钮组宽度为100% */
}

.el-button--primary {
  background-color: #409EFF;
  border-color: #409EFF;
}

.el-button--primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.el-button--success {
  background-color: #67C23A;
  border-color: #67C23A;
}

.el-button--success:hover {
  background-color: #85ce61;
  border-color: #85ce61;
}

/* 用途列的样式 */
.purpose-dev {
  background-color: rgba(246, 199, 109, 0.44); /* 背景颜色 */
  color: black; /* 文本颜色 */
  border-radius: 10px; /* 圆角半径 */
  width: 50px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
  text-align: center; /* 文本居中 */
  line-height: 20px; /* 垂直居中 */
  display: inline-block; /* 确保元素以块级形式显示 */
}
.purpose-prod {
  background-color: rgba(189, 211, 228, 0.38); /* 背景颜色 */
  color: black; /* 文本颜色 */
  border-radius: 10px; /* 圆角半径 */
  width: 50px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
  text-align: center; /* 文本居中 */
  line-height: 20px; /* 垂直居中 */
  display: inline-block; /* 确保元素以块级形式显示 */
}

.purpose-test {
  background-color: rgba(165, 214, 136, 0.4); /* 背景颜色 */
  color: black; /* 文本颜色 */
  border-radius: 10px; /* 圆角半径 */
  width: 50px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
  text-align: center; /* 文本居中 */
  line-height: 20px; /* 垂直居中 */
  display: inline-block; /* 确保元素以块级形式显示 */
}

.purpose-exp {
  background-color: rgba(174, 177, 243, 0.51); /* 背景颜色 */
  color: black; /* 文本颜色 */
  border-radius: 10px; /* 圆角半径 */
  width: 50px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
  text-align: center; /* 文本居中 */
  line-height: 20px; /* 垂直居中 */
  display: inline-block; /* 确保元素以块级形式显示 */
}

.purpose-bigdata {
  background-color: rgba(221, 148, 180, 0.44); /* 背景颜色 */
  color: black; /* 文本颜色 */
  border-radius: 10px; /* 圆角半径 */
  width: 50px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
  text-align: center; /* 文本居中 */
  line-height: 20px; /* 垂直居中 */
  display: inline-block; /* 确保元素以块级形式显示 */
}

/* 分页组件 */
.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>