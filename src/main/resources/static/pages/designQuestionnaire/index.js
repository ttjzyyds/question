let questionnaireTitle = '问卷标题'
let questionnaireDescription = '问卷说明'
let problem = []
let questionOption = null;

onload = () => {
  let question = JSON.parse(localStorage.getItem('pageParams')).question;
  $('#question_title').text(question.questionnaireName);
  console.log(question);

  selectQuestionOptionById(question.id);

}
/**
 * 添加问题
 *
 * @param {*} type 1：单选，2：多选，3：填空，4：矩阵，5：量表
 */
const onAddQuestion = (type) => {
  let ele
  switch (type) {
    case 1:
      ele = handleAddSingleChoice()
      break;
    case 2:
      ele = handleAddMultipleChoice()
      break;
    case 3:
      ele = handleAddFillBlanks()
      break;
    case 4:
      ele = handleAddMatrix()
      break;
    case 5:
      ele = handleAddGauge()
      break;
    default:
      break;
  }
  $('#problem').append(ele)
  problem.push({ content: '', type: 1, order:problem.length, mustAnswer: true, listByOption: [{}] })

  $(".question").hover(() => {
    let problemIndex = $('.question:hover').attr('data-problemIndex')
    let ele = `
      <div class="operation">
      <div class="button" onclick="handleMoveUp(${problemIndex})">上移</div>
      <div class="button" onclick="handleMoveDown(${problemIndex})">下移</div>
        <div class="button" onclick="handleEdit(${problemIndex})">编辑</div>
        <div class="button" onclick="handleDelete(${problemIndex})">删除</div>
      </div>
    `
    $('.question:hover').append(ele)
    $(".question:hover").css('border', '1px solid #fdb553')
  }, () => {
    $('.question > .operation').remove()
    $(".question").css('border', '1px solid #ffffff')
  })
}

const onInput = (problemIndex, optionIndex, key) => {
  if (optionIndex || optionIndex === 0)
    problem[problemIndex].option[optionIndex][key] = $(`#question${problemIndex} #optionItem${optionIndex} #${key}`)[0].value
  else
    problem[problemIndex][key] = $(`#question${problemIndex} #${key}`)[0].value
}

const onMustAnswerClick = (problemIndex) => {
  problem[problemIndex].mustAnswer = !problem[problemIndex].mustAnswer
  if (problem[problemIndex].mustAnswer) $(`#question${problemIndex} #mustAnswer`).text('必答题')
  else $(`#question${problemIndex} #mustAnswer`).text('非必答题')
}

const cancelEdit = (problemIndex) => {
  $(`#question${problemIndex} .bottom`).css('display', 'none')
  $(`#question${problemIndex} .bottom2`).css('display', 'block')
}

const handleMoveUp = (problemIndex) => {
  if (problemIndex === 0) return
  $(`#question${problemIndex - 1}`).before($(`#question${problemIndex}`))
  let i = problem[problemIndex]
  problem[problemIndex] = problem[problemIndex - 1]
  problem[problemIndex - 1] = i
  moveCommon()
}

const handleMoveDown = (problemIndex) => {
  if (problemIndex === problem.length - 1) return
  $(`#question${problemIndex + 1}`).after($(`#question${problemIndex}`))
  let i = problem[problemIndex]
  problem[problemIndex] = problem[problemIndex + 1]
  problem[problemIndex + 1] = i
  moveCommon()
}

