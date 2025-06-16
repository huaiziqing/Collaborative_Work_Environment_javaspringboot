package org.example.controller;

/**
 * 接收 HTTP 请求参数（如 page, pageSize, categoryId）
 * 调用 Service 层处理业务逻辑（如 bookService.addBook(book, userRole)）
 * 统一响应格式（如 200 成功、400 格式错误、403 权限不足）
 * 权限校验（通过 JWT 或请求头获取用户角色）
 */
public class BookController {
}
