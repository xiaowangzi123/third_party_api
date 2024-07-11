package com.example.huiyan.huiyan.utils.ffmpeg;

/**
 * @Auther: zmx
 * @Date: 2021/12/14 13:22
 * @Description:
 */
public class Test {
    public static void main(String[] args) {

        String mkv_audio = "D:\\resource\\mkv1.mkv";
        String pp4_audio1 = "D:\\resource\\mp41.mp4";
        String pp4_audio2 = "D:\\resource\\mp42.mp4";
        String src = "D:\\resource\\movie.srt";
        String font = "D:\\resource\\MSYH.TTF";

        /**
         * TODO ----添加logo -i 原始视频文件 -i logo图片文件 -filter_complex overlay=W-w 处理后视频文件
         * 添加图片-静图或者动图
         * 左上角:ffmpeg -i output.mp4 -i pptv.png -filter_complex overlay output3.mp4
         * 右上角：ffmpeg -i output.mp4 -i pptv.png -filter_complex overlay=W-w output4.mp4
         * 左下角：ffmpeg -i output.mp4 -i pptv.png -filter_complex overlay=0:H-h output5.mp4
         * 右下角：ffmpeg -i output.mp4 -i pptv.png -filter_complex overlay=W-w:H-h output6.mp4
         */
        String cmd_mov_gif_logo = " -i E:\\java\\demo\\test_video.mp4 -i E:\\java\\demo\\test2.gif -filter_complex overlay=W-w E:\\java\\demo\\动态图片.mov";
        String cmd_mov_logo = " -i E:\\java\\demo\\test_video.mp4 -i E:\\java\\demo\\logo.png -filter_complex overlay=W-w E:\\java\\demo\\logo合成.mov";


        //添加字幕 字幕在本地最慢，其次是字幕和视频在obs，视频在本地最快
        // mp4合成不了字幕，mkv可以
        String add_src1 = "-i https://transwai.obs.cn-south-1.myhuaweicloud.com/aea45101372b4aafaa66e414049721e5.mkv -i C:\\IdeaProject\\subtitle-server\\字幕长.ass -c copy C:\\IdeaProject\\subtitle-server\\驯龙高手字幕-字幕.mkv";
        //FFMPEGUtil.cmdExecut(add_src1);


        //01.Name             风格(Style)的名称. 区分大小写. 不能包含逗号.
        //02.Fontname         使用的字体名称, 区分大小写.
        //03.Fontsize         字体的字号
        //04.PrimaryColour    设置主要颜色, 为蓝-绿-红三色的十六进制代码相排列, BBGGRR. 为字幕填充颜色
        //05.SecondaryColour  设置次要颜色, 为蓝-绿-红三色的十六进制代码相排列, BBGGRR. 在卡拉OK效果中由次要颜色变为主要颜色.
        //06.OutlineColour    设置轮廓颜色, 为蓝-绿-红三色的十六进制代码相排列, BBGGRR.
        //07.BackColour       设置阴影颜色, 为蓝-绿-红三色的十六进制代码相排列, BBGGRR. ASS的这些字段还包含了alpha通道信息. (AABBGGRR), 注ASS的颜色代码要在前面加上&H
        //08.Bold             -1为粗体, 0为常规
        //09.Italic           -1为斜体, 0为常规
        //10.Underline       [-1 或者 0] 下划线
        //11.Strikeout       [-1 或者 0] 中划线/删除线
        //12.ScaleX          修改文字的宽度. 为百分数
        //13.ScaleY          修改文字的高度. 为百分数
        //14.Spacing         文字间的额外间隙. 为像素数
        //15.Angle           按Z轴进行旋转的度数, 原点由alignment进行了定义. 可以为小数
        //16.BorderStyle     1=边框+阴影, 3=纯色背景. 当值为3时, 文字下方为轮廓颜色的背景, 最下方为阴影颜色背景.
        //17.Outline         当BorderStyle为1时, 该值定义文字轮廓宽度, 为像素数, 常见有0, 1, 2, 3, 4.
        //18.Shadow          当BorderStyle为1时, 该值定义阴影的深度, 为像素数, 常见有0, 1, 2, 3, 4.
        //19.Alignment       定义字幕的位置. 字幕在下方时, 1=左对齐, 2=居中, 3=右对齐. 1, 2, 3加上4后字幕出现在屏幕上方. 1, 2, 3加上8后字幕出现在屏幕中间. 例: 11=屏幕中间右对齐. Alignment对于ASS字幕而言, 字幕的位置与小键盘数字对应的位置相同.
        //20.MarginL         字幕可出现区域与左边缘的距离, 为像素数
        //21.MarginR         字幕可出现区域与右边缘的距离, 为像素数
        //22.MarginV         垂直距离
        //Alignment：0:左对齐 1:左下，2:右下，3:右下，5:左上，6:顶中，7:右上，9:左中，10:中间，11:右中
        String add_src4 = " -i D:\\resource\\mkv1.mkv -lavfi subtitles=movie1.srt:force_style='Alignment=2,OutlineColour=&H100000000,BorderStyle=1,Outline=1,Shadow=0,Fontsize=18,MarginL=0,MarginV=10'  -y D:\\resource\\合成字幕.mkv";
        //FFMPEGUtil.cmdExecut(add_src4);

        //加文字水印
        //fontcolor=#FFFFFF@0.6，字体颜色可以用RGB代码,@后表示0.6的透明度，取值为0.1-1.0    shadowy表示文字阴影  -threads 2 限制为2个线程（放到-y前面）
        String cmd_mov_water = " -i D:\\resource\\mkv1.mkv -vf \"drawtext=fontfile=D:\\resource\\MSYH.TTF:text=加的水印-zmx:y=h-line_h-50:x=(w-text_w)/2:fontsize=34:fontcolor=blue:shadowy=0:shadowx=2\" -y D:\\resource\\加文字水印.mkv ";
        //FFMPEGUtil.cmdExecut(cmd_mov_water);

        //视频格式转换
        //String trans_type = " -i D:\\resource\\驯龙高手-剪辑版.mkv -vcodec h264 -y D:\\resource\\驯龙高手.mp4 ";
        String trans_type = " -i D:\\resource\\驯龙高手-剪辑版.mkv -codec copy -y D:\\resource\\驯龙高手-剪辑版.mp4 ";
        //FFMPEGUtil.cmdExecut(trans_type);

        //去水印
        //delogo=16:16:500:100  水印左距离：水印右距离：水印宽度：水印高度  x,y为距左上角坐标，w,h为水印的宽度和高度
        String remove_logo = " -i D:\\resource\\mp42.mp4 -vf delogo=700:456:201:80 -y D:\\resource\\mp42_去LOGO.mkv";
        //FFMPEGUtil.cmdExecut(remove_logo);

        String videoLength = " -v error -select_streams v:0 -show_entries stream=duration -of default=noprint_wrappers=1:nokey=1 D:\\resource\\mp42.mp4";
        //FFMPEGUtil.cmdExecut(videoLength);

        //视频提取音频
        //String getVideo=" -i D:\\resource\\驯龙高手.mkv -f wav -ab 16k -ac 1 -ac 16000 -vn D:\\resource\\驯龙高手-新.wav";
        String getVideo = " -i D:\\resource\\驯龙高手-剪辑版.mkv -acodec pcm_s16le -f s16le -ac 1 -ar 16000 -f wav D:\\resource\\驯龙高手-剪辑版.wav";
        //FFMPEGUtil.cmdExecut(getVideo);

        //视频压缩命令
        String yazhi = " -i C:/Users/Jedie/Desktop/test.avi -s 1920x1080 -b:v 8500k C:/Users/Jedie/Desktop/test_done.mp4";

        //裁剪视频
        String caijian = "-ss 00:02:45 -t 00:02:29 -i D:\\resource\\楚门的世界.mp4 -vcodec copy -acodec copy -y D:\\resource\\楚门的世界-剪辑版.mp4";
//        FFMPEGUtil.cmdExecut(caijian);

//        String cmdStr = "ffmpeg -i  /data/transwai/subtitle/test.mp4  -ss 00:00:1.000 -vframes 1 /data/transwai/subtitle/cover_1648365331843_.png";
//        String cmdStr = "-i D:\\TestAudioVideo\\cover\\2_36EnZh.mp4 -ss 00:00:2.123 -vframes 1 D:\\TestAudioVideo\\cover\\out\\out2.png";

        String cmdStr = "-i D:\\TestAudioVideo\\mkv\\duye.mkv -b:v 500k -b:a 384k -c:v h264 -c:a aac D:\\TestAudioVideo\\mkv\\yasuo\\duye_h264_041801.mp4";
        long start = System.currentTimeMillis();
        Integer resCode = FFMPEGUtil.cmdExecut(cmdStr);
        System.out.println("ffmpeg执行结果："+resCode);
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + ((end - start) / 1000));
    }
}