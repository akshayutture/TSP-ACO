
public class tspACO {
	public static void run(){
		//keep running the algorithm forever. (The 5 steps given below describe the algorithm)
		for (int i=0;;i++){
			//print some debug information
			if (Settings.DEBUG){
				System.out.println("Iteration number : " + i);
				tsp.printTour(tsp.bestSoFar);
			}
			//Step 1: initialize some values like clearing each ant's old tour and clearing the old tour cost.
			initializeTourConstruction();
			//Step 2: using the pheromone and heuristic information, make the ants construct the tour
			constructPaths();
			//Step 3: Whatever tour is constructed by step2 is improved in this step by Hill Climbing (Local Search)
			performLocalSearchImprovement();
			//Step 4: The pheromone is evaporated after each iteration
			evaporatePheromone();
			//Step 5: Let the ants deposit the pheromone.
			depositPheromone();
		}
	}
	
	//Step 1
	public static void initializeTourConstruction(){
		int i,j,randomCity;
		//looping through all the ants
		for (i=0;i<tsp.noOfAnts;i++){
			//set the visited cities value to false and tour length to zero for all the ants
			tsp.ants[i].antTour.cost=0;
			//set the visited cities value to false
			for (j=0;j<tsp.noOfCities;j++){
				tsp.ants[i].visitedCities[j]=false;
			}
			//set start points for all ants randomly on some city
			randomCity=tsp.randomGenerator.nextInt(tsp.noOfCities);
			tsp.ants[i].antTour.tour[0]=randomCity;
			tsp.ants[i].visitedCities[randomCity]=true;
		}	
		
		
		//filling in the choiceInfoMatrix
		for (i=0;i<tsp.noOfCities;i++){
			for (j=0;j<tsp.noOfCities;j++){
				tsp.choiceInfoMatrix[i][j]=Math.pow(tsp.pheromoneMatrix[i][j],Settings.ALPHA) * tsp.heuristicMatrixPowerALPHA[i][j];
			}
		}
	}
	
	//Step 2
	public static void constructPaths(){
		int stepNo,ant;
		//one step for each ant is adding one city to the tour
		for (stepNo=0;stepNo<tsp.noOfCities-1;stepNo++){
			for (ant=0;ant<tsp.noOfAnts;ant++){
				tsp.ants[ant].makeMove(stepNo);
			}
		}
		
		//for each ant - first city in tour = last city in tour
		for (ant=0;ant<tsp.noOfAnts;ant++){
			tsp.ants[ant].antTour.tour[tsp.noOfCities]=tsp.ants[ant].antTour.tour[0];
		}
	}
	
	//Step 3
	public static void performLocalSearchImprovement(){
		//for each ant - take it to its local maximum by local search improvement
		for (int ant=0;ant<tsp.noOfAnts;ant++){
			tsp.ants[ant].antTour=Greedy.greedyTour(tsp.ants[ant].antTour);
		}		
	}

	//Step 4
	//evaporate the pheromone on each edge to (1-RHO) times the previous pheromone level
	public static void evaporatePheromone(){
		for (int i=0;i<tsp.noOfCities;i++){
			for (int j=0;j<tsp.noOfCities;j++){
				tsp.pheromoneMatrix[i][j]*=(1-Settings.RHO);
			}
		}
	}
	
	//Step 5
	public static void depositPheromone(){
		int index1,index2;
		double pheromoneToBeAdded=0;
		//let all the ants deposit their pheromone
		for (int ant=0;ant<tsp.noOfAnts;ant++){
			//calculate the pheromone to be added by that ant
			tsp.ants[ant].antTour.cost=tsp.ants[ant].antTour.calculateCost();
			pheromoneToBeAdded=1/tsp.ants[ant].antTour.cost;
			//if it is the best tour so far - assign it so. Also print it
			if (tsp.ants[ant].antTour.cost<tsp.bestSoFar.cost){
				updateBest(ant);
			}
			
			//update all the edges in the path
			for (int i=0;i<tsp.noOfCities;i++){
				index1=tsp.ants[ant].antTour.tour[i];
				index2=tsp.ants[ant].antTour.tour[i+1];
				tsp.pheromoneMatrix[index1][index2]+=pheromoneToBeAdded;
				tsp.pheromoneMatrix[index2][index1]+=pheromoneToBeAdded; //due to symmetery of graph
			}
		}
		
		//Update pheromone for the best ant so far.
		
		//this is the pheromone to be added for this special ant
		pheromoneToBeAdded=tsp.eParameter/tsp.bestSoFar.cost;
		
		for (int i=0;i<tsp.noOfCities;i++){
			index1=tsp.bestSoFar.tour[i];
			index2=tsp.bestSoFar.tour[i+1];
			tsp.pheromoneMatrix[index1][index2]+=pheromoneToBeAdded;
			tsp.pheromoneMatrix[index2][index1]+=pheromoneToBeAdded; //due to symmetery of graph
		}
	}
	
	//used to update the best tour so far
	public static void updateBest(int ant){
		tsp.bestSoFar.cost=tsp.ants[ant].antTour.cost;
		for (int i=0;i<=tsp.noOfCities;i++){
			tsp.bestSoFar.tour[i]=tsp.ants[ant].antTour.tour[i];
		}
		if (Settings.DEBUG){
			tsp.printTour(tsp.bestSoFar);
		}
		else{
			tsp.printFormattedTour(tsp.bestSoFar);
		}
	}
}
