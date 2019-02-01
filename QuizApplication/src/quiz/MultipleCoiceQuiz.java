package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import pojo.MultipleChoiceAnswer;
import pojo.MultipleChoiceQuestion;
import repository.MultipleChoiceQRepo;


//TODO: Handle plurals and singulars in console output
public class MultipleCoiceQuiz {
	MultipleChoiceQRepo qRepo;
	Scanner scan;
	private int numberOfRounds;
	private List<Integer> scores;
	private List<Integer> questions;
	private List<Double> percent;
	private Map<MultipleChoiceQuestion, int[]> failedQuestions;

	public MultipleCoiceQuiz() {
		super();
		qRepo = new MultipleChoiceQRepo();
		scan = new Scanner(System.in);
		numberOfRounds = 0;
		scores = new  ArrayList<>();
		questions = new ArrayList<>();
		percent = new ArrayList<>();
		failedQuestions = new HashMap<>();
		loadGame();
	}

	private void loadGame() {
		boolean game = true;
		System.out.println("Welcome to the game!");

		while (game) {
			numberOfRounds++;
			System.out.println("How many questions do you want to have?");
			// TODO: Handle if the user wants more questions than in the repo
			int numberOfQuestions = scan.nextInt();
			questions.add(numberOfQuestions);
			List<MultipleChoiceQuestion> questions = qRepo.getRandomQuestions(numberOfQuestions);

			int score = 0;

			System.out.println("Print the numbers of the right answers separated by comma.");
			// TODO: Handle incorrect user input format

			for (int i = 0; i < questions.size(); i++) {
				List<MultipleChoiceAnswer> answers = questions.get(i).getAnswers();
				System.out.println((i + 1) + ") " + questions.get(i).getQuestion());

				for (int j = 0; j < answers.size(); j++) {
					System.out.println((j + 1) + ". " + questions.get(i).getAnswers().get(j).getAnswer());
				}

				int[] userAnswers = Pattern.compile(",").splitAsStream(scan.next()).mapToInt(Integer::parseInt)
						.toArray();

				int[] rightAnswers = IntStream.range(0, answers.size())
						.filter(answerIndex -> answers.get(answerIndex).isTrue()).map(num -> num + 1).toArray();

				Arrays.sort(userAnswers);
				if (Arrays.equals(userAnswers, rightAnswers)) {
					System.out.println("You got it right!");
					score++;
				} else {
					failedQuestions.put(questions.get(i), userAnswers);
					System.out.println("Wrong answer.");
				}

				System.out.println("Score: " + score);
				scores.add(score);
			}
			double pct = (double) score / questions.size() * 100;
			percent.add(pct);
			System.out.println("Your percent: " + String.format("%.2f", pct) + "%");

			if (!failedQuestions.isEmpty())
				failedQuestions();

			System.out.println("\nDo you want to have another round? y/n");
			if (!scan.next().toLowerCase().trim().equals("y"))
				game = false;
		}

		evaluate();
		exit();
		scan.close();
	}

	private void failedQuestions() {
		System.out.println("You failed the following questions: \n");

		for (MultipleChoiceQuestion k : failedQuestions.keySet()) {
			int[] values = failedQuestions.get(k);
			System.out.println(k.getQuestion());
			System.out.println(" Your answers: ");
			for (int i = 0; i < values.length; i++) {
				System.out.println("- " + k.getAnswers().get(values[i] - 1).getAnswer());
			}
			System.out.println("\n Right answers: ");
			for (int i = 0; i < k.getAnswers().size(); i++) {
				if (k.getAnswers().get(i).isTrue())
					System.out.println("- " + k.getAnswers().get(i).getAnswer());
			}
			System.out.println();
		}

	}

	private void evaluate() {
		double averagePercent = percent.stream().mapToDouble(i -> i).average().getAsDouble();
		int allQuestions = questions.stream().mapToInt(i -> i).sum();
		int allScores = scores.stream().mapToInt(i -> i).sum();
		System.out.println("Thank you for playing! You played " + numberOfRounds
				+ " rounds with the average percent of " + String.format("%.1f", averagePercent) + ". You had  "
				+ allScores + " of " + allQuestions + " right.!");
	}

	private void exit() {
		System.out.println("Press 'e' to exit application!");
		if (scan.next().equals("e")) {
			System.out.println("Good bye!");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
}
