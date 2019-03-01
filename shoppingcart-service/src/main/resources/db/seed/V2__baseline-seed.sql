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
  VALUES (current_timestamp, 'IPHONE8S', 'iPhone 8S', 'Apple iPhone 8S', 100, 799.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
  VALUES (current_timestamp,'IPHONE9S','iPhone 9S','Apple iPhone 9S', 100,  999.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
  VALUES (current_timestamp,'IPAD10','iPad 10', 'Apple iPad 10', 150, 799.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Electronics'));

INSERT INTO PRODUCT (
  created_date,
  sku_number,
  product_name,
  description,
  inventory_count,
  price,
  product_category_name)
VALUES (current_timestamp, 'TOROMOWER567', 'Toro Mower 567', 'Toro Mower 567', 150, 1299.99, (SELECT product_category_name FROM product_category WHERE product_category.product_category_name = 'Lawn & Garden'));

INSERT INTO CART (
  created_date,
  cart_name,
  description)
VALUES (current_timestamp, 'MyFirstCart', 'My First Cart');

INSERT INTO ITEM (
  created_date,
  quantity,
  cart_name,
  sku_number)
VALUES (current_timestamp, 1, (select cart_name FROM CART where cart_name = 'MyFirstCart'), (select product.sku_number FROM PRODUCT where sku_number = 'IPAD10'));

INSERT INTO ITEM (
  created_date,
  quantity,
  cart_name,
  sku_number)
VALUES (current_timestamp, 1, (select cart_name FROM CART where cart_name = 'MyFirstCart'), (select product.sku_number FROM PRODUCT where sku_number = 'TOROMOWER567'));
