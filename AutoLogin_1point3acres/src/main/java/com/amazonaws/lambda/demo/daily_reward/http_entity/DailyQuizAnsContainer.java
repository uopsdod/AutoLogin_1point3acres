package com.amazonaws.lambda.demo.daily_reward.http_entity;

import java.util.ArrayList;
import java.util.List;

public class DailyQuizAnsContainer {
	private List<DailyQuizAns> dailyQuizAnswers;
	
	public DailyQuizAnsContainer() {
		dailyQuizAnswers = new ArrayList<>();
		dailyQuizAnswers.add(new DailyQuizAnsHideContent());
//		dailyQuizAnswers.add(new DailyQuizHideContent());
//		dailyQuizAnswers.add(new DailyQuizHideContent());
	}

	public List<DailyQuizAns> getDailyQuizAnswers() {
		return dailyQuizAnswers;
	}

	public void setDailyQuizAnswers(List<DailyQuizAns> dailyQuizAnswers) {
		this.dailyQuizAnswers = dailyQuizAnswers;
	}
	
	
	
}
