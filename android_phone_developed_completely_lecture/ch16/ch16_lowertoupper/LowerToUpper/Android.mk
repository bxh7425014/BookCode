LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := LowerToUpper
LOCAL_SRC_FILES := LowerToUpper.c

include $(BUILD_SHARED_LIBRARY)
