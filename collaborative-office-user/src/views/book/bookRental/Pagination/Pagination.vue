<template>
  <div class="pagination-container">
    <el-pagination
        :page-size="20"
        :pager-count="5"
        layout="prev, pager, next"
        :total="200"
        v-model:current-page="currentPage"

    />
  </div>
</template>

<script setup>
import {ref, watch} from 'vue'
import axios from 'axios'
import {ElMessage} from 'element-plus'
// 1. 声明 emits
const emit = defineEmits(['pagechange']) // 声明可触发的事件名

// 当前页码
var currentPage = ref(1)
// 每页数量
const pageSize = ref(20)

watch(currentPage, (newPage) => {
  console.log("当前页面:", newPage)
  fetchData(newPage, pageSize.value)
})
// 发送请求获取数据
const fetchData = (page, size) => {
  axios.get('/dev-api/api/book', {
    params: {
      page,
      pageSize: size
    }
  })
      .then(response => {
        emit('pagechange', response.data)
        console.log("请求到的数据是"+response)
      })
      .catch(error => {
        console.error('获取数据失败:', error)
        ElMessage.error('获取数据失败，请稍后重试')
      })
}

</script>

<style scoped>
.pagination-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  gap: 20px;
}


</style>
