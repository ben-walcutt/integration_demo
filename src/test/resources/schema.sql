create table orders (
    orderId int,
    accountId int
);

create table lineitems (
    lineItemId int,
    orderId int,
    productId int,
    quantity int
);

create table products (
    productId int,
    name varchar(30),
    price decimal
);