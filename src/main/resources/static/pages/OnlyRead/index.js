const API_BASE_URL = "http://127.0.0.1:8085";

let problem = [];
let question;

window.onload = () => {
    question = JSON.parse(localStorage.getItem('pageParams')).question;
    $('#question_title').val(question.questionnaireName);
    $('#question_seq').val(question.description);
    selectQuestionOptionById(question.id);
    setReadOnlyMode(); // 设置只读模式
    console.log('Questionnaire ID:', question.id);
};

function setReadOnlyMode() {
    const buttons = document.querySelectorAll('button');
    buttons.forEach((button) => {
        button.disabled = true;
    });

    const radioLabels = document.querySelectorAll('label.radio-inline');
    radioLabels.forEach((label) => {
        label.style.pointerEvents = 'none'; // Disable click events on radio labels
    });

    const deleteButtons = document.querySelectorAll('button.btn-link');
    deleteButtons.forEach((button) => {
        button.disabled = true;
    });

    const textInputs = document.querySelectorAll('input[type="text"]');
    textInputs.forEach((input) => {
        input.readOnly = true;
        input.classList.add('disabled-input'); // Add a CSS class to make inputs grayed out
    });

    const radios = document.querySelectorAll('input[type="radio"]');
    radios.forEach((radio) => {
        radio.disabled = true;
    });
}

function selectQuestionOptionById(id) {
    $.ajax({
        url: `${API_BASE_URL}/getQuestionById?id=${id}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            questionOption = res;
            console.log(res);
            problem = res.data;
            $util.setItem('problem', res.data);
            loadQuestion(problem); //渲染页面
        }
    });
}

function loadQuestion(problem) {
    let str = '';
    if (problem.length == 0) {
        $('#qsList').html(str);
        return;
    }
    for (let i = 0; i < problem.length; i++) {
        str += `
      <li id="qs${i}">
        <div class="top">
          <div class="title">
            <input type="text" id="title${i}" value="${problem[i].content}" placeholder="请输入题目内容" readonly class="gray-input">
          </div>
          <div class="type">
            <div class="label">题目类型:</div>
            <label class="radio-inline">
              <input type="radio" name="${i}optionstype" id="${i}type1" value="1" ${problem[i].type == 1 ? 'checked' : ''} disabled class="gray-input">
              单选
            </label>
            <label class="radio-inline">
              <input type="radio" name="${i}optionstype" id="${i}type2" value="2" ${problem[i].type == 2 ? 'checked' : ''} disabled class="gray-input">
              多选
            </label>
          </div>
          <div class="">
            <div class="label">排序:</div>
            <input type="number" id="${i}order" value="${problem[i].order}" placeholder="请填入题目排序" readonly class="gray-input">
          </div>
        </div>
        <div class="option">
          <dl class="chose" id="choose${i}">
  `;
        let str2 = '';
        for (let j = 0; j < problem[i].listByOption.length; j++) {
            str2 += `
        <dt id="dt${j}">${String.fromCharCode(j + 65)}</dt>
        <dd>
          <input type="text" id="${j}op_content${i}" value="${problem[i].listByOption[j].option_content}" placeholder="请输入选项内容" readonly class="gray-input">
        </dd>
      `;
        }
        str += str2 + `
          </dl>
        </div>
      </li>
    `;
    }
    $('#qsList').html(str);
}

// 增加选项
let addOption = (index) => {
    problem[index].listByOption.push({
        question_id: question.id,
        id: null,
        sequence: index + 1,
        option_content: ""
    });
    let j = problem[index].listByOption.length - 1;
    let i = index;
    let str = `
    <dt id="dt${j}">${String.fromCharCode(j + 65)}</dt>
    <dd>
      <input type="text" id="${j}op_content${i}" value="${problem[i].listByOption[j].option_content}" placeholder="请输入选项内容">
      <div class="right">
        <button type="button" class="btn btn-link btn-xs" onclick="delOption(${i},${j})">删除</button>
      </div>
    </dd>
  `;
    $(`#choose${index}`).append(str);
}

// 增加问题
let addQS = () => {
    getPageData();
    console.log(problem);
    problem.push({
        id: null,
        questionnaire_id: question.id,
        type: 1,
        content: "",
        order: problem.length + 1,
        listByOption: []
    });
    console.log(problem);
    loadQuestion(problem);
}

// 删除问题
let delQs = (i) => {
    getPageData();
    if (problem[i].id != null) {
        delQAjax(problem[i].id);
    }
    if (i == 0) {
        problem = [];
    } else {
        problem.splice(i, 1);
    }
    loadQuestion(problem);
}

// 删除选项
let delOption = (i, j) => {
    getPageData();
    if (problem[i].listByOption[j].id != null) {
        delOAjax(problem[i].listByOption.id);
    }
    if (j == 0) {
        problem[i].listByOption = [];
    } else {
        problem[i].listByOption.splice(j, 1);
    }
    loadQuestion(problem);
}

let getPageData = () => {
    for (let i = 0; i < problem.length; i++) {
        problem[i].content = $(`#title${i}`).val();
        problem[i].order = $(`#${i}order`).val();
        problem[i].type = $(`input[name='${i}optionstype']:checked`).val();
        for (let j = 0; j < problem[i].listByOption.length; j++) {
            problem[i].listByOption[j].option_content = $(`#${j}op_content${i}`).val();
        }
    }
    console.log(problem);
    return problem;
}

let handleEditFinish = () => {
    let arr = getPageData();
    let question_title = $('#question_title').val();
    let question_seq = $('#question_seq').val();
    if (question_title == '') {
        alert("问卷标题不能为空!");
        return;
    }
    if (question_seq == '') {
        alert("问卷说明不能为空!");
        return;
    }
    console.log(arr);
    $.ajax({
        url: API_BASE_URL + '/updateANDInsertQuestandOption',
        type: "POST",
        dataType: "json",
        data: JSON.stringify(arr),
        contentType: "application/json;charset=UTF-8",
        success(res) {
            console.log(res);
            if (res.status == "200") {
                location.href = "/pages/seeProject/index.html";
            }
        }
    });
}

// 删除问题接口
let delQAjax = (id) => {
    $.ajax({
        url: API_BASE_URL + '/deleteByQ?id=' + id,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res);
            loadQuestion(problem);
        }
    });
}

// 删除选项接口
let delOAjax = (id) => {
    $.ajax({
        url: API_BASE_URL + '/deleteByO?id=' + id,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res);
            loadQuestion(problem);
        }
    });
}

const onPreview = (id) => {
    // 发送获取预览链接的请求
    fetch(API_BASE_URL + '/admin/getQuestionnairePreviewLink/' + id)
        .then(response => response.json())
        .then(data => {
            // 获取到预览链接后进行跳转到答题界面
            const previewLink = data.data;
            window.location.href = '/answersheet.html?previewLink=' + encodeURIComponent(previewLink);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function goBack() {
    window.history.go(-1);
}
