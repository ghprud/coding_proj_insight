cd src
echo "...compiling and running the test files....."
./run_src.sh

pwd
cd ../insight_testsuite
echo "..........running unit tests...."
./run_tests.sh
