<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="<s:property value='%{locale.language}' />">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><s:text name="page.title" /></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
        <link href="<s:url value='/assets/css/productos.css' />" rel="stylesheet">
    </head>
    <body>
        <s:set var="isAdminSession" value="admin" />
        <s:url var="loginUrl" action="login" />
        <div class="container py-5">
            <div class="hero p-4 p-md-5 mb-4 animate__animated animate__fadeIn">
                <div class="hero-content d-flex flex-column flex-lg-row gap-4 align-items-start align-items-lg-center">
                    <div>
                        <div class="text-uppercase fw-semibold mb-2 d-flex align-items-center gap-2">
                            <i class="fa-solid fa-store"></i>
                            <span><s:text name="store.name" /></span>
                            <span class="store-cart" aria-label="<s:text name='cart.label' />">
                                <i class="fa-solid fa-cart-shopping"></i>
                            </span>
                        </div>
                        <span class="badge badge-soft text-uppercase mb-3">
                            <s:text name="catalog.badge">
                                <s:param value="anioTexto" />
                            </s:text>
                        </span>
                        <h1 class="display-6 fw-bold mb-2"><s:text name="hero.title" /></h1>
                        <p class="mb-0 text-white-50">
                            <s:text name="hero.subtitle" />
                        </p>
                    </div>
                    <div class="ms-lg-auto d-flex flex-column flex-sm-row gap-3 align-items-start align-items-sm-center">
                        <div class="feature-icon">
                            <i class="fa-solid fa-truck-fast"></i>
                        </div>
                        <div class="feature-icon">
                            <i class="fa-solid fa-shield-halved"></i>
                        </div>
                        <div class="feature-icon">
                            <i class="fa-solid fa-tag"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between mb-3 gap-3">
                <div class="clock d-flex align-items-center gap-2">
                    <i class="fa-regular fa-clock"></i>
                    <span class="small text-muted"><s:text name="clock.label" /></span>
                    <span id="liveClock" class="fw-semibold"></span>
                </div>
                <div class="d-flex flex-wrap align-items-center gap-3">
                    <div class="lang-switch d-flex align-items-center gap-2">
                        <span class="small text-muted"><s:text name="language.label" /></span>
                        <div class="btn-group btn-group-sm" role="group" aria-label="<s:text name='language.label' />">
                            <s:a action="productos" cssClass="btn btn-outline-secondary">
                                <s:param name="request_cookie_locale">es</s:param>
                                <s:text name="language.es" />
                            </s:a>
                            <s:a action="productos" cssClass="btn btn-outline-secondary">
                                <s:param name="request_cookie_locale">en</s:param>
                                <s:text name="language.en" />
                            </s:a>
                        </div>
                    </div>
                    <s:if test="#isAdminSession">
                        <span class="badge text-bg-success"><s:text name="auth.admin_badge" /></span>
                        <s:a action="producto-nuevo" cssClass="btn btn-primary btn-sm">
                            <i class="fa-solid fa-circle-plus me-1"></i>
                            <s:text name="button.new" />
                        </s:a>
                        <s:a action="usuario-nuevo" cssClass="btn btn-outline-primary btn-sm">
                            <i class="fa-solid fa-user-plus me-1"></i>
                            <s:text name="button.user.new" />
                        </s:a>
                        <s:a action="password-cambiar" cssClass="btn btn-outline-primary btn-sm">
                            <i class="fa-solid fa-key me-1"></i>
                            <s:text name="button.password.self" />
                        </s:a>
                    </s:if>
                    <s:if test="autenticado">
                        <s:if test="usernameAutenticado != null && !usernameAutenticado.isEmpty()">
                            <span class="badge text-bg-secondary">
                                <i class="fa-solid fa-user me-1"></i>
                                <s:text name="auth.logged_as">
                                    <s:param value="usernameAutenticado" />
                                </s:text>
                            </span>
                        </s:if>
                        <s:a action="logout" cssClass="btn btn-outline-danger btn-sm">
                            <i class="fa-solid fa-right-from-bracket me-1"></i>
                            <s:text name="auth.logout" />
                        </s:a>
                    </s:if>
                    <s:else>
                        <button
                            type="button"
                            class="btn btn-outline-primary btn-sm"
                            data-bs-toggle="modal"
                            data-bs-target="#loginModal">
                            <i class="fa-solid fa-right-to-bracket me-1"></i>
                            <s:text name="auth.login" />
                        </button>
                    </s:else>
                </div>
            </div>

            <s:if test="hasActionErrors()">
                <div class="alert alert-danger d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <div><s:actionerror /></div>
                </div>
            </s:if>

            <s:if test="!autenticado">
                <div class="modal fade login-modal" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="loginModalLabel">
                                    <i class="fa-solid fa-user-shield me-2"></i>
                                    <s:text name="auth.login" />
                                </h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body p-4">
                                <s:if test="loginError != null && !loginError.isEmpty()">
                                    <div class="alert alert-danger d-flex align-items-center gap-2" role="alert">
                                        <i class="fa-solid fa-triangle-exclamation"></i>
                                        <div><s:property value="loginError" /></div>
                                    </div>
                                </s:if>
                                <form action="<s:property value='#loginUrl' />" method="post" class="d-grid gap-3">
                                    <div>
                                        <label for="loginUsername" class="form-label"><s:text name="auth.username" /></label>
                                        <input
                                            id="loginUsername"
                                            type="text"
                                            name="username"
                                            class="form-control"
                                            autocomplete="username"
                                            required />
                                    </div>
                                    <div>
                                        <label for="loginPassword" class="form-label"><s:text name="auth.password" /></label>
                                        <input
                                            id="loginPassword"
                                            type="password"
                                            name="password"
                                            class="form-control"
                                            autocomplete="current-password"
                                            required />
                                    </div>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa-solid fa-right-to-bracket me-1"></i>
                                        <s:text name="auth.login" />
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div
                    id="loginModalState"
                    class="d-none"
                    data-auto-open="<s:property value='loginErrorCode != null' />"></div>
            </s:if>

            <s:if test="listaProducto != null && listaProducto.size > 0">
                <div class="row g-4">
                    <s:iterator value="listaProducto">
                        <div class="col-12 col-md-6 col-xl-4">
                            <div class="card card-product h-100 animate__animated animate__fadeInUp">
                                <div class="card-body p-4 d-flex flex-column gap-3">
                                    <div class="d-flex align-items-start justify-content-between gap-3">
                                        <div class="product-info">
                                            <h5 class="fw-semibold mb-1">
                                                <i class="fa-solid fa-laptop-code text-primary me-2"></i>
                                                <s:property value="nombre" />
                                            </h5>
                                            <div class="text-muted small">
                                                <s:text name="product.code" /><s:property value="id" />
                                            </div>
                                        </div>
                                        <span class="price-tag">
                                            <s:text name="currency.symbol" /> <s:property value="precio" />
                                        </span>
                                    </div>
                                    <p class="mb-0 text-secondary">
                                        <s:property value="descripcion" />
                                    </p>
                                    <div class="d-flex flex-wrap gap-2 pt-2">
                                        <span class="badge text-bg-light">
                                            <i class="fa-solid fa-circle-check text-success me-1"></i>
                                            <s:text name="badge.warranty" />
                                        </span>
                                        <span class="badge text-bg-light">
                                            <i class="fa-solid fa-box text-primary me-1"></i>
                                            <s:text name="badge.stock" />: <s:property value="stock" />
                                        </span>
                                        <span class="badge text-bg-light">
                                            <i class="fa-solid fa-bolt text-warning me-1"></i>
                                            <s:text name="badge.delivery" />
                                        </span>
                                    </div>
                                    <div class="card-actions d-flex flex-wrap gap-2 pt-2">
                                        <s:url var="editUrl" action="producto-editar">
                                            <s:param name="id" value="id" />
                                        </s:url>
                                        <a
                                            class="btn btn-sm btn-outline-primary action-btn"
                                            href="<s:property value='#editUrl' />">
                                            <i class="fa-solid fa-pen-to-square me-1"></i>
                                            <s:text name="button.edit" />
                                        </a>
                                        <s:if test="#isAdminSession">
                                            <s:url var="deleteUrl" action="producto-eliminar" />
                                            <s:text name="confirm.delete" var="confirmDeleteMessage">
                                                <s:param value="nombre" />
                                            </s:text>
                                            <form
                                                class="d-inline"
                                                action="<s:property value='#deleteUrl' />"
                                                method="post"
                                                data-confirm="<s:property value='#confirmDeleteMessage' />">
                                                <input type="hidden" name="id" value="<s:property value='id' />" />
                                                <button type="submit" class="btn btn-sm btn-outline-danger action-btn">
                                                    <i class="fa-solid fa-trash-can me-1"></i>
                                                    <s:text name="button.delete" />
                                                </button>
                                            </form>
                                        </s:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </s:iterator>
                </div>
                <s:if test="totalPaginas > 1">
                    <div class="d-flex justify-content-center mt-4">
                        <nav aria-label="Paginación de productos">
                            <ul class="pagination pagination-sm mb-0">
                                <s:if test="tienePaginaAnterior">
                                    <s:url var="previousPageUrl" action="productos">
                                        <s:param name="page" value="paginaAnterior" />
                                    </s:url>
                                    <li class="page-item">
                                        <a class="page-link" href="<s:property value='#previousPageUrl' />">
                                            <s:text name="pagination.previous" />
                                        </a>
                                    </li>
                                </s:if>
                                <s:else>
                                    <li class="page-item disabled">
                                        <span class="page-link"><s:text name="pagination.previous" /></span>
                                    </li>
                                </s:else>

                                <li class="page-item disabled">
                                    <span class="page-link">
                                        <s:text name="pagination.page_status">
                                            <s:param value="paginaActual" />
                                            <s:param value="totalPaginas" />
                                        </s:text>
                                    </span>
                                </li>

                                <s:if test="tienePaginaSiguiente">
                                    <s:url var="nextPageUrl" action="productos">
                                        <s:param name="page" value="paginaSiguiente" />
                                    </s:url>
                                    <li class="page-item">
                                        <a class="page-link" href="<s:property value='#nextPageUrl' />">
                                            <s:text name="pagination.next" />
                                        </a>
                                    </li>
                                </s:if>
                                <s:else>
                                    <li class="page-item disabled">
                                        <span class="page-link"><s:text name="pagination.next" /></span>
                                    </li>
                                </s:else>
                            </ul>
                        </nav>
                    </div>
                </s:if>
            </s:if>
            <s:else>
                <div class="alert alert-info d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-info"></i>
                    <div><s:text name="empty.message" /></div>
                </div>
            </s:else>

            <footer class="footer mt-5 pt-4">
                <div class="d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between gap-3">
                    <div class="text-muted">
                        <strong><s:text name="store.name" /></strong> ·
                        <s:text name="catalog.badge">
                            <s:param value="anioTexto" />
                        </s:text>
                    </div>
                    <div class="d-flex gap-3">
                        <a class="social-link" href="#" aria-label="<s:text name='social.instagram' />">
                            <i class="fa-brands fa-instagram"></i>
                        </a>
                        <a class="social-link" href="#" aria-label="<s:text name='social.facebook' />">
                            <i class="fa-brands fa-facebook"></i>
                        </a>
                        <a class="social-link" href="#" aria-label="<s:text name='social.x' />">
                            <i class="fa-brands fa-x-twitter"></i>
                        </a>
                        <a class="social-link" href="#" aria-label="<s:text name='social.tiktok' />">
                            <i class="fa-brands fa-tiktok"></i>
                        </a>
                    </div>
                </div>
            </footer>
        </div>

        <script src="<s:url value='/assets/js/productos.js' />"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
