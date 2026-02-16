CREATE TABLE public.producto (
	id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	nombre varchar(50) NOT NULL,
	descripcion varchar(150) NULL,
	precio numeric(10, 2) NOT NULL,
	stock int4 DEFAULT 0 NOT NULL,
	creado_por varchar(60) DEFAULT 'sistema'::character varying NOT NULL,
	actualizado_por varchar(60) DEFAULT 'sistema'::character varying NOT NULL,
	creado_en timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	actualizado_en timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE public.usuario (
	id bigserial NOT NULL,
	username varchar(60) NOT NULL,
	clave bpchar(64) NOT NULL,
	nombre_completo varchar(120) NULL,
	es_admin bool DEFAULT false NOT NULL,
	activo bool DEFAULT true NOT NULL,
	creado_en timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	actualizado_en timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	creado_por varchar(60) DEFAULT 'sistema'::character varying NOT NULL,
	actualizado_por varchar(60) DEFAULT 'sistema'::character varying NOT NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (id),
	CONSTRAINT usuario_username_key UNIQUE (username)
);

INSERT INTO public.usuario (username, clave, nombre_completo, es_admin, activo, creado_por, actualizado_por)
VALUES ('admin', 'b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496', 'Administrador', TRUE, TRUE, 'sistema', 'sistema')
ON CONFLICT (username) DO NOTHING;
