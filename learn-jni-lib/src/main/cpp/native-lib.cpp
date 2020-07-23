#include <jni.h>
#include <string>
#include <android/log.h>
#include <log.h>
/**
     *  |JAVA             |JNI
     *  |boolean          |Z
     *  |byte             |B
     *  |char             |C
     *  |short            |S
     *  |int              |I
     *  |long             |L
     *  |float            |F
     *  |double           |D
     *
     *  |String           |Ljava/lang/String;
     *  |Class            |Ljava/lang/Class;
     *  |Throwable        |Ljava/lang/Throwable;
     *  |int[]            |[I
     *  |byte[]           |[B
     *  |Object[]         |[Ljava/lang/Object;
     *
     *
     */

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_hy_learn_TestJni_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "hello world from C++";
    return env->NewStringUTF(hello.c_str());
}
}

//------------  JNI修改类的属性字段     -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_accessField(JNIEnv *env, jclass clazz, jobject user) {
    jclass cls = env->GetObjectClass(user);
    jfieldID jfId = env->GetFieldID(cls, "name", "Ljava/lang/String;");
    jfieldID jfId_age = env->GetFieldID(cls, "age", "I");
    jstring str=env->NewStringUTF("my name is C++");
    env->SetObjectField(user,jfId,str);
    env->SetIntField(user,jfId_age,16);
}

//------------ JNI修改类静态属性字段    -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_accessStaticField(JNIEnv *env, jclass thiz, jobject user) {
    jclass cls = env->GetObjectClass(user);
    jfieldID jfId = env->GetStaticFieldID(cls, "score", "I");
    int score=env->GetStaticIntField(cls,jfId);
    env->SetStaticIntField(cls,jfId,score-1);
}

//---------------- JNI调用类方法   -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_callMethod(JNIEnv *env, jclass clazz, jobject user) {
    jclass cls = env->GetObjectClass(user);
    jmethodID mid=env->GetMethodID(cls,"getUser","(Ljava/lang/String;ILjava/lang/String;)V");
    jstring name = env->NewStringUTF("leo");
    jstring tel = env->NewStringUTF("10086");
    env->CallVoidMethod(user,mid,name,20,tel);
}

//---------------- JNI调用类静态方法   -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_callStaticMethod(JNIEnv *env, jclass clazz, jobject user) {
    jclass cls = env->GetObjectClass(user);
    jmethodID mid=env->GetStaticMethodID(cls,"getUserInfo","([BI)I");
    int size=6;
    char *c_str ="011011";
    jbyteArray array=env->NewByteArray(size);
    for(int i=0;i<size;i++){
        env->SetByteArrayRegion(array,0,strlen(c_str), (jbyte *) c_str);
    }
    env->CallStaticIntMethod(cls,mid,array,size);
}

//---------------- JNI主线程调用   -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_callbackMethod(JNIEnv *env, jclass clazz, jobject callback) {
    jclass cls = env->GetObjectClass(callback);
    jmethodID mid_success=env->GetMethodID(cls,"onSuccess","(Ljava/lang/String;)V");
    jmethodID mid_fail=env->GetMethodID(cls,"onFail","(Ljava/lang/String;)V");
    jstring msg=env->NewStringUTF("C++ success");
    jstring error=env->NewStringUTF("C++ fail");
    env->CallVoidMethod(callback,mid_success,msg);
    env->CallVoidMethod(callback,mid_fail,error);
}

static jobject threadObject;
static jmethodID threadMethod;

//声明一个线程
pthread_t childThread;
JavaVM *jvm;
//-- 在加载动态库时会去调用 JNI_Onload 方法，可以得到 JavaVM 实例对象 --
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    jvm = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    return JNI_VERSION_1_6;
}


//定义一个线程的回调
void *threadCallback(void *) {
    JNIEnv *env= nullptr;
    if(jvm->AttachCurrentThread(&env, nullptr)==0){
        jstring msg=env->NewStringUTF("C++ childThread success");
        env->CallVoidMethod(threadObject,threadMethod,msg);
        jvm->DetachCurrentThread();
    }
    //执行线程完毕之后，退出线程
    pthread_exit(&childThread);
}

//---------------- JNI子主线程调用   -----------------
extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_TestJni_callbackChildThread(JNIEnv *env, jclass clazz, jobject callback) {
    threadObject=env->NewGlobalRef(callback);
    jclass cls = env->GetObjectClass(callback);
    threadMethod=env->GetMethodID(cls,"onSuccess","(Ljava/lang/String;)V");
    pthread_create(&childThread, nullptr, threadCallback, nullptr);
}

//---------------- JNI访问Java构造器    -----------------
extern "C"
JNIEXPORT jobject JNICALL
Java_com_hy_learn_TestJni_callConstructor(JNIEnv *env, jclass clazz) {
    jclass cls = env->FindClass("com/hy/learn/UserBean");
    jmethodID mId = env->GetMethodID(cls, "<init>", "(Ljava/lang/String;I)V");
    jstring name = env->NewStringUTF("jim from jni");
    jint tel = 18;
    jobject user = env->NewObject(cls, mId, name, tel);
    /** 第二种方法
     *  jobject users=env->AllocObject(cls);
     *  env->CallNonvirtualVoidMethod(users,cls,mId,name,tel);
     */
    return user;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_hy_learn_DataConvert_rgbaToYuv(JNIEnv *env, jclass clazz, jbyteArray rgba, jint width,
                                        jint height, jbyteArray yuv, jint type) {
    // TODO: implement rgbaToYuv()
}