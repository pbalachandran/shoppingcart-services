INSERT INTO PRODUCT_CATEGORY (
  created_date,
  name,
  description)
  VALUES (
  current_timestamp,
  'Electronics',
  'Electronics & Computer Equipment');

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  name,
  description,
  inventory_count,
  price,
  product_category_id)
  VALUES (
  current_timestamp,
  'AppleIPhone6S',
  'iPhone 6S',
  'iPhone 6S',
  100,
  799.99,
  (SELECT id FROM product_category WHERE product_category.name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  name,
  description,
  inventory_count,
  price,
  product_category_id)
  VALUES (
  current_timestamp,
  'AppleIPhone8S',
  'iPhone 8S',
  'iPhone 8S',
  100,
  999.99,
  (SELECT id FROM product_category WHERE product_category.name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  name,
  description,
  inventory_count,
  price,
  product_category_id)
  VALUES (
  current_timestamp,
  'AppleIPad',
  'iPad',
  'iPad',
  100,
  799.99,
  (SELECT id FROM product_category WHERE product_category.name = 'Electronics'));