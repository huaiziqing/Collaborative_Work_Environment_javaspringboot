<template>
  <div>
    <search-and-filter  @search-result="handleSearchResult"></search-and-filter>
  </div>
  <div>
    <el-table :data="bookList" border v-loading="loading">
      <el-table-column prop="title" label="书名" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" />
      <el-table-column prop="isbn" label="索书号" />
      <el-table-column label="操作" >
        <template #default="scope">
          <el-button
              type="primary"
              size="small"
              @click="goView(scope.row)"

          >
            查看
          </el-button>
          <el-button
              type="success"
              size="small"
              @click="handleBorrow(scope.row)"
          >
            借阅
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div>
    <!-- 分页组件 -->
    <Pagination
        @pagechange="handpagechange"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import SearchAndFilter from '@/views/book/bookRental/SearchAndFilter/SearchAndFilter.vue'
import Pagination from '@/views/book/bookRental/Pagination/Pagination.vue'
import axios from 'axios'

const bookList = ref([])
const loading = ref(false)
//搜索框查到的数据
const handleSearchResult = (data) => {
  console.log("搜索查到的数据是111"+data)
  bookList.value = data.data
}
//翻页获得的新数据
const handpagechange = (data)=>{
  console.log("搜索查到的数据是222"+data)
  bookList.value = data.data
}
// 获取图书数据
const fetchBooks = async () => {
  try {
    loading.value = true
    const response = await axios.get('/dev-api/api/book')
    bookList.value = response.data.data
    console.log("获取数据成功"+bookList.value)
  } catch (error) {
    console.error('获取图书数据失败:', error)
    ElMessage.error('获取图书数据失败')
  } finally {
    loading.value = false
  }
}

//查看
const goView=()=>{
  console.log(123)
}
// 组件挂载时自动获取数据
onMounted(() => {
  fetchBooks()
})
</script>

<style scoped>

</style>
