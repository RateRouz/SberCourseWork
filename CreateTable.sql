CREATE TABLE sber_user.counterparties
(
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    kpp integer NOT NULL,
    account_number character varying(20) COLLATE pg_catalog."default" NOT NULL,
    bank_bik integer NOT NULL,
    inn bigint NOT NULL,
    id integer NOT NULL DEFAULT nextval('sber_user.counterparties_id_seq'::regclass),
    CONSTRAINT counterparties_pkey PRIMARY KEY (id)
)