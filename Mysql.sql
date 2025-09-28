-- 协同办公系统数据库初始化脚本
-- 包含图书管理系统和服务器资源管理系统

-- 1. 创建图书管理数据库
CREATE DATABASE IF NOT EXISTS library_rental_system
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE library_rental_system;

-- 2. 创建分类表（需最先创建，被图书表引用）
CREATE TABLE Category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- 3. 创建用户表（被借阅记录表引用）
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    role ENUM('admin', 'teacher', 'student') NOT NULL,
    email VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. 创建图书表（核心表）
CREATE TABLE Book (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    publish_year YEAR,
    category_id INT,
    total_count INT DEFAULT 0,
    borrowed_count INT DEFAULT 0,
    location VARCHAR(50),
    call_number VARCHAR(20),
    cover_image_path VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

-- 5. 创建借阅记录表（核心业务表）
CREATE TABLE BorrowRecord (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    audit_time DATETIME,
    borrow_time DATETIME,
    due_time DATETIME,
    return_time DATETIME,
    status ENUM('pending', 'approved', 'borrowed', 'returned', 'overdue', 'rejected', 'canceled') NOT NULL DEFAULT 'pending',
    auditor_id INT,
    reject_reason TEXT,
    FOREIGN KEY (book_id) REFERENCES Book(book_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (auditor_id) REFERENCES User(user_id)
);

-- 6. 创建通知表
CREATE TABLE Notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    borrow_record_id INT NOT NULL,
    content TEXT NOT NULL,
    type ENUM('due_reminder', 'overdue_alert') NOT NULL,
    sent_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (borrow_record_id) REFERENCES BorrowRecord(record_id)
);

-- 7. 添加索引优化查询性能
CREATE INDEX idx_book_title ON Book(title);
CREATE INDEX idx_book_category ON Book(category_id);
CREATE INDEX idx_borrow_status ON BorrowRecord(status);
CREATE INDEX idx_borrow_due_time ON BorrowRecord(due_time);

-- 8. 插入初始数据
INSERT INTO Category (name) VALUES ('文学'), ('科技'), ('历史');

INSERT INTO User (username, password, real_name, role, email)
VALUES
    ('admin', 'hash1', '张管理员', 'admin', 'admin@school.edu'),
    ('t_li', 'hash2', '李老师', 'teacher', 'li@school.edu'),
    ('s_wang', 'hash3', '王同学', 'student', 'wang@student.edu');

INSERT INTO Book (isbn, title, author, publisher, publish_year, category_id, total_count, borrowed_count, location, call_number)
VALUES
    ('9787101003048', '红楼梦', '曹雪芹', '人民文学出版社', 1996, 1, 5, 0, 'A区3排', 'I242.4/123'),
    ('9787111636665', 'Python编程', 'Mark Lutz', '机械工业出版社', 2018, 2, 3, 0, 'B区1排', 'TP311/456');

-- 9. 创建服务器资源管理数据库
CREATE DATABASE IF NOT EXISTS server_rental_system;
USE server_rental_system;

-- 10. 创建用户视图（与图书管理系统共享用户数据）
CREATE VIEW User AS SELECT * FROM library_rental_system.User;

-- 11. 资源分类表
CREATE TABLE ResourceCategory (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- 12. 服务器资源表
CREATE TABLE ServerResource (
    resource_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    cpu_capacity DECIMAL(5,2) NOT NULL,
    memory_capacity DECIMAL(5,2) NOT NULL,
    storage_capacity DECIMAL(10,2) NOT NULL,
    location VARCHAR(100),
    status ENUM('idle', 'in_use', 'maintenance') NOT NULL DEFAULT 'idle',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);

-- 13. 资源-分类关联表
CREATE TABLE ResourceCategoryMapping (
    resource_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (resource_id, category_id),
    FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id),
    FOREIGN KEY (category_id) REFERENCES ResourceCategory(category_id)
);

-- 14. 租借申请表
CREATE TABLE RentApplication (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    resource_id INT NOT NULL,
    applicant_id INT NOT NULL,
    expected_cpu DECIMAL(5,2) NOT NULL,
    expected_memory DECIMAL(5,2) NOT NULL,
    purpose TEXT,
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    start_time DATETIME,
    end_time DATETIME,
    duration INT,
    status ENUM('pending', 'approved', 'rejected', 'canceled') NOT NULL DEFAULT 'pending',
    auditor_id INT,
    audit_time DATETIME,
    reject_reason TEXT,
    FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id),
    FOREIGN KEY (applicant_id) REFERENCES library_rental_system.User(user_id),
    FOREIGN KEY (auditor_id) REFERENCES library_rental_system.User(user_id)
);

