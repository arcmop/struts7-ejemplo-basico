CREATE TABLE IF NOT EXISTS public.usuario (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60) NOT NULL UNIQUE,
    clave CHAR(64) NOT NULL,
    nombre_completo VARCHAR(120),
    es_admin BOOLEAN NOT NULL DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO public.usuario (username, clave, nombre_completo, es_admin, activo)
VALUES ('admin', 'b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496', 'Administrador', TRUE, TRUE)
ON CONFLICT (username) DO NOTHING;