const moveCommon = () => {
  $('.question').map((index, item) => {
    item.setAttribute('id', `question${index}`)
    item.setAttribute('data-problemIndex', index)
    let type = +$(`#question${index}`).attr('data-type')
    let value;
    value = $(`#question${index} #problemName`).attr('oninput').replace(/\(\d+,/g, `(${index},`)
    $(`#question${index} #problemName`).attr('oninput', value)
    $(`#question${index} #mustAnswer`).attr('onclick', `onMustAnswerClick(${index})`)
    $(`#question${index} #cancelEdit`).attr('onclick', `cancelEdit(${index})`)
    switch (type) {
      case 1:
        $(`#question${index} #listByOption`).map(((chooseTermIndex, chooseTermItem) => {
          chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'listByOption')
        }))
        $(`#question${index} .option-del`).map(((delIndex, delItem) => {
          delItem.oninput = onInput.bind(this, index, delIndex, 'listByOption')
        }))
        $(`#question${index} .btn-add-option`).attr('onclick', `singleChoiceAddOption(${index})`)
        $(`#question${index} #editFinish`).attr('onclick', `singleChoiceEditFinish(${index})`)
        break;
      case 2:
        $(`#question${index} #listByOption`).map(((chooseTermIndex, chooseTermItem) => {
          chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'listByOption')
        }))
        $(`#question${index} .option-del`).map(((delIndex, delItem) => {
          delItem.oninput = onInput.bind(this, index, delIndex, 'listByOption')
        }))
        $(`#question${index} .btn-add-option`).attr('onclick', `multipleChoiceAddOption(${index})`)
        $(`#question${index} #editFinish`).attr('onclick', `multipleChoiceEditFinish(${index})`)
        break;
      case 3:
        $(`#question${index} #editFinish`).attr('onclick', `fillBlanksEditFinish(${index})`)
        break;
      case 4:
        $(`#question${index} #listByOption`).map(((chooseTermIndex, chooseTermItem) => {
          chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'listByOption')
        }))
        $(`#question${index} .option-del`).map(((delIndex, delItem) => {
          delItem.oninput = onInput.bind(this, index, delIndex, 'listByOption')
        }))
        value = $(`#question${index} #leftTitle`).attr('oninput').replace(/\(\d+,/g, `(${index},`)
        $(`#question${index} #leftTitle`).attr('oninput', value)
        $(`#question${index} .btn-add-option`).attr('onclick', `matrixAddOption(${index})`)
        $(`#question${index} #editFinish`).attr('onclick', `matrixEditFinish(${index})`)
        break;
      case 5:
        $(`#question${index} #listByOption`).map(((chooseTermIndex, chooseTermItem) => {
          chooseTermItem.oninput = onInput.bind(this, index, chooseTermIndex, 'listByOption')
        }))
        $(`#question${index} #fraction`).map(((fractionIndex, fractionItem) => {
          fractionItem.oninput = onInput.bind(this, index, fractionIndex, 'listByOption')
        }))
        $(`#question${index} .option-del`).map(((delIndex, delItem) => {
          delItem.oninput = onInput.bind(this, index, delIndex, 'listByOption')
        }))
        $(`#question${index} .btn-add-option`).attr('onclick', `gaugeAddOption(${index})`)
        $(`#question${index} #editFinish`).attr('onclick', `gaugeEditFinish(${index})`)
        break;
      default:
        break;
    }
  })
}

const handleEdit = (problemIndex) => {
  $(`#question${problemIndex} .bottom`).css('display', 'block')
  $(`#question${problemIndex} .bottom2`).css('display', 'none')
}

const handleDelete = (problemIndex) => {
  $(`#question${problemIndex}`).remove()
  problem.splice(problemIndex, 1)
}

const handleAddSingleChoice = () => {
  let ele = `
    <div class="question" id="question${problem.length}" data-type="1" data-problemIndex="${problem.length}">
      <div class="top">
        <span class="question-title" id="questionTitle">1.请编辑问题？</span>
        <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
      </div>
      <div class="bottom">
        <textarea class="form-control textarea" id="problemName" placeholder="单选题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'content')"></textarea>
        <div class="option" id="option">
          <div class="option-item" id="optionItem0">
            <input type="text" class="form-control" id="listByOption" placeholder="选项【单选】" oninput="onInput(${problem.length}, 0, 'listByOption')" />
            <span class="option-del" onclick="singleChoiceDelOption(${problem.length}, 0)">删除</span>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-link btn-add-option" onclick="singleChoiceAddOption(${problem.length})">添加选项</button>
        </div>
        <div class="btn-group">
          <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
          <button type="button" id="editFinish" class="btn btn-default" onclick="singleChoiceEditFinish(${problem.length})">完成编辑</button>
        </div>
      </div>
      <div class="bottom2" style="display: none;">
        
      </div>
    </div>
  `
  return ele
}

