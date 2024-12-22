const API_BASE = 'http://127.0.0.1:8090/api/v1';
let userToken = null;

function setContent(html) {
    document.getElementById('main-content').innerHTML = html;
}
function setContentFilter(html) {
    document.getElementById('filter-content').innerHTML = html;
}
function setNavPanel(html) {
    document.getElementById('nav-panel').innerHTML = html;
}

function setModal(html) {
    document.getElementById('modal-body').innerHTML = html;
}
function showModal(){
    document.getElementById('modal-panel').style.display = 'flex';
}
function closeModal(){
    document.getElementById('modal-body').innerHTML='';
    document.getElementById('modal-panel').style.display = 'none';
}

function setNav(){
    if (userToken){
        setNavPanel(`
            <button id="btn-my-booking">Мои бронирования</button>
            <button id="btn-add-prop">Предложить свою недвижимость</button>
            <button id="btn-profile">Профиль</button>
            <button id="btn-exit">Выход</button>
        `);
        document.getElementById('btn-my-booking').onclick = handleMyBooking;
        document.getElementById('btn-add-prop').onclick = handleProperty;
        document.getElementById('btn-profile').onclick = handleProfile;
        document.getElementById('btn-exit').onclick = handleExit;
    }else{
        setNavPanel(`
            <button id="btn-login">Войти</button>
            <button id="btn-register">Регистрация</button>
        `);
        document.getElementById('btn-login').onclick = handleLogin;
        document.getElementById('btn-register').onclick = handleRegister;
    }
}

async function handleMyBooking(){


    try {
        const response = await fetch(`${API_BASE}/booking/my`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${userToken}`
            },
        });

        if (response.ok) {
            const data = await response.json();
            const bookings = data.bookings;

            setContent(`
                <h2>Мои бронирования</h2>
                <form id="my-booking-form">
                  <ul id="booking-list">
                    ${bookings.map((b) =>
                    `<li>
                        <div class="booking-item">
                            <div class="prop-desc">
                                <div class="p-title">${b.propertyTitle}</div>
                                <div class="p-city">${b.propertyDescription}</div>
                            </div>
                            <div class="prop-act">
                                <span>Даты бронирования с </span>
                                <input type="date" readonly value="${b.startDate}" />
                                <span> по </span>
                                <input type="date" readonly value="${b.endDate}" /><br><br>
                                <span> Cумма: </span>
                                <span class="prop-m-title">${b.totalAmount}</span>
                                <br><br>
                                <span> Статус бронирования: </span><span class="prop-m-title">${b.status}</span>
                                <br><br>
                                ${needButton(b.status, b.id)}
                                
                            </div>
                        </div>
                        </li>`
                        ).join('')}
                  </ul>                
                </form>
            `);
        } else {
            alert('Ошибка получения данных');
        }
    } catch (err) {
        console.error(err);
        alert('Error during get booking');
    }
}

const needButton = (st, id) => (st==="NEW"||st==="CONFIRMED")?`<button type="button" class="cancel-btn" onclick="handleCancel(${id})" >Отменить бронирование</button>`:``;

async function handleCancel(bookId){
    try {
        const response = await fetch(`${API_BASE}/booking/${bookId}/cancel`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${userToken}`
            },
        });

        if (response.ok) {
            //const data = await response.json();
            handleMyBooking();
        }else{
            alert('Ошибка при отмене бронирования');
        }

    } catch (err) {
        console.error(err);
        alert('Error during cancel booking');
    }
}

async function handleProperty() {
    setContent(`
        <h2>Разместить недвижимость</h2>
        <form id="prop-form">
          <select id="city" placeholder="Город" required>
              <option value="Москва">Москва</option>
              <option value="Санкт-петербург">Санкт-петербург</option>
              <option value="Казань">Казань</option>
          </select>
          <select id="property-type" required>
              <option value="FLAT">Квартира/номер</option>
              <option value="HOUSE">Дом/коттедж</option>
          </select>
          <input type="text" id="prop-title" placeholder="Краткая информация (заголовок)" required />
          <textarea id="description" placeholder="Полное описание" required ></textarea>
          <input type="number" id="price-per-day" placeholder="Цена за сутки" required />
          
          <h1>Загрузить фотографии</h1>
          <input type="file" id="photoInput" name="photos" multiple accept="image/*" />
                    
          <button type="submit">Добавить</button>
        </form>
    `);

    document.getElementById('prop-form').onsubmit = async (e) => {
        e.preventDefault();
        const city = document.getElementById('city').value;
        const propertyType = document.getElementById('property-type').value;
        const title = document.getElementById('prop-title').value;
        const description = document.getElementById('description').value;
        const pricePerDay = document.getElementById('price-per-day').value;

        try {
            const response = await fetch(`${API_BASE}/properties/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${userToken}`
                },
                body: JSON.stringify({city, propertyType, title, description, pricePerDay}),
            });

            if (response.ok) {

                 const data = await response.json();

                const photoInput = document.getElementById("photoInput");
                const files = photoInput.files;
                if (files.length > 0) {
                    const formData = new FormData();
                    for (const file of files) {
                        formData.append("files", file);
                    }

                    formData.append("propertyId", data.id);

                    const response = await fetch(`${API_BASE}/files/upload`, {
                        method: 'POST',
                        headers: {
                            Authorization: `Bearer ${userToken}`
                        },
                        body: formData,
                    });

                }


                alert('Недвижимость Успешно добавлена!');
                handleProfile();
            } else {
                alert('Ошибка добавления данных');
            }
        } catch (err) {
            console.error(err);
            alert('Error during add property');
        }
    };
}

