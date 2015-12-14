################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/rapidjson/example/capitalize/capitalize.cpp 

OBJS += \
./src/rapidjson/example/capitalize/capitalize.o 

CPP_DEPS += \
./src/rapidjson/example/capitalize/capitalize.d 


# Each subdirectory must supply rules for building sources it contributes
src/rapidjson/example/capitalize/%.o: ../src/rapidjson/example/capitalize/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I"../src" -I"../src/include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


