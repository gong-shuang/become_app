LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jni_mix
LOCAL_SRC_FILES := \
F:\StudioProjects\FurtherStudy\mixture\src\main\jni\find_name.cpp \
F:\StudioProjects\FurtherStudy\mixture\src\main\jni\get_cpu.cpp \
F:\StudioProjects\FurtherStudy\mixture\src\main\jni\get_encrypt.cpp \
F:\StudioProjects\FurtherStudy\mixture\src\main\jni\get_decrypt.cpp \
F:\StudioProjects\FurtherStudy\mixture\src\main\jni\aes.cpp \


LOCAL_CPPFLAGS += -fexceptions
LOCAL_WHOLE_STATIC_LIBRARIES += android_support
LOCAL_LDLIBS    := -llog


include $(BUILD_SHARED_LIBRARY)
$(call import-module, android/support)

