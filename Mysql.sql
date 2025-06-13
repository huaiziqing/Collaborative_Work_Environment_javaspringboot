-- 1. 创建数据库
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
                              status ENUM('pending', 'approved', 'borrowed', 'returned', 'overdue', 'rejected') NOT NULL DEFAULT 'pending',
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

SHOW DATABASES;

USE library_rental_system;

SHOW TABLES;

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

CREATE DATABASE IF NOT EXISTS server_rental_system;
USE server_rental_system;

#视图映射（保持数据一致性）
CREATE VIEW User AS SELECT * FROM library_rental_system.User;

#资源分类表
CREATE TABLE ResourceCategory (
                                  category_id INT PRIMARY KEY AUTO_INCREMENT,
                                  name VARCHAR(50) NOT NULL UNIQUE,
                                  description VARCHAR(255)
);

#服务器资源表
CREATE TABLE ServerResource (
                                resource_id INT PRIMARY KEY AUTO_INCREMENT,
                                name VARCHAR(100) NOT NULL,
                                description TEXT,
                                cpu_capacity DECIMAL(5,2) NOT NULL,
                                memory_capacity DECIMAL(5,2) NOT NULL,
                                storage_capacity DECIMAL(5,2) NOT NULL,
                                location VARCHAR(100),
                                status ENUM('idle', 'in_use', 'maintenance') NOT NULL DEFAULT 'idle',
                                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP
);

#资源-分类关联表（依赖ServerResource和ResourceCategory）
CREATE TABLE ResourceCategoryMapping (
                                         resource_id INT NOT NULL,
                                         category_id INT NOT NULL,
                                         PRIMARY KEY (resource_id, category_id),
                                         FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id),
                                         FOREIGN KEY (category_id) REFERENCES ResourceCategory(category_id)
);
USE server_rental_system;

CREATE VIEW User AS
SELECT
    user_id,
    username,
    password,
    real_name,
    role,
    email,
    created_at
FROM library_rental_system.User;

SET FOREIGN_KEY_CHECKS = 0;

#租借申请表（依赖ServerResource和User）
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

SET FOREIGN_KEY_CHECKS = 1;

#租借记录表（依赖RentApplication, ServerResource和User）
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

#资源使用历史表（依赖RentRecord和ServerResource）
CREATE TABLE ResourceUsageHistory (
                                      history_id INT PRIMARY KEY AUTO_INCREMENT,
                                      record_id INT NOT NULL,
                                      resource_id INT NOT NULL,
                                      cpu_usage DECIMAL(5,2) NOT NULL,
                                      memory_usage DECIMAL(5,2) NOT NULL,
                                      storage_usage DECIMAL(5,2) NOT NULL,
                                      recorded_time DATETIME NOT NULL,
                                      FOREIGN KEY (record_id) REFERENCES RentRecord(record_id),
                                      FOREIGN KEY (resource_id) REFERENCES ServerResource(resource_id)
);

#创建通知表（如果尚未存在）
CREATE TABLE IF NOT EXISTS Notification (
                                            notification_id INT PRIMARY KEY AUTO_INCREMENT,
                                            user_id INT NOT NULL,
                                            content TEXT NOT NULL,
                                            type ENUM('due_reminder', 'overdue_alert', 'server_due_reminder', 'server_overdue_alert') NOT NULL,
    sent_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES library_rental_system.User(user_id)
    );

#添加服务器租借关联字段
ALTER TABLE Notification
    ADD COLUMN rent_record_id INT AFTER user_id,
    ADD FOREIGN KEY (rent_record_id) REFERENCES RentRecord(record_id);

-- 资源表索引
CREATE INDEX idx_resource_status ON ServerResource(status);
CREATE INDEX idx_resource_cpu ON ServerResource(cpu_capacity);
CREATE INDEX idx_resource_memory ON ServerResource(memory_capacity);

-- 申请记录索引
CREATE INDEX idx_application_status ON RentApplication(status);
CREATE INDEX idx_application_time ON RentApplication(apply_time);

