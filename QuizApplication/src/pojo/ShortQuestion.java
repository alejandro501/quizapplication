package pojo;

import i.pojo.Question;

public class ShortQuestion implements Question {
	private String question;
	
	public ShortQuestion(String question) {
		super();
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}
}
