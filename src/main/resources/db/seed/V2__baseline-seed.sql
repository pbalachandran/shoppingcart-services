INSERT INTO PRODUCT_CATEGORY (
  created_date,
  product_category_name,
  description)
  VALUES (current_timestamp, 'Electronics', 'Electronics & Computer Equipment');

INSERT INTO PRODUCT_CATEGORY (
  created_date,
  product_category_name,
  description)
VALUES (current_timestamp, 'Lawn & Garden', 'Lawn & Garden Equipment');

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
  VALUES (current_timestamp, 'IPHONE8S', 'iPhone8S', 'Apple iPhone 8S', 100, 799.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
  VALUES (current_timestamp,'APPLEIPHONE9S','iPhone9S','Apple iPhone 9S', 100,  999.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
  VALUES (current_timestamp,'APPLEIPAD','iPad', 'Apple iPad', 150, 799.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
VALUES (current_timestamp, 'TOROMOWER567', 'Toro Mower', 'Toro Mower', 150, 799.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Lawn Equipment'));