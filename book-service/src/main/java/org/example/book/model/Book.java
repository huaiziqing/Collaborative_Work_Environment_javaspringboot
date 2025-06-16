package org.example.book.model;

import java.util.List;

/**
 * @author huaiziqng
 */

public class Book {
    // 图书唯一标识符（数据库主键）
    private int bookId;
    // 国际标准书号（13位数字）
    private String isbn;
    // 图书标题
    private String title;
    // 作者姓名
    private String author;
    // 出版社名称
    private String publisher;
    // 出版年份（使用short节省存储空间）
    private short publishYear;
    // 所属分类ID（关联分类表）
    private int categoryId;
    // 总馆藏数量
    private int totalCount;
    // 已借出数量
    private int borrowedCount;
    // 图书存放位置信息
    private String location;
    // 索书号（图书唯一标识符）
    private String callNumber;
    // 封面图片存储路径
    private String coverImagePath;

    /**
     * 获取图书唯一标识符
     * @return 返回bookId字段值
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * 设置图书唯一标识符
     * @param bookId 待设置的主键值
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(short publishYear) {
        this.publishYear = publishYear;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    public enum BorrowStatus {
        /** 待审核 - 用户已提交借阅申请，等待管理员审核 */
        PENDING("pending"),
        /** 已批准 - 管理员审核通过，等待用户取书 */
        APPROVED("approved"),
        /** 已借出 - 用户已取书，借阅中 */
        BORROWED("borrowed"),
        /** 已归还 - 用户已归还图书 */
        RETURNED("returned"),
        /** 逾期 - 用户未按时归还图书 */
        OVERDUE("overdue"),
        /** 已拒绝 - 管理员拒绝了借阅申请 */
        REJECTED("rejected");

        // 存储与数据库匹配的字符串值
        private final String value;

        // 构造函数绑定枚举值与字符串
        BorrowStatus(String value) {
            this.value = value;
        }

        // 直接返回字符串值，确保与数据库交互时格式统一
        @Override
        public String toString() {
            return value;
        }
    }

    // 验证 ISBN 格式（13位数字）
    public boolean validateISBN() {
        return isbn != null && isbn.matches("\\d{13}"); // 13位数字正则校验
    }


    // 验证图书库存有效性
    public boolean hasAvailableCopies() {
        return totalCount > 0 && (totalCount - borrowedCount) > 0;
    }
    // 计算当前可借数量
    public int getAvailableCopies() {
        return totalCount - borrowedCount;
    }

    // 更新借阅计数（借出时调用）
    public void incrementBorrowedCount() {
        if (hasAvailableCopies()) {
            borrowedCount++;
        }
    }

    // 归还时减少借阅计数
    public void decrementBorrowedCount() {
        if (borrowedCount > 0) {
            borrowedCount--;
        }
    }
    // 生成图书唯一标识符（结合 ISBN 和馆藏位置）
    public String generateCallNumber() {
        this.callNumber = String.format("%s-%s",
                isbn.substring(isbn.length()-4), // 取 ISBN 最后4位
                location.substring(0, 2).toUpperCase()); // 取位置前2位大写
        return callNumber;
    }

    // 覆盖 toString 方法（便于日志记录）
    @Override
    public String toString() {
        return String.format("Book[ID:%d, Title:'%s', Author:'%s', Available:%d/%d]",
                bookId, title, author, getAvailableCopies(), totalCount);
    }

    // 检查是否当前图书存在逾期借阅
    public boolean hasOverdueRecords(List<BorrowRecord> records) {
        return records.stream()
                .anyMatch(r -> "overdue".equals(r.getStatus()) && r.getBookId() == this.bookId);
    }

}

