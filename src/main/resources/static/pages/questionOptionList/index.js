onload = () => {
  $('#headerDivB').text('答卷列表')
  let answerList = $util.getPageParam('answer')

  fillTable(answerList)

}
const fetchAnswerList = () => {
  // 获取输入的用户名
  let uname = $('#uname').val();

  console.log('Fetching answer list for username:', uname); // 添加调试语句
  // 发送Ajax请求，并传递用户名参数
  $.ajax({
    url: API_BASE_URL + '/answer/list',
    type: "POST",
    data: JSON.stringify({ uname }), // 将用户名作为参数传递给后端
    dataType: "json",
    contentType: "application/json",
    success(answerList) {
      console.log('Response:', answerList); // 打印返回的数据
      fillTable(answerList, uname); // 使用返回的筛选后的数据填充表格，并传递用户名参数
    },
    error(xhr) {
      console.log('Error:', xhr.responseText); // 打印错误消息
    }
  });
};
// 修改fillTable()函数
const fillTable = (answerList, uname) => {
  var str = "";
  for (var i = 0; i < answerList.length; i++) {
    if (!uname || answerList[i].uname === uname) { // 当用户名为空或与输入的用户名匹配时，将行添加到表格中
      str += "<tr><td>" + (i + 1) + "</td><td>" + answerList[i].question_id + "</td><td>" + answerList[i].count + "</td><td>" + answerList[i].uname + "</td><td>" + answerList[i].answerTime + "</td>";
      str += "<td>" + '<button type="button" class="btn btn-link" onclick="onShow(' + "'" + answerList[i].id + "'" + ')">查看答案</button>';
      str += '<button type="button" class="btn btn-link" onclick="onViewQuestion(' + "'" + answerList[i].question_id + "'" + ')">查看原题</button>';
      str += "</td></tr>";
    }
  }
  $('#tbody').html(str);
};




const onShow = (id) => {
  let params = {
    id
  }
  $.ajax({
    url: API_BASE_URL + '/answer/info',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      $util.setPageParam('info', res)
      location.href = "/pages/answerInfo/index.html"
    }
  })

}
const onViewQuestion = (questionId) => {
  let link = "http://127.0.0.1:8085/" + questionId;
  let encodedLink = encodeURIComponent(link);
  window.location.href = "/pages/OnlyRead/index.html?link=" + encodedLink;
};
