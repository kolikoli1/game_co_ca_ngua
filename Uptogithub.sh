#! /bin/sh
find . -type f \( -name "*.java" -o -name "*.jar" -o -name "*.sh" -o -name "*.mf" \) -exec git add {} \;
git add img/
git commit -m "No message"
git push -u origin master
