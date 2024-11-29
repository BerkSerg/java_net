const api_address = "http://localhost:8090/api/v1";
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        firstname: document.getElementById('firstname').value,
        lastname: document.getElementById('lastname').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        city: document.getElementById('city').value,
        birth_date: document.getElementById('birth_date').value,
    };

    const response = await fetch(api_address+'/registration', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });

    const result = await response.json();
    document.getElementById('registerMessage').innerText = result.message || result.error;
});

// Вход
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = {
        email: document.getElementById('loginEmail').value,
        password: document.getElementById('loginPassword').value
    };

    const response = await fetch(api_address+'/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });

    const result = await response.json();
    if (result.body?.token) {
        localStorage.setItem('token', result.body.token);
        document.getElementById('loginMessage').innerText = 'Вход выполнен!';
    } else {
        document.getElementById('loginMessage').innerText = result.error;
    }
});

// Поиск жилья
document.getElementById('searchForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const params = {
        city: document.getElementById('searchCity').value,
        country: document.getElementById('searchCountry').value,
        minPrice: document.getElementById('minPrice').value,
        maxPrice: document.getElementById('maxPrice').value,
        availableFrom: document.getElementById('availableFrom').value,
        availableTo: document.getElementById('availableTo').value
    };

    const query = new URLSearchParams(params).toString();
    const response = await fetch(api_address+`/properties?${query}`, {
        headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
    });

    const properties = await response.json();
    const propertiesList = document.getElementById('propertiesList');
    propertiesList.innerHTML = '';

    if (Array.isArray(properties)) {
        properties.forEach(property => {
            const div = document.createElement('div');
            div.innerText = `Жилье: ${property.title}, Цена: ${property.price_per_day}, Город: ${property.city}`;
            propertiesList.appendChild(div);
        });
    } else {
        propertiesList.innerText = properties.error || 'Ошибка при поиске';
    }
});