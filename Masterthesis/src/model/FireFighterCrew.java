/** Author: Moritz Wiemker
 * 	Masterthesis
 *
 *
 *
 */


package model;

import java.util.ArrayList;
import java.util.List;

import controller.Main;

public class FireFighterCrew implements Comparable<FireFighterCrew> {
	private int ID;
	//number of non burning vertices
	private int Fitness = 0;
	private List<FireFighter> crew = new ArrayList<FireFighter>();

	//constructor
	public FireFighterCrew(){
		this.ID = Main.CrewID;
		Main.CrewID++;
	}

	//getter and setter
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getFitness() {
		return Fitness;
	}
	public void setFitness(int fitness) {
		Fitness = fitness;
	}
	public List<FireFighter> getCrew() {
		return crew;
	}
	public void setCrew(List<FireFighter> crew) {
		this.crew = crew;
	}

	@Override
	public int compareTo(FireFighterCrew arg0) {
		if(Fitness < arg0.getFitness()){
			return 1;
		}

		if(Fitness > arg0.getFitness()){
			return -1;
		}

		return 0;
	}

}
