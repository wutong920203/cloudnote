function checkLogin(){
	//获取请求参数
	var name = $('#account').val().trim();
	var password = $('#password').val().trim();
	var list = {'username':name,'password':password};
	//检测参数格式
	//清空以前的提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	//是否通过检测
	var ok = true;
	if(name==""){
		ok = false;
		$("#count_span").html("用户名为空");
	}
	if(password==""){
		ok = false;
		$("#password_span").html("密码为空");
	}
	if(ok){
		$.ajax({
			url: base_path + '/user/login.do',
			type:'post',
			data:list,
			dataType:'json',
			success:function(result){//admin是返回的admin实例
				if(result.status==0){
					var admin = result.data;
					//写入Cookie
					addCookie("uid",admin.cn_user_id,2);
					addCookie("uname",admin.cn_user_name,2);
					window.location.href="edit.html";
				}else if(result.status=1){
					$("#count_span").html(result.msg);
				}
			},
			error:function(){
				$("#count_span").html("用户名或密码错误");
			},
		});
	}
}

function checkName(){
	//获得用户名
	var name = $('#regist_username').val().trim();
	//
	var ok = true;
	if(name == ''){
		ok = false;
		$("#name_span").html("用户名不能为空");
		$("#warning_1").show();
	}
	if(ok){
		$.ajax({
			url:base_path + '/user/checkUsername.do',
			type:'post',
			data: {'username':name},
			dataType:'json',
			success:function(){
				//$("#name_span").html("该用户名不可用");
				$("#name_span").html("Username has already been taken");
				$("#warning_1").show();
			},
			error:function(){
				$("#name_span").html("");
				$("#name_span").html("用户名可以使用");
				$("#warning_1").show();
			},
		});	
	}
}

function register(){
	//获取请求参数
	var name = $('#regist_username').val().trim();
	var nickname = $('#nickname').val().trim();
	var r_password = $('#regist_password').val().trim();
	var f_password = $('#final_password').val().trim();
	//打包为json
	var list = {'username':name,'nickname':nickname,
				'password':f_password};
	//清空信息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	//发送ajax请求前检查是否满足条件
	var ok = true;
	if(name==""){
		ok = false;
		$("warning_1").show();
		$("#warning_1 span").html("用户名为空");
	}
	if(r_password==""){
		ok = false;
		$("warning_2").show();
		$("#warning_2 span").html("密码为空");
	}else if(password.length < 6){
		ok = false;
		$("warning_2").show();
		$("#warning_2 span").html("密码长度太短");
	}
	if(f_password==""){
		ok = false;
		$("warning_3").show();
		$("#warning_3 span").html("确认密码为空");
	}else if(password.length < 6){
		ok = false;
		$("warning_3").show();
		$("#warning_3 span").html("与密码不一致");
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path + '/user/save.do',
			type:'post',
			data: list,
			dataType:'json',
			success:function(result){
				if(result.status == 0){//成功
					alert("succeed!");//提示成功
					$("#back").click();//转向登录页面
					//window.location.href="edit.html";
				}else if(result.status == 1){//用户名被占
					$("warning_1").show();
					$("#warning_1 span").html(result.msg);
				}
			},
			error:function(){
				alert("注册异常")
			},
		});
	}
}



/*
function checkLogin(){
			//获取请求参数
			var name = $('#account').val().trim();
			var password = $('#password').val().trim();
			var list = {'username':name,'password':password};
			//检测参数格式
			//清空以前的提示信息
			$("#count_span").html("");
			$("#password_span").html("");
			var ok = true;//是否通过检测
			if(name==""){
				ok = false;
				$("#count_span").html("用户名为空");
			}
			if(password==""){
				ok = false;
				$("#password_span").html("密码为空");
			}
			if(ok){
				$.ajax({
					url:'login.do',
					type:'post',
					data:list,
					dataType:'json',
					success:function(){
						window.location.href="edit.html";
					},
					error:function(){
						$("#count_span").html("用户名或密码错误");
					},
				});
			}
}
*/