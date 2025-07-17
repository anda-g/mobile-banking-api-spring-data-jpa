INSERT INTO customer_segments (id, segment, benefit, is_deleted)
SELECT * FROM (
                  SELECT 1, 'GOLD', 50000, FALSE UNION ALL
                  SELECT 2, 'SILVER', 10000, FALSE UNION ALL
                  SELECT 3, 'REGULAR', 5000, FALSE
              ) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM customer_segments
);

INSERT INTO account_types (id, name)
SELECT * FROM (
                  SELECT 1, 'PAYROLL' UNION ALL
                  SELECT 2, 'SAVING' UNION ALL
                  SELECT 3, 'JUNIOR'
              ) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM account_types
);
