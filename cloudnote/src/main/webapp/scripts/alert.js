function alertAddBookWindow(){
	//弹出添加笔记本对话框
	$("#can").load("alert1/alert_notebook.html");
	//显示对话框灰色背景(使用class="opacity_bg"选择)
	$(".opacity_bg").show();
};

function closeAlertWindow(){
	$("#can").html("");//清空对话框内容
	$(".opacity_bg").hide();//隐藏背景色
};

function alertAddNoteWindow(){
	//弹出添加笔记本对话框
	$("#can").load("alert1/alert_note.html");
	//显示对话框灰色背景(使用class="opacity_bg"选择)
	$(".opacity_bg").show();
};

function alertDeleteNoteWindow(){
	$("#can").load("alert1/alert_delete_note.html");
	$(".opacity_bg").show();
}

function alertMoveNoteWindow(){
	$(".opacity_bg").show();
	$("#can").load("alert1/alert_move.html",function(){
		var books = $("#notebook-list li");
		for(var i=0;i < books.length;i++){
			$li = $(books[i]);
			var bookId = $li.data("bookId");
			var bookTitle = $li.text().trim();
			//console.log($(books[i]).text());
			var sopt = '';
			sopt+='<option value="'+bookId+'">';
			sopt+= bookTitle;
			sopt+='</option>';
			$("#moveSelect").append(sopt);

		}
	});

}

function alertRenameBookWindow(){
		$("#can").load("alert1/alert_rename.html");
		$(".opacity_bg").show();
}

function alertReplayWindow(){
	
	$(".opacity_bg").show();
	$("#can").load("alert1/alert_replay.html",function(){
		var books = $("#notebook-list li");
		for(var i=0;i < books.length;i++){
			$li = $(books[i]);
			var bookId = $li.data("bookId");
			var bookTitle = $li.text().trim();
			//console.log($(books[i]).text());
			var sopt = '';
			sopt+='<option value="'+bookId+'">';
			sopt+= bookTitle;
			sopt+='</option>';
			$("#replaySelect").append(sopt);
		}
	});
}

function alertCompleteDeleteWindow(){
	$("#can").load("alert1/alert_delete_rollback.html");
	$(".opacity_bg").show();
}

function alertCollectWindow(){
	$("#can").load("alert1/alert_like.html");
	$(".opacity_bg").show();
}

function alertActCollect(){
	$("#can").load("alert1/alert_act_like.html");
	$(".opacity_bg").show();
}

