/** Author: Moritz Wiemker
 * 	Masterthesis
 *
 *
 * Ablauf Evolutionärer Algorithmus
 *	1. Initialisierung: Die erste Generation von Lösungskandidaten wird (meist zufällig) erzeugt.
 *	2. Evaluation: Jedem Lösungskandidaten der Generation wird entsprechend seiner Güte ein Wert der Fitnessfunktion zugewiesen.
 *	3. Durchlaufe die folgenden Schritte, bis ein Abbruchkriterium erfüllt ist:
 *		3.1. Selektion: Auswahl von Individuen für die Rekombination
 *		3.2. Rekombination: Kombination der ausgewählten Individuen
 *		3.3. Mutation: Zufällige Veränderung der Nachfahren
 *		3.4. Evaluation: Jedem Lösungskandidaten der Generation wird entsprechend seiner Güte ein Wert der Fitnessfunktion zugewiesen.
 *		3.5. Selektion: Bestimmung einer neuen Generation
 *
 *
 */

package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.*;

public class EvolutionaryAlgo {
	private List<FireFighterCrew> population = new ArrayList<FireFighterCrew>();
	private boolean fighterAtBorder = false;
	// vertices that do not burn
	private List<Integer> nonBurningVertices = new ArrayList<>();
	// Vertices of the last timestep
	private List<Integer> latestVertices = new ArrayList<>();
	// defended vertices
	private List<Integer> defendedVertices = new ArrayList<>();

	private int maxFitness = 0;
	private int optimum = Main.CrewSize + 5;

	// constructor
	public EvolutionaryAlgo() {

	}

	private void evAlgo() {
		// stuff

		// 1. Initialisierung
		initialize();

		// 2. Evaluation
		for (int i = 0; i < population.size(); i++) {
			calculateFitness(population.get(i));
			if (population.get(i).getFitness() > maxFitness) {
				maxFitness = population.get(i).getFitness();
			}
		}

		// 3. Schleife
		while (maxFitness < optimum) {

			// 3.1 Selektion
			Collections.sort(population);
			for (int i = 0; i < Main.RecombinationSize; i++) {
				// von hinten Elemente rauswerfen, um Indexshift zu vermeiden
				population.remove(Main.PopulationSize - 1);
			}

			// 3.2. Rekombination
			for (int i = 0; i < Main.RecombinationSize; i++) {
				int parent1 = Main.rnd.nextInt(Main.PopulationSize - Main.RecombinationSize);
				int parent2 = Main.rnd.nextInt(Main.PopulationSize - Main.RecombinationSize);
				int crossOver = Main.rnd.nextInt(Main.TimeInterval);

				FireFighterCrew crew = new FireFighterCrew();

				// recombine parentcrew 1 and parentcrew 2 s.t. every kth
				// new fighter is one point crossover of the kth fighter of the
				// parent crews
				for (int j = 0; j < Main.CrewSize; j++) {
					int chain[] = new int[Main.TimeInterval];
					FireFighter fighter = new FireFighter();
					//set start vertice
					fighter.setStartVertice(population.get(parent1).getCrew().get(j).getStartVertice());
					//set chain
					for (int k = 0; k < crossOver; k++) {
						chain[k] = population.get(parent1).getCrew().get(j).getChainIndex(k);
					}
					for (int k = crossOver; k < Main.TimeInterval; k++) {
						chain[k] = population.get(parent2).getCrew().get(j).getChainIndex(k);
					}

					crew.getCrew().add(fighter);
				}

				population.add(crew);

			}

			//3.3 Mutation
			if(Main.rnd.nextInt(100) < Main.MutationProbability){

				//numbers??
				int numberOfCrews = Main.rnd.nextInt(15);
				int numberOfFighters = Main.rnd.nextInt(15);
				int numberOfBitflips = Main.rnd.nextInt(15);

				for(int i = 0; i < numberOfCrews; i++){
					for(int j = 0; j < numberOfFighters; j++){
						for(int k = 0; k < numberOfBitflips; k++){
							population.get(i).getCrew().get(j).setChainIndex(Main.rnd.nextInt(Main.TimeInterval), Main.rnd.nextInt(5));
						}
					}
				}

			}

			//3.4 Evaluation
			for (int i = 0; i < population.size(); i++) {
				calculateFitness(population.get(i));
				if (population.get(i).getFitness() > maxFitness) {
					maxFitness = population.get(i).getFitness();
				}
			}
		}

	}

