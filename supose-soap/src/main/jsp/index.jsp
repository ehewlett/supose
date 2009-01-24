<%@ page import="com.soebes.supose.soap.services.ReleaseProperties" %>
<html>
	<head>
		<title>SupoSE SOAP Service</title>
	</head>

	<body>
		<table>
			<tr>
				<td style="align:right;">Version:</td><td><%= ReleaseProperties.getRelease() %></td>
			</tr>
			<tr>
				<td style="align:right;">Buildnumber:</td><td><%= ReleaseProperties.getBuild() %></td>
			</tr>
		</table>
	</body>
</html>