async function handleProfile() {
    setContent(`
        <h2>Профиль пользователя</h2>
        <form id="profile-form">
          <span><label for="email">Email</label><input type="email" id="email" placeholder="Email" readonly /></span>
          <span><label for="firstname">Имя</label><input type="text" id="firstname" placeholder="Имя" required /></span>
          <span><label for="lastname">Фамилия</label><input type="text" id="lastname" placeholder="Фамилия" required /></span>
          <span><label for="nickname">Ник</label><input type="text" id="nickname" placeholder="Ник" /></span>
          <span><label for="birthdate">Дата рождения</label><input type="date" id="birthdate" placeholder="Дата рождения" required /></span>
          <span><label for="city">Город</label><select id="city" placeholder="Город" required></span>
              <option value="Москва">Москва</option>
              <option value="Санкт-петербург">Санкт-петербург</option>
              <option value="Казань">Казань</option>
          </select><br/><br/>
          <button type="submit">Сохранить</button>
        </form>
    `);

    try {
        const response = await fetch(`${API_BASE}/users/get`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${userToken}`,
            }
        });

        if (response.ok) {
            const data = await response.json();
            document.getElementById('email').value=data.email;
            document.getElementById('firstname').value=data.firstname;
            document.getElementById('lastname').value=data.lastname;
            document.getElementById('nickname').value=data.nickName;
            document.getElementById('birthdate').value=data.birthDate;
            document.getElementById('city').value=data.city;
        } else {
            alert('Ошибка получения данных');
        }
    } catch (err) {
        console.error(err);
        alert('Error during update');
    }

    document.getElementById('profile-form').onsubmit = async (e) => {
        e.preventDefault();
        const firstname = document.getElementById('firstname').value;
        const lastname = document.getElementById('lastname').value;
        const nickName = document.getElementById('nickname').value;
        const city = document.getElementById('city').value;
        const birthDate = document.getElementById('birthdate').value;

        try {
            const response = await fetch(`${API_BASE}/users/update`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${userToken}`
                },
                body: JSON.stringify({firstname, lastname, nickName, city, birthDate}),
            });

            if (response.ok) {
                alert('Данные пользователя успешно обновлены!');
                handleProfile();
            } else {
                alert('Ошибка обновления данных');
            }
        } catch (err) {
            console.error(err);
            alert('Error during update');
        }
    };
}

function handleExit() {
    userToken = null;
    setNav();
    startPage();
}

function handleLogin() {
    setContent(`
    <h2>Вход</h2>
    <form id="login-form">
      <input type="email" id="username" placeholder="Email" required />
      <input type="password" id="password" placeholder="Пароль" required />
      <button type="submit">Войти</button>
    </form>
  `);

    document.getElementById('login-form').onsubmit = async (e) => {
        e.preventDefault();
        const email = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(`${API_BASE}/auth/login`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({email, password}),
            });
            const data = await response.json();

            if (response.ok) {
                userToken = data.token;
                //alert('Login successful!');
                setNav();
                startProperties();
            } else {
                alert(data.message || 'Login failed');
            }
        } catch (err) {
            console.error(err);
            alert('Error during login');
        }
    };
}

function handleRegister() {
    setContent(`
    <h2>Регистрация</h2>
    <form id="register-form">
      <input type="text" id="firstname" placeholder="Имя" required />
      <input type="text" id="lastname" placeholder="Фамилия" required />
      <input type="email" id="email" placeholder="Email" required />
      <input type="password" id="password" placeholder="Пароль" required />
      <input type="date" id="birth_date" placeholder="Дата рождения" required />
      <select id="city" placeholder="Выберите город" required>
          <option value="Москва">Москва</option>
          <option value="Санкт-петербург">Санкт-петербург</option>
          <option value="Казань">Казань</option>
      </select><br/>
      <button type="submit">Зарегистрироваться</button>
    </form>
  `);

    document.getElementById('register-form').onsubmit = async (e) => {
        e.preventDefault();
        const firstname = document.getElementById('firstname').value;
        const lastname = document.getElementById('lastname').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const city = document.getElementById('city').value;
        const birth_date = document.getElementById('birth_date').value;

        try {
            const response = await fetch(`${API_BASE}/auth/register`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({firstname, lastname, email, password, city, birth_date}),
            });

            if (response.ok) {
                alert('Регистрация завершена успешно!');
                handleLogin();
            } else {
                alert('Ошибка регистрации');
            }
        } catch (err) {
            console.error(err);
            alert('Error during registration');
        }
    };
}