-- 租借记录索引
CREATE INDEX idx_record_status ON RentRecord(status);
CREATE INDEX idx_record_due_time ON RentRecord(due_time);

-- 使用历史索引
CREATE INDEX idx_usage_time ON ResourceUsageHistory(recorded_time);
CREATE INDEX idx_usage_cpu ON ResourceUsageHistory(cpu_usage);

INSERT INTO library_rental_system.User (username, password, real_name, role, email) VALUES
                                                                                        ('prof_zhang', SHA2('zhang_pass', 256), '张教授', 'teacher', 'zhang@cs.university.edu'),
                                                                                        ('prof_li', SHA2('li_pass', 256), '李教授', 'teacher', 'li@ai.university.edu'),
                                                                                        ('student_wang', SHA2('wang_pass', 256), '王同学', 'student', 'wang@student.university.edu'),
                                                                                        ('student_liu', SHA2('liu_pass', 256), '刘同学', 'student', 'liu@student.university.edu');

INSERT INTO ResourceCategory (name, description) VALUES
                                                     ('GPU服务器', '用于深度学习训练的高性能GPU服务器'),
                                                     ('计算服务器', '高性能CPU计算服务器'),
                                                     ('存储服务器', '大容量数据存储服务器'),
                                                     ('数据库服务器', '专用数据库服务服务器');

ALTER TABLE ServerResource
    MODIFY COLUMN storage_capacity DECIMAL(10,2) NOT NULL;

INSERT INTO ServerResource (name, description, cpu_capacity, memory_capacity, storage_capacity, location, status) VALUES
                                                                                                                      ('GPU-001', 'NVIDIA A100 80GB x4', 64.0, 256.0, 2000.0, '数据中心A-机架3', 'idle'),
                                                                                                                      ('Compute-101', 'AMD EPYC 64核', 128.0, 512.0, 4000.0, '数据中心B-机架5', 'in_use'),
                                                                                                                      ('Storage-201', '200TB NAS存储', 16.0, 64.0, 200000.0, '数据中心C-机架2', 'idle'),
                                                                                                                      ('DB-301', 'Oracle专用服务器', 32.0, 128.0, 8000.0, '数据中心A-机架7', 'maintenance'),
                                                                                                                      ('GPU-002', 'NVIDIA H100 80GB x8', 96.0, 512.0, 4000.0, '数据中心D-机架1', 'idle');

-- ======================
-- 4. 资源分类映射
-- ======================
INSERT INTO ResourceCategoryMapping (resource_id, category_id) VALUES
                                                                   (1, 1),  -- GPU-001 -> GPU服务器
                                                                   (2, 2),  -- Compute-101 -> 计算服务器
                                                                   (3, 3),  -- Storage-201 -> 存储服务器
                                                                   (4, 4),  -- DB-301 -> 数据库服务器
                                                                   (5, 1);  -- GPU-002 -> GPU服务器

SET @admin_id = (SELECT user_id FROM library_rental_system.User WHERE username = 'admin');
SET @prof_zhang_id = (SELECT user_id FROM library_rental_system.User WHERE username = 'prof_zhang');
SET @prof_li_id = (SELECT user_id FROM library_rental_system.User WHERE username = 'prof_li');
SET @student_wang_id = (SELECT user_id FROM library_rental_system.User WHERE username = 'student_wang');
SET @student_liu_id = (SELECT user_id FROM library_rental_system.User WHERE username = 'student_liu');


-- 插入租借申请
INSERT INTO RentApplication (resource_id, applicant_id, expected_cpu, expected_memory, purpose,
                             apply_time, start_time, duration, status, auditor_id, audit_time) VALUES
-- 教师申请（直接批准）
(1, @prof_zhang_id, 32.0, 128.0, '图像识别模型训练',
 '2023-10-01 09:00:00', '2023-10-02 10:00:00', 30, 'approved', @admin_id, '2023-10-01 14:30:00'),

