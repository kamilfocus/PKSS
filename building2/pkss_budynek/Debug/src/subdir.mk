################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/CApplication.cpp \
../src/CBuilding.cpp \
../src/CClient.cpp \
../src/CController.cpp \
../src/CModel.cpp \
../src/CRadiator.cpp \
../src/CRungeKutta.cpp \
../src/CTCPConnector.cpp \
../src/CTCPStream.cpp \
../src/main.cpp 

OBJS += \
./src/CApplication.o \
./src/CBuilding.o \
./src/CClient.o \
./src/CController.o \
./src/CModel.o \
./src/CRadiator.o \
./src/CRungeKutta.o \
./src/CTCPConnector.o \
./src/CTCPStream.o \
./src/main.o 

CPP_DEPS += \
./src/CApplication.d \
./src/CBuilding.d \
./src/CClient.d \
./src/CController.d \
./src/CModel.d \
./src/CRadiator.d \
./src/CRungeKutta.d \
./src/CTCPConnector.d \
./src/CTCPStream.d \
./src/main.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -std=c++98 -I"../src" -I"../src/include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