-- 15. 租借记录表
CREATE TABLE RentRecord (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT NOT NULL,
    resource_id INT NOT NULL,
    user_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    due_time DATETIME NOT NULL,
    expected_cpu DECIMAL(5,2) NOT NULL,
    expected_memory DECIMAL(5,2) NOT NULL,
    actual_cpu_usage DECIMAL(5,2),
    actual_memory_usage DECIMAL(5,2),
    status ENUM('borrowed', 'returned', 'overdue', 'paused') NOT NULL DEFAULT 'borrowed',
    pause_count INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id),
    FOREIGN KEY (user_id) REFERENCES library_rental_system.User(user_id),
    FOREIGN KEY (application_id) REFERENCES RentApplication(application_id)
);

-- 16. 资源使用历史表
CREATE TABLE ResourceUsageHistory (
    history_id INT PRIMARY KEY AUTO_INCREMENT,
    record_id INT NOT NULL,
    resource_id INT NOT NULL,
    cpu_usage DECIMAL(5,2) NOT NULL,
    memory_usage DECIMAL(5,2) NOT NULL,
    storage_usage DECIMAL(10,2) NOT NULL,
    recorded_time DATETIME NOT NULL,
    FOREIGN KEY (record_id) REFERENCES RentRecord(record_id),
    FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id)
);

-- 17. 通知表（扩展图书管理系统的通知表）
CREATE TABLE IF NOT EXISTS Notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    rent_record_id INT,
    content TEXT NOT NULL,
    type ENUM('due_reminder', 'overdue_alert', 'server_due_reminder', 'server_overdue_alert') NOT NULL,
    sent_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES library_rental_system.User(user_id),
    FOREIGN KEY (rent_record_id) REFERENCES RentRecord(record_id)
);

-- 18. 添加索引
CREATE INDEX idx_resource_status ON ServerResource(status);
CREATE INDEX idx_resource_cpu ON ServerResource(cpu_capacity);
CREATE INDEX idx_resource_memory ON ServerResource(memory_capacity);
CREATE INDEX idx_application_status ON RentApplication(status);
CREATE INDEX idx_application_time ON RentApplication(apply_time);
CREATE INDEX idx_record_status ON RentRecord(status);
CREATE INDEX idx_record_due_time ON RentRecord(due_time);
CREATE INDEX idx_usage_time ON ResourceUsageHistory(recorded_time);
CREATE INDEX idx_usage_cpu ON ResourceUsageHistory(cpu_usage);

-- 19. 插入服务器资源初始数据
INSERT INTO ResourceCategory (name, description) VALUES
    ('GPU服务器', '用于深度学习训练的高性能GPU服务器'),
    ('计算服务器', '高性能CPU计算服务器'),
    ('存储服务器', '大容量数据存储服务器'),
    ('数据库服务器', '专用数据库服务服务器');

INSERT INTO ServerResource (name, description, cpu_capacity, memory_capacity, storage_capacity, location, status) VALUES
    ('GPU-001', 'NVIDIA A100 80GB x4', 64.0, 256.0, 2000.0, '数据中心A-机架3', 'idle'),
    ('Compute-101', 'AMD EPYC 64核', 128.0, 512.0, 4000.0, '数据中心B-机架5', 'in_use'),
    ('Storage-201', '200TB NAS存储', 16.0, 64.0, 200000.0, '数据中心C-机架2', 'idle'),
    ('DB-301', 'Oracle专用服务器', 32.0, 128.0, 8000.0, '数据中心A-机架7', 'maintenance'),
    ('GPU-002', 'NVIDIA H100 80GB x8', 96.0, 512.0, 4000.0, '数据中心D-机架1', 'idle');

INSERT INTO ResourceCategoryMapping (resource_id, category_id) VALUES
    (1, 1),  -- GPU-001 -> GPU服务器
    (2, 2),  -- Compute-101 -> 计算服务器
    (3, 3),  -- Storage-201 -> 存储服务器
    (4, 4),  -- DB-301 -> 数据库服务器
    (5, 1);  -- GPU-002 -> GPU服务器
