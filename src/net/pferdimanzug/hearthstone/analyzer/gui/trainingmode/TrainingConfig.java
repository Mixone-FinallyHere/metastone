package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning.TDLearningBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;

public class TrainingConfig {
	
	private int numberOfGames;
	private final List<Deck> decks = new ArrayList<Deck>();
	private final TDLearningBehaviour learner;
	
	public TrainingConfig(TDLearningBehaviour learner) {
		this.learner = learner;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public List<Deck> getDecks() {
		return decks;
	}
	
	public Deck getRandomDeck() {
		return decks.get(ThreadLocalRandom.current().nextInt(decks.size()));
	}

	public TDLearningBehaviour getLearner() {
		return learner;
	}

}
