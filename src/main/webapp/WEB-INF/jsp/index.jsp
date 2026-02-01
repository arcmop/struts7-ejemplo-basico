<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>

    <body>
        <h2>Calculadora Suma</h2>

        <s:if test="hasActionErrors()">
            <div style="color:red">
                <s:actionerror />
            </div>
        </s:if>

        <s:form action="sumar">
            <s:textfield name="numero1" label="Número 1" />
            <s:textfield name="numero2" label="Número 2" />
            <s:submit value="Sumar" />
        </s:form>

        <s:if test="resultado != null">
            <h3>Resultado:
                <s:property value="resultado" />
            </h3>
        </s:if>

    </body>

</html>