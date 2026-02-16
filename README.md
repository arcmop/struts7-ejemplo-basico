# Tienda (Struts)

## Validaciones implementadas

Estas validaciones se aplican tanto al **registro** como a la **edición** de productos.

### Lado servidor (Struts)

**Nombre (`producto.nombre`)**

1. Requerido.
2. Longitud mínima: 3 caracteres.
3. Longitud máxima: 120 caracteres.
4. Se hace `trim()` antes de validar.

**Descripción (`producto.descripcion`)**

1. Opcional.
2. Longitud máxima: 500 caracteres.
3. Se hace `trim()` antes de validar.

**Precio (`producto.precio`)**

1. Requerido.
2. Debe ser un número válido (no NaN/Infinity).
3. Mínimo: 0.
4. Máximo: 99,999,999.99.
5. Máximo 2 decimales.

**Stock (`producto.stock`)**

1. Requerido.
2. Mínimo: 0.
3. Máximo: 2,147,483,647.

**Id (`producto.id`)**

1. Requerido solo en **edición**.

### Lado cliente (HTML)

**Nombre**

1. `required`
2. `minlength="3"`
3. `maxlength="120"`

**Descripción**

1. `maxlength="500"`

**Precio**

1. `required`
2. `min="0"`
3. `max="99999999.99"`
4. `step="0.01"`

**Stock**

1. `required`
2. `min="0"`
3. `max="2147483647"`
4. `step="1"`
