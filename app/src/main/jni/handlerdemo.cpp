//
// Created by 七喜 on 2017/6/8.
//

#include <string.h>
#include <jni.h>
/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
//jstring
//Java_com_bazhangkeji_MainActivity_stringFromJNI( JNIEnv* env,
//                                                  jobject thiz )
//{
//    return (*env)->NewStringUTF(env, "Hello from JNI !");
//}
extern "C" {
JNIEXPORT jstring JNICALL Java_com_honjane_handlerdemo_MainActivity_stringFromJNI
        (JNIEnv *env, jobject _obj) {

    return env->NewStringUTF((char *) "Hello from JNI !");
}


}