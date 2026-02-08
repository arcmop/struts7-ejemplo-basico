<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                                <s:param name="request_locale">es</s:param>
                                <s:text name="language.es" />
                            </s:a>
                            <s:a action="productos" cssClass="btn btn-outline-secondary">
                                <s:param name="request_locale">en</s:param>
                                <s:text name="language.en" />
                            </s:a>
                        </div>
                    </div>
                    <s:a action="producto-nuevo" cssClass="btn btn-primary btn-sm">
                        <i class="fa-solid fa-circle-plus me-1"></i>
                        <s:text name="button.new" />
                    </s:a>
                </div>
            </div>

            <s:if test="hasActionErrors()">
                <div class="alert alert-danger d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <div><s:actionerror /></div>
                </div>
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
                                    </div>
                                </div>
                            </div>
                        </div>
                    </s:iterator>
                </div>
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
