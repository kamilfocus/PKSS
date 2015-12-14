################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/rapidjson/test/unittest/allocatorstest.cpp \
../src/rapidjson/test/unittest/bigintegertest.cpp \
../src/rapidjson/test/unittest/documenttest.cpp \
../src/rapidjson/test/unittest/encodedstreamtest.cpp \
../src/rapidjson/test/unittest/encodingstest.cpp \
../src/rapidjson/test/unittest/filestreamtest.cpp \
../src/rapidjson/test/unittest/itoatest.cpp \
../src/rapidjson/test/unittest/jsoncheckertest.cpp \
../src/rapidjson/test/unittest/namespacetest.cpp \
../src/rapidjson/test/unittest/pointertest.cpp \
../src/rapidjson/test/unittest/prettywritertest.cpp \
../src/rapidjson/test/unittest/readertest.cpp \
../src/rapidjson/test/unittest/simdtest.cpp \
../src/rapidjson/test/unittest/stringbuffertest.cpp \
../src/rapidjson/test/unittest/strtodtest.cpp \
../src/rapidjson/test/unittest/unittest.cpp \
../src/rapidjson/test/unittest/valuetest.cpp \
../src/rapidjson/test/unittest/writertest.cpp 

OBJS += \
./src/rapidjson/test/unittest/allocatorstest.o \
./src/rapidjson/test/unittest/bigintegertest.o \
./src/rapidjson/test/unittest/documenttest.o \
./src/rapidjson/test/unittest/encodedstreamtest.o \
./src/rapidjson/test/unittest/encodingstest.o \
./src/rapidjson/test/unittest/filestreamtest.o \
./src/rapidjson/test/unittest/itoatest.o \
./src/rapidjson/test/unittest/jsoncheckertest.o \
./src/rapidjson/test/unittest/namespacetest.o \
./src/rapidjson/test/unittest/pointertest.o \
./src/rapidjson/test/unittest/prettywritertest.o \
./src/rapidjson/test/unittest/readertest.o \
./src/rapidjson/test/unittest/simdtest.o \
./src/rapidjson/test/unittest/stringbuffertest.o \
./src/rapidjson/test/unittest/strtodtest.o \
./src/rapidjson/test/unittest/unittest.o \
./src/rapidjson/test/unittest/valuetest.o \
./src/rapidjson/test/unittest/writertest.o 

CPP_DEPS += \
./src/rapidjson/test/unittest/allocatorstest.d \
./src/rapidjson/test/unittest/bigintegertest.d \
./src/rapidjson/test/unittest/documenttest.d \
./src/rapidjson/test/unittest/encodedstreamtest.d \
./src/rapidjson/test/unittest/encodingstest.d \
./src/rapidjson/test/unittest/filestreamtest.d \
./src/rapidjson/test/unittest/itoatest.d \
./src/rapidjson/test/unittest/jsoncheckertest.d \
./src/rapidjson/test/unittest/namespacetest.d \
./src/rapidjson/test/unittest/pointertest.d \
./src/rapidjson/test/unittest/prettywritertest.d \
./src/rapidjson/test/unittest/readertest.d \
./src/rapidjson/test/unittest/simdtest.d \
./src/rapidjson/test/unittest/stringbuffertest.d \
./src/rapidjson/test/unittest/strtodtest.d \
./src/rapidjson/test/unittest/unittest.d \
./src/rapidjson/test/unittest/valuetest.d \
./src/rapidjson/test/unittest/writertest.d 


# Each subdirectory must supply rules for building sources it contributes
src/rapidjson/test/unittest/%.o: ../src/rapidjson/test/unittest/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I"../src" -I"../src/include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


