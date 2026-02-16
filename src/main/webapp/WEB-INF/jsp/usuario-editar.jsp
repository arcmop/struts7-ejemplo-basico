<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="<s:property value='%{locale.language}' />">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><s:text name="user.new.title" /></title>
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
                            <s:text name="user.new.badge" />
                        </span>
                        <h1 class="display-6 fw-bold mb-2"><s:text name="user.new.title" /></h1>
                        <p class="mb-0 text-white-50"><s:text name="user.new.subtitle" /></p>
                    </div>
                    <div class="ms-lg-auto">
                        <s:a action="productos" cssClass="btn btn-outline-light btn-sm">
                            <i class="fa-solid fa-arrow-left me-1"></i>
                            <s:text name="button.back" />
                        </s:a>
                    </div>
                </div>
            </div>

            <s:if test="hasActionMessages()">
                <div class="alert alert-success d-flex align-items-center gap-2" role="alert">
                    <i class="fa-solid fa-circle-check"></i>
                    <div><s:actionmessage /></div>
                </div>
            </s:if>
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

            <s:url var="formActionUrl" action="usuario-registrar" />
            <div class="card form-card">
                <div class="card-body p-4">
                    <form action="<s:property value='#formActionUrl' />" method="post" class="row g-3">
                        <div class="col-12">
                            <label class="form-label"><s:text name="user.username" /></label>
                            <input
                                class="form-control"
                                name="username"
                                type="text"
                                minlength="3"
                                maxlength="60"
                                pattern="[A-Za-z0-9._-]+"
                                required
                                value="<s:property value='username' />" />
                        </div>
                        <div class="col-12">
                            <label class="form-label"><s:text name="user.fullname" /></label>
                            <input
                                class="form-control"
                                name="nombreCompleto"
                                type="text"
                                maxlength="120"
                                value="<s:property value='nombreCompleto' />" />
                        </div>
                        <div class="col-12 col-md-6">
                            <label class="form-label"><s:text name="user.password" /></label>
                            <input
                                class="form-control"
                                name="password"
                                type="password"
                                minlength="8"
                                maxlength="72"
                                autocomplete="new-password"
                                required />
                        </div>
                        <div class="col-12 col-md-6">
                            <label class="form-label"><s:text name="user.password.confirm" /></label>
                            <input
                                class="form-control"
                                name="confirmPassword"
                                type="password"
                                minlength="8"
                                maxlength="72"
                                autocomplete="new-password"
                                required />
                        </div>
                        <div class="col-12">
                            <div class="form-check">
                                <input
                                    class="form-check-input"
                                    id="esAdmin"
                                    name="esAdmin"
                                    type="checkbox"
                                    value="true"
                                    <s:if test="esAdmin">checked</s:if> />
                                <label class="form-check-label" for="esAdmin">
                                    <s:text name="user.is_admin" />
                                </label>
                            </div>
                        </div>
                        <div class="col-12 d-flex flex-wrap gap-2 pt-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="fa-solid fa-user-plus me-1"></i>
                                <s:text name="button.user.create" />
                            </button>
                            <s:a action="productos" cssClass="btn btn-outline-secondary">
                                <s:text name="button.cancel" />
                            </s:a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="<s:url value='/assets/js/productos.js' />"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
