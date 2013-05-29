package org.toilelibre.libe.ratrap.client.main;

import org.toilelibre.libe.ratrap.client.build.SearchView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WindowRATraP {

	public static void init(final SearchView searchView) {
		final DialogBox dialogBox = new DialogBox();

		dialogBox.setAnimationEnabled(true);

		dialogBox.setAutoHideEnabled(true);
		dialogBox.setGlassEnabled(true);
		final Label bienvenue = new Label("Bienvenue");
		final Label cliquer = new Label("Cliquer 'ici' pour lancer l'ihm");
		final Button sendButton = new Button("'ici'");
		final VerticalPanel vp = new VerticalPanel();
		vp.add(bienvenue);
		vp.add(cliquer);
		vp.add(sendButton);
		dialogBox.add(vp);

		RootPanel.get().add(dialogBox);
		RootPanel.get().add(searchView);

		sendButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				dialogBox.removeFromParent();
			}

		});

		searchView.getRatrapDist().addClickHandler(
				new LaunchSearchClickHandler("dist", searchView.getOriDist(),
						searchView.getDestDist(), searchView.getResultDist()));

		searchView.getRatrapCorresp().addClickHandler(
				new LaunchSearchClickHandler("corresp", searchView
						.getOriCorresp(), searchView.getDestCorresp(),
						searchView.getResultCorresp()));

		searchView.getRatrapLong().addClickHandler(
				new LaunchSearchClickHandler("long", searchView.getOriLong(),
						searchView.getDestLong(), searchView.getResultLong()));

		searchView.getRatrapLent().addClickHandler(
				new LaunchSearchClickHandler("lent", searchView.getOriLent(),
						searchView.getDestLent(), searchView.getResultLent()));
	}
}
