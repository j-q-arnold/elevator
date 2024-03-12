.PHONY:		all
all:
	mkdir -p classes
	javac -sourcepath src \
		-d classes \
		$$(find src/main -name '*.java')

.PHONY:		run
run:		all
	java -cp classes jqa.elevator.Driver events.sim

.PHONY:		doc
doc:
	rm -fr doc
	mkdir -p doc
	javadoc -d doc -sourcepath src/main/java jqa.elevator

.PHONY:		clean
clean:
	rm -fr classes
