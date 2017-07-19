//根据用户ID查询显示笔记本列表
function loadUserBooks(){
	//获取Cooike中的userId值
	var userId = getCookie("userId");
	//alert("userId");
	if (userId == null){//未找到
		window.location.href="log_in.html";
	}else {//登录过使用userId做其他操作
		$.ajax({
			url:path+"/book/loadbooks.do",
			type:"post",
			data:{"userId":userId},
			dataType:"json",
			success:function(result){
				if (result.status == 0){
					var books = result.data;
					//alert(books.length);
					for (var i=0;i<books.length;i++){
						var book = books[i];
						//alert(book.cn_notebook_createtime);
						//var $li = $('<li class="online">'+'<a><i class="fa fa-book" '+ 'title="online" '+'rel="tooltip-bottom">'+'</i> '+book.cn_notebook_name+'</a></li>');
						var bookId = book.cn_notebook_id;
						var bookName = book.cn_notebook_name;
						createBookLi(bookId,bookName);
						
					}
				}
			},
			error:function(){
				alert("加载笔记本失败");
			}
		})
	}
}

//创建一个笔记本li元素
function createBookLi(bookId,bookName){
	var sli = '';
	sli += '<li class="online" >';
	sli += '	<a>';
	sli += '		<i class="fa fa-book" title="online" rel="tooltip-bottom">';
	sli += '		</i>';
	sli += bookName;
	sli += '	</a>';
	sli += '</li>';
	var $li = $(sli);
	$li.data("bookId",bookId);//将notebook_id藏进节点，类似与key-value
	$("#book_ul").append($li);
}

//创建笔记本
function sureAddBook(){
	var userId = getCookie("userId");
	var bookName = $("#input_notebook").val().trim();
	//检测格式
	var ok = true;
	if(userId==null){
		window.location.href="log_in.html";
		ok = false;
	}
	if(bookName==""){
		$("#name_span").html("笔记本名为空");
		ok = false;
	}
	//alert(bookName+","+userId);
	if(ok){
		$.ajax({
			url:path+"/book/add.do",
			type:"post",
			data:{"userId":userId,"bookName":bookName},
			dateType:"json",
			success:function(result){
				if (result.status==0){
					var book = result.data;
					var id = book.cn_notebook_id;
					var name = book.cn_notebook_name;
					closeAlertWindow();
					createBookLi(book,name);
				}
				
			},
			error:function(){
				alert("创建笔记本失败")
			}
		});
	}
}

//改名
function renameBookName(){	
	var bookId = $("#book_ul a.checked").parent().data("bookId");
	var name_before = $("#book_ul a.checked").parent().text().trim();
	var name = $("#input_notebook_rename").val().trim();
	//alert(bookId+","+name_before+","+name);
	if (name == ""){
		$("#name_span").html("名字不能为空");
	}else if (name == name_before){
		$("#name_span").html("没有修改");
	}else{
		$.ajax({
			url:path+"/book/rename.do",
			type:"post",
			data:{"bookId":bookId,"bookName":name},
			dataType:"json",
			success:function(result){
				if (result.status==0){
					closeAlertWindow();
					var sli = "";
					sli += '	<a>';
					sli += '		<i class="fa fa-book" title="online" rel="tooltip-bottom">';
					sli += '		</i>';
					sli += name;
					sli += '	</a>';
					$("#book_ul a.checked").parent().html(sli);
				}
			},
			error:function(){
				alert("改名失败");
			}
			
		});
	}
}
