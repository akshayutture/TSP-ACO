# TSP-ACO
Ant Colony Optimization algorithm (Elitist Ant System (EAS) algorithm) for the Travelling Salesman Problem

REFERENCE FOR ALGORITHM : Ant Colony Optimization [BY Marco Dorigo and Thomas Stützle]

HOW TO USE
1. Import the project into Eclipse and run. Or you can compile the source files using the Javac compiler.
2. Supply the number of cities and the distance matrix and the program prints the best tour found so far.
3. A sample test case has been provided.


/*INPUT FORMAT 
 * 1st line - n=no of cities
 * next n lines - an n x n matrix of the adjacency matrix of the city graph. (assumed to be symmetric)
 */

/*OUTPUT FORMAT
 * Keep printing your best tour so far to the standard output.
 * When the algorithm is forcefully terminated, the last output line is the best tour so far 
 * (the algorithm will go on forever unless forcefully stopped).
 */

STRUCTURE OF THE PROJECT

1. tsp.java : the file  where the main() function is written. It contains most of the shared global variables declarations. It also does a lot of initializations for these variables. It creates a random tour and then using a greedy local search takes the tour to its local optimum. Then it calls tspACO.java to run the TSP

2. RandomTour.java : has a function getRandomTour() which is used to return any random tour.

3. Greedy.java : has a function greedyTour(tour n) which takes a tour 'n' and using a greedy local search takes the tour to its local optimum.

4. Settings.java : just contains some parameters needed for the algorithm. Good values for these parameters have been taken from the reference book


5. Tour.java : has the 'Tour' class with some supplementary functions.

6. Ant.java : has the 'Ant' class with some supplementary functions.

7. tspACO.java : has the ACO algorithm. Its function run() is called by the main() function in tsp.java.
