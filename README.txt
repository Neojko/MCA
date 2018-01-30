INTRODUCTION :

Download the .jar and the scripts you want. A few data is given in case you don't have some.

Output given by the algorithms (name of file contains c or in2):
name_instance_file n m c in2 weight_sol time

=> if the solution is not solved, then weight_sol = -1 (not possible in reality)


PRECONDITIONS : 

- In every instance file, the outneighbors of each vertex are "sorted by color" : if a blue vertex is declared as an outneighbor of a vertex v, then any other blue vertex which is an outneighbor of v must be declared before declaring an outneighbor of v which is not blue. In other words, we can not declare a blue vertex, a red vertex and an other blue vertex as outneighbors of v. 
=> Guillaume / Christian : this condition is respected for all our files.


/***********************************/

WARNINGS :

- Algorithms do not handle instances with more than 30 colors


/***********************************/

HOW TO USE SCRIPTS :

1) Run one time for a file :

bash run_mca p1 p2 p3 p4

p1 : instance file
p2 : file for writing results (can be created)
p3 : Choose the processed algorithm : c or in2
p4 : time limit in seconds


2) Run one time for all files in a folder :

bash run_folder p1 p2 p3 p4

p1 : folder containing instance files
p2 : file for writing results (can be created) : it will be the same for all instances
p3 : Choose the processed algorithm : c or in2
p4 : time limit in seconds for each file run


3) Run one time for all graph folders in a folder :

bash run_all_folders p1 p2 p3 p4

p1 : folder containing folders of instance files
p2 : folder where results will be written : a different file will be created for each folder in folder p1
p3 : Choose the processed algorithm : c or in2
p4 : time limit in seconds for each file run
