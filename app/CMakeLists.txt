# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp
             src/main/cpp/Codec.cpp
             src/main/cpp/H264Decoder.cpp
             )


#Add other prebuilt libraries
 add_library(avcodec-lib  SHARED IMPORTED )
 set_target_properties(avcodec-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libavcodec.so )
 add_library(avdevice-lib  SHARED IMPORTED )
 set_target_properties(avdevice-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libavdevice.so )
 add_library(avfilter-lib  SHARED IMPORTED )
 set_target_properties(avfilter-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libavfilter.so )
 add_library(avformat-lib  SHARED IMPORTED )
 set_target_properties(avformat-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libavformat.so )
 add_library(avutil-lib  SHARED IMPORTED )
 set_target_properties(avutil-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libavutil.so )
 add_library(swresample-lib  SHARED IMPORTED )
 set_target_properties(swresample-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libswresample.so )
 add_library(swscale-lib  SHARED IMPORTED )
 set_target_properties(swscale-lib PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/armeabi/libswscale.so )

# Specifies a path to native header files.
include_directories(src/main/cpp/ffmpeg3/include/)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.



find_library( # Sets the name of the path variable.

              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib
                        avcodec-lib
                        avdevice-lib
                        avfilter-lib
                        avformat-lib
                        avutil-lib
                        swresample-lib
                        swscale-lib
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )