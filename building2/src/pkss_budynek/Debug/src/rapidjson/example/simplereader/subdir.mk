################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/rapidjson/example/simplereader/simplereader.cpp 

OBJS += \
./src/rapidjson/example/simplereader/simplereader.o 

CPP_DEPS += \
./src/rapidjson/example/simplereader/simplereader.d 


# Each subdirectory must supply rules for building sources it contributes
src/rapidjson/example/simplereader/%.o: ../src/rapidjson/example/simplereader/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I"../src" -I"../src/include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