	// initalisierung des Problems
	private void initialize() {
		int temp = 0;
		// intialize every individuum of the population

		for (int i = 0; i < Main.PopulationSize; i++) {
			FireFighterCrew crew = new FireFighterCrew();

			// initalize every fighter of the crew
			for (int j = 0; j < Main.CrewSize; j++) {
				FireFighter fighter = new FireFighter();

				// initialize startvertice, check if unique
				int startVertice = Main.rnd.nextInt(Main.GridSize);
				startVertice = uniqueStartVertice(startVertice, crew);

				fighter.setStartVertice(startVertice);
				fighter.setCurrentVertice(startVertice);

				// initialize Chain
				int[] chain = new int[Main.TimeInterval];
				for (int k = 0; k < Main.TimeInterval; k++) {
					chain[k] = Main.rnd.nextInt(5);
				}

				fighter.setChain(chain);
				crew.getCrew().add(fighter);
			}

			crew.setFitness(Main.CrewSize);
			population.add(crew);

		}
	}

	private void calculateFitness(FireFighterCrew crew) {
		// move fighters (switch case unterscheidung), expand fire
		int tempDirection, currentVertice;
		// for every time step
		for (int i = 0; i < Main.TimeInterval; i++) {

			// move every fighter
			// TODO: Randfälle????
			for (int j = 0; j < Main.CrewSize; j++) {
				currentVertice = crew.getCrew().get(j).getCurrentVertice();
				tempDirection = crew.getCrew().get(j).getChainIndex(i);

				// Randfälle, bleibe stehenn wenn Grid zu Ende
				// Ecken: 0; GridLength; GridLength^2 - (GridLength);
				// GridLength^2 - 1
				if (currentVertice == 0) {
					if (tempDirection == 3 || tempDirection == 4) {
						fighterAtBorder = true;
						continue;
					}
				}

				if (currentVertice == Main.GridLength) {
					if (tempDirection == 2 || tempDirection == 3) {
						fighterAtBorder = true;
						continue;
					}
				}

				if (currentVertice == (Main.GridSize - Main.GridLength)) {
					if (tempDirection == 1 || tempDirection == 4) {
						fighterAtBorder = true;
						continue;
					}
				}

				if (currentVertice == (Main.GridSize - 1)) {
					if (tempDirection == 1 || tempDirection == 2) {
						fighterAtBorder = true;
						continue;
					}
				}

				// Rand des Grids
				// unten
				if (currentVertice < Main.GridLength) {
					if (tempDirection == 3) {
						fighterAtBorder = true;
						continue;
					}
				}

				// oben
				if (currentVertice > (Main.GridSize - Main.GridLength)) {
					if (tempDirection == 1) {
						fighterAtBorder = true;
						continue;
					}
				}

				// links
				if ((currentVertice % Main.GridLength) == 0) {
					if (tempDirection == 4) {
						fighterAtBorder = true;
						continue;
					}
				}

				// rechts
				if ((currentVertice % Main.GridLength) == (Main.GridLength - 1)) {
					if (tempDirection == 2) {
						fighterAtBorder = true;
						continue;
					}
				}

				switch (tempDirection) {
				// dont move
				case 0:
					defendedVertices.add(crew.getCrew().get(j).getCurrentVertice());
					break;
				// go north
				case 1:
					crew.getCrew().get(j).setCurrentVertice(currentVertice + Main.GridSize);
					crew.setFitness(crew.getFitness() + 1);
					nonBurningVertices.add(currentVertice);
					latestVertices.add(currentVertice);
					defendedVertices.add(crew.getCrew().get(j).getCurrentVertice());

					break;
				// go east
				case 2:
					crew.getCrew().get(j).setCurrentVertice(currentVertice + 1);
					crew.setFitness(crew.getFitness() + 1);
					nonBurningVertices.add(currentVertice);
					latestVertices.add(currentVertice);
					defendedVertices.add(crew.getCrew().get(j).getCurrentVertice());
					break;
				// go south
				case 3:
					crew.getCrew().get(j).setCurrentVertice(currentVertice - Main.GridSize);
					crew.setFitness(crew.getFitness() + 1);
					nonBurningVertices.add(currentVertice);
					latestVertices.add(currentVertice);
					defendedVertices.add(crew.getCrew().get(j).getCurrentVertice());
					break;
				// go west
				case 4:
					crew.getCrew().get(j).setCurrentVertice(currentVertice - 1);
					crew.setFitness(crew.getFitness() + 1);
					nonBurningVertices.add(currentVertice);
					latestVertices.add(currentVertice);
					defendedVertices.add(crew.getCrew().get(j).getCurrentVertice());
					break;

				}
			}

			// expand fire

			for (int k = 0; k < latestVertices.size(); k++) {

				// Randfälle! verlassener Knoten liegt am Rand/Ecke
				if (latestVertices.get(k).intValue() == 0) {
					// only check upper and right vertice
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

				}

				if (latestVertices.get(k).intValue() == Main.GridLength) {
					// only check upper and left vertice
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				if (latestVertices.get(k).intValue() == (Main.GridSize - Main.GridLength)) {
					// only check lower and right vertice
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				if (latestVertices.get(k).intValue() == (Main.GridSize - 1)) {
					// only check lower and left vertice
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				// Rand des Grids
				// unten
				if (latestVertices.get(k).intValue() < Main.GridLength) {
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				// oben
				if (latestVertices.get(k).intValue() > (Main.GridSize - Main.GridLength)) {
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}

					}
				}

				// links
				if ((latestVertices.get(k).intValue() % Main.GridLength) == 0) {
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				// rechts
				if ((latestVertices.get(k).intValue() % Main.GridLength) == (Main.GridLength - 1)) {
					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}

					if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
						if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
							nonBurningVertices.remove(latestVertices.get(k));
							crew.setFitness(crew.getFitness() - 1);
							continue;
						}
					}
				}

				// check if latestvertices has burning neighbours __ kein
				// Randfall!
				if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - 1))) {
					if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
						nonBurningVertices.remove(latestVertices.get(k));
						crew.setFitness(crew.getFitness() - 1);
						continue;
					}
				}

				if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + 1))) {
					if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
						nonBurningVertices.remove(latestVertices.get(k));
						crew.setFitness(crew.getFitness() - 1);
						continue;
					}
				}

				if (!nonBurningVertices.contains((latestVertices.get(k).intValue() + Main.GridLength))) {
					if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
						nonBurningVertices.remove(latestVertices.get(k));
						crew.setFitness(crew.getFitness() - 1);
						continue;
					}
				}

				if (!nonBurningVertices.contains((latestVertices.get(k).intValue() - Main.GridLength))) {
					if (!defendedVertices.contains((latestVertices.get(k).intValue() - 1))) {
						nonBurningVertices.remove(latestVertices.get(k));
						crew.setFitness(crew.getFitness() - 1);
						continue;
					}
				}

			}

			latestVertices.clear();
			defendedVertices.clear();
		}

	}

	// Hilfsfunktionen
	private int uniqueStartVertice(int startVertice, FireFighterCrew crew) {
		// check if startVertice equals already existing startVertice
		for (int i = 0; i < crew.getCrew().size(); i++) {
			if (startVertice == crew.getCrew().get(i).getStartVertice()) {
				startVertice = Main.rnd.nextInt(Main.GridSize);
				uniqueStartVertice(startVertice, crew);
			}
		}
		return startVertice;
	}

}
