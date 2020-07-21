
#include "H264Decoder.h"
#define LOG_TAG "H264Decoder from C++"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


int H264Decoder::start() {
    const char * test="/mnt/sdcard/test.264";
    av_register_all();
    avFormatContext=avformat_alloc_context();
    int ret=avformat_open_input(&avFormatContext,test,NULL,NULL);
    if(ret!=0){
        LOGE("avFormat_open_input %d",ret);
        return ret;
    }
    ret=avformat_find_stream_info(avFormatContext,NULL);
    LOGE("avFormat_find_stream_info %d",ret);
    if(ret<0){
        return ret;
    }
    avCodec=avcodec_find_decoder(AV_CODEC_ID_H264);
    avCodecContext=avcodec_alloc_context3(avCodec);
    ret=avcodec_open2(avCodecContext,avCodec,NULL);
    LOGE("avCodec_open2 %d",ret);
    if(ret!=0){
        return ret;
    }
    avPacket=av_packet_alloc();
    av_init_packet(avPacket);
    avFrame=av_frame_alloc();
    width=avFormatContext->streams[0]->codecpar->width;
    height=avFormatContext->streams[0]->codecpar->height;
    yFrameSize= (size_t) (width * height);
    uvFrameSize= yFrameSize>>2;
    LOGE("width===%d",width);
    return 0;
}

int H264Decoder::input(uint8_t *data) {
    return 0;
}

int H264Decoder::output(uint8_t *data) {
    int ret=av_read_frame(avFormatContext,avPacket);
    if(ret!=0){
        log(ret,"av_read_frame");
        return ret;
    }
    ret=avcodec_send_packet(avCodecContext,avPacket);
    if(ret!=0){
        log(ret,"avcodec_send_packet");
        return ret;
    }
    ret=avcodec_receive_frame(avCodecContext,avFrame);
    if(ret==0){
        memcpy(data, avFrame->data[0], yFrameSize);
        memcpy(data+yFrameSize, avFrame->data[1], uvFrameSize);
        memcpy(data+yFrameSize+uvFrameSize, avFrame->data[2], uvFrameSize);
    }else{
        log(ret,"avcodec_receive_frame");
    }
    av_packet_unref(avPacket);
    return ret;
}

int H264Decoder::stop() {
    avcodec_free_context(&avCodecContext);
    avformat_close_input(&avFormatContext);
    return 0;
}

void H264Decoder::set(int key, int value) {

}

int H264Decoder::get(int key) {
    switch (key){
        case KEY_WIDTH:
            return width;
        case KEY_HEIGHT:
            return height;
        default:
            break;
    }
    return Codec::get(key);
}