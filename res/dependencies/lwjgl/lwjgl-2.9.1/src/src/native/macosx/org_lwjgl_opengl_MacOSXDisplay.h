/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_lwjgl_opengl_MacOSXDisplay */

#ifndef _Included_org_lwjgl_opengl_MacOSXDisplay
#define _Included_org_lwjgl_opengl_MacOSXDisplay
#ifdef __cplusplus
extern "C" {
#endif
#undef org_lwjgl_opengl_MacOSXDisplay_PBUFFER_HANDLE_SIZE
#define org_lwjgl_opengl_MacOSXDisplay_PBUFFER_HANDLE_SIZE 24L
#undef org_lwjgl_opengl_MacOSXDisplay_GAMMA_LENGTH
#define org_lwjgl_opengl_MacOSXDisplay_GAMMA_LENGTH 256L
/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nCreateWindow
 * Signature: (IIIIZZZZLjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nCreateWindow
  (JNIEnv *, jobject, jint, jint, jint, jint, jboolean, jboolean, jboolean, jboolean, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetCurrentDisplayMode
 * Signature: ()Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetCurrentDisplayMode
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetDisplayModes
 * Signature: (Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetDisplayModes
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nIsMiniaturized
 * Signature: (Ljava/nio/ByteBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nIsMiniaturized
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nIsFocused
 * Signature: (Ljava/nio/ByteBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nIsFocused
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nSetResizable
 * Signature: (Ljava/nio/ByteBuffer;Z)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nSetResizable
  (JNIEnv *, jobject, jobject, jboolean);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nResizeWindow
 * Signature: (Ljava/nio/ByteBuffer;IIII)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nResizeWindow
  (JNIEnv *, jobject, jobject, jint, jint, jint, jint);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nWasResized
 * Signature: (Ljava/nio/ByteBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nWasResized
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetX
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetX
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetY
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetY
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetWidth
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetWidth
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nGetHeight
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nGetHeight
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nIsNativeMode
 * Signature: (Ljava/nio/ByteBuffer;)Z
 */
JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nIsNativeMode
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nDestroyCALayer
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nDestroyCALayer
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nDestroyWindow
 * Signature: (Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nDestroyWindow
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    setGammaRamp
 * Signature: (Ljava/nio/FloatBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_setGammaRamp
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    restoreGamma
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_restoreGamma
  (JNIEnv *, jobject);

/*
 * Class:     org_lwjgl_opengl_MacOSXDisplay
 * Method:    nSetTitle
 * Signature: (Ljava/nio/ByteBuffer;Ljava/nio/ByteBuffer;)V
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_MacOSXDisplay_nSetTitle
  (JNIEnv *, jobject, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
