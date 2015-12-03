/* 
 * REFERENCE
 * Ant Colony Optimization [BY Marco Dorigo and Thomas Stützle]
 */




import java.util.Random;
import java.util.Scanner;
/*INPUT FORMAT 
 * 1st line - n=no of cities
 * next n lines - an n x n matrix of the adjacency matrix of the city graph. (assumed to be symmetric)
 */

/*OUTPUT FORMAT
 * Keep printing your best tour so far to the standard output.
 * When the algorithm is forcefully terminated, the last output line is the best tour so far 
 * (the algorithm will go on forever unless forcefully stopped).
 */


public class tsp {
	//SHARED GLOBAL VARIABLES
	
	//e=the constant e as mentioned in the algorithm. 
	//The amount of pheromone deposited by the best so far ant is proportional to this
	public static double eParameter;
	//n=no of citites
	public static int noOfCities;
	//no of ants
	public static int noOfAnts;  
	//initial pheromone level
	public static double initialPheromoneLevel;
	//distance matrix - symmetric
	public static double [][] distanceMatrix;
	//heuristic matrix = (1/d)^ALPHA for all entries 'd' of the distance matrix
	public static double [][] heuristicMatrixPowerALPHA;
	//pheromone matrix
	public static double [][] pheromoneMatrix;
	//choiceInfo matrix
	//choiceInfo matrix[i][j]=T[i][j]^ALPHA * d[i][j]^BETA    (T-pheromone matrix;d-heuristicmatrix)
	public static double [][] choiceInfoMatrix;
	//array of ants
	public static Ant [] ants;
	//best tour so far
	public static Tour bestSoFar;
	//random number generator
	public static Random randomGenerator;
	
	//JUST INITIALIZES VARIABLES AND CALLS THE ACO ALGORITHM
	public static void main (String[] args){
		Scanner sc=new Scanner(System.in);
		int i,j;
		randomGenerator=new Random();//initialize the random generator
		
		
		
		//TAKING IN INPUT

		//take in the input of no of cities
		noOfCities=sc.nextInt();

		//filling in the distance matrix	
		distanceMatrix=new double[noOfCities][noOfCities];
		for (i=0;i<noOfCities;i++){
			for (j=0;j<noOfCities;j++){
				distanceMatrix[i][j]=sc.nextDouble();
			}
		}
		sc.close();
		//END OF TAKING INPUT
		
		
		
		//SETTING AND COMPUTING SOME VALUES BEFORE WE START THE ALGORITHM
		//eg. pheromone matrix,heuristic matrix,noOfAnts,etc.
		
		//is the suggested value for noOfAnts and eParameter
		noOfAnts=noOfCities;
		eParameter=(double)noOfCities;
		
		//setup heuristic matrix
		heuristicMatrixPowerALPHA=new double[noOfCities][noOfCities];
		for (i=0;i<noOfCities;i++){
			for (j=0;j<noOfCities;j++){
				if (i!=j){
					heuristicMatrixPowerALPHA[i][j]=Math.pow(1/distanceMatrix[i][j], Settings.ALPHA);
				}
				else{
					heuristicMatrixPowerALPHA[i][j]=0;
				}
			}
		}
		
		//set the best tour so far to some random initial value initial value
		bestSoFar = RandomTour.getRandomTour(noOfCities);
		if (Settings.DEBUG){
			printTour(bestSoFar);
			System.out.println("Random Tour cost = "+bestSoFar.cost);
		}
		//getTourCost from a greedy algorithm. It is used to set the initial pheromone levels.
		bestSoFar = Greedy.greedyTour(bestSoFar);
		if (Settings.DEBUG){
			printTour(bestSoFar);
			System.out.println("After greedy Tour cost = "+bestSoFar.cost);
		}
		
		//set the initial pheromone level based on the best greedy tour value found.
		//formula used is T0=(e+m)/(RHO*c) 
		//where T0=initial pheromone level. m=noOfAnts. RHO =parameter in Settings.java; c= cost of greedy path;e=eParameter
		initialPheromoneLevel=(eParameter+noOfAnts)/(Settings.RHO*bestSoFar.cost);
		//fill the pheromone matrix with this initial pheromone level
		pheromoneMatrix=new double[noOfCities][noOfCities];
		for (i=0;i<noOfCities;i++){
			for (j=0;j<noOfCities;j++){
				if (i!=j){
					pheromoneMatrix[i][j]=initialPheromoneLevel;
				}
				else{
					pheromoneMatrix[i][j]=0;
				}
			}
		}
		
		//initializing the ants and choiceInfo arrays
		choiceInfoMatrix=new double [noOfCities][noOfCities];
		ants=new Ant [noOfAnts];
		for (i=0;i<noOfAnts;i++){
			ants[i]=new Ant(noOfCities);
		}
		
		//END OF SETTING AND COMPUTING SOME VALUES BEFORE WE START THE ALGORITHM
		
		
		//print the best answer found so far and start the ACO algorithm
		printFormattedTour(bestSoFar);
		tspACO.run();
	}
	
	public static void printTour (Tour t){
		printFormattedTour(t);
		System.out.println("Tour cost = " + t.cost);
	}
	
	public static void printFormattedTour (Tour t){
		for (int i=0;i<tsp.noOfCities;i++){
			System.out.print((t.tour[i]+1)+" ");
		}
		System.out.println();
	}
	
}
