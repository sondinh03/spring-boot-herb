-- Thiết lập để giải quyết vấn đề khi xóa bảng có ràng buộc khóa ngoại
SET FOREIGN_KEY_CHECKS = 0;

-- Bảng Disease Group (Nhóm bệnh)
DROP TABLE IF EXISTS disease_group;
CREATE TABLE disease_group (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               group_name VARCHAR(255) NOT NULL,
                               description TEXT DEFAULT NULL,
                               url_slug VARCHAR(255) DEFAULT NULL,
                               display_order INT DEFAULT 0,
                               is_active BOOLEAN DEFAULT TRUE,
                               create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               created_by VARCHAR(255) DEFAULT NULL,
                               modify_date DATETIME DEFAULT NULL,
                               modified_by VARCHAR(255) DEFAULT NULL,
                               INDEX idx_group_name (group_name),
                               INDEX idx_url_slug (url_slug),
                               INDEX idx_is_active (is_active)
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant Family (Họ thực vật)
DROP TABLE IF EXISTS plant_family;
CREATE TABLE plant_family (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              family_name VARCHAR(255) NOT NULL,
                              scientific_name VARCHAR(255) DEFAULT NULL,
                              description TEXT DEFAULT NULL,
                              url_slug VARCHAR(255) DEFAULT NULL,
                              is_active BOOLEAN DEFAULT TRUE,
                              create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(255) DEFAULT NULL,
                              modify_date DATETIME DEFAULT NULL,
                              modified_by VARCHAR(255) DEFAULT NULL,
                              INDEX idx_family_name (family_name),
                              INDEX idx_scientific_name (scientific_name),
                              INDEX idx_url_slug (url_slug)
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng User (Người dùng)
DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      full_name VARCHAR(255) DEFAULT NULL,
--     role ENUM('user', 'editor', 'admin', 'superadmin') DEFAULT 'user',
                      role INT DEFAULT 0,
                      is_active BOOLEAN DEFAULT TRUE,
                      avatar_url VARCHAR(255) DEFAULT NULL,
                      last_login DATETIME DEFAULT NULL,
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT NULL,
                      INDEX idx_username (username),
                      INDEX idx_email (email),
                      INDEX idx_role (role)
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng File (Tệp tin)
DROP TABLE IF EXISTS file;
CREATE TABLE file (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      path VARCHAR(255) NOT NULL,
                      original_name VARCHAR(255) DEFAULT NULL,
                      description VARCHAR(255) DEFAULT NULL,
                      type VARCHAR(50) DEFAULT NULL,
                      mime_type VARCHAR(100) DEFAULT NULL,
                      size INT DEFAULT NULL,
                      width INT DEFAULT NULL,
                      height INT DEFAULT NULL,
                      upload_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      uploaded_by VARCHAR(255) DEFAULT NULL,
                      INDEX idx_file_type (type),
                      INDEX idx_file_name (name)
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Image (Hình ảnh)
DROP TABLE IF EXISTS image;
CREATE TABLE image (
                       id INT PRIMARY KEY,
                       caption VARCHAR(255) DEFAULT NULL,
                       alt_text VARCHAR(255) DEFAULT NULL,
                       is_primary BOOLEAN DEFAULT FALSE,
                       display_order INT DEFAULT 0,
                       CONSTRAINT fk_image_file FOREIGN KEY (id) REFERENCES file(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Keyword (Từ khóa)
DROP TABLE IF EXISTS keyword;
CREATE TABLE keyword (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         slug VARCHAR(255) DEFAULT NULL,
--     keyword_type ENUM('general', 'disease', 'usage', 'region', 'effect', 'property') DEFAULT 'general',
                         keyword_type INT DEFAULT 0,
                         description TEXT DEFAULT NULL,
                         search_count INT DEFAULT 0,
                         create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         modify_date DATETIME DEFAULT NULL,
                         INDEX idx_name (name),
                         INDEX idx_slug (slug),
                         INDEX idx_keyword_type (keyword_type),
                         INDEX idx_search_count (search_count),
                         UNIQUE INDEX idx_slug_unique (slug)
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant (Cây dược liệu)
DROP TABLE IF EXISTS plant;
CREATE TABLE plant (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(255) DEFAULT NULL,
                       modify_date DATETIME DEFAULT NULL,
                       modified_by VARCHAR(255) DEFAULT NULL,
                       vietnamese_name VARCHAR(255) NOT NULL,
                       scientific_name VARCHAR(255) NOT NULL,
                       other_name TEXT DEFAULT NULL,
                       plant_family_id INT DEFAULT NULL,
                       disease_group_id INT DEFAULT NULL,
                       image_url VARCHAR(255) DEFAULT NULL,
                       uses TEXT DEFAULT NULL,
                       distribution TEXT DEFAULT NULL,
                       description TEXT DEFAULT NULL,
                       content TEXT DEFAULT NULL,
                       chemical_composition TEXT DEFAULT NULL,
                       medicinal_properties TEXT DEFAULT NULL,
                       harvesting_processing TEXT DEFAULT NULL,
                       dosage TEXT DEFAULT NULL,
                       contraindications TEXT DEFAULT NULL,
                       url_slug VARCHAR(255) DEFAULT NULL,
                       search_count INT DEFAULT 0,
                       is_featured BOOLEAN DEFAULT FALSE,
                       is_published BOOLEAN DEFAULT FALSE,
                       publish_date DATETIME DEFAULT NULL,
                       meta_title VARCHAR(255) DEFAULT NULL,
                       meta_description TEXT DEFAULT NULL,
                       INDEX idx_vietnamese_name (vietnamese_name),
                       INDEX idx_scientific_name (scientific_name),
                       INDEX idx_plant_family_id (plant_family_id),
                       INDEX idx_disease_group_id (disease_group_id),
                       INDEX idx_search_count (search_count),
                       INDEX idx_url_slug (url_slug),
                       INDEX idx_is_published (is_published),
                       INDEX idx_is_featured (is_featured),
                       FULLTEXT INDEX ft_plant_content (vietnamese_name, scientific_name, other_name, medicinal_properties, uses, description, content),
                       CONSTRAINT fk_plant_family FOREIGN KEY (plant_family_id) REFERENCES plant_family(id) ON DELETE SET NULL,
                       CONSTRAINT fk_plant_disease_group FOREIGN KEY (disease_group_id) REFERENCES disease_group(id) ON DELETE SET NULL
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant Image (Hình ảnh cây)
DROP TABLE IF EXISTS plant_image;
CREATE TABLE plant_image (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             plant_id INT NOT NULL,
                             image_id INT NOT NULL,
                             is_primary BOOLEAN DEFAULT FALSE,
                             caption VARCHAR(255) DEFAULT NULL,
--     image_type ENUM('general', 'leaf', 'flower', 'root', 'stem', 'fruit', 'seed', 'bark') DEFAULT 'general',
                             image_type INT DEFAULT 0,
                             display_order INT DEFAULT 0,
                             create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                             INDEX idx_plant_id (plant_id),
                             INDEX idx_image_id (image_id),
                             INDEX idx_is_primary (is_primary),
                             CONSTRAINT fk_pi_plant FOREIGN KEY (plant_id) REFERENCES plant(id) ON DELETE CASCADE,
                             CONSTRAINT fk_pi_image FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Plant Keyword (Từ khóa cây)
DROP TABLE IF EXISTS plant_keyword;
CREATE TABLE plant_keyword (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               plant_id INT NOT NULL,
                               keyword_id INT NOT NULL,
                               relevance INT DEFAULT 100, -- 0-100 mức độ liên quan
                               create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               INDEX idx_plant_id (plant_id),
                               INDEX idx_keyword_id (keyword_id),
                               UNIQUE INDEX idx_plant_keyword (plant_id, keyword_id),
                               CONSTRAINT fk_pk_plant FOREIGN KEY (plant_id) REFERENCES plant(id) ON DELETE CASCADE,
                               CONSTRAINT fk_pk_keyword FOREIGN KEY (keyword_id) REFERENCES keyword(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Article (Bài viết)
DROP TABLE IF EXISTS article;
CREATE TABLE article (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         created_by VARCHAR(255) DEFAULT NULL,
                         modify_date DATETIME DEFAULT NULL,
                         modified_by VARCHAR(255) DEFAULT NULL,
                         plant_id INT DEFAULT NULL,
                         title VARCHAR(255) NOT NULL,
                         url_slug VARCHAR(255) DEFAULT NULL,
                         content TEXT NOT NULL,
                         summary TEXT DEFAULT NULL,
--     article_type ENUM('general', 'plant_info', 'research', 'news', 'guide') DEFAULT 'general',
--     status ENUM('draft', 'pending', 'published', 'archived') DEFAULT 'draft',
                         article_type INT DEFAULT 0,
                         status INT DEFAULT 0,
                         thumbnail_url VARCHAR(255) DEFAULT NULL,
                         view_count INT DEFAULT 0,
                         publish_date DATETIME DEFAULT NULL,
                         is_featured BOOLEAN DEFAULT FALSE,
                         meta_title VARCHAR(255) DEFAULT NULL,
                         meta_description TEXT DEFAULT NULL,
                         INDEX idx_plant_id (plant_id),
                         INDEX idx_title (title),
                         INDEX idx_url_slug (url_slug),
                         INDEX idx_article_type (article_type),
                         INDEX idx_status (status),
                         INDEX idx_view_count (view_count),
                         INDEX idx_create_date (create_date),
                         INDEX idx_is_featured (is_featured),
                         FULLTEXT INDEX ft_article_content (title, content, summary),
                         CONSTRAINT fk_article_plant FOREIGN KEY (plant_id) REFERENCES plant(id) ON DELETE SET NULL
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Article Image (Hình ảnh bài viết)
DROP TABLE IF EXISTS article_image;
CREATE TABLE article_image (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               article_id INT NOT NULL,
                               image_id INT NOT NULL,
                               caption VARCHAR(255) DEFAULT NULL,
                               display_order INT DEFAULT 0,
                               create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               INDEX idx_article_id (article_id),
                               INDEX idx_image_id (image_id),
                               CONSTRAINT fk_ai_article FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
                               CONSTRAINT fk_ai_image FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Article Keyword (Từ khóa bài viết)
DROP TABLE IF EXISTS article_keyword;
CREATE TABLE article_keyword (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 article_id INT NOT NULL,
                                 keyword_id INT NOT NULL,
                                 relevance INT DEFAULT 100, -- 0-100 mức độ liên quan
                                 create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 INDEX idx_article_id (article_id),
                                 INDEX idx_keyword_id (keyword_id),
                                 UNIQUE INDEX idx_article_keyword (article_id, keyword_id),
                                 CONSTRAINT fk_ak_article FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_ak_keyword FOREIGN KEY (keyword_id) REFERENCES keyword(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Comment (Bình luận)
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         created_by VARCHAR(255) DEFAULT NULL,
                         modify_date DATETIME DEFAULT NULL,
                         modified_by VARCHAR(255) DEFAULT NULL,
                         user_id INT DEFAULT NULL,
                         article_id INT DEFAULT NULL,
                         plant_id INT DEFAULT NULL,
                         parent_id INT DEFAULT NULL, -- Cho phép comment phản hồi
                         content TEXT NOT NULL,
--     status ENUM('pending', 'approved', 'spam', 'rejected') DEFAULT 'pending',
                         status INT DEFAULT 0,
                         INDEX idx_user_id (user_id),
                         INDEX idx_article_id (article_id),
                         INDEX idx_plant_id (plant_id),
                         INDEX idx_parent_id (parent_id),
                         INDEX idx_create_date (create_date),
                         INDEX idx_status (status),
                         CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL,
                         CONSTRAINT fk_comment_article FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
                         CONSTRAINT fk_comment_plant FOREIGN KEY (plant_id) REFERENCES plant(id) ON DELETE CASCADE,
                         CONSTRAINT fk_comment_parent FOREIGN KEY (parent_id) REFERENCES comment(id) ON DELETE CASCADE
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bảng Search Log (Lịch sử tìm kiếm)
DROP TABLE IF EXISTS search_log;
CREATE TABLE search_log (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT DEFAULT NULL,
                            query VARCHAR(255) NOT NULL,
                            results_count INT DEFAULT 0,
                            created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            ip_address VARCHAR(45) DEFAULT NULL,
                            user_agent VARCHAR(255) DEFAULT NULL,
                            session_id VARCHAR(255) DEFAULT NULL,
                            INDEX idx_user_id (user_id),
                            INDEX idx_query (query),
                            INDEX idx_created_date (created_date),
                            CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE SET NULL
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;