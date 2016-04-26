<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>留言记录</title>
<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.12.3.min.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/jquery.form.js"></c:url>'></script>
<script type="text/javascript">
	$(function() {
		$("#cross-form").ajaxForm({
			iframe : true,
            method: "POST",
            dataType: "json",
            success : function(data){
                $("#file-name").val(JSON.stringify(data));
            }
		});
	});
</script>
</head>
<body>
  <h1>留言记录</h1>
  <div>
    <form id="cross-form" action="http://localhost:3000/demo/upload" method="POST"
      autocomplete="off">
      <div>
        <input type="text" name="id" value="file-name">
      </div>
      <div>
        <input type="text" name="redirectUrl" value="http://localhost:8080/mvc-simple/cross/data">
      </div>
      <div>
        <input id="file-name" type="text" name="fileName">
      </div>
      <div>
        <input type="submit" value="提交">
      </div>
    </form>
  </div>
</body>
</html>
