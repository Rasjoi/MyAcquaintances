const HOST = 'http://localhost:8001/back?';

const KEY_FIRST_NAME = 'firstName';
const KEY_MIDDLE_NAME = 'middleName';
const KEY_LAST_NAME = 'lastName';
const KEY_AGREEMENT_NUMBER = 'agreementNumber';
const KEY_HOME_ADDRESS = 'homeAddress';
const KEY_DEPOSIT_SUM = 'depositSum';
const KEY_AUTO_PROLONGATION = 'autoProlongation';
const KEY_CONTRACT_TERM = 'contractTerm';

let JsonArr = [];

fetch(HOST + "show", {
    method: 'POST',
}).then(response =>
    response.json().then(data => ({
        data: data,
    })).then(res => {
        try {
            JsonArr = res.data;
            render(JsonArr);
        } catch {
            JsonArr = [];
        }
    }));


const $content_table = document.querySelector('.content-table')


function render(array) {
    $content_table.innerHTML = '<thead class="align-top">\n' +
        '<tr>\n' +

        '<th scope="col">Имя</th>\n' +
        '<th scope="col">Отчество</th>\n' +
        '<th scope="col">Фамилия</th>\n' +
        '<th scope="col">Номер договора</th>\n' +
        '<th scope="col">Домашний адрес</th>\n' +
        '<th scope="col">Сумма вклада</th>\n' +
        '<th scope="col">Автопролонгация</th>\n' +
        '<th scope="col">Срок договора</th>\n' +

        '</tr>\n' +
        '</thead>';

    for (let i = 0; i < array.length; i++) {
        $content_table.innerHTML += '<tbody><tr><td>' +

            array[i][KEY_FIRST_NAME] + '</td><td>' +
            array[i][KEY_MIDDLE_NAME] + '</td><td>' +
            array[i][KEY_LAST_NAME] + '</td><td>' +
            array[i][KEY_AGREEMENT_NUMBER] + '</td><td>' +
            array[i][KEY_HOME_ADDRESS] + '</td><td>' +
            array[i][KEY_DEPOSIT_SUM] + '</td><td>' +
            array[i][KEY_AUTO_PROLONGATION] + '</td><td>' +
            array[i][KEY_CONTRACT_TERM] + '</td>' +

            '</tr></tbody>';
    }
}

function filterShowHigherThanTwelveMonths() {
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_CONTRACT_TERM] > 12 * 30) {
            result.push(JsonArr[i]);
        }
    }
    // Очищаем поле ввода фамилии для живого поиска, чтобы не было конфликтов между фильтрами
    document.querySelector('#search-last-name').value = "";
    return result;
}

document.querySelector('#show-higher-than-12-months').addEventListener('change', function () {
    if (this.checked) {
        render(filterShowHigherThanTwelveMonths());
    }
});
document.querySelector('#show-all').addEventListener('change', function () {
    if (this.checked) {
        // Очищаем поле ввода фамилии для живого поиска, чтобы не было конфликтов между фильтрами
        document.querySelector('#search-last-name').value = "";
        render(JsonArr);
    }
});

document.querySelector('#show-investors-with-deposit-higher-than-button').addEventListener('click', function () {
    let result = [];
    let depositSum = Number(document.querySelector('#show-investors-with-deposit-higher-than').value);
    if (depositSum < 0 || depositSum >= 100000000) {
        alert('Введена некорректная сумма депозита');
    } else {
        for (i = 0; i < JsonArr.length; i++) {
            if (JsonArr[i][KEY_DEPOSIT_SUM] > depositSum) {
                result.push(JsonArr[i]);
            }
        }
        // Очищаем поле ввода фамилии для живого поиска, чтобы не было конфликтов между фильтрами
        document.querySelector('#search-last-name').value = "";
        render(result);
    }

})

