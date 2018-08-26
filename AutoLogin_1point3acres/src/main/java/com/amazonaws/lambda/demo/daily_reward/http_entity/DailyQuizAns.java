package com.amazonaws.lambda.demo.daily_reward.http_entity;

public abstract class DailyQuizAns {
	private String questionKeyWord = "";
	private String answerKeyWord = "";
	
	public DailyQuizAns(String questionKeyWord, String answerKeyWord) {
		super();
		this.questionKeyWord = questionKeyWord;
		this.answerKeyWord = answerKeyWord;
	}

	private boolean match(String question) {
		return question.contains(this.questionKeyWord);
	}
	
	public String getAns(String question) {
		String ans = "";
		if (this.match(question)) {
			ans = this.getAnsFromHtml(question);
		}
		return ans;
	}
	
	abstract protected String getAnsFromHtml(String question);
	
}
