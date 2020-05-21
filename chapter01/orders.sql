create table orders (
id int not null,
name varchar(20),
address varchar(200)
);

create table products (
id int not null,
product_name varchar(100),
cost float
);

insert into orders(id, name, address) values
(1, 'John Smith', '123 Main St'), (2, 'Jane Right', '234 Park St');

insert into products(id, product_name, cost) values
(123, 'widget 1', 5.95), (234, 'widget 2', 10.76);

SELECT id, name, address, null AS product_name, null AS cost, 'Order' AS object_type
FROM Orders
UNION
SELECT id, null AS name, null AS address, product_name, cost, 'Product' AS object_type 
FROM Products;
