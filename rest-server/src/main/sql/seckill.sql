-- stored procedure for seckill transaction!
DELIMITER $$ -- Change the statement delimiter from ; to $$

-- in=>input out=>output
-- r_result = {0: seckill expired,
--             1: seckill successful,
--             -1: repeated seckill,
--             -2: internal server error}
CREATE PROCEDURE execute_seckill
  (IN v_seckill_id BIGINT, IN v_phone BIGINT,
   IN v_kill_time  TIMESTAMP, OUT r_result INT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION; -- start a transaction
    INSERT IGNORE INTO success_killed
    (product_id, user_phone, create_time)
    VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT row_count()
    INTO insert_count; -- row_count() return the number of affected rows by the previous delete/insert/update statement
    IF (insert_count = 0)
    THEN
      ROLLBACK;
      SET r_result = -1; -- repeated seckill
    ELSEIF (insert_count < 0)
      THEN
        ROLLBACK;
        SET r_result = -2; -- internal server error
    ELSE
      UPDATE products
      SET product_num = product_num - 1
      WHERE id = v_seckill_id
            AND end_time > v_kill_time
            AND start_time < v_kill_time
            AND product_num > 0;
      SELECT row_count()
      INTO insert_count;
      IF (insert_count = 0)
      THEN
        ROLLBACK;
        SET r_result = 0;     -- seckill expired!!
      ELSEIF (insert_count < 0)
        THEN
          ROLLBACK;
          SET r_result = -2;   -- internal server error
      ELSE
        COMMIT;
        SET r_result = 1;  -- seckill successful!!!
      END IF;
    END IF;
  END;
$$

DELIMITER ;

SET @r_result = -3;

CALL execute_seckill(1003, 4675506521, now(), @r_result); -- call the procedure!!

SELECT @r_result; -- check the result!!

# Some thoughts about stored procedures
# 1:  don't rely on stored procedures too much
# 2. for simple logic, we can use stored procedures, but don't for complex logic unless you work in a bank
# 3. QPS: 6000qps for each item !!

