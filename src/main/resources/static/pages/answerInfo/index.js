onload = () => {
  $('#headerDivB').text('答案详情')
  let info = $util.getPageParam('info')

  fillInfo(info)
}


const fillInfo = (info) => {

  var str="";
  var list = info.list;
  for(var i=0;i< list.length;i++){
    str+="<tr><td>"+list[i].no+"</td><td>"+list[i].type+"</td><td>" + list[i].content + "</td></tr>";
  }
  $('#tbody').html(str)
}

