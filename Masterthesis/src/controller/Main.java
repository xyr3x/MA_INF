/** Author: Moritz Wiemker
 * 	Masterthesis
 * 	This class is the mainclass of the project
 *
 *
 */

package controller;

import java.util.Random;

import model.*;

public class Main {
	public static int FighterID = 0;
	public static int CrewID = 0;
	public static int GridLength = 30;
	public static int GridSize = GridLength * GridLength;
	public static int TimeInterval = 15;
	public static Random rnd = new Random(1337);

	public static int CrewSize = 10;
	public static int PopulationSize = 100;
	public static int RecombinationSize = PopulationSize / 2;
	public static int MutationProbability = 15;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EvolutionaryAlgo evAlgo = new EvolutionaryAlgo();
		FireFighterCrew crew = new FireFighterCrew();

		evAlgo.evAlgo();

		for (int i = 0; i < PopulationSize; i++) {
			System.out.println("Crew: " + i + "; Fitness: " + evAlgo.getPopulation().get(i).getFitness());
		}

		// Maximum speichern
		crew = evAlgo.getPopulation().get(0);

		System.out.println();
		System.out.println();

		System.out.println("Beste Crew __ EndSetup: ");
		for (int i = 0; i < Main.CrewSize; i++) {
			System.out.print(
					"Fighter " + i + ": " + "StartVertice: " + crew.getCrew().get(i).getStartVertice() + " ; CurrentVertice: " + crew.getCrew().get(i).getCurrentVertice() + " ; Chain: ");
			for (int j = 0; j < TimeInterval; j++) {
				System.out.print(crew.getCrew().get(i).getChainIndex(j) + ";");
			}
			System.out.println("\n");
		}

		int[] temp = new int[Main.CrewSize];
		temp = crew.getBestSetup();
		System.out.println("Bestes Setup: ");
		for (int i = 0; i < Main.CrewSize; i++) {

			System.out.print(
					"Fighter " + i + ": " + "StartVertice: " + crew.getCrew().get(i).getStartVertice() + " ; BestVertice: " + temp[i] + " ; Chain: ");
			for (int j = 0; j < TimeInterval; j++) {
				System.out.print(crew.getCrew().get(i).getChainIndex(j) + ";");
			}
			System.out.println("\n");
		}

	}

}
