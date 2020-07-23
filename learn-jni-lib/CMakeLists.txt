# 指定cmake的最小版本
cmake_minimum_required(VERSION 3.4.1)

# 搜索当前目录下的所有.cpp文件
aux_source_directory(src/main/cpp dir_cpp)

message(${CMAKE_ANDROID_ARCH_ABI})
message(${CMAKE_SOURCE_DIR})

# 指定头文件目录
include_directories(src/main/cpp/ffmpeg3/include)
include_directories(src/main/cpp/include)
include_directories(src/main/cpp/ffmpeg)

add_library( # 生成库的名字
        native-lib

        # 生成动态库或共享库
        SHARED

        # 将以下的C++文件编译到动态库
        src/main/cpp/native-lib.cpp
        ${dir_cpp}
        )

# 生成静态库
# 静态库在程序编译时会被连接到目标代码中，程序运行时将不再需要该静态库。
# add_library(native-static-lib STATIC native-static-lib.cpp)

set(lib_src_dir  ${CMAKE_SOURCE_DIR}/src/main/jniLibs/armeabi)

# 添加其他预构建的库
add_library(avcodec-lib SHARED IMPORTED)
set_target_properties(avcodec-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libavcodec.so)

add_library(avdevice-lib SHARED IMPORTED)
set_target_properties(avdevice-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libavdevice.so)

add_library(avfilter-lib SHARED IMPORTED)
set_target_properties(avfilter-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libavfilter.so)

add_library(avformat-lib SHARED IMPORTED)
set_target_properties(avformat-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libavformat.so)

add_library(avutil-lib SHARED IMPORTED)
set_target_properties(avutil-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libavutil.so)

add_library(swresample-lib SHARED IMPORTED)
set_target_properties(swresample-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libswresample.so)

add_library(swscale-lib SHARED IMPORTED)
set_target_properties(swscale-lib PROPERTIES IMPORTED_LOCATION ${lib_src_dir}/libswscale.so)

# 查找到指定的预编译库，并将它的路径存储在变量中
find_library(
        log-lib

        log)

# 设置 target 需要链接的库
target_link_libraries( # 目标库
        native-lib

        # 目标库需要链接的库
        avcodec-lib
        avdevice-lib
        avfilter-lib
        avformat-lib
        avutil-lib
        swresample-lib
        swscale-lib

        # log-lib 是上面 find_library 指定的变量名
        ${log-lib})