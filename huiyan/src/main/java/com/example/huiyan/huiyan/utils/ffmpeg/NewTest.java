package com.example.huiyan.huiyan.utils.ffmpeg;

/**
 * @Auther: zmx
 * @Date: 2021/12/16 14:24
 * @Description:
 */
public class NewTest {
    public static void main(String[] args) {

        //格式转换
        //# 命令: ffmpeg -i source target
        //# 例如avi转mp4
        //ffmpeg -i input.avi output.mp4

        //从视频中提取音频
        //# 命令: ffmpeg -i input -acodec type -vn output
        //# 例如mp4提取音频一般为aac，某些格式可能会报错
        //ffmpeg -i input.mp4 -acodec aac -vn output.aac

        //提取音频,只保留视频而不保留音频
        //ffmpeg -i input.mp4 -vcodec copy -an output.mp4

        //视频剪切
        //# 从时间为00:00:15开始，截取5秒钟的视频
        //ffmpeg -ss 00:00:15 -t 00:00:05 -i input.mp4 -vcodec copy -acodec copy output.mp4

        //添加字幕
        //# mp4添加软字幕
        //ffmpeg -i infile.mp4 -i infile.srt -c copy -c:s mov_text outfile.mp4

        //# mkv添加软字幕
        //ffmpeg -i input.mkv -i subtitles.srt -c copy output.mkv

        //# 硬字幕
        //ffmpeg -i end_font.mp4 -vf subtitles=words.srt -y output.mp4


        //码率控制
        //一个视频源的码率太高了，有10Mbps，文件太大，想把文件弄小一点，但是又不破坏分辨率
        //# 将码率将为2Mbps
        //ffmpeg -i input.mp4 -b:v 2000k output.mp4

        //ffmpeg官方wiki比较建议，设置b:v时，同时加上 -bufsize，用于设置码率控制缓冲器的大小，设置的好处是，让整体的码率更趋近于希望的值，减少波动。
        //# 设置为码率在2Mpbs波动
        //ffmpeg -i input.mp4 -b:v 2000k -bufsize 2000k output.mp4

        //-minrate最小码率，-maxrate最大码率 码率最大不超过2500k
        //ffmpeg -i input.mp4 -b:v 2000k -bufsize 2000k -maxrate 2500k output.mp4

        //编码转换 比如一个视频的编码是MPEG4，转换为H264编码
        //ffmpeg -i input.mp4 -vcodec h264 output.mp4

        //修改分辨率
        //将输入的1920x1080缩小到960x540输出
        //ffmpeg -i input.mp4 -vf scale=960:540 output.mp4

        //添加图片 可以用于给视频添加logo
        //
        //# 将图片添加到视频中，默认在左上角
        //ffmpeg -i input.mp4 -i logo.png -filter_complex overlay output.mp4
        //# 右上角
        //ffmpeg -i input.mp4 -i logo.png -filter_complex overlay=W-w output.mp4
        //# 左下角
        //ffmpeg -i input.mp4 -i logo.png -filter_complex overlay=0:H-h output.mp4
        //# 右下角
        //ffmpeg -i input.mp4 -i logo.png -filter_complex overlay=W-w:H-h output.mp4

        String cmd = " -i D:\\resource\\测试视频1.mkv -i D:\\resource\\logo.png -i D:\\resource\\logo1.png -filter_complex overlay=100:200:enable='between(t,5,10)',overlay=100:400:enable='between(t,7,20)' -y D:\\resource\\addLogo2.mkv";
        //FFMPEGUtil.cmdExecut(cmd);
        //局部打码 可用于一些网站logo打码
        //# 语法：-vf delogo=x:y:w:h[:t[:show]]
        //ffmpeg -i input.mp4 -vf delogo=0:0:220:90:100:1 output.mp4

        //截取画面
        //# r表示每一秒几帧，-q:v表示存储jpeg的图像质量，一般2是高质量。
        //ffmpeg -i input.mp4 -r 1 -q:v 2 -f image2 pic-%03d.jpeg

        //如此，ffmpeg会把input.mp4，每隔一秒，存一张图片下来。
        //
        //# -ss表示开始时间，-t表示共要多少时间
        //ffmpeg -i input.mp4 -ss 00:00:20 -t 10 -r 1 -q:v 2 -f image2 pic-%03d.jpeg
        //如此，ffmpeg会从input.mp4的第20s时间开始，往下10s，即20~30s这10秒钟之间，每隔1s就抓一帧，总共会抓10帧。
        //
        //视频拼接
        //ffmpeg -i "concat:input1.mpg|input2.mpg|input3.mpg" -c copy output.mpg

        //截取视频
        String ss=" -i D:\\resource\\截取.mkv -vcodec copy -acodec copy -ss 00:00:00 -to 00:01:00 -y D:\\resource\\截取1.mkv ";
        FFMPEGUtil.cmdExecut(ss);
    }
}