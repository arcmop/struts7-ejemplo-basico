<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="isNuevo" value="producto != null && producto.id == null" />
<!doctype html>
<html lang="<s:property value='%{locale.language}' />">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>
            <s:if test="#isNuevo">
                <s:text name="new.title" />
            </s:if>
            <s:else>
                <s:text name="edit.title" />
            </s:else>
        </title>
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
                        <span class="badge badge-soft text-uppercase mb-3">
                            <s:if test="#isNuevo">
                                <s:text name="new.badge" />
                            </s:if>
                            <s:else>
                                <s:text name="edit.badge" />
                            </s:else>
                        </span>
                        <h1 class="display-6 fw-bold mb-2">
                            <s:if test="#isNuevo">
                                <s:text name="new.title" />
                            </s:if>
                            <s:else>
                                <s:text name="edit.title" />
                            </s:else>
                        </h1>
                        <p class="mb-0 text-white-50">
                            <s:if test="#isNuevo">
                                <s:text name="new.subtitle" />
                            </s:if>
                            <s:else>
                                <s:text name="edit.subtitle" />
                            </s:else>
                        </p>
                    </div>
                    <div class="ms-lg-auto">
                        <s:a action="productos" cssClass="btn btn-outline-light btn-sm">
                            <i class="fa-solid fa-arrow-left me-1"></i>
                            <s:text name="button.back" />
                        </s:a>
                    </div>
                </div>
            </div>

            <s:if test="hasActionErrors()">
                <div class="alert alert-danger d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <div><s:actionerror /></div>
                </div>
            </s:if>
            <s:if test="hasFieldErrors()">
                <div class="alert alert-danger d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <div><s:fielderror /></div>
                </div>
            </s:if>

            <s:url var="formActionUrl" action="producto-actualizar" />
            <s:if test="#isNuevo">
                <s:url var="formActionUrl" action="producto-registrar" />
            </s:if>

            <s:if test="producto != null">
                <div class="card form-card">
                    <div class="card-body p-4">
                        <form action="<s:property value='#formActionUrl' />" method="post" class="row g-3">
                            <s:if test="producto.id != null">
                                <input type="hidden" name="producto.id" value='<s:property value="producto.id" />' />
                            </s:if>
                            <div class="col-12">
                                <label class="form-label"><s:text name="form.name" /></label>
                                <input
                                    class="form-control"
                                    name="producto.nombre"
                                    type="text"
                                    minlength="3"
                                    maxlength="120"
                                    required
                                    value='<s:property value="producto.nombre" />' />
                            </div>
                            <div class="col-12">
                                <label class="form-label"><s:text name="form.description" /></label>
                                <textarea
                                    class="form-control"
                                    name="producto.descripcion"
                                    maxlength="500"
                                    rows="3"><s:property value="producto.descripcion" /></textarea>
                            </div>
                            <div class="col-12 col-md-6">
                                <label class="form-label"><s:text name="form.price" /></label>
                                <input
                                    class="form-control"
                                    name="producto.precio"
                                    type="number"
                                    min="0"
                                    max="99999999.99"
                                    step="0.01"
                                    required
                                    value="<s:property value='producto.precio' />" />
                            </div>
                            <div class="col-12 col-md-6">
                                <label class="form-label"><s:text name="form.stock" /></label>
                                <input
                                    class="form-control"
                                    name="producto.stock"
                                    type="number"
                                    min="0"
                                    max="2147483647"
                                    step="1"
                                    required
                                    value="<s:property value='producto.stock' />" />
                            </div>
                            <div class="col-12 d-flex flex-wrap gap-2 pt-2">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fa-solid fa-floppy-disk me-1"></i>
                                    <s:if test="#isNuevo">
                                        <s:text name="button.create" />
                                    </s:if>
                                    <s:else>
                                        <s:text name="button.save" />
                                    </s:else>
                                </button>
                                <s:a action="productos" cssClass="btn btn-outline-secondary">
                                    <s:text name="button.cancel" />
                                </s:a>
                            </div>
                        </form>
                    </div>
                </div>
            </s:if>
            <s:else>
                <div class="alert alert-warning d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-triangle-exclamation"></i>
                    <div><s:text name="edit.not_found" /></div>
                </div>
            </s:else>
        </div>

        <script src="<s:url value='/assets/js/productos.js' />"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
