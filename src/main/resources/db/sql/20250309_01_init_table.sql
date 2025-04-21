-- Tạo cơ sở dữ liệu
-- CREATE DATABASE IF NOT EXISTS herbal_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE herbal_db;

-- Tạo bảng users
-- status: 1 = ACTIVE, 2 = INACTIVE, 3 = BANNED, 4 = PENDING, 5 = DELETED
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       role_type INT NOT NULL DEFAULT 3, -- 1=ADMIN, 2=EDITOR, 3=USER
                       status INT NOT NULL DEFAULT 4, -- Mặc định là PENDING
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       created_by VARCHAR(50) NOT NULL,
                       updated_by VARCHAR(50) NOT NULL,
                       UNIQUE INDEX idx_username (username),
                       UNIQUE INDEX idx_email (email)
);

-- Tạo bảng diseases
CREATE TABLE diseases (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          slug VARCHAR(100) NOT NULL,
                          description TEXT,
                          parent_id INT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          created_by VARCHAR(50) NOT NULL,
                          updated_by VARCHAR(50) NOT NULL,
                          UNIQUE INDEX idx_slug (slug),
                          INDEX idx_parent_id (parent_id),
                          FOREIGN KEY (parent_id) REFERENCES diseases(id) ON DELETE SET NULL
);

