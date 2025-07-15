ALTER TABLE plants
    ADD COLUMN favorites_count INT DEFAULT 0;
CREATE INDEX idx_plant_favorite_count ON plants (favorites_count);

ALTER TABLE articles
    ADD COLUMN favorites_count INT DEFAULT 0;
CREATE INDEX idx_article_favorites_count ON articles (favorites_count);

