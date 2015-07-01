################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../app-android.c \
../app-linux.c \
../app-win32.c \
../demo.c \
../importgl.c 

OBJS += \
./app-android.o \
./app-linux.o \
./app-win32.o \
./demo.o \
./importgl.o 

C_DEPS += \
./app-android.d \
./app-linux.d \
./app-win32.d \
./demo.d \
./importgl.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


