#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "videoPlayer from C"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hy_android_utils_PlayerJNI_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO
    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hy_android_utils_PlayerJNI_funFromJava(JNIEnv *env, jclass type, jobject obj,
                                                jstring id_) {
    const char *id = env->GetStringUTFChars(id_, 0);

    LOGE("---%s",id);
    //打印值 E/videoPlayer from C: ---1000

    jclass cls=env->GetObjectClass(obj);
    jmethodID methodID=env->GetMethodID(cls,"getData","(II)V");

    if(NULL==methodID){
        return;
    }

    env->CallVoidMethod(obj,methodID,111,666);

    env->ReleaseStringUTFChars(id_, id);
}