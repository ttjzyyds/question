onload = () => {
  $('#headerDivB').text('项目详情')
  let projectId = $util.getPageParam('seeProject')

  console.log(projectId, 'projectId')
  fetchProjectInfo(projectId)
  fetchQuestionList(projectId)
}

const fetchQuestionList = (id) => {
  let params = {
    id
  }
  //加载问卷信息
  $.ajax({
    url: API_BASE_URL + '/admin/selectQuestionByProId',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      var str="";
      for(var i=0;i<res.length;i++){
        str+="<tr><td>"+ (i + 1) +"</td><td>"+res[i].questionnaireName+"</td><td>"+res[i].issue_time+"</td>";
        str += "<td>" +  '<button type="button" class="btn btn-link" onclick="onEdit(' + "'" + res[i].id + "'" + ')">编辑</button>';
        str += '<button type="button" class="btn btn-link" onclick="onPublish(' + "'" + res[i].questionnaireName + "'" + ')">发布</button>';
        str += '<button type="button" class="btn btn-link" onclick="onPreview(' + "'" + res[i].id + "'" + ')">预览</button>';
        str += '<button type="button" class="btn btn-link" onclick="onAnalysis(' + "'" + res[i].id + "'" + ')">分析</button>';
        str += '<button type="button" class="btn btn-link" onclick="onDel(' + "'" + res[i].id + "'" + ')">删除</button>' + "</td></tr>";
      }
      $('#tbody').html(str)
    }
  })
}
const fetchProjectInfo = (id) => {
  let params = {
    id
  }
  $.ajax({
    url: API_BASE_URL + '/admin/selectProById',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      let info = res;
      console.log(info, 'res')
      $('#projectName').text(info.projectName)
      $('#createTime').text(info.createDate)
      $('#projectDescription').text(info.projectContent)
    }
  })
}

const onPublish = (name) => {
  let params = {
    questionnaireName:name
  }
  $.ajax({
    url: API_BASE_URL + '/admin/generateLink',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      if (res.code === '666') {
        alert(API_BASE_URL + "/pages/OnlyRead/index.html?link=" + res.data)
      } else {
        alert(res.msg)
      }
    }
  })
}

const onAnalysis = (id) => {
  let params = {
    id
  }
  $.ajax({
    url: API_BASE_URL + '/answer/analysis',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      $util.setPageParam('info', res)
      location.href = "/pages/analysis/index.html"
    }
  })
}

const onDel = (id) => {
  let params = {
    id
  }
  $.ajax({
    url: API_BASE_URL + '/admin/delQuestionById',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",

    success(res) {
      let projectId = $util.getPageParam('seeProject')
      console.log(projectId, 'projectId')
      fetchProjectInfo(projectId)
      fetchQuestionList(projectId)
    }
  })
}

const onEdit = (id) => {
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
      location.href = "/pages/newDesignquestionnaire/index.html"
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
