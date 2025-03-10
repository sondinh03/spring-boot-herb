
-- Bảng Disease Group
DROP TABLE IF EXISTS disease_group;
CREATE TABLE disease_group (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               create_date DATETIME,
                               created_by VARCHAR(255),
                               modify_date DATETIME,
                               modified_by VARCHAR(255),
                               group_name VARCHAR(255) NOT NULL,
                               description VARCHAR(255)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant Family
DROP TABLE IF EXISTS plant_family;
CREATE TABLE plant_family (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              create_date DATETIME,
                              created_by VARCHAR(255),
                              modify_date DATETIME,
                              modified_by VARCHAR(255),
                              family_name VARCHAR(255) NOT NULL,
                              description VARCHAR(255)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant
DROP TABLE IF EXISTS plant;
CREATE TABLE plant (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       create_date DATETIME,
                       created_by VARCHAR(255),
                       modify_date DATETIME,
                       modified_by VARCHAR(255),
                       vietnamese_name VARCHAR(255) NOT NULL,
                       scientific_name VARCHAR(255) NOT NULL,
                       plant_family_id INT,
                       disease_group_id INT,
                       image_url VARCHAR(255),
                       uses VARCHAR(255),
                       distribution VARCHAR(255),
                       description VARCHAR(255),
                       content TEXT,
                       FOREIGN KEY (plant_family_id) REFERENCES plant_family(id),
                       FOREIGN KEY (disease_group_id) REFERENCES disease_group(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Image
DROP TABLE IF EXISTS image;
CREATE TABLE image (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       create_date DATETIME,
                       created_by VARCHAR(255),
                       modify_date DATETIME,
                       modified_by VARCHAR(255),
                       name VARCHAR(255) NOT NULL,
                       path VARCHAR(255) NOT NULL,
                       is_primary BOOLEAN,
                       description VARCHAR(255),
                       type VARCHAR(50),
                       size INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng User
DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      create_date DATETIME,
                      created_by VARCHAR(255),
                      modify_date DATETIME,
                      modified_by VARCHAR(255),
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      full_name VARCHAR(255),
                      role INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Article
DROP TABLE IF EXISTS article;
CREATE TABLE article (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         create_date DATETIME,
                         created_by VARCHAR(255),
                         modify_date DATETIME,
                         modified_by VARCHAR(255),
                         plant_id INT,
                         title VARCHAR(255) NOT NULL,
                         content TEXT NOT NULL,
                         summary TEXT,
                         type INT,
                         status INT,
                         view_count INT DEFAULT 0,
                         FOREIGN KEY (plant_id) REFERENCES plant(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Comment
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         create_date DATETIME,
                         created_by VARCHAR(255),
                         modify_date DATETIME,
                         modified_by VARCHAR(255),
                         user_id INT,
                         article_id INT,
                         content TEXT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES user(id),
                         FOREIGN KEY (article_id) REFERENCES article(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Keyword
DROP TABLE IF EXISTS keyword;
CREATE TABLE keyword (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         create_date DATETIME,
                         created_by VARCHAR(255),
                         modify_date DATETIME,
                         modified_by VARCHAR(255),
                         name VARCHAR(255) NOT NULL,
                         type INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant Keyword
DROP TABLE IF EXISTS plant_keyword;
CREATE TABLE plant_keyword (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               plant_id INT,
                               keyword_id INT,
                               FOREIGN KEY (plant_id) REFERENCES plant(id),
                               FOREIGN KEY (keyword_id) REFERENCES keyword(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Article Keyword
DROP TABLE IF EXISTS article_keyword;
CREATE TABLE article_keyword (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 article_id INT,
                                 keyword_id INT,
                                 FOREIGN KEY (article_id) REFERENCES article(id),
                                 FOREIGN KEY (keyword_id) REFERENCES keyword(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Search Log
DROP TABLE IF EXISTS search_log;
CREATE TABLE search_log (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT,
                            query VARCHAR(255) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (user_id) REFERENCES user(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;