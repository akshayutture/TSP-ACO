
//To generate a local maximum given an initial tour (in a greedy manner )
public class Greedy {
	
	//currently implemented using 2opt steepest ascent Hill Climbing
	public static Tour greedyTour(Tour initialTour){
		int n=tsp.noOfCities,i,j;
		boolean localMaxima=false;
		double newCost=Double.MAX_VALUE;
		Tour t = new Tour(n);
		for (i=0;i<n+1;i++){
			t.tour[i]=initialTour.tour[i];
		}
		t.cost=initialTour.cost;
		
		//2-opt hill climbing.
		//basically mark 2 points in the sequence and reverse that part of the sequence and check if we get a better neighbour.
		//repeat till we reach a localMaxima
		while (localMaxima==false){
			localMaxima=true;
			for (i=1;i<n;i++)
			{
				for (j=i+1;j<n;j++){
					newCost=t.cost - tsp.distanceMatrix[t.tour[i-1]][t.tour[i]] - tsp.distanceMatrix[t.tour[j]][t.tour[j+1]] + tsp.distanceMatrix[t.tour[i-1]][t.tour[j]] + tsp.distanceMatrix[t.tour[i]][t.tour[j+1]]; 
					if (newCost<t.cost){
						localMaxima=false;
						t=t.reverseSubTour(i,j);
						t.cost=newCost;
						break;
					}
				}
			}
		}

		return t;
	}
	
}
