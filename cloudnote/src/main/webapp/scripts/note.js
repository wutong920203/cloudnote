//"保存笔记"按钮的处理
function update(){
	//获取请求参数
	var title = $("#input_note_title").val().trim();
	var content = um.getContent();
	//取出刚才保存的noteid数据
	var noteId = $('#save_note').data("noteId");
	//封装成为json
	var list = {"content":content,"noteId":noteId,"title":title};
	 //清除上次提示信息
	 $("#note_title_span").html("");
	 //检查格式
	 if(noteId==null){
		 alert("请选择要保存的笔记");
	 }else if(title==""){
		 $("#note_title_span").html("<font color='red'>标题不能为空</font>");
	 }else{
		//发送Ajax请求
		$.ajax({
			url:base_path+"/note/update.do",
			type:"post",
			data:list,
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//将笔记列表中的名字更新
					$("#note-list .checked").children().eq(1).text(title);
					//提示成功
					alert(result.msg);
				}else if(result.status == 1){
					//提示失败
					alert(result.msg);
				}
			},
			error:function(){
				alert("保存笔记异常");
			}
		});
	 }
}

//根据笔记ID加载笔记信息
function loadNote(name){
	//清空
	$("#note-list .checked").removeClass("checked");
	$("#input_note_title").val("");
	//初始化视图
	$("#pc_part_2").show();//笔记列表
	$("#pc_part_3").show();//编辑笔记
	$("#pc_part_4").hide();//回收站笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	
	//增加class，显示被选中
	$(name).addClass("checked");
	//得到笔记的名称
	var title = $(name).children().eq(1).text();
	//放到编辑笔记的标题框中
	$("#input_note_title").val(title);
	//取出隐藏的id
	var noteId = $(name).children().eq(2).text();
	console.log(noteId);
	//给保存按钮绑定数据
	$('#save_note').data("noteId",noteId)
	//
	$.ajax({
		url:base_path + '/note/content.do',
		type:'post',
		data:{"noteId":noteId},
		dataType:'json',
		success:function(result){
			var note = result.data;
			if(result.status==0){
				//获取笔记标题
				var title = note.cn_note_title;
				//获取笔记内容
				var body = note.cn_note_body;
				 //设置到编辑区域
				 $("#input_note_title").val(title);
				 um.setContent(body);
			 }else if(result.status == 1){
				//提示失败
				alert(result.msg);
			 }
		},
		error:function(){
			alert("笔记内容加载失败！");
		},
	});
	
}

