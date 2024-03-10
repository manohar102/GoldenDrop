CREATE VIEW product_stock_view AS
SELECT
    p.id,
    p.brand_name,
    p.type,
    p.quantity,
    p.store_id,
    p.image_name,
    p.created_at,
    p.updated_at,
    COALESCE(SUM(CASE WHEN s.type = 'IN' THEN s.quantity ELSE 0 END), 0) AS in_quantity,
    COALESCE(SUM(CASE WHEN s.type = 'OUT' THEN s.quantity ELSE 0 END), 0) AS out_quantity
FROM
    product p
        LEFT JOIN
    stock_detail s ON p.id = s.product_id
GROUP BY
    p.id;