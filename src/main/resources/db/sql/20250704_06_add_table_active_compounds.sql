CREATE TABLE active_compounds (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  slug VARCHAR(100) NOT NULL,
                                  description TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  created_by VARCHAR(50) NOT NULL,
                                  updated_by VARCHAR(50) NOT NULL,
                                  UNIQUE INDEX idx_slug (slug)
);

CREATE TABLE data_sources (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              slug VARCHAR(100) NOT NULL,
                              type VARCHAR(50) NOT NULL, -- Loại nguồn: 'book', 'journal', 'website', 'other'
                              description TEXT,
                              author VARCHAR(100), -- Tác giả (nếu có)
                              publisher VARCHAR(100), -- Nhà xuất bản (cho sách/tạp chí)
                              publication_date DATE, -- Ngày xuất bản
                              url VARCHAR(255), -- Đường dẫn URL (cho website)
                              isbn_issn VARCHAR(20), -- Số ISBN (sách) hoặc ISSN (tạp chí)
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              created_by VARCHAR(50) NOT NULL,
                              updated_by VARCHAR(50) NOT NULL,
                              UNIQUE INDEX idx_slug (slug)
);