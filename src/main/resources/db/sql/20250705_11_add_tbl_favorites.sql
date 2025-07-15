
CREATE TABLE favorites
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    user_id          INT NOT NULL,
    favoritable_type TINYINT(1) NOT NULL,
    favoritable_id   INT NOT NULL,
    is_active        TINYINT(1) DEFAULT 1,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by       VARCHAR(50),
    updated_by       VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE KEY unique_favorite (user_id, favoritable_type, favoritable_id)
);

