<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Register page</title>
</head>
<body>
<h1>Tenants</h1>
<p>Edit: </p>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <#list tenants as tenant>
        <tr>
            <td>${tenant.id}</td>
            <td>${tenant.email}</td>
        </tr>
    </#list>
    </tbody>

</table>
</body>
</html>