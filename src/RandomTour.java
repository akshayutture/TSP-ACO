import java.util.ArrayList;


public class RandomTour {

	public static Tour getRandomTour (int n){ 
		//n = noOfCities
		Tour randomTour=new Tour(n);
		int i,citiesLeft=n,temp1,temp2;
		
		
		//creating temp - an array of nos. 1 to n
		ArrayList<Integer> temp = new ArrayList<Integer>(n);
		for (i=0;i<n;i++){
			temp.add(i);
		}
		
		for (i=0;i<n;i++){
			//remove a random city from temp and add it to randomTour
			temp1=tsp.randomGenerator.nextInt(citiesLeft);
			temp2=temp.remove(temp1);
			randomTour.tour[i]=temp2;
			citiesLeft--;
		}
		randomTour.tour[n]=randomTour.tour[0]; //start and end in the same city
		randomTour.cost+=randomTour.calculateCost();
		
		return randomTour;
	}
}
