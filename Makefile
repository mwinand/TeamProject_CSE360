main:
	javac src/Main.java -d build
	echo "java Main.java" > build/main
	chmod +x build/main
	cp -r src/* build/

clean:
	rm -rf build/*
