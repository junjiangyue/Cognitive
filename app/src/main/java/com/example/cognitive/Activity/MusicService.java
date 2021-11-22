package com.example.cognitive.Activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.cognitive.R;

import java.io.IOException;

public class MusicService extends Service implements Runnable
{
    MyReceiver serviceReceiver;
    //asset文件管理对象
    AssetManager am;
    String[] musics = new String[] { "music0.mp3", "music1.mp3", "music2.mp3" };
    public static MediaPlayer mPlayer;
    // 当前的状态，0x11代表没有播放；0x12代表正在播放；0x13代表暂停
    int status = 0x11;
    // 记录当前正在播放的音乐
    int current = 0;
    @Override
    //非绑定式服务
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    //定义初始化函数
    public void onCreate()
    {
        super.onCreate();
        //获取asset文件夹里的所有歌曲
        am = getAssets();
        // 创建具有筛选功能的服务端接收器
        serviceReceiver = new MyReceiver();
        // 创建IntentFilter过滤器
        IntentFilter filter = new IntentFilter();
        //使音乐服务端的接收器只能接收客户端发来的CTL_ACTION消息
        filter.addAction(Music_Activity.CTL_ACTION);
        //注册带有此筛选器且名字叫做serviceReceiver的接收器
        registerReceiver(serviceReceiver, filter);
        // 创建MediaPlayer
        mPlayer = new MediaPlayer();
        // 为MediaPlayer播放完成事件绑定监听器
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() // ①
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                current++;
                if (current >= 3)
                {
                    current = 0;
                }
                //发送广播通知Activity更改文本框
                Intent sendIntent = new Intent(Music_Activity.UPDATE_ACTION);
                sendIntent.putExtra("current", current);
                // 发送广播，将被Activity组件中的BroadcastReceiver接收到
                sendBroadcast(sendIntent);
                // 准备并播放音乐
                prepareAndPlay(musics[current]);
            }
        });
    }
    public class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(final Context context, Intent intent)
        {
            int control = intent.getIntExtra("control", -1);
            switch (control)
            {
                // 播放或暂停
                case 1:
                    // 原来处于没有播放状态
                    if (status == 0x11)
                    {
                        // 准备并播放音乐
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    // 原来处于播放状态
                    else if (status == 0x12)
                    {
                        // 暂停
                        mPlayer.pause();
                        // 改变为暂停状态
                        status = 0x13;
                    }
                    // 原来处于暂停状态
                    else if (status == 0x13)
                    {
                        // 播放
                        mPlayer.start();
                        // 改变状态
                        status = 0x12;
                    }
                    break;
                // 停止声音
                case 2:
                    //上一首切换
                    if (current <= 0) {
                        //停止播放
                        mPlayer.stop();
                        //修改current
                        current = musics.length-1;
                        //播放
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    else{
                        mPlayer.stop();
                        current--;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    break;
                //下一首切换
                case 3:
                    if (current >= 2) {
                        mPlayer.stop();
                        current = 0;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    else{
                        mPlayer.stop();
                        current++;
                        prepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    break;

            }
            // 广播通知Activity更改图标、文本框
            Intent sendIntent = new Intent(Music_Activity.UPDATE_ACTION);
            sendIntent.putExtra("update", status);
            sendIntent.putExtra("current", current);
            // 发送广播，将被Activity组件中的BroadcastReceiver接收到
            sendBroadcast(sendIntent);
        }
    }
    private void prepareAndPlay(String music) {
        try {
            // 打开指定音乐文件

            AssetFileDescriptor afd = am.openFd(music);
            mPlayer.reset();
            // 使用MediaPlayer加载指定的声音文件。
            mPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            // 准备声音
            mPlayer.prepare();
            // 播放
            mPlayer.start();
            // 设置进度条最大值
            Music_Activity.audioSeekBar.setMax(MusicService.mPlayer.getDuration());
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 刷新进度条
    public void run() {
        int CurrentPosition = 0;
        int total = mPlayer.getDuration();
        while (mPlayer != null && CurrentPosition < total) {
            try {
                Thread.sleep(1000);
                if (mPlayer != null) {
                    CurrentPosition = mPlayer.getCurrentPosition();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Music_Activity.audioSeekBar.setProgress(CurrentPosition);
        }
    }
}


