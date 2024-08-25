-- Active: 1720792668180@@127.0.0.1@3306
CREATE DATABASE new_restaurant;


use new_restaurant;



CREATE table users(
    id BIGINT AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) not NULL ,
    email VARCHAR(50) not null UNIQUE,
    birthdate DATE not NULL,
    signup_date date,
    role ENUM("ADMIN","USER","DELIVERER") NOT NULL,
    constraint user_pk PRIMARY KEY(id)
)

CREATE Table foods (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(50) not null UNIQUE,
    price DOUBLE not null,
    description TEXT ,
    status BOOLEAN not null,
    typ ENUM('main', 'salad', 'drink', 'dessert'),
    constraint food_pk PRIMARY KEY(id)
)


CREATE Table orders (
    id BIGINT AUTO_INCREMENT,
    userId BIGINT NOT null,
    delivererId BIGINT ,
    addressId BIGINT ,
    constraint order_pk PRIMARY KEY(id),
    constraint user_fk FOREIGN KEY(userId) REFERENCES users(id)
)


CREATE table payments (
    id BIGINT AUTO_INCREMENT,
    amount DOUBLE DEFAULT 0.0,
    datePay DATETIME ,
    status BOOLEAN DEFAULT 0,
    orderId BIGINT NOT null,
    constraint pay_pk PRIMARY KEY(id),
    constraint order_fk FOREIGN KEY(id) REFERENCES orders(id)
)



CREATE Table likes (
    id BIGINT AUTO_INCREMENT,
    userId BIGINT not null,
    foodId BIGINT not null,
    constraint like_pk PRIMARY KEY(id),
    constraint food_fk FOREIGN KEY(foodId) REFERENCES foods(id),
    constraint like_user_fk FOREIGN Key (userId) REFERENCES users(id)
)



CREATE Table food_img (
    id BIGINT AUTO_INCREMENT,
    path VARCHAR(300) NOT null UNIQUE,
    foodId BIGINT not null,
    constraint img_pk PRIMARY KEY(id),
    constraint food_img_fk FOREIGN KEY(foodId) REFERENCES foods(id)
)



CREATE Table comments(
    id BIGINT AUTO_INCREMENT,
    comment TEXT not null,
    date DATETIME not null,
    constraint commnet_pk PRIMARY KEY(id)
)


CREATE Table user_comment_food (
    id BIGINT AUTO_INCREMENT,
    userId BIGINT not null,
    foodId BIGINT not null,
    commentId BIGINT not null,
    constraint PRIMARY KEY(id),
    constraint FOREIGN KEY(userId) REFERENCES users(id),
    constraint FOREIGN KEY(foodId) REFERENCES foods(id),
    constraint FOREIGN KEY(commentId) REFERENCES comments(id)
)



CREATE Table user_profile_img (
    id BIGINT AUTO_INCREMENT,
    path VARCHAR(300) NOT null UNIQUE,
    userId BIGINT not null,
    constraint img_pk PRIMARY KEY(id),
    constraint user_img_fk FOREIGN KEY(userId) REFERENCES users(id)
)


CREATE Table addresses(
    id BIGINT AUTO_INCREMENT,
    addr VARCHAR(200) not null,
    postal_code VARCHAR(30) not null unique,
    userId BIGINT not null unique,
    constraint PRIMARY KEY(id),
    constraint user_addr_fk FOREIGN KEY(userId) REFERENCES users(id)
)


CREATE Table food_order(
    id BIGINT AUTO_INCREMENT,
    foodId BIGINT not NULL,
    orderId BIGINT not NULL,
    num BIGINT ,
    constraint PRIMARY KEY(id) ,
    constraint FOREIGN KEY(foodId) REFERENCES foods(id),
    constraint FOREIGN KEY(orderId) REFERENCES orders(id)
)


DELIMITER //


CREATE PROCEDURE `getAllUsers`(in lim int,in ofs int)
begin
    SELECT * FROM users limit lim OFFSET ofs;
END //


CREATE Procedure getUser(in id BIGINT)
begin
    SELECT * from users WHERE users.id = id;
END //


CREATE Procedure getUserByUsername(in username VARCHAR(100),in password VARCHAR(100) )

BEGIN
    select * from users WHERE users.username = username and users.password = password;
END //



CREATE Trigger insert_new_order after insert on orders

    for EACH row
    begin
        insert into payments (amount,status,orderId) VALUES (0.0,0,new.id);
    END//


CREATE Trigger insert_order_food after insert on food_order

    for each row
    begin

        set @oldPrice = 0;
        select amount into @oldPrice from payments py where py.orderId =  new.orderId;
        set @price =0;
        select price into @price from foods where id=new.foodId;
        update payments set amount = @oldPrice + new.num*@price where orderId=new.orderId;

    END //


create trigger delete_food_order  after delete on food_order

    for EACH row
    begin
        set @oldPrice =0;
        select amount into @oldPrice from payments py where py.orderId = old.orderId;
        set @price =0;
        select price into @price from foods where id = old.foodId;
        update payments set amount = @oldPrice - @price*old.num WHERE orderId=old.orderId;
    end //



CREATE Trigger update_food_order before update on food_order
    for each row
    begin

    set @oldPrice =0;
    select amount into @oldPrice from payments py where py.orderId = old.orderId;
    set @price =0;
    select price into @price from foods where id = old.foodId;
    update payments set amount = @oldPrice + (new.num-old.num)*@price where orderId=new.orderId;
    END //


CREATE TRIGGER recompute_amount_after_food_update
AFTER UPDATE ON foods
FOR EACH ROW
BEGIN
    DECLARE order_id BIGINT;
    DECLARE new_amount DOUBLE;
    DECLARE done BOOLEAN DEFAULT FALSE;

    -- Cursor to iterate through each order that includes the updated food item
    DECLARE order_cursor CURSOR FOR
        SELECT o.id
        FROM orders o
        JOIN food_order fo ON o.id = fo.orderId
        WHERE fo.foodId = NEW.id;

    -- Handler to set the done variable to TRUE when the cursor has no more data to fetch
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN order_cursor;

    -- Loop through each order
    read_loop: LOOP
        FETCH order_cursor INTO order_id;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Calculate the new total amount for the current order
        SELECT SUM(f.price * fo.num) INTO new_amount
        FROM food_order fo
        JOIN foods f ON fo.foodId = f.id
        WHERE fo.orderId = order_id;

        -- Update the payments table with the new amount
        UPDATE payments
        SET amount = new_amount
        WHERE orderId = order_id;
    END LOOP;

    CLOSE order_cursor;
END;
//



DELIMITER ;



insert into users (username,password,email,birthdate,signup_date) VALUES ("saman","123456","saman@g.com",CURDATE(),now());

insert into users (username,password,email,birthdate,signup_date) VALUES ("saman2","1232456","saman2@g.com",CURDATE(),now());

insert into users (username,password,email,birthdate,signup_date) VALUES ("saman3","12w32456","saman3@g.com",CURDATE(),now());

insert into foods (name ,price ,description,status) VALUES ("pizza",12000,"this pizza",0);


insert into foods (name ,price ,description,status) VALUES ("pizza2",12000,"this pizza2",0);

insert into orders (userId,delivererId) VALUES (4,1);


insert into food_order (foodId,orderId,num) VALUES (1,1,3);

insert into food_order (foodId,orderId,num) VALUES (2,2,4);

UPDATE food_order set num =5 where foodId=1 and orderId=1;


DELETE from food_order where foodId=2 and orderId=1;
