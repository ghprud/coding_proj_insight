# coding_proj_insight

#approach

I used a hashmap to calculate the total number of crossings. The hashmap's key is based on Border, Date, and Measure. I used a list to copy the required data from the hashmap to an arraylist. The sorted list is used to 
calculate the running average, and the final ouput is written to a text file from the sorted list.  

# build instructions
1. ./run.sh in the root folder can be used to build the project and run the tests.
2. ant has been used to set up the project. The required binaries for ant have been checked in.
3. It is assumed that javac and java path are set. The other required dependencies are set in the run.sh.