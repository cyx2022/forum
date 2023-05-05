function getSelected() {
    if (window.getSelection) {
        return window.getSelection().toString();
    } else if (document.getSelection) {
        return document.getSelection().toString();
    } else {
        var selection = document.selection && document.selection.createRange();
        if (selection.text) {
            return selection.text.toString();
        }
        return "";
    }
}

// 校验用户名
function checkUserName(str){
    var str2 = "该用户名合法";
    if ("" == str)
    {
        str2 = "用户名为空";
        return {flag:false,msg:str2};
    }
    else if ((str.length < 3) || (str.length > 20))
    {
        str2 = "用户名必须为3 ~ 20位";
        return {flag:false,msg:str2};
    }
    else if (check_other_char(str))
    {
        str2 = "不能含有特殊字符";
        return {flag:false,msg:str2};
    }
    return {flag:true,msg:''};
}


function check_other_char(str)
{
    var arr = ["&", "\\", "/", "*", ">", "<", "@", "!"];
    for (var i = 0; i < arr.length; i++)
    {
        for (var j = 0; j < str.length; j++)
        {
            if (arr[i] == str.charAt(j))
            {
                return true;
            }
        }
    }   
    return false;
}




// 校验密码
function checkPassword(password){		
    //用正则表达式 
    var reg = /^\w{6,12}$/;
    //判断值是否符合正则的规则
    var flag = reg.test(password);
    //提示信息
    
    if(flag){
        return {flag:true,msg:''}
    }else{
        return {flag:false,msg:'密码必须为6-12位数字'}
    }
}

// 校验验证码
function checkCode(code){
      var reg = /^\w{4}$/;
      var flag = reg.test(code);
      if(flag){
          return {flag:true,msg:''}
      }else{
          return {flag:false,msg:'验证码必须为5位数字'}
      }
}



export default{
    getSelected,
    checkUserName,
    checkPassword,
    checkCode,
}