let $searchLastName = document.querySelector('#search-last-name');
$searchLastName.addEventListener('input', function () {
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_LAST_NAME].toLowerCase().includes($searchLastName.value.toLowerCase())) {
            result.push(JsonArr[i]);
        }
    }
    render(result);
})

document.querySelector('#modal-add-send').addEventListener('click', function () {
    let agreementNumber = Number(document.querySelector('#modal-input-agreement-number').value);
    let duplicateAgreementNumber = false;

    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_AGREEMENT_NUMBER] === agreementNumber) {
            // Если нашли дубликат номера договора, задаем значение флага
            duplicateAgreementNumber = true;
            break;
        }
    }

    if (!duplicateAgreementNumber) {

        let autoProlongationRadioButtons = document.getElementsByName('auto-prolongation');
        let autoProlongation = false;
        if (autoProlongationRadioButtons[0].checked)
            autoProlongation = true;

        let result = {
            firstName: document.querySelector('#modal-input-first-name').value,
            middleName: document.querySelector('#modal-input-middle-name').value,
            lastName: document.querySelector('#modal-input-last-name').value,
            agreementNumber: Number(document.querySelector('#modal-input-agreement-number').value),
            homeAddress: document.querySelector('#modal-input-home-address').value,
            depositSum: Number(document.querySelector('#modal-input-deposit-sum').value),
            autoProlongation: autoProlongation,
            contractTerm: Number(document.querySelector('#modal-input-contract-term').value)
        };
        fetch(HOST + "add", {
            method: 'POST',
            body: JSON.stringify(result),
        }).then(response =>
            response.json().then(data => ({
                data: data,
            })).then(res => {
                if (res.data["added_successfully"] === true) {
                    JsonArr.push(result);
                    render(JsonArr);

                    alert("Инвестор успешно добавлен");

                    const addModalWrapper = document.querySelector('#add-modal');
                    const addModal = bootstrap.Modal.getInstance(addModalWrapper);
                    addModal.hide();

                    document.querySelector('#modal-input-first-name').value = "";
                    document.querySelector('#modal-input-middle-name').value = "";
                    document.querySelector('#modal-input-last-name').value = "";
                    document.querySelector('#modal-input-agreement-number').value = "";
                    document.querySelector('#modal-input-home-address').value = "";
                    document.querySelector('#modal-input-deposit-sum').value = "";
                    document.querySelector('#modal-input-contract-term').value = "";
                } else {
                    alert("Введены неправильные данные вкладчика")
                }
            }));

    } else {
        alert("Запись с таким номером договора уже существует")
    }
})

let editId = -1;
document.querySelector('#edit-element-button').addEventListener('click', function () {
    let investorFound = false;
    let investorEditAgreementNumber = Number(document.querySelector('#edit-element').value);
    for (i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_AGREEMENT_NUMBER] === investorEditAgreementNumber) {
            let editModalWrapper = document.querySelector('#edit-modal');
            let editModal = new bootstrap.Modal(editModalWrapper);
            editModal.show();

            editId = i;

            document.querySelector('#modal-input-first-name-edit').value = JsonArr[i][KEY_FIRST_NAME];
            document.querySelector('#modal-input-middle-name-edit').value = JsonArr[i][KEY_MIDDLE_NAME];
            document.querySelector('#modal-input-last-name-edit').value = JsonArr[i][KEY_LAST_NAME];
            document.querySelector('#modal-input-agreement-number-edit').value = JsonArr[i][KEY_AGREEMENT_NUMBER];
            document.querySelector('#modal-input-home-address-edit').value = JsonArr[i][KEY_HOME_ADDRESS];
            document.querySelector('#modal-input-deposit-sum-edit').value = JsonArr[i][KEY_DEPOSIT_SUM];
            document.querySelector('#modal-input-contract-term-edit').value = JsonArr[i][KEY_CONTRACT_TERM];

            investorFound = true;

            break;
        }
    }

    if (investorFound === false) {
        alert("Вкладчик с таким номером контракта не найден");
    }
})

