# Variables
SRC_DIR := .
BIN_DIR := bin
PACKAGE := com/psociety/lox
MAIN_FILE := $(SRC_DIR)/$(PACKAGE)/Lox.java
MAIN_CLASS := com.psociety.lox.lox
JAVAC := javac
JAVA := java

# Default target
all: compile

# Compile the main file
compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(MAIN_FILE)

# Run the program
run: compile
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

# Clean up compiled files
clean:
	rm -rf $(BIN_DIR)

# Phony targets
.PHONY: all compile run clean
