<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>文件上传</title>
<script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
  $(function() {
    $(parent.document).find("#file-name").value("${fileName}");
  });
</script>
</head>
<body>
  <h1>${fileName}</h1>
</body>
</html>
