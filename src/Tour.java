import java.util.ArrayList;


public class Tour{
	double cost;
	int [] tour;
	
	public Tour (int n){
		tour = new int[n+1];
		cost=0;			
	}
	
	//reverses the sub tour from i to j (i j both included)
	public Tour reverseSubTour (int i,int j){
		int k,n=tsp.noOfCities;
		Tour t = new Tour (n);
		
		//copy the part from 0 to i-1 as it is
		for (k=0;k<i;k++){
			t.tour[k]=tour[k];
		}
		//reverse the part from i to j
		for (k=i;k<=j;k++){
			t.tour[i+j-k]=tour[k];
		}
		//copy the remaining part as it is
		for (k=j+1;k<n+1;k++){
			t.tour[k]=tour[k];
		}
		return t;
			
	}
	
	public double calculateCost(){
		int i;
		double cost=0;
		for (i=0;i<tsp.noOfCities;i++){
			cost+=tsp.distanceMatrix[tour[i]][tour[i+1]];
		}
		return cost;
	}
}