-- 学生申请（待审核）
(5, @student_wang_id, 24.0, 96.0, '毕业设计-大语言模型研究',
 '2023-10-05 11:20:00', NULL, 60, 'pending', NULL, NULL),

-- 学生申请（已批准）
(3, @student_liu_id, 8.0, 32.0, '研究数据存储',
 '2023-10-03 14:15:00', '2023-10-05 09:00:00', 45, 'approved', @prof_li_id, '2023-10-04 10:00:00'),

-- 教师申请（已拒绝）
(1, @prof_li_id, 48.0, 192.0, '自然语言处理研究',
 '2023-09-28 16:45:00', NULL, 30, 'rejected', @admin_id, '2023-09-29 09:30:00'),

-- 学生申请（已取消）
(4, @student_wang_id, 16.0, 64.0, '数据库课程实验',
 '2023-10-02 13:10:00', NULL, 15, 'canceled', NULL, NULL);

-- 更新拒绝理由
UPDATE RentApplication
SET reject_reason = '请求资源超过服务器剩余容量'
WHERE application_id = 4;

-- ======================
-- 6. 租借记录数据
-- ======================
INSERT INTO RentRecord (application_id, resource_id, user_id, start_time, end_time, due_time,
                        expected_cpu, expected_memory, actual_cpu_usage, actual_memory_usage, status) VALUES
-- 进行中的租借
(1, 1, @prof_zhang_id, '2023-10-02 10:00:00', NULL, '2023-11-01 10:00:00',
 32.0, 128.0, 28.5, 110.2, 'borrowed'),

-- 已归还的租借
(3, 3, @student_liu_id, '2023-10-05 09:00:00', '2023-10-20 14:00:00', '2023-11-19 09:00:00',
 8.0, 32.0, 7.2, 30.5, 'returned'),

-- 逾期的租借
(1, 2, @prof_zhang_id, '2023-09-01 10:00:00', NULL, '2023-10-01 10:00:00',
 64.0, 256.0, 60.5, 240.0, 'overdue'),

-- 暂停中的租借
(1, 1, @prof_li_id, '2023-10-10 14:00:00', NULL, '2023-11-09 14:00:00',
 24.0, 96.0, 22.0, 90.0, 'paused');

-- ======================
-- 7. 资源使用历史数据
-- ======================
-- 为进行中的GPU租借添加使用记录
INSERT INTO ResourceUsageHistory (record_id, resource_id, cpu_usage, memory_usage, storage_usage, recorded_time) VALUES
                                                                                                                     (1, 1, 28.5, 110.2, 450.0, '2023-10-15 09:00:00'),
                                                                                                                     (1, 1, 29.8, 115.5, 480.0, '2023-10-15 12:00:00'),
                                                                                                                     (1, 1, 27.2, 105.8, 460.0, '2023-10-15 15:00:00');

SHOW COLUMNS FROM ResourceUsageHistory;

ALTER TABLE ResourceUsageHistory
    MODIFY COLUMN storage_usage DECIMAL(10,2) NOT NULL;
-- 为已归还的存储服务器添加使用记录
INSERT INTO ResourceUsageHistory (record_id, resource_id, cpu_usage, memory_usage, storage_usage, recorded_time) VALUES

    (2, 3, 5.5, 28.2, 1800.0, '2023-10-20 09:00:00');

-- ======================
-- 8. 通知数据
-- ======================
INSERT INTO Notification (user_id, rent_record_id, content, type, sent_time) VALUES
                                                                                 (@student_liu_id, 2, '您的存储服务器租借将于3天后到期，请及时处理', 'server_due_reminder', '2023-10-16 08:00:00'),
                                                                                 (@prof_zhang_id, 3, '您的计算服务器租借已逾期5天，请立即处理', 'server_overdue_alert', '2023-10-06 09:30:00'),
                                                                                 (@student_wang_id, NULL, '您的图书《Python高级编程》将于明天到期', 'due_reminder', '2023-10-18 10:00:00');