//根据笔记本ID加载所有笔记标题
function loadBookNotes(name){
	//初始化视图
	$("#pc_part_2").show();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").hide();
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	
	//清空
	$("#notebook-list .checked").removeClass("checked");
	//清空全部笔记列表
	$('#note-list').html("");
	
	//清空活动列表
	$("#action_ul").html("");
	//清空收藏笔记本
	$("#collect_ul").html("");
	//清空回收站
	$("#recycle_ul").html("");
	//清空搜索列表
	$("#pc_part_6 ul").html("");
	
	$('#myEditor').html("");
	$("#input_note_title").val("");
	//获取之前绑定的bookId值
	var bookId = $(name).parent().data("bookId");
	//设置笔记选中状态
	$(name).addClass("checked");
	//ajax请求
	$.ajax({
		url:base_path + '/note/loadnotes.do',
		type:'post',
		data:{"bookId":bookId},
		dataType:'json',
		success:function(result){
			if(result.status==0){
				//获取服务器返回的笔记集合信息
				var notes = result.data;
				//如果有叉叉，先把叉叉清空
				$(name).children().eq(2).remove();
				//如果该笔记本没有内容，则显示一个叉叉
				if(notes.length == 0){
					//$but是一个删除的叉叉
					var $but = $(butdelete);
					//附到没有笔记的条目上
					$(name).append($but);
				}
				//循环生成笔记li元素
				for(var i=0;i<notes.length;i++){
					//获取笔记ID和笔记标题
					var noteId = notes[i].cn_note_id;
					var noteTitle = notes[i].cn_note_title;
					//获取li
					var $li = $(notesli);
					$li.children().eq(0).children().eq(1).text(noteTitle);
					$li.children().eq(0).children().eq(2).text(noteId);
					$li.data("noteId",noteId);
		    		$('#note-list').append($li);
		    		//新添加的元素判断是否该加分享图标
		    		var typeId = notes[i].cn_note_type_id;
		    		//
		    		if(typeId=='3'){
		    			var img = '<i class="fa fa-sitemap"></i>';
		    			//获取新添加的li元素
		    			var $li2 = $("#note-list li:last");
		    			$li2.find(".btn_slide_down").before(img);
		    		}
				}
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("笔记本列表加载失败！");
		},
	});
	
}

//添加笔记
function addNote(){
	
	var title = $("#input_note").val().trim();
	var userId = getCookie("uid");
	//获取笔记本的Id
	var notebookId = $("#notebook-list .checked").parent().data("bookId");
	console.log(notebookId);
	//
	if(notebookId == null){
		alert("请选择笔记本");
	}else if(userId == null){
		alert("用户加载异常");
	}else if(title == ""){
		$("#input_span").text("笔记名不能为空");
	}else{
		$.ajax({
			url:base_path + "/note/add.do",
			type:"post",
			data:{"userId":userId,"notebookId":notebookId,"title":title},
			dataType:'json',
			success:function(result){
				if(result.status==0){
					//关闭对话框
					closeAlertWindow();
					var note = result.data;
					//关闭对话框//closeAlertWindow();
					//创建一个笔记li元素
					var noteId = note.cn_note_id;
					var title = note.cn_note_title;
					//
					var $li = $(notesli);
					
					$li.children().eq(0).children().eq(1).text(title);
					$li.children().eq(0).children().eq(2).text(noteId);
					$li.data("noteId",noteId);
					$('#note-list').append($li);
					//弹出提示
					alert(result.msg);
				 }else if(result.status == 1){
						//提示失败
						alert(result.msg);
				 }
			},
			error:function(){
				alert("创建笔记异常");
			},
		});
	}
	
}

//弹出笔记菜单
function popNoteMenu(){
	 //隐藏所有笔记菜单
	 $("#note-list div").hide();
	 //显示点击的笔记菜单
	 var $menu = $(this).parent().next();
	 $menu.slideDown(500);
	 //设置点击笔记选中效果
	 $("#note-list a").removeClass("checked");
	 $(this).parent().addClass("checked");
	 //阻止事件向li,body冒泡
	 return false;
 };

//隐藏笔记菜单
function hideNoteMenu(){
	//隐藏所有笔记菜单
	$("#note-list div").hide();
};

//删除功能
function deleteNote(){
	//获取请求参数
	var $li = $("#note-list a.checked").parent();
	var noteId = $li.data("noteId");
	console.log(noteId);
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/delete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//删除li
				$li.remove();
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记异常");
		}
	});
}

//转移笔记
function moveNote(){
	//获取请求参数
	//获取要转移的笔记ID
	var $li = $("#note-list a.checked").parent();
	var noteId = $li.data("noteId");
	//获取要转入的笔记本ID
	var bookId = $("#moveSelect").val();
	//发送Ajax请求
	$.ajax({
		url:base_path+"/note/move.do",
		type:"post",
		data:{"noteId":noteId,"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//移除笔记li
				$li.remove();
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("转移笔记异常");
		}
	});
	
}

//分享笔记
function shareNote(){
	var $li = $(this).parents("li");
	var noteId = $li.data("noteId");
	//
	$.ajax({
		url:base_path+"/note/share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status == 0){
				//TODO添加分享图标
				var img = '<i class="fa fa-sitemap"></i>';
				$li.find(".btn_slide_down").before(img);
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				alert(result.msg);
			}
		},
		error:function(){
			alert("笔记分享异常");
		},
	});
}

//分页加载搜索分享笔记
function searchSharePage(keyword,page){
	//清空原搜索
	if(page==1){
		$("#pc_part_6 ul").html("");//自己
	}
	//清空参加活动列表
	$("#action_ul").html("");
	//清空收藏笔记本
	$("#collect_ul").html("");
	//清空回收站
	$("#recycle_ul").html("");
	//清空全部笔记列表
	$('#note-list').html("");
	//取消笔记本列表选中
	$("#notebook-list .checked").removeClass("checked");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/search_share.do",
		type:"post",
		data:{"keyword":keyword,"page":page},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//获取服务器返回的搜索结果
				var shares = result.data;
				//循环解析生成列表li元素
				for(var i=0;i<shares.length;i++){
					//分享ID
					var shareId = shares[i].cn_share_id;
					//分享标题
					var shareTitle = shares[i].cn_share_title;
					//生成一个li
					var $li = $(noteli);
					console.log(shareTitle);
					$li.children().eq(0).children().eq(1).text(shareTitle);
					$li.data("shareId",shareId);
					//添加到搜索结果ul中
					$("#pc_part_6 ul").append($li);
				}
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
 		error:function(){
 			alert("搜索异常");
 		},
	});
}

