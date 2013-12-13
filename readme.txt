Build as follows:

javac -cp lib/* -sourcepath source -d classes source/InterpreterTest.java
jar -cvmf manifest.txt java_scheme_app.jar -C classes .
java -jar java_scheme_app.jar
