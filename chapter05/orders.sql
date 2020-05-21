drop table if exists Orders;
create table Orders (
  order_id int primary key,
  order_number text
);

drop table if exists Products;
create table Products (
  product_id int primary key,
  name text not null
);

drop table if exists ProductsInOrder;
create table ProductsInOrder (
  order_id int references Orders(order_id),
  product_id int references Products(product_id),
  qty int not null default 1
);

insert into Orders (order_id, order_number) values (1, 'ABC123'), (2, 'DEF234');
insert into Products (product_id, name) values (1, 'widget 1'), (2, 'widget 2'), (3, 'widget 3');
insert into ProductsInOrder (order_id, product_id, qty)
values (1, 1, 5), (1,2,10), (2,2,4), (2,3,6);

SELECT * FROM Orders 
       JOIN ProductsInOrder ON ProductsInOrder.order_id = Orders.order_id
       JOIN Products ON Products.product_id = ProductsInOrder.product_id;
