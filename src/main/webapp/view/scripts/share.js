//笔记分享 分享按钮单击
function sureShareNote(){
					//获取请求参数
					var $li = $(this).parents("li");
					var noteId = $("#note_ul a.checked").parent().data("noteId");//或者$(this).parents("li")
					$.ajax({  													 //当前事件源的父类中叫 li 的
						url:path+"/share/add.do",
						type:"post",
						data:{"noteId":noteId},
						dataType:"json",
						success:function(result){
							//给笔记添加分享图标
							var noteTitle = $li.text();
							var str = "";
							str+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
							str+= noteTitle;
							str+='<i class="fa fa-sitemap"></i>';
							str+='<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
							//将笔记li元素中的<a>的内容替换
							$li.find("a").html(str);
							//提示成功
							alert(result.msg);
						},
						error:function(){
							alert("分享笔记失败");
						}
					});
				}
//点击更多
function clickMoreNotes(){
	 $.ajax({
   	  url:path+"/share/search.do",
   	  type:"post",
   	  data:{"shareTitle":input,"count":count},
   	  dataType:"json",
   	  success:function(result){
   		  if (result.status==0){
	    			var shares = result.data;
	    			for (var i=0;i<shares.length;i++){
	    				//获取分享id
	    				var shareId = shares[i].cn_share_id;
	    				if (shareId==null){
	    					return;
	    				}
	    				//获取分享标题
	    				var sharetitle = shares[i].cn_share_title;
	    				var sli = '<li class="online">';
	    				sli += '	<a >';
	    				sli += '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
	    				sli += sharetitle;
	    				sli += '	<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
	    				sli += '	</a>';
	    				sli += '</li>';
	    				var $li = $(sli);
	    				$li.data("shareId",shareId);
	    				$("#search_ul").append($li);
	    			}
	    			count += 5;
	    			$("#pc_part_5").show();
	    			$("#pc_part_6").show();
	    			$("#pc_part_2").hide();
	    			$("#pc_part_3").hide()
	    			$("#pc_part_4").hide();
	    			$("#pc_part_7").hide();
	    			$("#pc_part_8").hide();
   		  }else{
	    		  $("#search_ul").empty();
	    		  $("#search_note").val("没有搜索结果");
   		  }
   	  },
   	  error:function(){
   		  alert("搜索分享失败");
   	  }
     }); 
}

