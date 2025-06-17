CREATE TABLE article_media
(
    article_id    INT     NOT NULL,
    media_id    INT     NOT NULL,
    is_featured TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (article_id, media_id),
    INDEX       idx_article_id (article_id),
    INDEX       idx_media_id (media_id),
    FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE CASCADE,
    FOREIGN KEY (media_id) REFERENCES media (id) ON DELETE CASCADE
);