-- Tạo bảng media
-- file_type: 1 = image, 2 = video, 3 = document
CREATE TABLE media (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       file_name VARCHAR(255) NOT NULL,
                       file_path VARCHAR(255) NOT NULL,
                       file_type INT NOT NULL,
                       file_size INT NOT NULL,
                       alt_text VARCHAR(255),
                       uploaded_by INT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       created_by VARCHAR(50) NOT NULL,
                       updated_by VARCHAR(50) NOT NULL,
                       INDEX idx_uploaded_by (uploaded_by),
                       FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Tạo bảng tags
CREATE TABLE tags (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      slug VARCHAR(50) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      created_by VARCHAR(50) NOT NULL,
                      updated_by VARCHAR(50) NOT NULL,
                      UNIQUE INDEX idx_slug (slug)
);

-- Tạo bảng plants
-- status: 1 = DRAFT, 2 = PENDING_REVIEW, 3 = PUBLISHED, 4 = ARCHIVED
CREATE TABLE plants (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        scientific_name VARCHAR(100) NOT NULL,
                        slug VARCHAR(100) NOT NULL,
                        family VARCHAR(100),
                        genus VARCHAR(100),
                        other_names TEXT,
                        parts_used TEXT,
                        description TEXT,
                        botanical_characteristics TEXT,
                        chemical_composition TEXT,
                        distribution TEXT,
                        altitude VARCHAR(100),
                        harvest_season VARCHAR(100),
                        ecology TEXT,
                        medicinal_uses TEXT,
                        indications TEXT,
                        contraindications TEXT,
                        dosage TEXT,
                        folk_remedies TEXT,
                        side_effects TEXT,
                        status INT NOT NULL DEFAULT 1, -- Loại bỏ chiều rộng hiển thị
                        featured TINYINT NOT NULL DEFAULT 0, -- Loại bỏ chiều rộng hiển thị
                        views INT NOT NULL DEFAULT 0, -- Loại bỏ chiều rộng hiển thị
                        created_by VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_by VARCHAR(50) NOT NULL,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        UNIQUE INDEX idx_slug (slug)
);

-- Tạo bảng plant_diseases
CREATE TABLE plant_diseases (
                                plant_id INT NOT NULL,
                                disease_id INT NOT NULL,
                                PRIMARY KEY (plant_id, disease_id),
                                INDEX idx_plant_id (plant_id),
                                INDEX idx_disease_id (disease_id),
                                FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE,
                                FOREIGN KEY (disease_id) REFERENCES diseases(id) ON DELETE CASCADE
);

-- Tạo bảng plant_media
CREATE TABLE plant_media (
                             plant_id INT NOT NULL, -- Loại bỏ chiều rộng hiển thị
                             media_id INT NOT NULL, -- Loại bỏ chiều rộng hiển thị
                             is_featured TINYINT NOT NULL DEFAULT 0, -- Loại bỏ chiều rộng hiển thị
                             PRIMARY KEY (plant_id, media_id),
                             INDEX idx_plant_id (plant_id),
                             INDEX idx_media_id (media_id),
                             FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE,
                             FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

-- Tạo bảng plant_tags
CREATE TABLE plant_tags (
                            plant_id INT NOT NULL,
                            tag_id INT NOT NULL,
                            PRIMARY KEY (plant_id, tag_id),
                            INDEX idx_plant_id (plant_id),
                            INDEX idx_tag_id (tag_id),
                            FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE,
                            FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Tạo bảng articles
-- status: 1 = DRAFT, 2 = PENDING_REVIEW, 3 = PUBLISHED, 4 = ARCHIVED
CREATE TABLE articles (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          slug VARCHAR(255) NOT NULL,
                          excerpt TEXT,
                          content TEXT NOT NULL,
                          featured_image INT, -- Loại bỏ chiều rộng hiển thị
                          status INT NOT NULL DEFAULT 1, -- Loại bỏ chiều rộng hiển thị
                          is_featured TINYINT NOT NULL DEFAULT 0, -- Loại bỏ chiều rộng hiển thị
                          allow_comments TINYINT NOT NULL DEFAULT 1, -- Loại bỏ chiều rộng hiển thị
                          views INT NOT NULL DEFAULT 0, -- Loại bỏ chiều rộng hiển thị
                          author_id INT, -- Loại bỏ chiều rộng hiển thị
                          disease_id INT, -- Loại bỏ chiều rộng hiển thị
                          published_at TIMESTAMP NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          created_by VARCHAR(50) NOT NULL,
                          updated_by VARCHAR(50) NOT NULL,
                          UNIQUE INDEX idx_slug (slug),
                          INDEX idx_author_id (author_id),
                          INDEX idx_disease_id (disease_id),
                          INDEX idx_featured_image (featured_image),
                          FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL,
                          FOREIGN KEY (disease_id) REFERENCES diseases(id) ON DELETE SET NULL,
                          FOREIGN KEY (featured_image) REFERENCES media(id) ON DELETE SET NULL
);

-- Tạo bảng article_tags
CREATE TABLE article_tags (
                              article_id INT NOT NULL,
                              tag_id INT NOT NULL,
                              PRIMARY KEY (article_id, tag_id),
                              INDEX idx_article_id (article_id),
                              INDEX idx_tag_id (tag_id),
                              FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
                              FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Tạo bảng article_plants
CREATE TABLE article_plants (
                                article_id INT NOT NULL,
                                plant_id INT NOT NULL,
                                PRIMARY KEY (article_id, plant_id),
                                INDEX idx_article_id (article_id),
                                INDEX idx_plant_id (plant_id),
                                FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
                                FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE
);

-- Tạo bảng experts
-- status: 1 = ACTIVE, 2 = INACTIVE, 3 = PENDING_APPROVAL, 4 = SUSPENDED
CREATE TABLE experts (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         slug VARCHAR(100) NOT NULL,
                         title VARCHAR(100),
                         specialization VARCHAR(100),
                         institution VARCHAR(100),
                         education TEXT,
                         bio TEXT,
                         achievements TEXT,
                         contact_email VARCHAR(100),
                         avatar INT,
                         status INT NOT NULL DEFAULT 3, -- Mặc định là PENDING_APPROVAL
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         created_by VARCHAR(50) NOT NULL,
                         updated_by VARCHAR(50) NOT NULL,
                         UNIQUE INDEX idx_slug (slug),
                         INDEX idx_avatar (avatar),
                         FOREIGN KEY (avatar) REFERENCES media(id) ON DELETE SET NULL
);

-- Tạo bảng expert_plants
CREATE TABLE expert_plants (
                               expert_id INT NOT NULL,
                               plant_id INT NOT NULL,
                               PRIMARY KEY (expert_id, plant_id),
                               INDEX idx_expert_id (expert_id),
                               INDEX idx_plant_id (plant_id),
                               FOREIGN KEY (expert_id) REFERENCES experts(id) ON DELETE CASCADE,
                               FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE
);

-- Tạo bảng research
-- status: 1 = DRAFT, 2 = UNDER_REVIEW, 3 = PUBLISHED, 4 = ARCHIVED
CREATE TABLE research (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          slug VARCHAR(255) NOT NULL,
                          abstract TEXT,
                          content TEXT NOT NULL,
                          authors TEXT,
                          institution VARCHAR(255),
                          published_year INT,
                          journal VARCHAR(255),
                          field VARCHAR(100),
                          status INT NOT NULL DEFAULT 1, -- Mặc định là DRAFT
                          views INT NOT NULL DEFAULT 0,
                          created_by VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_by VARCHAR(50) NOT NULL,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          UNIQUE INDEX idx_slug (slug)
);

-- Tạo bảng research_plants
CREATE TABLE research_plants (
                                 research_id INT NOT NULL,
                                 plant_id INT NOT NULL,
                                 PRIMARY KEY (research_id, plant_id),
                                 INDEX idx_research_id (research_id),
                                 INDEX idx_plant_id (plant_id),
                                 FOREIGN KEY (research_id) REFERENCES research(id) ON DELETE CASCADE,
                                 FOREIGN KEY (plant_id) REFERENCES plants(id) ON DELETE CASCADE
);

-- Tạo bảng research_tags
CREATE TABLE research_tags (
                               research_id INT NOT NULL,
                               tag_id INT NOT NULL,
                               PRIMARY KEY (research_id, tag_id),
                               INDEX idx_research_id (research_id),
                               INDEX idx_tag_id (tag_id),
                               FOREIGN KEY (research_id) REFERENCES research(id) ON DELETE CASCADE,
                               FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Tạo bảng comments
-- status: 1 = PENDING, 2 = APPROVED, 3 = SPAM, 4 = TRASH
CREATE TABLE comments (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          content TEXT NOT NULL,
                          user_id INT,
                          article_id INT,
                          parent_id INT,
                          status INT NOT NULL DEFAULT 1, -- Mặc định là PENDING
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          created_by VARCHAR(50) NOT NULL,
                          updated_by VARCHAR(50) NOT NULL,
                          INDEX idx_user_id (user_id),
                          INDEX idx_article_id (article_id),
                          INDEX idx_parent_id (parent_id),
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
                          FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
                          FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE SET NULL
);

-- Thêm dữ liệu vào bảng users
-- status: 1 = ACTIVE, 4 = PENDING
INSERT INTO users (username, email, password, full_name, role_type, status, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                                                ('admin', 'admin@duoclieuvn.com', '$2a$10$yfIAJbq5Kn0wQGlE6Ru.4OALqXrBJLnvW9Je3iGmf4fP9JbpAjBOe', 'Quản trị viên', 1, 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                ('editor', 'editor@duoclieuvn.com', '$2a$10$yfIAJbq5Kn0wQGlE6Ru.4OALqXrBJLnvW9Je3iGmf4fP9JbpAjBOe', 'Biên tập viên', 2, 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                ('user1', 'user1@example.com', '$2a$10$yfIAJbq5Kn0wQGlE6Ru.4OALqXrBJLnvW9Je3iGmf4fP9JbpAjBOe', 'Nguyễn Văn A', 3, 4, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                ('user2', 'user2@example.com', '$2a$10$yfIAJbq5Kn0wQGlE6Ru.4OALqXrBJLnvW9Je3iGmf4fP9JbpAjBOe', 'Trần Thị B', 3, 4, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                ('user3', 'user3@example.com', '$2a$10$yfIAJbq5Kn0wQGlE6Ru.4OALqXrBJLnvW9Je3iGmf4fP9JbpAjBOe', 'Lê Văn C', 3, 4, NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng diseases
INSERT INTO diseases (name, slug, description, parent_id, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                              ('Bệnh gan', 'benh-gan', 'Các bệnh liên quan đến gan như viêm gan, xơ gan, sỏi mật', NULL, NOW(), 'admin', NOW(), 'admin'),
                                                                                                              ('Bệnh tiêu hóa', 'benh-tieu-hoa', 'Các bệnh liên quan đến hệ tiêu hóa như viêm loét dạ dày, đầy hơi', NULL, NOW(), 'admin', NOW(), 'admin'),
                                                                                                              ('Bệnh huyết áp', 'benh-huyet-ap', 'Các bệnh liên quan đến huyết áp cao hoặc thấp', NULL, NOW(), 'admin', NOW(), 'admin'),
                                                                                                              ('Bệnh miễn dịch', 'benh-mien-dich', 'Các bệnh liên quan đến hệ miễn dịch yếu', NULL, NOW(), 'admin', NOW(), 'admin'),
                                                                                                              ('Viêm nhiễm', 'viem-nhiem', 'Các bệnh liên quan đến viêm nhiễm như viêm họng, viêm phổi', NULL, NOW(), 'admin', NOW(), 'admin'),
                                                                                                              ('Sỏi thận', 'soi-than', 'Các bệnh liên quan đến sỏi thận và tiết niệu', NULL, NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng media
-- file_type: 1 = image
INSERT INTO media (file_name, file_path, file_type, file_size, alt_text, uploaded_by, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                                                          ('atiso.jpg', '/uploads/plants/atiso.jpg', 1, 1024, 'Cây Atiso', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('dinh-lang.jpg', '/uploads/plants/dinh-lang.jpg', 1, 1024, 'Cây Đinh Lăng', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('nghe.jpg', '/uploads/plants/nghe.jpg', 1, 1024, 'Cây Nghệ', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('sa-sam.jpg', '/uploads/plants/sa-sam.jpg', 1, 1024, 'Cây Sâm', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('tra-xanh.jpg', '/uploads/plants/tra-xanh.jpg', 1, 1024, 'Cây Trà Xanh', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('gung.jpg', '/uploads/plants/gung.jpg', 1, 1024, 'Cây Gừng', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('tao-meo.jpg', '/uploads/plants/tao-meo.jpg', 1, 1024, 'Cây Táo Mèo', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('diep-ca.jpg', '/uploads/plants/diep-ca.jpg', 1, 1024, 'Cây Diếp Cá', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('kim-tien-thao.jpg', '/uploads/plants/kim-tien-thao.jpg', 1, 1024, 'Cây Kim Tiền Thảo', 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                          ('la-sen.jpg', '/uploads/plants/la-sen.jpg', 1, 1024, 'Lá Sen', 1, NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng tags
INSERT INTO tags (name, slug, created_at, created_by, updated_at, updated_by) VALUES
                                                                                  ('Hạ sốt', 'ha-sot', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Kháng viêm', 'khang-viem', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Tiêu hóa', 'tieu-hoa', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Gan mật', 'gan-mat', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Huyết áp', 'huyet-ap', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Miễn dịch', 'mien-dich', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Chống oxy hóa', 'chong-oxy-hoa', NOW(), 'admin', NOW(), 'admin'),
                                                                                  ('Đau nhức', 'dau-nhuc', NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng plants
-- status: 3 = PUBLISHED
INSERT INTO plants (name, scientific_name, slug, family, genus, other_names, parts_used, description, botanical_characteristics, chemical_composition, distribution, altitude, harvest_season, ecology, medicinal_uses, indications, contraindications, dosage, folk_remedies, side_effects, status, featured, views, created_by, created_at, updated_by, updated_at) VALUES
                                                                                                                                                                                                                                                                                                                                                                          ('Atiso', 'Cynara scolymus', 'atiso', 'Asteraceae', 'Cynara', 'Atisô, Actiso', 'Lá, hoa', 'Atiso là cây thảo, sống nhiều năm, cao 1-1,5m.', 'Atiso là cây thảo, sống nhiều năm, cao 1-1,5m.', 'Lá và hoa Atiso chứa các acid phenolic.', 'Đà Lạt, Lâm Đồng', '1000-1500m', 'Quanh năm', 'Atiso ưa khí hậu mát mẻ.', 'Hỗ trợ chức năng gan.', 'Rối loạn chức năng gan.', 'Không dùng cho người mẫn cảm.', 'Dạng trà: 2-3g lá Atiso khô.', 'Lá Atiso 10g.', 'Hiếm khi gây tác dụng phụ.', 3, 1, 120, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Đinh Lăng', 'Polyscias fruticosa', 'dinh-lang', 'Araliaceae', 'Polyscias', 'Đinh lăng gai', 'Rễ, lá', 'Đinh lăng là cây bụi nhỏ, cao 1-3m.', 'Đinh lăng là cây bụi nhỏ, cao 1-3m.', 'Rễ đinh lăng chứa saponin.', 'Miền Bắc và Trung Việt Nam', '100-800m', 'Quanh năm', 'Đinh lăng ưa khí hậu ấm áp.', 'Bổ phế, tiêu đờm.', 'Suy nhược cơ thể.', 'Không dùng cho người mẫn cảm.', 'Rễ đinh lăng 15g.', 'Rễ đinh lăng 15g.', 'Hiếm khi gây tác dụng phụ.', 3, 1, 95, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Nghệ', 'Curcuma longa', 'nghe', 'Zingiberaceae', 'Curcuma', 'Nghệ vàng', 'Thân rễ', 'Nghệ là cây thảo, cao 0.6-1m.', 'Nghệ là cây thảo, cao 0.6-1m.', 'Thân rễ nghệ chứa curcuminoid.', 'Phổ biến ở Việt Nam', '100-1000m', 'Tháng 10-12', 'Nghệ ưa khí hậu nóng ẩm.', 'Kháng viêm.', 'Viêm loét dạ dày.', 'Không dùng cho người mẫn cảm.', 'Bột: 1-3g/ngày.', 'Nghệ 10g.', 'Hiếm khi gây tác dụng phụ.', 3, 1, 110, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Sâm Ngọc Linh', 'Panax vietnamensis', 'sam-ngoc-linh', 'Araliaceae', 'Panax', 'Sâm Việt Nam', 'Rễ, thân rễ', 'Sâm Ngọc Linh là cây thảo, cao 0.5-1m.', 'Sâm Ngọc Linh là cây thảo.', 'Rễ sâm chứa saponin.', 'Núi Ngọc Linh', '1200-2000m', 'Tháng 10-12', 'Sâm ưa khí hậu mát mẻ.', 'Bổ khí, tăng lực.', 'Suy nhược cơ thể.', 'Không dùng cho người cao huyết áp.', 'Bột: 1-3g/ngày.', 'Sâm 5g.', 'Hiếm khi gây tác dụng phụ.', 3, 1, 150, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Trà Xanh', 'Camellia sinensis', 'tra-xanh', 'Theaceae', 'Camellia', 'Chè xanh', 'Lá', 'Trà xanh là cây bụi, cao 1-3m.', 'Trà xanh là cây bụi.', 'Lá trà chứa polyphenol.', 'Miền Bắc và Trung Việt Nam', '400-1500m', 'Quanh năm', 'Trà xanh ưa khí hậu mát mẻ.', 'Chống oxy hóa.', 'Béo phì.', 'Không dùng cho người mẫn cảm caffeine.', 'Trà: 2-3g lá.', 'Trà xanh 5g.', 'Có thể gây mất ngủ.', 3, 1, 85, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Gừng', 'Zingiber officinale', 'gung', 'Zingiberaceae', 'Zingiber', 'Sinh khương', 'Thân rễ', 'Gừng là cây thảo, cao 0.5-1m.', 'Gừng là cây thảo.', 'Thân rễ gừng chứa tinh dầu.', 'Phổ biến ở Việt Nam', '100-1000m', 'Tháng 10-12', 'Gừng ưa khí hậu nóng ẩm.', 'Kích thích tiêu hóa.', 'Say tàu xe.', 'Không dùng cho người viêm loét dạ dày.', 'Trà: 1-2g gừng khô.', 'Gừng tươi 10g.', 'Hiếm khi gây tác dụng phụ.', 3, 1, 90, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Táo Mèo', 'Docynia indica', 'tao-meo', 'Rosaceae', 'Docynia', 'Sơn tra', 'Quả', 'Táo mèo là cây gỗ nhỏ, cao 5-10m.', 'Táo mèo là cây gỗ nhỏ.', 'Quả táo mèo chứa acid hữu cơ.', 'Miền núi phía Bắc', '800-2000m', 'Tháng 9-11', 'Táo mèo ưa khí hậu mát mẻ.', 'Hạ mỡ máu.', 'Béo phì.', 'Không dùng cho người viêm loét dạ dày.', 'Trà: 5-10g táo mèo khô.', 'Táo mèo khô 10g.', 'Hiếm khi gây tác dụng phụ.', 3, 0, 75, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Diếp Cá', 'Houttuynia cordata', 'diep-ca', 'Saururaceae', 'Houttuynia', 'Giấp cá', 'Toàn cây', 'Diếp cá là cây thảo, cao 20-50cm.', 'Diếp cá là cây thảo.', 'Diếp cá chứa tinh dầu.', 'Phổ biến ở Việt Nam', '100-1000m', 'Quanh năm', 'Diếp cá ưa khí hậu nóng ẩm.', 'Kháng khuẩn.', 'Viêm phổi.', 'Không dùng cho người mẫn cảm.', 'Nước ép: 30-50ml/ngày.', 'Diếp cá tươi 30g.', 'Hiếm khi gây tác dụng phụ.', 3, 0, 65, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Kim Tiền Thảo', 'Desmodium styracifolium', 'kim-tien-thao', 'Fabaceae', 'Desmodium', 'Cỏ kim tiền', 'Toàn cây', 'Kim tiền thảo là cây thảo, cao 30-60cm.', 'Kim tiền thảo là cây thảo.', 'Kim tiền thảo chứa flavonoid.', 'Phổ biến ở Việt Nam', '100-800m', 'Quanh năm', 'Kim tiền thảo ưa khí hậu nóng ẩm.', 'Lợi tiểu, tán sỏi.', 'Sỏi thận.', 'Không dùng cho người mẫn cảm.', 'Trà: 5-10g kim tiền thảo khô.', 'Kim tiền thảo 15g.', 'Hiếm khi gây tác dụng phụ.', 3, 0, 60, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                                                                                                                                                                                                                          ('Lá Sen', 'Nelumbo nucifera', 'la-sen', 'Nelumbonaceae', 'Nelumbo', 'Liên diệp', 'Lá', 'Lá sen là lá của cây sen.', 'Lá sen hình tròn.', 'Lá sen chứa alkaloid.', 'Phổ biến ở Việt Nam', '0-800m', 'Quanh năm', 'Sen ưa nước.', 'An thần.', 'Mất ngủ.', 'Không dùng cho người huyết áp thấp.', 'Trà: 3-5g lá sen khô.', 'Lá sen 10g.', 'Hiếm khi gây tác dụng phụ.', 3, 0, 55, 'admin', NOW(), 'admin', NOW());

-- Thêm dữ liệu vào bảng plant_diseases
INSERT INTO plant_diseases (plant_id, disease_id) VALUES
                                                      (1, 1), (1, 2), -- Atiso: Bệnh gan, Bệnh tiêu hóa
                                                      (2, 4), (2, 5), -- Đinh Lăng: Bệnh miễn dịch, Viêm nhiễm
                                                      (3, 2), (3, 5), -- Nghệ: Bệnh tiêu hóa, Viêm nhiễm
                                                      (4, 4), -- Sâm Ngọc Linh: Bệnh miễn dịch
                                                      (5, 2), (5, 3), -- Trà Xanh: Bệnh tiêu hóa, Bệnh huyết áp
                                                      (6, 2), (6, 5), -- Gừng: Bệnh tiêu hóa, Viêm nhiễm
                                                      (7, 1), (7, 3), -- Táo Mèo: Bệnh gan, Bệnh huyết áp
                                                      (8, 5), -- Diếp Cá: Viêm nhiễm
                                                      (9, 6), -- Kim Tiền Thảo: Sỏi thận
                                                      (10, 3); -- Lá Sen: Bệnh huyết áp

-- Thêm dữ liệu vào bảng plant_media
INSERT INTO plant_media (plant_id, media_id, is_featured) VALUES
                                                              (1, 1, 1), -- Atiso
                                                              (2, 2, 1), -- Đinh Lăng
                                                              (3, 3, 1), -- Nghệ
                                                              (4, 4, 1), -- Sâm Ngọc Linh
                                                              (5, 5, 1), -- Trà Xanh
                                                              (6, 6, 1), -- Gừng
                                                              (7, 7, 1), -- Táo Mèo
                                                              (8, 8, 1), -- Diếp Cá
                                                              (9, 9, 1), -- Kim Tiền Thảo
                                                              (10, 10, 1); -- Lá Sen

-- Thêm dữ liệu vào bảng plant_tags
INSERT INTO plant_tags (plant_id, tag_id) VALUES
                                              (1, 3), (1, 4), -- Atiso: Tiêu hóa, Gan mật
                                              (2, 2), (2, 6), -- Đinh Lăng: Kháng viêm, Miễn dịch
                                              (3, 1), (3, 2), (3, 8), -- Nghệ: Hạ sốt, Kháng viêm, Đau nhức
                                              (4, 6), (4, 7), -- Sâm Ngọc Linh: Miễn dịch, Chống oxy hóa
                                              (5, 3), (5, 7), -- Trà Xanh: Tiêu hóa, Chống oxy hóa
                                              (6, 1), (6, 3), -- Gừng: Hạ sốt, Tiêu hóa
                                              (7, 4), (7, 5), -- Táo Mèo: Gan mật, Huyết áp
                                              (8, 1), (8, 2), -- Diếp Cá: Hạ sốt, Kháng viêm
                                              (9, 2), (9, 4), -- Kim Tiền Thảo: Kháng viêm, Gan mật
                                              (10, 1), (10, 5); -- Lá Sen: Hạ sốt, Huyết áp

--- Thêm dữ liệu vào bảng articles
-- status: 3 = PUBLISHED
INSERT INTO articles (title, slug, excerpt, content, featured_image, status, is_featured, allow_comments, views, author_id, disease_id, published_at, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                                                                                                                          ('Công dụng của cây Atiso trong điều trị bệnh gan', 'cong-dung-cua-cay-atiso-trong-dieu-tri-benh-gan', 'Tìm hiểu về công dụng của cây Atiso.', 'Atiso (Cynara scolymus) là một loại cây dược liệu quý...', 1, 3, 1, 1, 120, 1, 1, NOW(), NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                                                                                          ('Cách trồng và chăm sóc cây Đinh Lăng tại nhà', 'cach-trong-va-cham-soc-cay-dinh-lang-tai-nha', 'Hướng dẫn chi tiết cách trồng Đinh Lăng.', 'Đinh Lăng (Polyscias fruticosa) là một loại cây dược liệu quý...', 2, 3, 0, 1, 85, 2, 4, NOW(), NOW(), 'editor', NOW(), 'editor'),
                                                                                                                                                                                                          ('10 loại cây dược liệu quý hiếm của Việt Nam', '10-loai-cay-duoc-lieu-quy-hiem-cua-viet-nam', 'Giới thiệu 10 loại cây dược liệu quý hiếm.', 'Việt Nam có hệ thực vật phong phú...', 3, 3, 1, 1, 200, 1, 1, NOW(), NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng article_tags
INSERT INTO article_tags (article_id, tag_id) VALUES
                                                  (1, 3), (1, 4), -- Công dụng của cây Atiso: Tiêu hóa, Gan mật
                                                  (2, 2), (2, 6), -- Cách trồng Đinh Lăng: Kháng viêm, Miễn dịch
                                                  (3, 6), (3, 7); -- 10 loại cây dược liệu: Miễn dịch, Chống oxy hóa

-- Thêm dữ liệu vào bảng article_plants
INSERT INTO article_plants (article_id, plant_id) VALUES
                                                      (1, 1), -- Công dụng của cây Atiso: Atiso
                                                      (2, 2), -- Cách trồng Đinh Lăng: Đinh Lăng
                                                      (3, 1), (3, 2), (3, 3), (3, 4); -- 10 loại cây dược liệu: Atiso, Đinh Lăng, Nghệ, Sâm Ngọc Linh

-- Thêm dữ liệu vào bảng experts
-- status: 1 = ACTIVE, 3 = PENDING_APPROVAL
INSERT INTO experts (name, slug, title, specialization, institution, education, bio, achievements, contact_email, avatar, status, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                                                                                                      ('GS.TS. Nguyễn Văn A', 'gs-ts-nguyen-van-a', 'Giáo sư, Tiến sĩ', 'Dược liệu học', 'Đại học Y Dược TP. Hồ Chí Minh', 'Tiến sĩ Dược học', 'Chuyên gia hàng đầu về dược liệu học.', 'Nhà nghiên cứu xuất sắc 2020.', 'nguyenvana@example.com', 1, 1, NOW(), 'admin', NOW(), 'admin'),
                                                                                                                                                                                      ('PGS.TS. Trần Thị B', 'pgs-ts-tran-thi-b', 'Phó Giáo sư, Tiến sĩ', 'Hóa dược', 'Đại học Dược Hà Nội', 'Tiến sĩ Hóa dược', 'Chuyên gia về hóa dược.', 'Giải thưởng Nhà khoa học trẻ 2021.', 'tranthib@example.com', 2, 3, NOW(), 'admin', NOW(), 'admin');

-- Thêm dữ liệu vào bảng expert_plants
INSERT INTO expert_plants (expert_id, plant_id) VALUES
                                                    (1, 1), (1, 2), (1, 3), -- GS.TS. Nguyễn Văn A: Atiso, Đinh Lăng, Nghệ
                                                    (2, 4), (2, 5); -- PGS.TS. Trần Thị B: Sâm Ngọc Linh, Trà Xanh

-- Thêm dữ liệu vào bảng research
-- status: 3 = PUBLISHED
INSERT INTO research (title, slug, abstract, content, authors, institution, published_year, journal, field, status, views, created_by, created_at, updated_by, updated_at) VALUES
                                                                                                                                                                               ('Nghiên cứu tác dụng bảo vệ gan của chiết xuất Atiso', 'nghien-cuu-tac-dung-bao-ve-gan-cua-chiet-xuat-atiso', 'Chiết xuất Atiso có tác dụng bảo vệ gan.', 'Nghiên cứu đánh giá tác dụng bảo vệ gan...', 'Nguyễn Văn A, Trần Thị B', 'Đại học Y Dược TP. Hồ Chí Minh', 2021, 'Tạp chí Y Dược học', 'Dược lý', 3, 80, 'admin', NOW(), 'admin', NOW()),
                                                                                                                                                                               ('Đánh giá tác dụng hạ lipid máu của cao chiết Atiso', 'danh-gia-tac-dung-ha-lipid-mau-cua-cao-chiet-atiso', 'Cao chiết Atiso làm giảm cholesterol.', 'Nghiên cứu lâm sàng ngẫu nhiên...', 'Lê Văn C, Phạm Thị D', 'Bệnh viện Chợ Rẫy', 2022, 'Tạp chí Nghiên cứu Y học', 'Y học', 3, 65, 'editor', NOW(), 'editor', NOW());

-- Thêm dữ liệu vào bảng research_plants
INSERT INTO research_plants (research_id, plant_id) VALUES
                                                        (1, 1), -- Nghiên cứu Atiso: Atiso
                                                        (2, 1); -- Đánh giá Atiso: Atiso

-- Thêm dữ liệu vào bảng research_tags
INSERT INTO research_tags (research_id, tag_id) VALUES
                                                    (1, 4), -- Nghiên cứu Atiso: Gan mật
                                                    (2, 4); -- Đánh giá Atiso: Gan mật

-- Thêm dữ liệu vào bảng comments
-- status: 2 = approved
INSERT INTO comments (content, user_id, article_id, parent_id, status, created_at, created_by, updated_at, updated_by) VALUES
                                                                                                                           ('Bài viết rất hữu ích, cảm ơn tác giả!', 3, 1, NULL, 2, NOW(), 'user1', NOW(), 'user1'),
                                                                                                                           ('Có thể chia sẻ thêm cách dùng Atiso tươi không?', 4, 1, NULL, 2, NOW(), 'user2', NOW(), 'user2');