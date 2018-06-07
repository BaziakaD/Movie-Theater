insert
into
    discount
    (discount, type)
values
    (0.4, 'BIRTHDAY');

insert
into
    discount
    (discount, type)
values
    (0.5, 'TENTH_TICKET');

insert
into
    discount
    (discount, type)
values
    (1.0, 'LUCKY_WINNER');

insert
into
    discount
    (discount, type)
values
    (0, 'NONE');

insert
into
    role
    (role)
values
    ('ROLE_ADMIN');

insert
into
    role
    (role)
values
    ('ROLE_USER');

insert
into
    user
    (bill, BIRTHDAY, email, password, role_id)
values
        (1000, '1998-01-05', 'admin@mail.com', '$2a$10$3EBwfaF8jh8DKobDQfASlueSr.tczbGsvC/TWWN11qRtT/Q69Yl4G', 1);

insert
into
    user
    (bill, BIRTHDAY, email, password, role_id)
values
    (1500, '1998-02-06', 'user@mail.com', '$2a$10$3EBwfaF8jh8DKobDQfASlueSr.tczbGsvC/TWWN11qRtT/Q69Yl4G', 2);