const API_BASE_URL = "http://127.0.0.1:8085"

let problem =[]
let question
onload = () => {
    question = JSON.parse(localStorage.getItem('pageParams')).question;
    $('#question_title').val(question.questionnaireName);

    $('#question_seq').val(question.description);
    console.log(question);

    selectQuestionOptionById(question.id);

}

function selectQuestionOptionById(id){
    $.ajax({
        url: API_BASE_URL + '/getQuestionById?id=' + id,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            questionOption = res;
            console.log(res);
            $util.setItem('problem', res.data)
            if (Array.isArray(res.data) && res.data.length > 0) {
                problem = res.data
                $util.setItem('problem',res.data)
                loadQuestion(problem) //渲染页面
            }

        }
    })
}

function loadQuestion(problem){
    let str = ''
    if(problem.length==0){
        $('#qsList').html(str)
        return
    }
    for (let i = 0; i < problem.length; i++) {
        str += `
                        <li id="qs${i}">
                       <div class="top">
                        <div class="title">
                        <input type="text" id="title${i}" value="${problem[i].content} " placeholder="请输入题目内容"></div>
                        <div class="type">
                            <div class="label">
                                题目类型:
                            </div>
                            <label class="radio-inline">
                                <input type="radio" name="${i}optionstype" id="${i}type1" checked="${problem[i].type == 1}" value="1"> 单选 </label>
                                <input type="radio" name="${i}optionstype" id="${i}type2"  checked="${problem[i].type == 2}" value="2"> 多选</label>
                                <div class="right">
                                <button type="button" class="btn btn-link" onclick="delQs(${i})">删除</button>
                            </div>
                                </div>
                        <div class="">
                            <div class="label">
                                排序:
                            </div>
                            <input type="number" id="${i}order" value="${problem[i].order}" placeholder="请填入题目排序">
                        </div>
                       </div> 
                       <div class="option">
                        <div>
                            <button type="button" class="btn btn-success btn-xs" onclick="addOption(${i})">添加选项</button>
                        </div>
                        <dl class="chose" id="choose${i}">`
        let str2 = ''
        for (let j = 0; j < problem[i].listByOption.length; j++) {
            str2 += ` <dt id="dt${j}">${String.fromCharCode(j + 65)}</dt>
                                    <dd><input type="text" id="${j}op_content${i}" value="${problem[i].listByOption[j].option_content} " placeholder="请输入选项内容">
                                    <div class="right"> <button type="button" class="btn btn-link btn-xs" onclick="delOption(${i},${j})">删除</button>
                                    </div></dd>`
        }
        str += str2 + `</dl>
                       </div>
                    </li>
                    `
    }
    $('#qsList').html(str)
}
// 增加选项
let addOption = (index) => {
    problem[index].listByOption.push({
        question_id:question.id,
        id: null,
        sequence: index+1,
        option_content: ""
    })
    let j = problem[index].listByOption.length-1
    let i = index
    let str =  `<dt id="dt${j}">${String.fromCharCode(j + 65)}</dt>
    <dd><input type="text" id="${j}op_content${i}" value="${problem[i].listByOption[j].option_content} " placeholder="请输入选项内容">
    <div class="right"> <button type="button" class="btn btn-link btn-xs" onclick="delOption(${i},${j})">删除</button>
    </div></dd>`
    $(`#choose${index}`).append(str)
    // loadQuestion(problem)
    // let j =  problem[index].listByOption.length-1
    // let str=` <dt>${String.fromCharCode(j + 65)}</dt>
    // <dd>-${problem[index].listByOption[j].option_content}</dd>`
    // let id = '#choose'+ problem[index].questionnaire_id
    // $(id).append(str)
}
//增加问题
let addQS = () => {
    // problem = $util.getItem('problem')
    getPageData()
    console.log(problem);
    problem.push(
        {   id:null,
            questionnaire_id:question.id,
            type:1,
            content:"",
            order:problem.length+1,
            listByOption:[]
        }
    )

    console.log(problem);
    loadQuestion(problem)
}
// // 删除
let delQs=(i)=>{
    getPageData()
    if(problem[i].id!=null){
        delQAjax(problem[i].id)
    }
    if(i=0){
        problem =[]
    }else{
        problem.splice(i,1)
    }

    loadQuestion(problem)
}

let delOption=(i,j)=>{
    getPageData()
    if(problem[i].listByOption[j].id !=null){
        delOAjax(problem[i].listByOption.id)
    }
    if(j=0){
        problem[i].listByOption =[]
    }else{
        problem[i].listByOption.splice(j,1)
    }
    loadQuestion(problem)
}

let getPageData=()=>{
    for(let i=0;i<problem.length;i++){
        problem[i].content= $(`#title${i}`).val()
        problem[i].order= $(`#${i}order`).val()
        problem[i].type=$(`input[name='${i}optionstype']:checked`).val()
        for(let j=0;j<problem[i].listByOption.length;j++){
            problem[i].listByOption[j].option_content=$(`#${j}op_content${i}`).val()
        }
    }
    console.log(problem);
    return problem
}

let handleEditFinish=()=>{

    let arr =  getPageData()
    let question_title = $('#question_title').val()
    let question_seq =$('#question_seq').val()
    if(question_title==''){
        alert("问卷标题不能为空!")
        return
    }
    if(question_seq==''){
        alert("问卷说明不能为空!")
        return
    }
    console.log(arr)
    $.ajax({
        url: API_BASE_URL + '/updateANDInsertQuestandOption',
        type: "POST",
        dataType: "json",
        data:JSON.stringify(arr),
        contentType: "application/json;charset=UTF-8",
        success(res) {
            console.log(res)
            if(res.status=="200"){
                location.href = "/pages/seeProject/index.html"
            }
        }
    })


}

// 删除接口
let delQAjax=(id)=>{
    $.ajax({
        url: API_BASE_URL + '/deleteByQ?id=' + id,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res);
            loadQuestion(problem)
        }
    })
}
let delOAjax=(id)=>{
    $.ajax({
        url: API_BASE_URL + '/deleteByO?id=' + id,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success(res) {
            console.log(res);
            loadQuestion(problem)
        }
    })
}

const onPreview = (id) => {
    let params = {
        id
    }
    $.ajax({
        url: API_BASE_URL + '/admin/editQuestionById',
        type: "POST",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success(res) {
            $util.setPageParam('question', res)
            // location.href = "/pages/designquestionnaire/index.html"
            location.href = "/pages/OnlyRead/index.html"
        }
    })

}