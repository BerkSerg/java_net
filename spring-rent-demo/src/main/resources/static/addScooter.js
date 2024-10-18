function addScooter(color, model) {
    let body = {
        color: color,
        model, model
    }
    const req = new XMLHttpRequest();
    req.open("POST", "/scooters", false);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(body));

    if (req.status !== 201) {
        console.error("Error: " + req.statusText);
    } else {

    }


}