#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_hy_android_utils_PlayerJNI_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}