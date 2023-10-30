onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('创建问卷')

  $('#startTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd hh:mm:ss', // 显示格式
    minView: 0,  // 0表示可以选择小时、分钟，1只可以选择小时
    minuteStep: 1, // 分钟间隔1分钟
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })
  $('#endTime').datetimepicker({
    language: 'zh-CN', // 显示中文
    format: 'yyyy-mm-dd hh:mm:ss', // 显示格式
    minView: 0,  // 0表示可以选择小时、分钟，1只可以选择小时
    minuteStep: 1, // 分钟间隔1分钟
    initialDate: new Date(), // 初始化当前日期
    autoclose: true, // 选中自动关闭
    todayBtn: true // 显示今日按钮
  })

  let questionnaire = $util.getPageParam('questionnaire')
  if (questionnaire) {
    $('#questionnaireName').val(questionnaire.questionnaireName)
    $('#description').val(questionnaire.description)
    $('#startDate').val(questionnaire.startTime)
    $('#endDate').val(questionnaire.endTime)
    $('#targetAudience').val(questionnaire.target)
  }

  //获取项目名称和id
  $.ajax({
    url: API_BASE_URL + '/queryProList',
    type: 'POST',
    data: JSON.stringify(questionnaire),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      //清空列表
      $("#projectname").empty();
      //循环渲染数据
      $(res).each(function (i, e) {
        $("#projectname").append("<option value=" + e.id + ">" + e.projectName + "</option>"); //循环添加下拉框标签
      });
    }
  })
}

const API_BASE_URL = "http://127.0.0.1:8085"

const handleCreateQuestionnaire = () => {
  if (!$('#projectname').val()) return alert('项目名称不能为空!')
  if (!$('#questionnaireName').val()) return alert('调查名称不能为空！')
  if (!$('#description').val()) return alert('调查说明不能为空！')
  if (!$('#target').val()) return alert('请选择面向群体！')
  if (!$('#startDate').val()) return alert('开始时间不能为空！')
  if (!$('#endDate').val()) return alert('结束时间不能为空！')

  let questionnaire = $util.getPageParam('questionnaire');
  if (!questionnaire) {
    questionnaire = {};
  }

  questionnaire.projectname = $('#projectname').val();
  questionnaire.questionnaireName = $('#questionnaireName').val();
  questionnaire.description = $('#description').val();
  questionnaire.target = $('#target').val();
  questionnaire.startTime = $('#startDate').val();
  questionnaire.endTime = $('#endDate').val();

  // 修改
  if (questionnaire.id) {
    $.ajax({
      url: API_BASE_URL + '/admin/updateQuestionnaire',
      type: 'POST',
      data: JSON.stringify(questionnaire),
      dataType: 'json',
      contentType: 'application/json',
      success(res) {
        if (res.code === "666") {
          location.href = '/pages/questionnaire/index.html'
        } else {
          alert(res.message)
        }
      }
    })
  } else {
    // 新建
    $.ajax({
      url: API_BASE_URL + '/admin/createQuestionnaire',
      type: 'POST',
      data: JSON.stringify(questionnaire),
      dataType: 'json',
      contentType: 'application/json',
      success(res) {
        if (res.code === "666") {
          location.href = '/pages/questionnaire/index.html'
        } else {
          alert(res.message)
        }
      }
    })
  }
}
