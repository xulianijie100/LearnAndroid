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

extern "C"
JNIEXPORT jobject JNICALL
Java_com_hy_learn_TestJni_invokeUserConstructor(JNIEnv *env, jobject thiz) {
    /** -------------------  JNI访问Java构造器   ----------------------------  **/
    jclass cls = env->FindClass("com/hy/learn/User");
    jmethodID mId= env->GetMethodID(cls,"<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    jstring name=env->NewStringUTF("jim from jni");
    jstring tel=env->NewStringUTF("16800002222");
    jobject user=env->NewObject(cls,mId,name,tel);
    /** 第二种方法
     *  jobject users=env->AllocObject(cls);
     *  env->CallNonvirtualVoidMethod(users,cls,mId,name,tel);
     */
    return user;
}