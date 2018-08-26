package com.amazonaws.lambda.demo.daily_reward.http_entity;

public class DailyQuizAnsHideContent extends DailyQuizAns{

	public DailyQuizAnsHideContent() {
		super("地里发帖可以隐藏内容。假如要设置200积分以上才可以看到，下面哪个语法正确"
				,"ChangeToRegex: value=\"3\" >&nbsp;&nbsp;[hide=200]想要隐藏的内容[/hide]");
	}

	@Override
	protected String getAnsFromHtml(String question) {
		String ans = "";
		// TODO add logic heres
		
		// value="3" >&nbsp;&nbsp;[hide=200]想要隐藏的内容[/hide]
		return ans;
	}

}

//	<form action="plugin.php?id=ahome_dayquestion:pop" method="post"  id="myform" name="myform"  enctype="multipart/form-data" onsubmit="return CheckPost();">
//	<input type="hidden" name="formhash" value="0fe9da43">	
//	<div style="padding-bottom:10px;"><span style="font-size:16px;"><font color=#FF6600><b>【题目】</b>&nbsp;地里发帖可以隐藏内容。假如要设置200积分以上才可以看到，下面哪个语法正确？</font></span></div><div style="padding-left:20px;"><div class='qs_option' style='cursor:pointer;' onclick="document.getElementById('a1').checked='checked';"><input type="radio" id="a1" name="answer" value="1">&nbsp;&nbsp;[hide]想要隐藏的内容[/hide]</div></div>
//	<div style="padding-left:20px;"><div class='qs_option' style='cursor:pointer;' onclick="document.getElementById('a3').checked='checked';"><input type="radio" id="a3" name="answer" value="3" >&nbsp;&nbsp;[hide=200]想要隐藏的内容[/hide]</div></div>
//	<div style="padding-left:20px;"><div class='qs_option' style='cursor:pointer;' onclick="document.getElementById('a2').checked='checked';"><input type="radio" id="a2" name="answer" value="2">&nbsp;&nbsp;[hide=200 ]想要隐藏的内容[/hide]</div></div>
//	<div style="padding-left:20px;"><div class='qs_option' style='cursor:pointer;' onclick="document.getElementById('a4').checked='checked';"><input type="radio" id="a4" name="answer" value="4">&nbsp;&nbsp;[hide=200]想要隐藏的内容[hide]</div></div>
//	<div style="padding:15px;"><center><button type="submit" style="padding-left:10px;padding-right:10px;" onclick="validate(this);" name="submit" value="true" class="pn pnc"><strong>提交答案</strong></button></center></div>
//	</form>