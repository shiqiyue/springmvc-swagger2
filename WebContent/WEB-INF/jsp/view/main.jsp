<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="blog" uri="http://www.wuwenyao.cn/blog/tld"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>

<!DOCTYPE html>
<html>
<head>
<title>main</title>
</head>
<body>
hello world!!!
<a href="<c:url value='/test/t1?info=hello'></c:url>">带参数的test</a>
<a href="<c:url value='/test/t2'></c:url>">不带参数的test</a>
<a href="<c:url value='/user/save'></c:url>">添加用户</a>
</body>