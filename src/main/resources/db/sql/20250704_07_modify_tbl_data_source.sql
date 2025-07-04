ALTER TABLE data_sources
    ADD COLUMN publication_year YEAR;

ALTER TABLE plants
    ADD COLUMN active_compound_id INT;
