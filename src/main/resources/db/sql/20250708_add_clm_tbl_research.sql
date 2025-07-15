ALTER TABLE research
    ADD COLUMN media_id INT;
ALTER TABLE research
    ADD COLUMN download_price DECIMAL(10, 2) DEFAULT 0.00;