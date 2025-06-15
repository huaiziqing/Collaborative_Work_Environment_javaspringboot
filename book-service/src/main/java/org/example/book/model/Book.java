package org.example.book.model;

import java.util.List;

/**
 * @author huaiziqng
 */

public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private short publishYear;
    private int categoryId;
    private int totalCount;
    private int borrowedCount;
    private String location;
    private String callNumber;
    private String coverImagePath;

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

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
        PENDING("pending"),
        APPROVED("approved"),
        BORROWED("borrowed"),
        RETURNED("returned"),
        OVERDUE("overdue"),
        REJECTED("rejected");

        private final String value;

        BorrowStatus(String value) {
            this.value = value;
        }

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
