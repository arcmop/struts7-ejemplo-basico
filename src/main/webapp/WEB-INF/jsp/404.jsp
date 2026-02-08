<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="<%= request.getLocale().getLanguage() %>">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>404 | P&aacute;gina no encontrada</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/productos.css" rel="stylesheet">
    </head>
    <body>
        <div class="container py-5">
            <div class="hero p-4 p-md-5 mb-4">
                <div class="hero-content d-flex flex-column flex-lg-row gap-4 align-items-start align-items-lg-center">
                    <div>
                        <span class="badge badge-soft text-uppercase mb-3">404</span>
                        <h1 class="display-6 fw-bold mb-2">P&aacute;gina no encontrada</h1>
                        <p class="mb-0 text-white-50">
                            La acci&oacute;n o recurso solicitado no existe.
                        </p>
                    </div>
                </div>
            </div>

            <div class="card form-card">
                <div class="card-body p-4 d-flex flex-column gap-3">
                    <p class="mb-0">
                        Verifica la URL o vuelve al listado de productos.
                    </p>
                    <div>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/productos.action">
                            <i class="fa-solid fa-arrow-left me-1"></i>
                            Volver al cat&aacute;logo
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