//分享笔记搜索里面输入 和 实现分页显示功能
function searchAndMoreNote(event){
	$("#search_note").empty();
	var code = event.keyCode;//获取键盘的code码
	  	if(code==13){
	  		var count=0;
		      //回车键,获取关键词,发送Ajax请求
		      var input = $("#search_note").val().trim();
		     // alert(input)
		      $.ajax({
		    	  url:path+"/share/search.do",
		    	  type:"post",
		    	  data:{"shareTitle":input,"count":count},
		    	  dataType:"json",
		    	  success:function(result){
		    		  if (result.status==0){
		    			  	//清空原有搜索列表
		    			  	$("#search_ul").empty();
			    			var shares = result.data;
			    			for (var i=0;i<shares.length;i++){
			    				//获取分享id
			    				var shareId = shares[i].cn_share_id;
			    				//获取分享标题
			    				var sharetitle = shares[i].cn_share_title;
			    				var sli = '<li class="online">';
			    				sli += '	<a >';
			    				sli += '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
			    				sli += sharetitle;
			    				sli += '	<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
			    				sli += '	</a>';
			    				sli += '</li>';
			    				var $li = $(sli);
			    				$li.data("shareId",shareId);
			    				$("#search_ul").append($li);
			    				count++;
			    			}
			    			$("#pc_part_5").show();
			    			$("#pc_part_6").show();
			    			$("#pc_part_2").hide();
			    			$("#pc_part_3").hide()
			    			$("#pc_part_4").hide();
			    			$("#pc_part_7").hide();
			    			$("#pc_part_8").hide();
		    		  }else{
			    		  $("#search_ul").empty();
			    		  $("#search_note").val("没有搜索结果");
		    		  }
		    	  },
		    	  error:function(){
		    		  alert("搜索分享失败");
		    	  }
	      }); 
	     $("#more_note").click(function(){
	    	 $.ajax({
	    	   	  url:path+"/share/search.do",
	    	   	  type:"post",
	    	   	  data:{"shareTitle":input,"count":count},
	    	   	  dataType:"json",
	    	   	  success:function(result){
	    	   		  if (result.status==0){
	    		    			var shares = result.data;
	    		    			for (var i=0;i<shares.length;i++){
	    		    				//获取分享id
	    		    				var shareId = shares[i].cn_share_id;
	    		    				//获取分享标题
	    		    				var sharetitle = shares[i].cn_share_title;
	    		    				var sli = '<li class="online">';
	    		    				sli += '	<a >';
	    		    				sli += '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
	    		    				sli += sharetitle;
	    		    				sli += '	<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
	    		    				sli += '	</a>';
	    		    				sli += '</li>';
	    		    				var $li = $(sli);
	    		    				$li.data("shareId",shareId);
	    		    				$("#search_ul").append($li);
	    		    			}
	    		    			count += 5;
	    		    			$("#pc_part_5").show();
	    		    			$("#pc_part_6").show();
	    		    			$("#pc_part_2").hide();
	    		    			$("#pc_part_3").hide()
	    		    			$("#pc_part_4").hide();
	    		    			$("#pc_part_7").hide();
	    		    			$("#pc_part_8").hide();
	    	   		  }
	    	   	  },
	    	   	  error:function(){
	    	   		  alert("搜索分享失败");
	    	   	  }
	    	     }); 
	     });
	      
	   }
}

/***share.js封装分享和收藏笔记操作***/
//按回车执行分享笔记搜索，默认加载第一页记录
function sureSearchShare(event){
	var code = event.keyCode;
	if(code==13){//回车键
		//清空原有搜索列表
		$("#search_ul li").remove();
		//获取请求参数
		var input = $("#search_note").val().trim();
		page = 1;//按新条件检索,显示第一页记录
		//发送Ajax请求
		loadPageShare(input,page);
	}
};

function loadPageShare(input,page){
	$.ajax({
		  url:path+"/share/search.do",
		  type:"post",
		  data:{"shareTitle":input,"page":page},
		  dataType:"json",
		  success:function(result){
			  if (result.status==0){
	  			var shares = result.data;
	  			for (var i=0;i<shares.length;i++){
	  				//获取分享id
	  				var shareId = shares[i].cn_share_id;
	  				//获取分享标题
	  				var sharetitle = shares[i].cn_share_title;
	  				var sli = '<li class="online">';
	  				sli += '	<a >';
	  				sli += '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> ';
	  				sli += sharetitle;
	  				sli += '	<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
	  				sli += '	</a>';
	  				sli += '</li>';
	  				var $li = $(sli);
	  				$li.data("shareId",shareId);
	  				$("#search_ul").append($li);
	  			}
	  			
	  			$("#pc_part_5").show();
	  			$("#pc_part_6").show();
	  			$("#pc_part_2").hide();
	  			$("#pc_part_3").hide()
	  			$("#pc_part_4").hide();
	  			$("#pc_part_7").hide();
	  			$("#pc_part_8").hide();
			  }else{
		  		  //$("#search_ul").empty();
		  		  $("#search_note").val("没有搜索结果");
			  }
		  },
		  error:function(){
			  alert("搜索分享失败");
		  }
	});
}

function moreSearchShare(){
	//获取请求参数
	var input = $("#search_note").val().trim();
	page = page+1;//计算要加载的下一页页数
	//发送Ajax请求加载数据列表
	loadPageShare(input,page);
}