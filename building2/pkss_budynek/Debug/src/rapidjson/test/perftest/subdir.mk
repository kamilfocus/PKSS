################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/rapidjson/test/perftest/misctest.cpp \
../src/rapidjson/test/perftest/perftest.cpp \
../src/rapidjson/test/perftest/platformtest.cpp \
../src/rapidjson/test/perftest/rapidjsontest.cpp 

OBJS += \
./src/rapidjson/test/perftest/misctest.o \
./src/rapidjson/test/perftest/perftest.o \
./src/rapidjson/test/perftest/platformtest.o \
./src/rapidjson/test/perftest/rapidjsontest.o 

CPP_DEPS += \
./src/rapidjson/test/perftest/misctest.d \
./src/rapidjson/test/perftest/perftest.d \
./src/rapidjson/test/perftest/platformtest.d \
./src/rapidjson/test/perftest/rapidjsontest.d 


# Each subdirectory must supply rules for building sources it contributes
src/rapidjson/test/perftest/%.o: ../src/rapidjson/test/perftest/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I"../src" -I"../src/include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


