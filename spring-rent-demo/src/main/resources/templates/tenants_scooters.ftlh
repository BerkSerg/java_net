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
<h1>Scooters of tenant</h1>
<table>
    <thead>
    <tr>
        <th>Model</th>
        <th>Color</th>
    </tr>
    </thead>
    <tbody>
    <#list scooters as scooter>
        <tr>
            <td>${scooter.model}</td>
            <td>${scooter.color}</td>
        </tr>
    </#list>
    </tbody>

</table>

<h1>Free scooters</h1>
<table>
    <thead>
    <tr>
        <th>Model</th>
        <th>Color</th>
    </tr>
    </thead>
    <tbody>
    <#list freeScooters as scooter>
        <tr>
            <td>${scooter.model}</td>
            <td>${scooter.color}</td>
            <td>
                <form action="" method="post">
                    <input type="hidden" name="id" value="${scooter.id}">
                    <input  type="submit" value="Reserve">
                </form>
            </td>
        </tr>
    </#list>
    </tbody>

</table>

</body>
</html>
