package repository;

import java.util.ArrayList;
import java.util.List;

import i.pojo.Question;
import i.repository.QuestionRepository;
import pojo.ShortQuestion;

public class ShortAnswerQRepo implements QuestionRepository {
	private List<ShortQuestion> questions;

	public ShortAnswerQRepo() {
		super();
		this.questions = new ArrayList<>();
		populate();
	}
	
	private void populate(){
		
	}

	@Override
	public List<ShortQuestion> getQuestions() {
		return questions;
	}
	
	@Override
	public void uploadQuestionFromUser(){
		
	}

	@Override
	public void add(Question question) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Question question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void listAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Question> getRandomQuestion(int num) {
		// TODO Auto-generated method stub
		return null;
	}
}