document.getElementById('btn-login').onclick = handleLogin;
document.getElementById('btn-register').onclick = handleRegister;
document.getElementById('title').onclick = startPage;
document.getElementById('modal-close').onclick = closeModal;

function startPage(){
    setContent(`
        <div class="_e296pg">
            <div class="_ds7650">
                <div class="_vm0m18z" data-ev-n="dashboard_media" data-ev-t="video" data-n="wat-dashboard-media"
                     data-gtm-vis-recent-on-screen71109250_45="4713379"
                     data-gtm-vis-first-on-screen71109250_45="4713379" data-gtm-vis-total-visible-time71109250_45="100"
                     data-gtm-vis-has-fired71109250_45="1" data-gtm-vis-recent-on-screen71109250_81="4713391"
                     data-gtm-vis-first-on-screen71109250_81="4713391" data-gtm-vis-total-visible-time71109250_81="100"
                     data-gtm-vis-has-fired71109250_81="1">
                    <video src="https://disk.2gis.com/otello/features/dashboard/videos/720/winter-town.v1.mp4" loop=""
                           playsinline="" class="videoframe"
                           poster="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACoAAAAoAQMAAACyxfoTAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAAxJREFUGNNjGAVYAQABGAABtdVzhwAAAABJRU5ErkJggg=="
                           data-n="wat-media-video" style="object-fit: cover; border-radius: 24px;" autoplay muted></video>
                    <div class="_10w21br"></div>
                </div>
            </div>
            <div class="_adqssrp"><h1 class="_1y6saaeo" data-n="wat-main-page-text">Дома и апартаменты для путешествий
                по&nbsp;России</h1></div>
        </div>
    `);
}

async function startProperties() {
    try {
        const response = await fetch(`${API_BASE}/properties`, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'},
        });
        const data = await response.json();

        if (response.ok) {
            loadProps(data);
        } else {
            alert(data.message || 'Requestr error');
        }
    } catch (err) {
        console.error(err);
        alert('Error during filter parsing');
    }
}

function loadFilter() {
    setContentFilter(`
    <div id="filter"> 
    <form id="filter-form">
      <select id="city" placeholder="Выберите город" required>
          <option value="Москва">Москва</option>
          <option value="Санкт-петербург">Санкт-петербург</option>
          <option value="Казань">Казань</option>
      </select>
      <select id="ptype" placeholder="Вид объета">
          <option value="">Неважно</option>
          <option value="HOUSE">Дом/коттедж</option>
          <option value="FLAT">Квартира/апартамаенты</option>
      </select>
      
      <input type="number" min="1" max="5" id="rating" placeholder="Рейтинг от"/>
      
      <input type="date" id="date-from" placeholder="Дата заселения" required />
      <input type="date" id="date-to" placeholder="Дата выезда" required />
      
      <input type="number" id="price-from" placeholder="Цена от" />
      <input type="number" id="price-to" placeholder="Цена до" />
            
      <button type="submit">Найти</button>
    </form>
    </div>
  `);

    const today = new Date();
    let  yyyy = today.getFullYear();
    let mm = today.getMonth() + 1;
    let dd = today.getDate();
    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;
    const formattedToday = yyyy + '-' + mm + '-' + dd;
    document.getElementById('date-from').value=formattedToday;

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate()+1);
    yyyy = tomorrow.getFullYear();
    mm = tomorrow.getMonth() + 1;
    dd = tomorrow.getDate();
    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;
    const formattedTomorrow = yyyy + '-' + mm + '-' + dd
    document.getElementById('date-to').value=formattedTomorrow;


    document.getElementById('filter-form').onsubmit = async (e) => {
        e.preventDefault();
        const city = document.getElementById('city').value;
        const property_type = document.getElementById('ptype').value;
        const rating = document.getElementById('rating').value;
        const start_date = document.getElementById('date-from').value;
        const end_date = document.getElementById('date-to').value;
        const price_from = document.getElementById('price-from').value;
        const price_to = document.getElementById('price-to').value;

        const filterParams =
            `?property_type=${property_type}`+
            `&title=`+
            `&desc=`+
            `&city=${city}`+
            `&start_date=${start_date}`+
            `&end_date=${end_date}`+
            `&price_from=${price_from}`+
            `&price_to=${price_to}`+
            `&rating=${rating}`;

        try {
            const response = await fetch(`${API_BASE}/properties/search`+filterParams, {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
            });
            const data = await response.json();

            if (response.ok) {
                loadProps(data);
            } else {
                alert(data.message || 'Requestr error');
            }
        } catch (err) {
            console.error(err);
            alert('Сервис недоступен, попробуйте позже');
        }
    };
}

