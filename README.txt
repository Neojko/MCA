INTRODUCTION :

You can find here two implementations of algorithms solving the Maximum Colorful Arborescence (MCA) problem relatively to two parameters : c and in2 (n^*_h in the literature). The first one is an algorithm from the literature and the second one is an algorithm which was created with Guillaume Fertin and Christian Komusiewicz (see refs below).

References :
- https://hal.archives-ouvertes.fr/hal-01524069 (Introduction of MCA)
- https://arxiv.org/pdf/1710.07584.pdf (in2 algorithm)

Download mca.jar and the scripts you want. A few data is given in case you don't have some.


/*************************************************************/

INSTANCE FILES SHAPE :

1) Your instance must be a MCA instance.

2) Shape of instance :

- lign 1 : name_molecule n m rest_is_not_considerered

- node_lign (one per node) : node_number random_color
=> node_number starts at 0 and ends at n-1
=> a node u has the same color than last entered node v if random_color is the same for u and v.
=> Any pair of nodes which share the same color must be written consecutively.

- arc_lign : node1 node2 weight
=> builds an arc from node1 to node2
=> order : do it for each node2 from 1 to n-1 (if such node2 has inneighbors). For each such node2, do it for each node1 from 0 to n-2 (if there exists an arc from node1 to node2).


/*************************************************************/

HOW TO USE SCRIPTS :

1) Run one time for a file :

bash run_mca p1 p2 p3 p4

p1 : instance file
p2 : file for writing results (can be created)
p3 : Choose the processed algorithm : c or in2
p4 : time limit in seconds



2) Run the two algorithms in a folder :

In case no file is already done :
bash run_folder_start_zero p1 p2 p3

In case X files are already done :
bash run_folder_start_last p1 p2 p3 X

p1 : main folder containing instance files
p2 : main folder where results will be written
p3 : timeout per algorithm



3) Run the two algorithms for all folders in parallel :

In case no file is already done :
bash para_all_folders_start_zero p1 p2 p3 p4

In case X files are already done :
bash para_all_folders_start_last p1 p2 p3 p4

p1 : main folder of folders of instances
p2 : main folder where results will be written
p3 : timeout per algorithm
p4 : timeout per folder of instances


/*************************************************************/

OUTPUT (per instance) :
name_graph name_molecule -- n m c in2 s - weight_c time_c weight_in2 time_in2

with :
- n : number of vertices in the graph
- m : number of arcs in the graph
- c : number of colors in the graph
- in2 : number of colors of indegree at least 2 in color hierarchy graph (see reference 2)
- s : minimum number of arcs which need to be removed in order to make the color hierarchy graph a tree (see reference 1).
- weight_c/weight_in2 : weight of solution found by c/in2 algorithm. In case no solution is found within the time limit (which will be time_c/time_in2 in such a case) the weight is equal to -1, otherwise the weight is positive.
- time_c/time_in2 : computation time.









