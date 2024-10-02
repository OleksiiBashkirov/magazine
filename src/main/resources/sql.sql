create table store
(
    id             int generated by default as identity primary key,
    name           varchar(128)                                                                        not null,
    address        varchar(128) check ( address ~ '^\d{5}, [A-Za-z]{2,25}, [A-Za-z0-9,. ''-]{2,50}$' ) not null,
    edrpou         varchar(8) check ( edrpou ~ '^\d{8}$' ) unique                                      not null,
    special_number varchar(9) check ( special_number ~ '^[A-Z]{2}-\d{6}$' ) unique                     not null,
    description    varchar(256)
);

insert into store(name, address, edrpou, special_number, description)
values
-- Novus
('Novus', '01001, Ukraine, Kyiv, vul. Khreshchatyk, 22', '12345678', 'KV-000001', 'Novus main branch'),
('Novus', '02002, Ukraine, Kyiv, vul. Drahomanova, 10', '23456789', 'KV-000002', 'Novus branch 2'),
('Novus', '03003, Ukraine, Kyiv, vul. Shuliavska, 15', '34567890', 'KV-000003', 'Novus branch 3'),

-- Ashan
('Ashan', '04004, Ukraine, Kyiv, vul. Povitroflotskyi ave., 16', '45678901', 'KV-000004', 'Ashan main branch'),
('Ashan', '05005, Ukraine, Kyiv, vul. Velyka Vasylkivska, 44', '56789012', 'KV-000005', 'Ashan branch 2'),
('Ashan', '06006, Ukraine, Kyiv, vul. Bazhana ave., 20', '67890123', 'KV-000006', 'Ashan branch 3'),

-- ATB
('ATB', '07007, Ukraine, Kyiv, vul. Zhylianska, 40', '78901234', 'KV-000007', 'ATB main branch'),
('ATB', '08008, Ukraine, Kyiv, vul. Sichovykh Striltsiv, 77', '89012345', 'KV-000008', 'ATB branch 2'),
('ATB', '09009, Ukraine, Kyiv, vul. Lvivska, 55', '90123456', 'KV-000009', 'ATB branch 3'),

-- FORA
('FORA', '10010, Ukraine, Kyiv, vul. Boryspilska, 88', '01234567', 'KV-000010', 'FORA main branch'),
('FORA', '11011, Ukraine, Kyiv, vul. Sadova, 101', '12345670', 'KV-000011', 'FORA branch 2'),
('FORA', '12012, Ukraine, Kyiv, vul. Smilyanska, 14', '23456701', 'KV-000012', 'FORA branch 3'),

-- METRO
('METRO', '13013, Ukraine, Kyiv, vul. Brovarskyi ave., 19', '34567012', 'KV-000013', 'METRO main branch'),
('METRO', '14014, Ukraine, Kyiv, vul. Zabolotnoho, 45', '45670123', 'KV-000014', 'METRO branch 2'),
('METRO', '15015, Ukraine, Kyiv, vul. Vokzalna, 72', '56701234', 'KV-000015', 'METRO branch 3');

select * from store;

create table product
(
    id          int generated by default as identity primary key,
    name        varchar(64)                                                 not null,
    article     varchar(13) check ( article ~ '^\d{4}-\d{4}(-\d{1,3})?$') unique         not null,
    price       numeric(8, 2) check ( price > 0 and price < 100000 )        not null,
    quantity    numeric(7, 2) check ( quantity >= 0 and quantity <= 10000 ) not null,
    description varchar(256),
    store_id    int                                                         references store (id) on delete set null
);

drop table product;

insert into product(name, article, price, quantity, description, store_id)
values
-- Спільні товари з різними артикульними кодами для кожного магазину
('Milk', '0001-0001-1', 25.50, 200.00, '1L pack of milk', 1),
('Milk', '0001-0001-4', 25.50, 180.00, '1L pack of milk', 4),
('Milk', '0001-0001-7', 25.50, 220.00, '1L pack of milk', 7),
('Milk', '0001-0001-10', 25.50, 210.00, '1L pack of milk', 10),
('Milk', '0001-0001-13', 25.50, 190.00, '1L pack of milk', 13),

('Bread', '0002-0002-2', 10.75, 500.00, 'Fresh white bread', 2),
('Bread', '0002-0002-5', 10.75, 480.00, 'Fresh white bread', 5),
('Bread', '0002-0002-8', 10.75, 510.00, 'Fresh white bread', 8),
('Bread', '0002-0002-11', 10.75, 495.00, 'Fresh white bread', 11),
('Bread', '0002-0002-14', 10.75, 505.00, 'Fresh white bread', 14),

('Butter', '0003-0003-3', 65.30, 300.00, '200g of butter', 3),
('Butter', '0003-0003-6', 65.30, 290.00, '200g of butter', 6),
('Butter', '0003-0003-9', 65.30, 320.00, '200g of butter', 9),
('Butter', '0003-0003-12', 65.30, 305.00, '200g of butter', 12),
('Butter', '0003-0003-15', 65.30, 315.00, '200g of butter', 15),

-- Унікальні товари для Новус
('Chocolate', '1001-0001', 45.60, 120.00, '100g dark chocolate', 1),
('Cereal', '1002-0002', 32.10, 250.00, '500g oat cereal', 2),
('Coffee', '1003-0003', 110.00, 100.00, '200g instant coffee', 3),

-- Унікальні товари для Ашан
('Olive Oil', '2001-0001', 150.50, 90.00, '1L bottle of olive oil', 4),
('Rice', '2002-0002', 24.80, 300.00, '1kg white rice', 5),
('Tea', '2003-0003', 55.70, 150.00, '100g black tea', 6),

-- Унікальні товари для АТБ
('Sugar', '3001-0001', 19.50, 400.00, '1kg of sugar', 7),
('Pasta', '3002-0002', 25.30, 350.00, '500g of pasta', 8),
('Juice', '3003-0003', 28.60, 200.00, '1L apple juice', 9),

-- Унікальні товари для ФОРА
('Eggs', '4001-0001', 29.00, 150.00, '10 pack of eggs', 10),
('Tomatoes', '4002-0002', 45.00, 180.00, '1kg fresh tomatoes', 11),
('Bananas', '4003-0003', 32.80, 200.00, '1kg bananas', 12),

-- Унікальні товари для МЕТРО
('Chicken', '5001-0001', 85.00, 120.00, '1kg chicken breast', 13),
('Fish', '5002-0002', 130.00, 100.00, '1kg fresh fish', 14),
('Cheese', '5003-0003', 95.00, 80.00, '200g hard cheese', 15);


select * from product;

create table person(
                       id int generated by default as identity primary key ,
                       name varchar(32) not null,
                       lastname varchar(32) not null,
                       username varchar(64) not null unique ,
                       password varchar not null,
                       role varchar(32) not null
);