function showShareNote(){
	//清空
	$("#pc_part_6 .checked").removeClass("checked");
	$("#input_note_title").val("");
	//增加class，显示被选中
	$(this).children().eq(0).addClass("checked");
	//取出ID
	var shareId = $(this).data("shareId");
	//console.log(shareId);
	$.ajax({
		url:base_path+"/note/loadsharenote.do",
		type:"post",
		data:{"shareId":shareId},
		dataType:"json",
		success:function(result){
			if(result.status == 0){
				$("#pc_part_2").hide();
				$("#pc_part_3").hide();
				$("#pc_part_4").hide();
				$("#pc_part_5").show();//预览笔记
				$("#pc_part_6").show();
				$("#pc_part_7").hide();
				$("#pc_part_8").hide();
				var share = result.data;
				//得到标题和内容
				var title = share.cn_share_title;
				var body = share.cn_share_body;
				$("#noput_note_title").text(title);
				$("#noput_note_title").next().html(body);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
 			alert("笔记加载异常");
 		},
	});
	
}
//显示回收站内容
function recycleBin(){
	//清空自己
	$("#recycle_ul").html("");
	
	//清空活动
	$("#action_ul").html("");
	//清空收藏笔记本
	$("#collect_ul").html("");
	//清空全部笔记列表
	$('#note-list').html("");
	//取消笔记本列表选中
	$("#notebook-list .checked").removeClass("checked");
	//清空搜索列表
	$("#pc_part_6 ul").html("");
	
	//视图初始化
	$("#pc_part_2").hide();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").show();//回收站内的笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	//得到userID
	var userId = getCookie("uid");
	
	$.ajax({
		url:base_path + '/note/recycle.do',
		type:'post',
		data:{"userId":userId},
		dataType:'json',
		success:function(result){
			var notes = result.data;
			if(result.status==0){
				var notes = result.data;
				for(var i=0;i < notes.length;i++){
					$li = $(recycleli);	
					var title = notes[i].cn_note_title;
					var noteId = notes[i].cn_note_id;
					//将标题存入li
					$li.children().eq(0).children().eq(1).text(title);
					//将noteid存入li
					$li.data("noteId",noteId);
					//追加到ul
	    	 		$("#recycle_ul").append($li);
				}
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("回收站列表加载失败！");
		},
	});
}

function recycleChecked(){
	//清空选中样式
	$("#recycle_ul .checked").removeClass("checked");
	//添加选中样式
	$(this).children().eq(0).addClass("checked");
}

//从回收站还原
function replay(){
	var $li = $("#recycle_ul a.checked").parent();
	//获取笔记id
	var noteId = $li.data("noteId");
	//获取要还原到的笔记本id
	var bookId = $("#replaySelect").val();
	//发送ajax请求
	$.ajax({
		url:base_path + '/note/recycle_replay.do',
		type:'post',
		data:{"bookId":bookId,"noteId":noteId},
		dataType:'json',
		success:function(result){
			if(result.status == 0){
				//从回收站列表中移出
				$li.remove();
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("还原失败！");
		},
	});
}

//彻底删除
function completelyDelete(){
	//获取选中的li元素
	var $li = $("#recycle_ul a.checked").parent();
	//兼任两种情况的彻底删除任务
	if($li.data("noteId")==null){
		$li = $("#collect_ul a.checked").parent();
	}
	//获取笔记id
	var noteId = $li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path + '/note/recycle_delete.do',
		type:'post',
		data:{"noteId":noteId},
		dataType:'json',
		success:function(result){
			if(result.status == 0){
				//从列表中移除被删除的信息
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

function searchNote(event){
	var code = event.keyCode;
	if(code==13){//按回车键
		//清除原有列表结果
	$("pc_part_6 ul").empty();
	//显示搜索结果列表，其他列表隐藏
	$("#pc_part_2").hide();
	$("#pc_part_4").hide();
	$("#pc_part_6").show();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
		keyword = $("#search_note").val().trim();
		page = 1;//设置初始值1
		//发送Ajax请求
		searchSharePage(keyword,page);
	}
}

function showMoreNotes(){
	//将page加1
	page = page + 1;
	//发送Ajax请求加载数据
	searchSharePage(keyword,page);
}

function sureCollect(){
	//获取分享笔记的ID
	var shareId = $("#search_ul .checked").parent().data("shareId")
	//获取用户的ID
	var userId = getCookie("uid");
	//发送ajax请求
	$.ajax({
		url:base_path + '/note/collect_note.do',
		type:'post',
		data:{"userId":userId,"shareId":shareId},
		dataType:'json',
		success:function(result){
			if(result.status == 0){
				//提示成功
				alert(result.msg);
			}else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("收藏失败！");
		},
	});
}

function loadColNoteContent(){
	//清空选中样式
	$("#collect_ul .checked").removeClass("checked");
	//视图初始化
	$("#pc_part_2").hide();//笔记列表
	$("#pc_part_3").show();//编辑笔记
	$("#pc_part_4").hide();//回收站内的笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").show();//收藏笔记列表
	$("#pc_part_8").hide();
	
	//添加选中样式
	$(this).find("a").addClass("checked");
	//获取noteId
	var noteId = $(this).data("noteId");
	//给保存按钮绑定数据
	$('#save_note').data("noteId",noteId);
	//
	$.ajax({
		url:base_path + '/note/collect_content.do',
		type:'post',
		data:{"noteId":noteId},
		dataType:'json',
		success:function(result){
			if(result.status==0){
				var note = result.data;
				//获取笔记标题
				var title = note.cn_note_title;
				//获取笔记内容
				var body = note.cn_note_body;
				//设置到编辑区域
				$("#input_note_title").val(title);
				um.setContent(body);
			 }else if(result.status == 1){
				//提示失败
				alert(result.msg);
			}
		},
		error:function(){
			alert("笔记内容加载失败！");
		},
	});
}

function showActionNotes(){
		//视图初始化
	$("#pc_part_2").hide();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").hide();//回收站内的笔记
	$("#pc_part_5").hide();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").show();
	//清空
	$("#action_ul").html("");//自己
	
	//清空收藏笔记本
	$("#collect_ul").html("");
	//清空回收站
	$("#recycle_ul").html("");
	//清空全部笔记列表
	$('#note-list').html("");
	//取消笔记本列表选中
	$("#notebook-list .checked").removeClass("checked");
	//清空搜索列表
	$("#pc_part_6 ul").html("");
	//
	var userId = getCookie("uid");
	$.ajax({
			url:base_path + '/note/action_note.do',
			type:'post',
			data:{"userId":userId},
			dataType:'json',
			success:function(result){
				if(result.status==0){
					var notes = result.data;
					for(var i = 0;i < notes.length;i++){
						var title = notes[i].cn_note_title;
						var noteId = notes[i].cn_note_id;
						var $li = $(editactli);
						$li.find("span").text(title);
						$li.data("noteId",noteId);
						$("#action_ul").append($li);	
					}
				 }else if(result.status == 1){
					//提示失败
					alert(result.msg);
				}
			},
			error:function(){
				alert("笔记内容加载失败！");
			},
	});
}

function loadActNoteContent(){
	//清空
	$("#noput_note_title").text("");
	$("#noput_note_body").html("");
	//清空选中样式
	$("#action_ul .checked").removeClass("checked");
	//添加选中样式
	$(this).children().eq(0).addClass("checked");
	//视图初始化
	$("#pc_part_2").hide();//笔记列表
	$("#pc_part_3").hide();//编辑笔记
	$("#pc_part_4").hide();//回收站内的笔记
	$("#pc_part_5").show();//预览笔记
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").show();
	//获取noteId
	var noteId = $(this).data("noteId");
	//发送Ajax请求
	$.ajax({
		url:base_path + '/note/content.do',
		type:'post',
		data:{"noteId":noteId},
		dataType:'json',
		success:function(result){
			var note = result.data;
			if(result.status==0){
				//获取笔记标题
				var title = note.cn_note_title;
				//获取笔记内容
				var body = note.cn_note_body;
				//设置到预览区域
				$("#noput_note_title").text(title);
				$("#noput_note_body").html(body);
			 }else if(result.status == 1){
				//提示失败
				alert(result.msg);
			 }
		},
		error:function(){
			alert("笔记内容加载失败！");
		},
	});
}