function getStars(num){
    let res="";
    for (i=0; i<parseInt(num); i++){
        res+=
            `
            <div class="_1bijofo" data-n="wat-star-icon"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none"><path fill="currentColor" d="M7.325 2.887a.783.783 0 0 1 1.35 0l1.257 2.15c.11.189.296.323.51.37l2.441.526a.778.778 0 0 1 .418 1.28l-1.664 1.856a.777.777 0 0 0-.195.597l.251 2.476a.781.781 0 0 1-1.092.79L8.315 11.93a.784.784 0 0 0-.63 0l-2.286 1.004a.781.781 0 0 1-1.092-.791l.251-2.476a.777.777 0 0 0-.195-.597L2.7 7.213a.778.778 0 0 1 .418-1.28l2.44-.527c.215-.046.4-.18.51-.369l1.258-2.15Z"></path></svg></div>        
            `;
    }
    return res==="" ? " " : res;
}

function loadProps(jdata){
    const props = jdata.properties;
    try {
      setContent(`
      <h2>Результат поиска</h2>
      <ul id="properties-list">
        ${props.map((p) =>
          `<li>
            <div class="prop-item" onclick="openProp(${p.id})">
                <div class="prop-pics">
                    <img src="${API_BASE}/files/get-first/${p.id}">
                </div>
                <div class="prop-desc">
                    <div class="p-stars">${getStars(p.rating)}</div>
                    <div class="p-title">${p.title}</div>
                    <div class="p-city">${p.city}</div>
                </div>
                <div class="prop-price">
                   <div>${p.pricePerDay} ₽</div>
                   <div>Цена за сутки</div>
                </div>
            </div>
            </li>`
        ).join('')}
      </ul>
    `);

    } catch (err) {
        console.error(err);
        alert('Error loading galleries');
    }
}


async function openProp(propId) {
    setModal(`
        <form id="property-form">
          <div class="prop-img-date">
            <span class="prop-img"></span>  
          </div>
          <div id="m-title" class="prop-m-title"></div>
          <div id="m-desc" class="prop-m-desc"></div>
          <div id="m-price" class="prop-m-price"></div>
          <div id="m-dates" class="prop-m-dates">
            <span>Выберите дату заселения и выезда</span>
            <input type="date" id="start_date" placeholder="Выберите дату заселения" required>
            <input type="date" id="end_date" placeholder="Выберите дату выузда" required>
          </div>
          <div class="prop-btn">
            <button type="submit">Забронировать</button>
          </div>
        </form>
    `);

    try {
        let response = await fetch(`${API_BASE}/properties/id/`+propId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            const data = await response.json();
            document.getElementById('m-title').textContent=data.title;
            document.getElementById('m-desc').textContent=data.description;
            document.getElementById('m-price').textContent="Цена за сутки: "+data.pricePerDay;
            showModal();
        } else {
            alert('Ошибка получения данных');
        }

         response = await fetch(`${API_BASE}/files/get-all-ids/`+propId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            const data = await response.json();
            const imgBlock = document.querySelector('.prop-img');
            data.photos.map(id=>{
                imgBlock.innerHTML=imgBlock.innerHTML+
                    `<div class="prop-pics">
                        <img src="${API_BASE}/files/get/${id}">
                    </div>`;
            });

        } else {
            alert('Ошибка получения данных');
        }

    } catch (err) {
        console.error(err);
        alert('Error during update');
    }

    document.getElementById('property-form').onsubmit = async (e) => {
        e.preventDefault();
        if(!userToken){
            alert("Для бронирования нужгно авторизоваться");
            return;
        }
        alert(propId);
        const propertyId = propId;
        const startDate = document.getElementById('start_date').value;
        const endDate = document.getElementById('end_date').value;

        try {
            const response = await fetch(`${API_BASE}/booking/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${userToken}`
                },
                body: JSON.stringify({propertyId, startDate, endDate}),
            });

            if (response.ok) {
                alert('Успешно забронировано, ожидайте ответа от владельца!');
                closeModal();
            } else {
                alert('Ошибка при бронировании');
            }
        } catch (err) {
            console.error(err);
            alert('Error during booking');
        }
    };
}


loadFilter();

