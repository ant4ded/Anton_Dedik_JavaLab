SET MODE PostgreSQL;

CREATE TABLE public.gift_certificate
(
    id               bigserial                NOT NULL,
    name             character varying(60)    NOT NULL,
    description      character varying(120),
    price            double precision         NOT NULL,
    duration         integer                  NOT NULL,
    create_date      timestamp with time zone NOT NULL,
    last_update_date timestamp with time zone NOT NULL
);

CREATE TABLE public.tag
(
    id   bigserial             NOT NULL,
    name character varying(60) NOT NULL
);

CREATE TABLE public.gift_certificate_tag
(
    id_gift_certificate bigint NOT NULL,
    id_tag              bigint NOT NULL
);

ALTER TABLE public.gift_certificate
    ADD CONSTRAINT gift_certificate_pkey PRIMARY KEY (id);


ALTER TABLE public.gift_certificate_tag
    ADD CONSTRAINT gift_certificate_tag_pkey PRIMARY KEY (id_gift_certificate, id_tag);

ALTER TABLE public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE public.tag
    ADD CONSTRAINT unique_tag_name UNIQUE (name);

ALTER TABLE public.gift_certificate_tag
    ADD CONSTRAINT "fk_GiftCertificateTag_idGiftCertificate_GiftCertificate_id"
        FOREIGN KEY (id_gift_certificate) REFERENCES public.gift_certificate (id) ON UPDATE CASCADE ON DELETE RESTRICT;


ALTER TABLE public.gift_certificate_tag
    ADD CONSTRAINT "fk_GiftCertificateTag_idTag_Tag_id"
        FOREIGN KEY (id_gift_certificate) REFERENCES public.tag (id) ON UPDATE CASCADE ON DELETE RESTRICT;
