package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public interface IBehaviour extends Cloneable {

	String getName();

	List<Card> mulligan(GameContext context, Player player, List<Card> cards);

	Entity provideTargetFor(Player player, GameAction action);
	
	GameAction requestAction(GameContext context, Player player, List<GameAction> validActions);
	
	IBehaviour clone();
}
