CREATE TABLE collections
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by  VARCHAR(50),
    updated_by  VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


