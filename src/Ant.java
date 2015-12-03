
public class Ant {
	
	//each ant has the following 2 associated attributes
	Tour antTour;
	public boolean [] visitedCities;
	
	//creates a new ant (constructor)
	public Ant(int n){
		antTour=new Tour(n);
		visitedCities=new boolean [n];
	}
	
	//lets the ant make one move (from 1 city to another). 
	public void makeMove(int stepNo){
		//stepNo is the number of steps alreadly made + 1 (i.e. the current step no)
		double totalWeight=0;
		int currentCity=antTour.tour[stepNo],i;
		double [] selectionWeights=new double [tsp.noOfCities];
		
		//setting the selectionWeights 
		//(or selection probabilites, but their probabilites are not b/w 0 and 1. instead they are relative probabilites.)
		//(we will normalize the probabilites later)
		for (i=0;i<tsp.noOfCities;i++){
			//if the city is visited, the probability of visiting it is 0.
			if (visitedCities[i]){
				selectionWeights[i]=0;
			}
			//else set the probability according to the choiceInfoMatrix computed at the start of the tour
			else{
				selectionWeights[i]=tsp.choiceInfoMatrix[currentCity][i];
				totalWeight+=selectionWeights[i];
			}
		}
		
		/*
		 * selecting one of the cities probabilistically
		 * It is like a coloured disc, where the fraction of the disc coloured by one city is proportional to 
		 * the selectionWeight calculated. Then we place a pointer randomly on the disc, and wherever it stops 
		 * - we pick that city. (only difference here is that instead of a circle we are using a length [0..totalWeight]
		 */
		
		//randomNo is a random number from [0..totalWeight]
		double randomNo=(tsp.randomGenerator.nextDouble())*totalWeight;
		double w=0;
		for (i=0;;i++){
			w+=selectionWeights[i];
			if (w>=randomNo){
				break;
			}
		}
		
		//i is now the selected next city to go to.
		//set i as visited and tour[currentCity+1] as i
		visitedCities[i]=true;
		antTour.tour[stepNo+1]=i;
		
		//also update the tourCost =>will be done at a later stage. THis is causing some problems
		//antTour.cost+=tsp.distanceMatrix[currentCity][i];
	}
}
