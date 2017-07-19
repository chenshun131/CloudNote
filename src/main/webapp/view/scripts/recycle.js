//点击垃圾桶 切换到垃圾桶页面，加载回收站笔记
function clickRecycle(){
					//$("#note_ul li").remove();
					$("#pc_part_2").hide();
					$("#pc_part_3").hide();
					$("#pc_part_4").show();
					$("#pc_part_5").show();
					$("#pc_part_6").hide();
					$("#pc_part_7").hide();
					$("#pc_part_8").hide();
					var userId = getCookie("userId");
					if (userId==null){
						window.location.href("log_in.html");
					}else{
						$.ajax({
							url:path+"/note/loadRecycleNotes.do",
							type:"post",
							data:{"userId":userId},
							dataType:"json",
							success:function(result){
								$("#recyclenote_ul").empty();
								var notes = result.data;
								for (var i=0;i<notes.length;i++){
									var noteTitle = notes[i].cn_note_title;
									var noteId = notes[i].cn_note_id;
									var bookId = notes[i].cn_user_id;
									//alert(noteTitle+","+bookId+","+noteId);
									var sli = "";
									sli += '<li class="disable">'
									sli += '	 <a >'
									sli += '   	 	<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom">';
									sli += '		</i> '+noteTitle;
									sli += '		<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i>';
									sli += '		</button>';
									sli += '		<button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i>';
									sli += '		</button>';
									sli += '	</a>';
									sli += '</li>';
									var li = $(sli);
									$(li).data("noteId",noteId);
									$(li).data("bookId",bookId);
									$("#recyclenote_ul").append(li);
								}
								
							},
							error:function(){
								alert("载入垃圾桶失败");
							}
						});
					}
					
				}

//点击回收站里的笔记li
function clickRecycleLi(){
	var noteId = $(this).data("noteId");
	var bookId = $(this).data("bookId");
	$("#recyclenote_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	$.ajax({
		url:path+"/note/loadnote.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if (result.status == 0){
				var note = result.data;
				var body = note.cn_note_body;
				var title = note.cn_note_title;
				$("#noput_note_title").html(title);
				$("#noput_note_body").html(body);
				//um.setContent(body);//设置笔记内容，um是Ueditor编辑器的实例
			}
			
		},
		error:function(){
			alert("加载笔记内容失败");
		}
	});
}

//点击删除弹出页面的确认
function sureDeleteRecycleNotes(){
	var noteId = $("#recyclenote_ul a.checked").parent().data("noteId");
	$.ajax({
		url:path+"/note/finalDelete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			closeAlertWindow();
			$("#recyclenote_ul a.checked").parent().remove();
		},
		error:function(){
			alert("删除笔记失败啊")
		}
	});
}

//点击笔记的还原按钮弹出还原页面
function clickReplayRecycleNote(){
	$("#can").load("alert/alert_replay.html",function(){//封装了ajax，专门用来执行内容的载入
		var $lis = $("#book_ul li");
		//var bookId = $(this).data("bookId");
		for (var i=0;i<$lis.length;i++){
			var bookId = $lis.eq(i).data("bookId");
			var soption = "";
			soption += '<option value='+bookId+' style="color:blue">';
			soption +=  $lis.eq(i).text().trim();
			soption += '</option>';
			var option = $(soption);
			$('#replaySelect').append(option);
		}
		$(".opacity_bg").show();//显示对话框灰色背景
	});
}

//点击还原弹出页面的确认
function sureReplayRecycleNote(){
	var bookId = $("#replaySelect option:selected").val();//同$("#moveSelect")
	var noteId = $("#recyclenote_ul a.checked").parent().data("noteId");
	//alert(bookId+","+noteId);
	$.ajax({
		url:path+"/note/move.do",
		type:"post",
		data:{"bookId":bookId,"noteId":noteId},
		dataType:"json",
		success:function(result){
			$("#recyclenote_ul a.checked").parent().remove();
			alert(result.msg);
		},
		error:function(){
			alert("笔记本转移失败");
		}
	});  
}