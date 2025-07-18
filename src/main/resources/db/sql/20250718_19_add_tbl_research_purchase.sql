CREATE TABLE research_purchase
(
    purchase_id    INT PRIMARY KEY AUTO_INCREMENT,
    media_id    INT            NOT NULL,
    user_id        INT            NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by  VARCHAR(50),
    updated_by  VARCHAR(50)
);
