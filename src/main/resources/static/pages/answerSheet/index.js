const API_BASE_URL = "http://127.0.0.1:8085";
let problem = [];
let questionOption;

window.onload = () => {
  const urlParams = new URLSearchParams(window.location.search);
  const questionnaireLink = urlParams.get('link');
  const questionnaireId = getQuestionnaireIdFromLink(questionnaireLink);
  console.log('Questionnaire ID:', questionnaireId);
  selectQuestionOptionById(questionnaireId);
};

const getQuestionnaireIdFromLink = (link) => {
  const url = new URL(link, window.location.href);
  const questionnaireId = url.searchParams.get('id');
  //return questionnaireId;
  return '79425405f7ca4fbdb2a12cc55798c325';
};

function selectQuestionOptionById(id) {
  $.ajax({
    async:false,
    url: API_BASE_URL + '/getQuestionById?id=' + id,
    type: "GET",
    dataType: "json",
    contentType: "application/json",
    async: true,
    success(res) {
      questionOption = res;
      console.log(res);
      $util.setItem('problem', res.data);
      if (Array.isArray(res.data) && res.data.length > 0) {
        problem = res.data;
        $util.setItem('problem', res.data);
        renderQuestions(problem);
      } else {
        console.log("No problem data found.");
      }
    },
    error(err) {
      console.error(err);
      console.log(666); // 处理请求错误
    }
  });
}

function renderQuestions(problem) {
  problem.forEach((item) => {
    switch (item.type) {
      case 1:
        renderSingleChoiceQuestion(item.title, item.options);
        console.log("No 111problem data found.");
        break;
      case 2:
        renderMultipleChoiceQuestion(item.title, item.options);
        break;
      case 3:
        renderFillInTheBlankQuestion(item.title);
        break;
      case 4:
        renderMatrixQuestion(item.title, item.rows, item.columns);
        break;
      case 5:
        renderLikertScaleQuestion(item.title, item.scaleOptions);
        break;
      default:
        break;
    }
  });
}

function renderSingleChoiceQuestion(title, options) {
  let questionHTML = `
    <div class="question" data-type="1" data-problemIndex="1">
      <div class="top">
        <span class="question-title">${title}</span>
        <span class="must-answer">必答题</span>
      </div>
      <div class="bottom">
  `;
  console.log("No problem data found1111.");
  options.forEach((option) => {
    questionHTML += `
      <div style="display: flex; align-items: center; margin-bottom: 3px;">
        <label class="radio-inline">
          <input type="radio" name="chooseTerm">${option}
        </label>
      </div>
    `;
  });
  questionHTML += `
      </div>
    </div>
  `;

  $('#problem').append(questionHTML);
}

function renderMultipleChoiceQuestion(title, options) {
  let questionHTML = `
    <div class="question" data-type="2" data-problemIndex="2">
      <div class="top">
        <span class="question-title">${title}</span>
        <span class="must-answer">必答题</span>
      </div>
      <div class="bottom">
  `;
  options.forEach((option) => {
    questionHTML += `
      <div style="display: flex; align-items: center; margin-bottom: 3px;">
        <label class="checkbox-inline">
          <input type="checkbox" name="chooseTerm">${option}
        </label>
      </div>
    `;
  });
  questionHTML += `
      </div>
    </div>
  `;

  $('#problem').append(questionHTML);
}

function renderFillInTheBlankQuestion(title) {
  let questionHTML = `
    <div class="question" data-type="3" data-problemIndex="3">
      <div class="top">
        <span class="question-title">${title}</span>
        <span class="must-answer">必答题</span>
      </div>
      <div class="bottom">
        <textarea class="form-control" placeholder="请输入" rows="4" style="width: 70%;"></textarea>
      </div>
    </div>
  `;

  $('#problem').append(questionHTML);
}

function renderMatrixQuestion(title, rows, columns) {
  let questionHTML = `
    <div class="question" data-type="4" data-problemIndex="4">
      <div class="top">
        <span class="question-title">${title}</span>
        <span class="must-answer">必答题</span>
      </div>
      <div class="bottom">
        <table class="table">
          <thead>
            <tr>
              <th></th>
  `;
  columns.forEach((column) => {
    questionHTML += `
      <th>${column}</th>
    `;
  });
  questionHTML += `
            </tr>
          </thead>
          <tbody>
  `;
  rows.forEach((row) => {
    questionHTML += `
      <tr>
        <td>${row}</td>
    `;
    columns.forEach(() => {
      questionHTML += `
        <td><input type="radio" name="chooseTerm" /></td>
      `;
    });
    questionHTML += `
      </tr>
    `;
  });
  questionHTML += `
          </tbody>
        </table>
      </div>
    </div>
  `;

  $('#problem').append(questionHTML);
}

function renderLikertScaleQuestion(title, scaleOptions) {
  let questionHTML = `
    <div class="question" data-type="5" data-problemIndex="5">
      <div class="top">
        <span class="question-title">${title}</span>
        <span class="must-answer">必答题</span>
      </div>
      <div class="bottom" style="display: flex; align-items: center; justify-content: space-between;">
        <div>很满意</div>
  `;
  scaleOptions.forEach((option) => {
    questionHTML += `
      <div>
        <label class="radio-inline">
          <input type="radio" name="fraction" />${option}
        </label>
      </div>
    `;
  });
  questionHTML += `
        <div>很不满意</div>
      </div>
    </div>
  `;

  $('#problem').append(questionHTML);
}
$(document).ready(() => {
  // 获取 URL 参数中的问卷链接
  const urlParams = new URLSearchParams(window.location.search);
  const questionnaireLink = urlParams.get('link');


});

