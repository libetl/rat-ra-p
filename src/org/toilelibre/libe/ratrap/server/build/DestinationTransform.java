package org.toilelibre.libe.ratrap.server.build;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DestinationTransform {

	private static final Map<String, String> transforms = new HashMap<String, String>() {
		/**
     * 
     */
		private static final long serialVersionUID = 3197729322466008243L;

		{

			this.put("Les Courtilles (Asnieres - Gennevilliers)",
					"Asnieres-Gennevilliers Les Courtilles");
			this.put("Châtillon - Montrouge", "Châtillon Montrouge");
			this.put("Saint-Denis - Universite", "Saint-Denis-Universite");
			this.put("Villejuif - Louis Aragon", "Villejuif-Louis Aragon");
			this.put("La Courneuve - 8 mai 1945", "La Courneuve-8-Mai-1945");
			this.put(
					"Marne-la-Vallée-Chessy - Boissy-St-Leger/Cergy-St-Christophe - Poissy - St-Germain-en-Laye",
					"Marne-la-Vallée Chessy/Boissy-Saint-Léger/Cergy-Saint-Christophe/Poissy/Saint-Germain-en-Laye");
			this.put(
					"St-Rémy-les-Chevreuse - Robinson/Roissy-Aéroport-Charles-de-Gaulle - Mitry-Claye",
					"Saint-Rémy-lès-Chevreuse/Robinson/Aeroport Charles de Gaulle 2 TGV/Mitry-Claye");
			this.put(
					"Chelles-Gournay - Tournan/Haussmann-St-Lazare - Gare de l'Est",
					"Chelles Gournay/Tournan/Haussmann-Saint-Lazare/Gare de l'Est");
			this.put("St-", "Saint-");
			this.put(" St ", " Saint-");
			this.put(" - ", "/");
			this.put("Orry la ville Coye", "");
			this.put("Gare de Corbeil Essonnes", "Corbeil-Essonnes");
			this.put("Porte Dauphine (Maréchal de Lattre de Ta",
					"Porte Dauphine (Maréchal de Lattre de Tassigny)");
			this.put("Boulogne - Pont de Saint Cloud",
					"Boulogne Pont de Saint-Cloud");
			this.put("Charles de Gaulle - Etoile", "Charles de Gaulle-Etoile");
			this.put("Bobigny - Pablo Picasso", "Bobigny-Pablo-Picasso");
			this.put("Saint-Martin-d'Etampes", "Saint-Martin d'Etampes");
			this.put("Gare de Lyon", "");// Empecher le
											// rer d de
											// s'arreter a
											// gare de
											// lyon
			this.put("Massy-Palaiseau", "");// Empecher le
											// rer c de
											// s'arreter a
											// massy-palaiseau
			this.put("Champ de Mars-Tour Eiffel", "");// Empecher le
														// rer c de
														// s'arreter a
														// Champ de
														// Mars
			// this.put
			// ("Gare d'Austerlitz",
			// "");//Empecher le
			// rer c de s'arreter
			// a Gare d'Austerlitz
			this.put("Gare de l'Est", "");// Empecher le
											// rer e de
											// s'arreter a
											// Gare de
											// l'Est
		}
	};

	public static final String transform(final String s) {
		String result = "";
		final Pattern p = java.util.regex.Pattern
				.compile("\\([A-Z0-9][A-Z0-9]\\)");
		final Matcher m = p.matcher(s);
		int lastResult = 0;
		while (m.find()) {
			result += s.substring(lastResult, m.start());
			lastResult = m.end();
		}
		result += s.substring(lastResult);
		final SortedSet<String> set = new TreeSet<String>(
				new Comparator<String>() {

					@Override
					public int compare(final String arg0, final String arg1) {
						if (arg0 == null) {
							return -1;
						}
						if (arg1 == null) {
							return 1;
						}
						final int result = new Integer(arg1.length())
								.compareTo(new Integer(arg0.length()));
						return result != 0 ? result : 1;
					}

				});
		set.addAll(DestinationTransform.transforms.keySet());
		for (final String key : set) {
			result = result.replace(key,
					DestinationTransform.transforms.get(key));
		}
		if (result.length() > 500) {
			result = result.replace("Gare d'Austerlitz", "");
		}
		return result;
	}
}
