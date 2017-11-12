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

public class FireFighterCrew {
	private int ID;
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

}