const singleChoiceAddOption = (problemIndex) => {
  $(`#question${problemIndex} #option`).append(`
    <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
      <input type="text" class="form-control" id="listByOption" placeholder="选项【单选】" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'listByOption')" />
      <span class="option-del" onclick="singleChoiceDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
    </div>
  `)
  problem[problemIndex].option.push({})
}

const singleChoiceDelOption = (problemIndex, optionIndex) => {
  $(`#question${problemIndex} .option-item`)[optionIndex].remove()
  problem[problemIndex].option.splice(optionIndex, 1)
  $(`#question${problemIndex} .option-del`).map((item, index) => {
    index.onclick = singleChoiceDelOption.bind(this, problemIndex, item)
  })
}

const singleChoiceEditFinish = (problemIndex) => {
  $(`#question${problemIndex} .bottom`).css('display', 'none')
  $(`#question${problemIndex} .bottom2`).css('display', 'inline')
  $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].content}`)
  $(`#question${problemIndex} .bottom2`).html('')
  problem[problemIndex].option.map(item => {
    $(`#question${problemIndex} .bottom2`).append(`
      <div style="display: flex; align-items: center;">
        <label class="radio-inline">
          <input type="radio">${item.listByOption ? item.listByOption : ''}
        </label>
      </div>
    `)
  })
}

const handleAddMultipleChoice = () => {
  let ele = `
    <div class="question" id="question${problem.length}" data-type="2" data-problemIndex="${problem.length}">
      <div class="top">
        <span class="question-title" id="questionTitle">1.请编辑问题？</span>
        <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
      </div>
      <div class="bottom">
        <textarea class="form-control textarea" id="problemName" placeholder="多选题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'content')"></textarea>
        <div class="option" id="option">
          <div class="option-item" id="optionItem0">
            <input type="text" class="form-control" id="listByOption" placeholder="选项【多选】" oninput="onInput(${problem.length}, 0, 'listByOption')" />
            <span class="option-del" onclick="multipleChoiceDelOption(${problem.length}, 0)">删除</span>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-link btn-add-option" onClick="multipleChoiceAddOption(${problem.length})">添加选项</button>
        </div>
        <div class="btn-group">
          <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
          <button type="button" id="editFinish" class="btn btn-default" onClick="multipleChoiceEditFinish(${problem.length})">完成编辑</button>
        </div>
      </div>
      <div class="bottom2" style="display: none;">
        
      </div>
    </div>
  `
  return ele
}

const multipleChoiceAddOption = (problemIndex) => {
  $(`#question${problemIndex} #option`).append(`
    <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
      <input type="text" class="form-control" id="listByOption" placeholder="选项【多选】" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'listByOption')" />
      <span class="option-del" onclick="multipleChoiceDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
    </div>
  `)
  problem[problemIndex].option.push({})
}

const multipleChoiceDelOption = (problemIndex, optionIndex) => {
  $(`#question${problemIndex} .option-item`)[optionIndex].remove()
  problem[problemIndex].option.splice(optionIndex, 1)
  $(`#question${problemIndex} .option-del`).map((item, index) => {
    index.onclick = multipleChoiceDelOption.bind(this, problemIndex, item)
  })
}

const multipleChoiceEditFinish = (problemIndex) => {
  $(`#question${problemIndex} .bottom`).css('display', 'none')
  $(`#question${problemIndex} .bottom2`).css('display', 'inline')
  $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].content}`)
  $(`#question${problemIndex} .bottom2`).html('')
  problem[problemIndex].type = 2 //题目类型：多选
  problem[problemIndex].option.map(item => {
    $(`#question${problemIndex} .bottom2`).append(`
      <div style="display: flex; align-items: center;">
        <label class="checkbox-inline">
          <input type="checkbox">${item.listByOption ? item.listByOption : ''}
        </label>
      </div>
    `)
  })
}

