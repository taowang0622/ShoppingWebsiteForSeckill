CREATE TABLE products (
  id            BIGINT             NOT NULL AUTO_INCREMENT,
  product_name  VARCHAR(100)       NOT NULL,
  product_num   INT                NOT NULL,
  product_price DOUBLE NOT NULL
  COMMENT 'in canadian dollars',
  create_time   TIMESTAMP          NOT NULL DEFAULT current_timestamp,
  start_time    TIMESTAMP          NOT NULL
  COMMENT 'seckill start time',
  end_time      TIMESTAMP          NOT NULL
  COMMENT 'seckill end time',
  PRIMARY KEY (id),
  KEY start_time_idx(start_time),
  KEY end_time_idx(end_time),
  KEY create_time_idx(create_time)
)
  ENGINE = innodb
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT = 'The table for seckill products';

# initiate the table products
INSERT INTO products (product_name, product_num, product_price, start_time, end_time) VALUES
  ('iphone6', 100, 120, '2016-01-01 00:00:00', '2016-01-02 00:00:00'),
  ('ps4', 50, 100, '2016-01-02 00:00:00', '2016-01-03 00:00:00'),
  ('xbox one', 50, 80, '2016-01-04 00:00:00', '2016-01-05 00:00:00'),
  ('mac pro', 100, 70, '2016-01-04 00:00:00', '2016-01-05 00:00:00');

CREATE TABLE success_killed (
  product_id  BIGINT    NOT NULL,
  user_phone  BIGINT    NOT NULL,
  state       TINYINT   NOT NULL DEFAULT -1
  COMMENT '-1:invalid 0:success 1:paid 2:shipped',
  create_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (product_id, user_phone)
    COMMENT 'prevent the same user from purchasing the same product more than once',
  FOREIGN KEY (product_id) REFERENCES products (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  KEY create_time_idx(create_time)
)
  DEFAULT CHARSET = utf8
  COMMENT = 'The table for seckill success records';