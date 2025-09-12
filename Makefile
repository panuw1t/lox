.PHONY: all run gen

BUILD = ./build
SRC = ./src/com/craftinginterpreters

TARGET_CLASS = $(BUILD)/com/craftinginterpreters/lox/Lox.class
TARGET_GEN = $(BUILD)/com/craftinginterpreters/tool/GenerateAst.class
TARGET_SRC = $(SRC)/*/*.java

all: $(TARGET_CLASS)

$(TARGET_CLASS): $(TARGET_SRC)
	javac -d $(BUILD) $(TARGET_SRC)

run: $(TARGET_CLASS)
	java -cp $(BUILD) com.craftinginterpreters.lox.Lox

test: $(TARGET_CLASS) test.c
	java -cp $(BUILD) com.craftinginterpreters.lox.Lox "test.c"

gen: $(TARGET_GEN)
	java -cp $(BUILD) com.craftinginterpreters.tool.GenerateAst $(SRC)/lox

test2: $(TARGET_CLASS) 
	java -cp $(BUILD) com.craftinginterpreters.lox.AstPrinter