// const handleAddFillBlanks = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="3" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请输入题目" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'listByOption')"></textarea>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="fillBlanksEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none;">
        
//       </div>
//     </div>
//   `
//   return ele
// }

// const fillBlanksEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'inline')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].content}`)
//   $(`#question${problemIndex} .bottom2`).html(`
//     <div style="border: 1px solid #CCCCCC; width: 50%; height: 70px;"></div>
//   `)
// }

// const handleAddMatrix = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="4" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请编辑问题！" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div style="margin-bottom: 10px;">左标题</div>
//         <textarea class="form-control textarea" id="leftTitle" placeholder="例子：CCTV1,CCTV2,CCTV3" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'leftTitle')"></textarea>
//         <div class="option" id="option">
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="listByOption" placeholder="选项" oninput="onInput(${problem.length}, 0, 'chooseTerm')" />
//             <span class="option-del" onclick="matrixDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onClick="matrixAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="matrixEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none; padding-left: 80px;"></div>
//     </div>
//   `
//   return ele
// }

const matrixAddOption = (problemIndex) => {
  $(`#question${problemIndex} #option`).append(`
    <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
      <input type="text" class="form-control" id="listByOption" placeholder="选项" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'listByOption')" />
      <span class="option-del" onclick="matrixDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
    </div>
  `)
  problem[problemIndex].option.push({})
}

const matrixDelOption = (problemIndex, optionIndex) => {
  $(`#question${problemIndex} .option-item`)[optionIndex].remove()
  problem[problemIndex].option.splice(optionIndex, 1)
  $(`#question${problemIndex} .option-del`).map((item, index) => {
    index.onclick = matrixDelOption.bind(this, problemIndex, item)
  })
}

const matrixEditFinish = (problemIndex) => {
  $(`#question${problemIndex} .bottom`).css('display', 'none')
  $(`#question${problemIndex} .bottom2`).css('display', 'inline')
  $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].content}`)
  $(`#question${problemIndex} .bottom2`).html('')
  let trs = problem[problemIndex].leftTitle ? problem[problemIndex].leftTitle.split(',') : []
  $(`#question${problemIndex} .bottom2`).append(`
    <table class="table">
      <thead>
        <tr>
          <th></th>
        </tr>
      </thead>
      <tbody>
        
      </tbody>
    </table>
  `)
  trs.map((item, index) => {
    $(`#question${problemIndex} .bottom2 tbody`).append(`
      <tr class="tr${index}">
        <td>${item}</td>
      </tr>
    `)
    problem[problemIndex].option.map(() => {
      $(`#question${problemIndex} .bottom2 tbody .tr${index}`).append(`
        <td>
          <input type="radio" name="radio${index}">
        </td>
      `)
    })
  })
  problem[problemIndex].option.map(item => {
    $(`#question${problemIndex} .bottom2 thead tr`).append(`
      <th>${item.listByOption}</th>
    `)
  })

}

