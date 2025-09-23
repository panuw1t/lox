.PHONY: all run test gen debug clean

BUILD = ./build
DEBUG_BUILD = ./build-debug
SRC = ./src/com/craftinginterpreters

CLASS ?= Lox

TARGET_SRC = $(SRC)/*/*.java
TARGET_SRC_GEN = $(SRC)/tool/GenerateAst.java

MAIN_CLASS = $(BUILD)/com/craftinginterpreters/lox/Lox.class
MAIN_CLASS_DEBUG = $(DEBUG_BUILD)/com/craftinginterpreters/lox/Lox.class
TARGET_GEN = $(BUILD)/com/craftinginterpreters/tool/GenerateAst.class

all: $(MAIN_CLASS)

$(MAIN_CLASS): $(TARGET_SRC)
	@mkdir -p $(BUILD)
	javac -d $(BUILD) $(TARGET_SRC)

run: $(MAIN_CLASS)
	java -cp $(BUILD) com.craftinginterpreters.lox.$(CLASS)

test: $(MAIN_CLASS) test.c
	java -cp $(BUILD) com.craftinginterpreters.lox.Lox "test.c"

gen: $(TARGET_GEN)
	@mkdir -p $(SRC)/lox
	java -cp $(BUILD) com.craftinginterpreters.tool.GenerateAst $(SRC)/lox

$(TARGET_GEN): $(TARGET_SRC_GEN)
	@mkdir -p $(BUILD)
	javac -d $(BUILD) $(TARGET_SRC_GEN)

debug: $(MAIN_CLASS_DEBUG) test.c
	jdb -classpath $(DEBUG_BUILD) com.craftinginterpreters.lox.Lox test.c

$(MAIN_CLASS_DEBUG): $(TARGET_SRC)
	@mkdir -p $(DEBUG_BUILD)
	javac -g -d $(DEBUG_BUILD) $(TARGET_SRC)

clean:
	rm -rf $(BUILD) $(DEBUG_BUILD)
