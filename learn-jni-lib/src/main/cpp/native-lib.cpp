#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_hy_learn_TestJni_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "hello world from C++";
    return env->NewStringUTF(hello.c_str());
}
}