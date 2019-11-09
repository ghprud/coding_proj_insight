echo "setting the environment"
export LIB_HOME=./libs
export CLASSPATH=$CLASSPATH:$LIB_HOME/junit4.10.jar:.
export PATH=$PATH:$LIB_HOME/apache-ant-1.10.5/bin/

echo $PATH
echo $CLASSPATH
echo $JAVA_HOME

ant compile
ant run

#echo "building jar"
#ant buildjar
