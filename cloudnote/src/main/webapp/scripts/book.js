/** book.js 封装笔记本相关处理 */
//加载用户笔记本列表
function loadUserBooks(){
	//初始化视图
	$("#pc_part_2").show();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").hide();//回收站笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	//
	var userId = getCookie("uid");
	var userName = getCookie("uname");
	console.log(userId);
	//检查格式
	if(userId==null){
		window.location.href="log_in.html";
	}else{
		//发送ajax请求
		$.ajax({
			url: base_path + '/book/notebooks.do',
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.status==0){
	   				//获取返回的笔记本集合
	   				var books = result.data;
	   				$(".profile-username").text(userName);
	   				for(var i = 0;i < books.length;i++){
	   					//获取笔记本ID
	   					var bookId = books[i].cn_notebook_id;
	   					//获取笔记本名称
	   					var bookName = books[i].cn_notebook_name;
	   					//得到li元素
						var $li = $(notebooks);
						//将bookId绑定到li元素上
						$li.data("bookId",bookId);
						//将笔记本名称附到li中
						$li.children().children().eq(1).text(bookName);
						//将li元素添加到ul列表中
						$('#notebook-list').append($li);
					}
				}else if(result.status == 1){
					//提示失败
					alert(result.msg);
				}
			},
			error:function(){
				alert("加载笔记本信息失败！")
				window.location.href="log_in.html";
			},
		});
	}
}

function addNotebook(){
	//获取请求参数
	var name = $("#input_notebook").val().trim();
	var userId = getCookie("uid");
	console.log(userId);
	//格式检查
	var ok = true;
	if(name==""){
		ok = false;
		$("#notebook_span").html("笔记本名为空");
	}
	if(userId==null){
		ok = false;
		window.location.href="log_in.html";
	}
	//发送Ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/book/add.do",
			type:"post",
			data:{"userId":userId,"name":name},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//关闭对话框
					closeAlertWindow();
					//生成笔记本li元素
					var bookId = result.data.cn_notebook_id;
					var bookName = result.data.cn_notebook_name;
					//得到li元素
					var notebook = $(notebooks);
					//将bookId绑定到li元素上
					notebook.data("bookId",bookId);
					//将笔记本名称附到li中
					notebook.children().children().eq(1).text(bookName);
					//将li元素添加到ul列表中
					$('#notebook-list').append(notebook);
					//提示成功
					alert(result.msg);
				}else if(result.status == 1){
						//提示失败
						alert(result.msg);
				}
			},
			error:function(){
				alert("创建笔记本异常");
			}
		});
	}
}

//笔记本重命名
function renameBook(){
	//
	var bookName = $("#input_notebook_rename").val();
	var $li = $("#notebook-list a.checked").parent();
	var bookId = $li.data("bookId");
	//发送ajax
	$.ajax({
		url:base_path+"/book/rename.do",
		type:"post",
		data:{"bookId":bookId,"bookName":bookName},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//笔记本列表名字更新
				$li.children().children().eq(1).text(bookName);
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("重命名失败");
		}
	});
}

//删除笔记本功能
function deleteBook(){
	if(confirm("确定删除该笔记本？")){
		//获取选中笔记本的bookID
	var bookId = $(this).parents().eq(1).data("bookId");
		//获取li
		var $li = $(this).parents().eq(1);
	//发送ajax请求
	$.ajax({
		url:base_path + '/book/delete_book.do',
		type:'post',
		data:{"bookId":bookId},
		dataType:'json',
		success:function(result){
			if(result.status == 0){
				//从列表中移除被删除的笔记本
				$li.remove();
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除失败！");
		},
	});
	}
}

function collectionBook(){
	//清空选中样式
	$("#collect_ul").html("");//自己
	
	//清空活动表
	$("#action_ul").html("");
	//清空回收站
	$("#recycle_ul").html("");
	//清空全部笔记列表
	$('#note-list').html("");
	//取消笔记本列表选中
	$("#notebook-list .checked").removeClass("checked");
	//清空搜索列表
	$("#pc_part_6 ul").html("");
	
	$("#collect_ul .checked").removeClass("checked");//自己
	//初始化视图
	$("#pc_part_2").hide();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").hide();//回收站笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").show();//收藏笔记列表
	$("#pc_part_8").hide();
	//获取用户的ID
	var userId = getCookie("uid");
	//发送ajax请求
	$.ajax({
		url:base_path + '/note/load_collect_note.do',
		type:'post',
		data:{"userId":userId},
		dataType:'json',
		success:function(result){
			if(result.status == 0){
				//获取笔记信息
				var notes = result.data;
				for(var i = 0;i < notes.length;i++){
					//获取笔记ID和笔记标题
					var noteId = notes[i].cn_note_id;
					var title = notes[i].cn_note_title;
					//包装li
					var $li = $(collectli);
					$li.children().eq(0).children().eq(1).text(title);
					$li.data("noteId",noteId);
					$("#collect_ul").append($li);
				}
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("加载失败！");
		},
	});
}