document.querySelector('#modal-edit-send').addEventListener('click', function () {

    let editAgreementNumber = Number(document.querySelector('#modal-input-agreement-number-edit').value);
    let editDuplicateAgreementNumber = false;

    for (let i = 0; i < JsonArr.length; i++) {
        // Если мы проходим через текущий элемент (который редактируем), то его надо пропустить
        if (i === editId) {
            continue;
        }
        if (JsonArr[i][KEY_AGREEMENT_NUMBER] === editAgreementNumber) {
            // Если нашли вкладчика с введенной фамилией, меняем значение флага
            editDuplicateAgreementNumber = true;
            break;
        }
    }

    if (!editDuplicateAgreementNumber) {

        let autoProlongationEditRadioButtons = document.getElementsByName('auto-prolongation-edit');
        let autoProlongationEdit = false;
        if (autoProlongationEditRadioButtons[0].checked)
            autoProlongationEdit = true;

        let result = {
            firstName: document.querySelector('#modal-input-first-name-edit').value,
            middleName: document.querySelector('#modal-input-middle-name-edit').value,
            lastName: document.querySelector('#modal-input-last-name-edit').value,
            agreementNumber: editAgreementNumber,
            homeAddress: document.querySelector('#modal-input-home-address-edit').value,
            depositSum: Number(document.querySelector('#modal-input-deposit-sum-edit').value),
            autoProlongation: autoProlongationEdit,
            contractTerm: Number(document.querySelector('#modal-input-contract-term-edit').value)
        };

        let editData = {
            id: editId,
            investor: result
        }

        fetch(HOST + "edit", {
            method: 'POST',
            body: JSON.stringify(editData),
        }).then(response =>
            response.json().then(data => ({
                data: data,
            })).then(res => {
                if (JSON.stringify(res.data) === '{"edited_successfully":true}') {

                    JsonArr[editId] = result;
                    render(JsonArr);

                    alert("Вкладчик успешно отредактирован");

                    const editModalWrapper = document.querySelector('#edit-modal');
                    const editModal = bootstrap.Modal.getInstance(editModalWrapper);
                    editModal.hide();

                    document.querySelector('#modal-input-first-name-edit').value = "";
                    document.querySelector('#modal-input-middle-name-edit').value = "";
                    document.querySelector('#modal-input-last-name-edit').value = "";
                    document.querySelector('#modal-input-agreement-number-edit').value = "";
                    document.querySelector('#modal-input-home-address-edit').value = "";
                    document.querySelector('#modal-input-deposit-sum-edit').value = "";
                    document.querySelector('#modal-input-contract-term-edit').value = "";
                } else {
                    alert("Введены неправильные данные при редактировании")
                }
            }));

    } else {
        alert("Запись с таким номером договора уже существует")
    }

})

document.querySelector('#delete-element-button').addEventListener('click', function () {
    let investorsDeleteFound = false;
    let investorsDeleteLastName = document.querySelector('#delete-element').value;
    let result = [];
    for (let i = 0; i < JsonArr.length; i++) {
        if (JsonArr[i][KEY_LAST_NAME] === investorsDeleteLastName) {
            // Если нашли вкладчика с введенной фамилией, меняем значение флага
            investorsDeleteFound = true;
        } else {
            // Сохраняем только элементы, где фамилия отличается от введенной в результирующий массив
            result.push(JsonArr[i]);
        }
    }
    if (investorsDeleteFound === false) {
        alert("Ни одного вкладчика с введенной фамилией не найдено");
    } else {
        fetch(HOST + "delete", {
            method: 'POST',
            body: JSON.stringify(result),
        }).then(response =>
            response.json().then(data => ({
                data: data,
            })).then(res => {
                if (JSON.stringify(res.data) === '{"deleted_successfully":true}') {
                    alert('Удалено успешно');
                    JsonArr = result;
                    render(JsonArr);
                } else {
                    alert("Ошибка при удалении")
                }
            }));

    }
})
