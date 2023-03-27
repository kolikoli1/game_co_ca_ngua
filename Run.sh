#! /bin/sh
find . -type f -name "*.java" -exec javac -classpath /home/microsoft/Dropbox/Java_Code/ -d /home/microsoft/Dropbox/Java_Code/HorseSea/ {} \;
cp -r img/. HorseSea/
jar -cvmf HorseSea.mf HorseSea.jar HorseSea/
chmod +x HorseSea.jar
rm -rf HorseSea/
