-- collectable_type: 1- plant, 2- article
CREATE TABLE collection_items
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    collection_id    INT NOT NULL,
    collectable_type TINYINT(1) NOT NULL,
    collectable_id   INT NOT NULL,
    notes            TEXT,
    added_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (collection_id) REFERENCES collections (id) ON DELETE CASCADE,
    UNIQUE KEY unique_collection_item (collection_id, collectable_type, collectable_id)
);

