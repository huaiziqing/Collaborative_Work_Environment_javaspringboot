<template>
  <div class="search-container">
    <div class="left">
    <el-input
        v-model="keyword"
        placeholder="输入关键字..."
        clearable
        @keyup.enter="handleSearch"
        class="search-input"
    />

    <el-select
        v-model="categoryId"
        placeholder="类别不限"
        clearable
        class="category-select"
    >
      <el-option
          v-for="category in categories"
          :key="category.id"
          :label="category.name"
          :value="category.id"
      />
    </el-select>
    </div>
    <div class="right">
    <el-button
        type="primary"
        @click="handleSearch"
        class="search-button"
    >
      搜索
    </el-button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 搜索关键字
const keyword = ref('')
// 选择的分类ID
const categoryId = ref('')

// 分类选项数据
const categories = ref([
  { id: 1, name: 'JavaScript' },
  { id: 2, name: 'Python' },
  { id: 3, name: 'Java' },
  { id: 4, name: '算法与数据结构' },
  { id: 5, name: '计算机基础' },
  { id: 6, name: '移动开发' },
  { id: 7, name: '云计算' },
  { id: 8, name: '人工智能' }
])

// 搜索方法
const handleSearch = () => {
  if (!keyword.value && !categoryId.value) {
    ElMessage.warning('请输入搜索关键字或选择分类')
    return
  }

  // 构建请求参数
  const params = {}
  if (keyword.value) params.keyword = keyword.value
  if (categoryId.value) params.categoryId = categoryId.value

  // 发送搜索请求
  axios.get('/dev-api/api/book', { params })
      .then(response => {
        // 触发自定义事件，将搜索结果传递给父组件
        emit('search-result', response.data)
      })
      .catch(error => {
        console.error('搜索失败:', error)
        ElMessage.error('搜索失败，请稍后重试')
      })
}

// 定义自定义事件
const emit = defineEmits(['search-result'])
</script>

<style scoped>
.search-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  padding: 15px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
.left{
  display: flex;

}
.search-input {
  flex: 1;
  max-width: 300px;
  margin-right: 60px;
}

.category-select {
  width: 150px;
  margin-right: 60px;
}

.search-button {
  margin-right: 60px;

}
</style>