// const handleAddGauge = () => {
//   let ele = `
//     <div class="question" id="question${problem.length}" data-type="5" data-problemIndex="${problem.length}">
//       <div class="top">
//         <span class="question-title" id="questionTitle">1.请编辑问题？</span>
//         <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${problem.length})">必答题</span>
//       </div>
//       <div class="bottom">
//         <textarea class="form-control textarea" id="problemName" placeholder="请编辑问题！" rows="4" oninput="onInput(${problem.length}, ${undefined}, 'problemName')"></textarea>
//         <div class="option" id="option">
//           <div style="display: flex; margin-bottom: 10px;">
//             <div style="width: calc(50% + 90px)">选项文字</div>
//             <div style="width: 140px;">分数</div>
//             <div>操作</div>
//           </div>
//           <div class="option-item" id="optionItem0">
//             <input type="text" class="form-control" id="listByOption" oninput="onInput(${problem.length}, 0, 'listByOption')" />
//             <input type="text" class="form-control" id="fraction" oninput="onInput(${problem.length}, 0, 'fraction')" style="width: 50px;" />
//             <span class="option-del" onclick="gaugeDelOption(${problem.length}, 0)">删除</span>
//           </div>
//         </div>
//         <div>
//           <button type="button" class="btn btn-link btn-add-option" onClick="gaugeAddOption(${problem.length})">添加选项</button>
//         </div>
//         <div class="btn-group">
//           <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${problem.length})">取消编辑</button>
//           <button type="button" id="editFinish" class="btn btn-default" onClick="gaugeEditFinish(${problem.length})">完成编辑</button>
//         </div>
//       </div>
//       <div class="bottom2" style="display: none; align-items: center; justify-content: space-between;"></div>
//     </div>
//   `
//   return ele
// }

// const gaugeAddOption = (problemIndex) => {
//   $(`#question${problemIndex} #option`).append(`
//     <div class="option-item" id="optionItem${problem[problemIndex].option.length}">
//       <input type="text" class="form-control" id="listByOption" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'chooseTerm')" />
//       <input type="text" class="form-control" id="fraction" oninput="onInput(${problemIndex}, ${problem[problemIndex].option.length}, 'fraction')" style="width: 50px;" />
//       <span class="option-del" onclick="gaugeDelOption(${problemIndex}, ${problem[problemIndex].option.length})">删除</span>
//     </div>
//   `)
//   problem[problemIndex].option.push({})
// }

// const gaugeDelOption = (problemIndex, optionIndex) => {
//   $(`#question${problemIndex} .option-item`)[optionIndex].remove()
//   problem[problemIndex].option.splice(optionIndex, 1)
//   $(`#question${problemIndex} .option-del`).map((item, index) => {
//     index.onclick = gaugeDelOption.bind(this, problemIndex, item)
//   })
// }

// const gaugeEditFinish = (problemIndex) => {
//   $(`#question${problemIndex} .bottom`).css('display', 'none')
//   $(`#question${problemIndex} .bottom2`).css('display', 'flex')
//   $(`#question${problemIndex} #questionTitle`).text(`${problemIndex + 1}.${problem[problemIndex].content}`)
//   $(`#question${problemIndex} .bottom2`).html('')
//   $(`#question${problemIndex} .bottom2`).append(`
//     <div>${problem[problemIndex].option[0].listByOption}</div>
//   `)
//   problem[problemIndex].option.map(item => {
//     $(`#question${problemIndex} .bottom2`).append(`
//       <div>
//         <label class="radio-inline">
//           <input type="radio" name="fraction" />${item.fraction}
//         </label>
//       </div>
//     `)
//   })
//   $(`#question${problemIndex} .bottom2`).append(`
//     <div>${problem[problemIndex].option[problem[problemIndex].option.length - 1].listByOption}</div>
//   `)
// }

const handleModifyTitle = () => {
  $('#modifyTitleModal').modal('show')
  $('#questionnaireTitle').val(questionnaireTitle)
  $('#questionnaireDescription').val(questionnaireDescription)
}


const API_BASE_URL = "http://127.0.0.1:8085"
const handleEditFinish = () => {
  const str = $('#problem').html();
  let id
  //判断是新增还是更新
  if (questionOption != undefined && questionOption.id != undefined) {
    id = questionOption.id;
  } else {
    id = undefined;
  }

  if ($('#question_seq').text() == undefined) {
    alert("问卷说明不能为空!")
  }
  // 问卷问题数组
  let qArr = JSON.parse(JSON.stringify(problem))
  console.log(qArr);
  let params = {
    id: id,
    question_id: $util.getPageParam("question").id,
    sequence: $('#question_seq').text(),
    option_content: qArr
  }
  // let params = {
  //   id: id,
  //   question_id: $util.getPageParam("question").id,
  //   sequence: $('#question_seq').text(),
  //   option_content: str
  // }
  console.log(params);
  $.ajax({
    url: API_BASE_URL + '/admin/modifyQuestionnaire',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      alert(res.message)
    }
  })
  // location.href = "/pages/seeProject/index.html"
}

