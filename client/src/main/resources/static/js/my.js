$(document).ready(function () {

        alert("reboot");
        getAuthUser();
        getUsers();
        addUser();

    }
);

function getAuthUser() {
    $.ajax({
        url: 'http://localhost:8080/api/auth',
        cache: false,
        type: 'GET',
        contentType: "application/json;charset=UTF-8",
        dataType: 'json',
        success: function (user) {
            let login = user.login;
            let roles = user.roles;
            $('#headerLogin').html(login);
            $('#headerRoles').html(roles + " ");
            let id = user.id;
            let name = user.name;
            let lastName = user.surName;
            let age = user.age;
            let addTd = `<tr>
                    <td>${id}</td>
                    <td>${name}</td>
                    <td>${lastName}</td>
                    <td>${age}</td>
                    <td>${login}</td>
                    <td>${roles}</td>
                </tr>'`;
            $('#usersAuthTableTbody').append(addTd);
        },
        error: function () {
            alert("Ошибка получения api/auth");
        }
    });
}


function getUsers() {// Ajax get all users
    $.ajax({
        url: 'http://localhost:8080/api/',
        cache: false,
        type: 'GET',
        contentType: "application/json;charset=UTF-8",
        dataType: 'json',
        success: function (data) {
            data.forEach(function (element) {
                addRow(element);
            });
        },
        error: function () {
            alert("Ошибка загрузки всех пользователей");
        }
    });
}

function addRow(user) {
    let id = user.id;
    let name = user.name;
    let lastName = user.surName;
    let email = user.login;
    let age = user.age;
    let roles = user.roles;
    let addTd = `<tr id = "${id}">
                    <td id = "userId_${id}">${id}</td>
                    <td id = "userName_${id}">${name}</td>
                    <td id = "lastName_${id}">${lastName}</td>
                    <td id = "age_${id}">${age}</td>
                    <td id = "email_${id}">${email}</td>
                    <td id = "userRoles_${id}">${roles}</td>
                    <td><p><button onclick="edtClick(${id})" id="editUser_${id}" name="editUserBtn" class="btn btn-info edit-user" data-toggle="modal">edit</button></p></td>
                    <td><p><button onclick="delClick(${id})" id="deleteUser_${id}" name="deleteUserBtn" class="btn btn-danger delete-user" data-toggle="modal">delete</button></p></td>
                </tr>'`;
    $('#usersTableTbody').append(addTd);
}

// Add user
function addUser() {
    $("#btnAdd").click(
        function () {
            // if (!($('#formAdd').valid())) return;
            let name = document.getElementById("userNameAdd").value;
            let lastName = document.getElementById("surNAdd").value;
            let password = document.getElementById("passwordAdd").value;
            let age = document.getElementById("ageAdd").value;
            let login = document.getElementById("loginAdd").value;
            let roles = [];
            let rolesFromHtml = document.getElementById("rolesFromHtml").value;
            if (rolesFromHtml === 'adminAndUser') {
                roles.push("ADMIN");
                roles.push("USER");
            }
            if (rolesFromHtml === 'admin') {
                roles.push("ADMIN");
            }
            if (rolesFromHtml === 'user') {
                roles.push("USER");
            }
            let data = {
                password: password,
                login: login,
                age: age,
                roles: roles,
                name: name,
                surName: lastName
            };
             let serializedUser = JSON.stringify(data);//todo delete
            $.ajax({
                url: 'http://localhost:8080/api/',
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                data: serializedUser,
                success: function () {
                    clearTable();
                    getUsers();
                    $("#navItemNewUser").removeClass("active");
                    $("#newUser").removeClass("active show");
                    $('#navItemMain').addClass("active");
                    $('#allUsers').addClass("active show");
                },
                error: function () {
                    alert("add was not success");
                }
            });
        }
    )
}

function clearTable() {
    for (; document.getElementById('allUsersTable').getElementsByTagName('tr').length > 1;) {
        document.getElementById('allUsersTable').deleteRow(1);
    }
}

function edtClick(id) {
    $('#editUserModal').modal();
    setValueInModal(id);
}
function delClick(id) {
    $('#deleteUserModal').modal();
    setValueInModal(id);
}
// Set value in modal
function setValueInModal(id) {
    let roles = $("#userRoles_" + id).text();

    $('.idEdit').attr('value', id);
    $('.firstNameEdit').attr('value', $("#userName_" + id).text());
    $('.lastNameEdit').attr('value', $("#lastName_" + id).text());
    $('.passwordEdit').attr('value', '');
    $('.passwordEdit').attr('placeholder', '');
    $('.ageEdit').attr('value', $("#age_" + id).text());
    $('.emailEdit').attr('value', $("#email_" + id).text());

    if (roles === 'USER,ADMIN') $('.rolesEdit [value="adminAndUser"]').attr('selected', 'selected');
    if (roles === 'USER') $('.rolesEdit [value="user"]').attr('selected', 'selected');
    if (roles === 'ADMIN') $('.rolesEdit [value="admin"]').attr('selected', 'selected');
};

//Edit User Ajax
$('#editUserBtn').click(
    function () {
        let id = document.getElementById("idEdit").value;
        let userName = document.getElementById("firstNameEdit").value;
        let lastName = document.getElementById('lastNameEdit').value;
        let password = document.getElementById('passwordEdit').value;
        let age = document.getElementById('ageEdit').value;
        let email = document.getElementById('emailEdit').value;
        let roles = [];
        let rolesFromHtml = document.getElementById('rolesEdit').value;
        if (rolesFromHtml === 'adminAndUser') {
            roles.push("ADMIN");
            roles.push("USER");
        }
        if (rolesFromHtml === 'admin') {
            roles.push("ADMIN");
        }
        if (rolesFromHtml === 'user') {
            roles.push("USER");
        }
        let data = {
            id: id,
            password: password,
            login: email,
            age: age,
            roles: roles,
            name: userName,
            surName: lastName
        };
        let serializedUser = JSON.stringify(data);
        $.ajax({
            url: 'http://localhost:8080/api/',
            type: "PUT",
            contentType: "application/json;charset=UTF-8",
            data: serializedUser,
            success: function () {
                clearTable();
                getUsers();
                $('#editUserModal').modal('hide');
                $("#navItemNewUser").removeClass("active");
                $('#navItemMain').addClass("active");
            },
            error: function () {
                alert("add not success :( ");
            }
        });
    });

// Delete Ajax
$('#deleteUserBtn').click(
    function () {
        let id = document.getElementById("idEdit").value;
        $.ajax({
            url: 'http://localhost:8080/api/' + id,
            type: "DELETE",
            contentType: "application/json;charset=UTF-8",
            success: function () {
                clearTable();
                getUsers();
                $('#deleteUserModal').modal('hide');
                $("#navItemNewUser").removeClass("active");
                $('#navItemMain').addClass("active");
            },
            error: function () {
                alert("add not success :( ");
            }
        });
    }
);