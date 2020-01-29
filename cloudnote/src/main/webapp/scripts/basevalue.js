var base_path = "http://localhost:8080/cloudnote";
//星星按钮
var butstar = "<button typr='button' class='btn btn-default btn-xs btn_position'><i class='fa fa-star'></i></button>"
//笔记本列表
var notebooks = "<li><a onclick='loadBookNotes(this);'> <i class='fa fa-book' title='online' rel='tooltip-bottom'></i><span id='notebooks'></span></i></a></li>";
//笔记列表
var notesli = "<li class='online'><a onclick='loadNote(this);'><i class='fa fa-file-text-o' title='online' rel='tooltip-bottom'></i><span id='text'></span><span id='noteid' style='display:none;'></span><button type='button' class='btn btn-default btn-xs btn_position btn_slide_down'><i class='fa fa-chevron-down'></i></button></a><div class='note_menu' tabindex='-1'><dl><dt><button type='button' class='btn btn-default btn-xs btn_move' title='移动至...'><i class='fa fa-random'></i></button></dt><dt><button type='button' class='btn btn-default btn-xs btn_share' title='分享'><i class='fa fa-sitemap'></i></button></dt><dt><button type='button' class='btn btn-default btn-xs btn_delete' title='删除'><i class='fa fa-times'></i></button></dt></dl></div></li>";
//搜索结果笔记列表
var noteli = "<li class='online'><a onclick=''><i class='fa fa-file-text-o' title='online' rel='tooltip-bottom'></i><span id='text'></span><button type='button' class='btn btn-default btn-xs btn_position btn_slide_down'><i class='fa fa-star'></i></button></a></li>";
//回收站笔记列表
var recycleli = '<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i><span></span><button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button></a></li>'
//叉子删除按钮
var butdelete = '<button type="button" class="btn btn-default btn-xs btn_position btn_delete" title="删除"><i class="fa fa-times"></i></button>'
//星星图标
var imgstar = "<i class='fa fa-star'>"
//收藏笔记本列表
var collectli = '<li class="idle"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> <span></span> <button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button></a></li>'
//近期活动
var actdiv = "<div class='div0'>" +
				"<div class='div1' style='height:80px;text-align:center;'><p class='p1'></p></div>" +
				"<div class='div2'></div>" +
				"<div class='div3' ></div>" +
			 "</div>"	
//活动笔记列表
var actli = '<li class="online"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> <span></span> <button type="button" class="btn btn-default btn-xs btn_position_3 btn_up"><i class="fa fa-thumbs-o-up"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_down"><i class="fa fa-thumbs-o-down"></i></button><button type="button" class="btn btn-default btn-xs btn_position btn_like"><i class="fa fa-star-o"></i></button></a></li>'
//活动中自己的笔记本列表
var actbookli = '<li class="online"><a ><i class="fa fa-book" title="online" rel="tooltip-bottom"></i> <span></span></a></li>'
//活动中自己的笔记列表
var actnoteli = '<li class="online"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> <span></span> </a></li>'
//主界面中活动笔记的列表	
var editactli = '<li class="offline"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> <span></span> </a></li>'
	
	
	