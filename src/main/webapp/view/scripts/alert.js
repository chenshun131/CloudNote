//点击 + 弹出添加笔记本对话框
function alertAddBookWindow(){
					//弹出添加笔记本对话框 就是一个div
					$("#can").load("alert/alert_notebook.html");//封装了ajax，专门用来执行内容的载入
					//显示对话框灰色背景
					$(".opacity_bg").show();
				};
//关闭对话框
function closeAlertWindow(){
	$("#can").html("");//清空对话框内容
	$(".opacity_bg").hide();//隐藏背景色
}

//弹出创建笔记对话框
function alertAddNoteWindow(){
	//判断是否有笔记本选中
	var $li = $("#book_ul a.checked").parent();
	if ($li.length == 0){//.length jquery对象指的是dom数组的长度，即dom数
		alert("亲,请选择笔记本");
	}else{
		$("#can").load("alert/alert_note.html");
		$(".opacity_bg").show();
	}
}

//确认删除笔记对话框
function alertDeleteNoteWindow(){
	$("#can").load("alert/alert_delete_note.html");//封装了ajax，专门用来执行内容的载入
	//显示对话框灰色背景
	$(".opacity_bg").show();
};

//弹出转移笔记对话框
function alertMoveNoteWindow(){
	$("#can").load("alert/alert_move.html",function(){//封装了ajax，专门用来执行内容的载入
		var $lis = $("#book_ul li");
		var sbookId = $("#book_ul a.checked").parent().data("bookId");
		for (var i=0;i<$lis.length;i++){
			var bookId = $lis.eq(i).data("bookId");
			if (bookId==sbookId){
				continue;
			}
			var soption = "";
			soption += '<option value='+bookId+' style="color:blue">';
			soption +=  $lis.eq(i).text().trim();
			soption += '</option>';
			var option = $(soption);
			$('#moveSelect').append(option);
		}
		$(".opacity_bg").show();//显示对话框灰色背景
	});
}

//弹出改名框
function alertRenameBookNameWindow(){
	$("#can").load("alert/alert_rename.html");
	$(".opacity_bg").show();
}

				
				