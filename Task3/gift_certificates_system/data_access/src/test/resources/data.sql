-- #############################################
-- ##########gift_certificate table#############
-- #############################################
INSERT INTO public.gift_certificate
(id,
 name,
 description,
 price,
 duration,
 create_date,
 last_update_date)
VALUES (1,
        '1certificate',
        '1description',
        10.0,
        1,
        '2001-01-01T06:12:15.156',
        '2001-01-01T06:12:15.156');
INSERT INTO public.gift_certificate
(id,
 name,
 description,
 price,
 duration,
 create_date,
 last_update_date)
VALUES (2,
        '2certificate',
        '2description',
        20.0,
        2,
        '2002-02-02T06:12:15.156',
        '2002-02-02T06:12:15.156');
INSERT INTO public.gift_certificate
(id,
 name,
 description,
 price,
 duration,
 create_date,
 last_update_date)
VALUES (3,
        '3certificate',
        '3description',
        30.0,
        3,
        '2003-03-03T06:12:15.156',
        '2003-03-03T06:12:15.156');
-- #############################################
-- ################tag table####################
-- #############################################
INSERT INTO public.tag
(id,
 name)
VALUES (1,
        '1tag');
INSERT INTO public.tag
(id,
 name)
VALUES (2,
        '2tag');
INSERT INTO public.tag
(id,
 name)
VALUES (3,
        '3tag');
-- #############################################
-- ########gift_certificate_tag table###########
-- #############################################
INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (1,
        1);

INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (2,
        1);
INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (2,
        2);

INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (3,
        1);
INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (3,
        2);
INSERT INTO public.gift_certificate_tag
(id_gift_certificate,
 id_tag)
VALUES (3,
        3);