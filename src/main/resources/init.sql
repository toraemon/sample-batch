-- 一時テーブルの作成
CREATE TABLE IF NOT EXISTS temporary_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    address VARCHAR(255)
);

-- カレントテーブルの作成
CREATE TABLE IF NOT EXISTS current_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    processed_data VARCHAR(255)
);