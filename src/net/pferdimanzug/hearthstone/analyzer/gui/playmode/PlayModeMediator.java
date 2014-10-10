package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanMulliganOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeMediator";

	private final PlayModeView view;
	private final GameBoardView boardView;
	private final HumanActionPromptView actionPromptView;

	public PlayModeMediator() {
		super(NAME);
		view = new PlayModeView();
		boardView = view.getBoardView();
		actionPromptView = view.getActionPromptView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case GAME_STATE_UPDATE:
			GameContext context = (GameContext) notification.getBody();
			Platform.runLater(() -> boardView.updateGameState(context));
			break;
		case GAME_ACTION_PERFORMED:
			// view.updateTurnLog((GameContext) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			HumanActionOptions actionOptions = (HumanActionOptions) notification.getBody();
			Platform.runLater(() -> actionPromptView.setActions(actionOptions));
			break;
		case HUMAN_PROMPT_FOR_TARGET:
			HumanTargetOptions options = (HumanTargetOptions) notification.getBody();
			Platform.runLater(() -> boardView.enableTargetSelection(options));
			break;
		case HUMAN_PROMPT_FOR_MULLIGAN:
			HumanMulliganOptions mulliganOptions = (HumanMulliganOptions) notification.getBody();
			Platform.runLater(() -> new HumanMulliganView(mulliganOptions));
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.GAME_ACTION_PERFORMED);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_MULLIGAN);
		notificationInterests.add(GameNotification.ANSWER_DECKS);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
	}

}