let selectQuestionOptionById = (id) => {
console.log(id);
  $.ajax({
    // url: API_BASE_URL + '/admin/selectQuestionOptionById',
    // type: "POST",
    // dataType: "json",
    // contentType: "application/json",
    url:API_BASE_URL+'/getQuestionById?id=' +id,
    type:"GET",
    // data:params,
    // dataType: "JSON",
    success(res) {
      questionOption = res;
      console.log(res);
      if( Array.isArray(res.data) && res.data.length>0){
        problem = res.data
        loadQuestion() //渲染页面
      }
    },
    error(err){
      console.log(err);
    }
  })




  
}

let loadQuestion = ()=>{
  let str=''
  console.log(problem);
  for(let i = 0;i<problem.length;i++){
    if(problem[i].type==1){
      str+=` <div class="question" id="question${i}" data-type="1" data-problemIndex="${i}">
      <div class="top">
        <span class="question-title" id="questionTitle">1.请编辑问题？</span>
        <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${i})">必答题</span>
      </div>
      <div class="bottom">
        <textarea class="form-control textarea" id="problemName" placeholder="单选题目" rows="4" oninput="onInput(${i}, ${undefined}, 'problemName')"></textarea>
        <div class="option" id="option">
          <div class="option-item" id="optionItem0">
            <input type="text" class="form-control" id="listByOption" placeholder="选项【单选】" oninput="onInput(${i}, 0, 'listByOption')" />
            <span class="option-del" onclick="singleChoiceDelOption(${i}, 0)">删除</span>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-link btn-add-option" onclick="singleChoiceAddOption(${i})">添加选项</button>
        </div>
        <div class="btn-group">
          <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${i})">取消编辑</button>
          <button type="button" id="editFinish" class="btn btn-default" onclick="singleChoiceEditFinish(${i})">完成编辑</button>
        </div>
      </div>
      <div class="bottom2" style="display: none;">
        
      </div>
    </div>`
    }
    if(problem[i].type==2){
      str+=` <div class="question" id="question${i}" data-type="2" data-problemIndex="${i}">
      <div class="top">
        <span class="question-title" id="questionTitle">1.请编辑问题？</span>
        <span class="must-answer" id="mustAnswer" onclick="onMustAnswerClick(${i})">必答题</span>
      </div>
      <div class="bottom">
        <textarea class="form-control textarea" id="problemName" placeholder="多选题目" rows="4" oninput="onInput(${i}, ${undefined}, 'problemName')"></textarea>
        <div class="option" id="option">
          <div class="option-item" id="optionItem0">
            <input type="text" class="form-control" id="listByOption" placeholder="选项【多选】" oninput="onInput(${i}, 0, 'listByOption')" />
            <span class="option-del" onclick="multipleChoiceDelOption(${i}, 0)">删除</span>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-link btn-add-option" onClick="multipleChoiceAddOption(${i})">添加选项</button>
        </div>
        <div class="btn-group">
          <button type="button" id="cancelEdit" class="btn btn-default" onclick="cancelEdit(${i})">取消编辑</button>
          <button type="button" id="editFinish" class="btn btn-default" onClick="multipleChoiceEditFinish(${i})">完成编辑</button>
        </div>
      </div>
      <div class="bottom2" style="display: none;">
        
      </div>
    </div>`
    }
  }
  $('#problem').html(str)
  for(let i =0;i<problem.length;i++){
    if(problem[i].type==1){
      singleChoiceEditFinish(i)
    }
    if(problem[i].type==2){
      singleChoiceEditFinish(i)
    }
  }
}
