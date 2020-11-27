package com.wangwb.web.somelearn;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerChatMain extends JFrame{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        //
        new ServerChatMain();//调用构造方法
    }
    //属性
    //文本域
    private JTextArea jta;
    //滚动条
    private JScrollPane jsp;
    //面板
    private JPanel jp;
    //文本框
    private JTextField jtf;

    //按钮
    private JButton jb;
    //行为

    //构造方法
    public ServerChatMain(){
        //初始化组件
        jta = new JTextArea();
        //设置文本与不可编辑
        jta.setEditable(false);
        //注意：需要将文本框添加到滚动条中，实现滚动效果
        jsp = new JScrollPane(jta);
        //面板
        jp = new JPanel();
        //文本框
        jtf = new JTextField(10);
        //按钮
        jb = new JButton("发送");
        //注意：需要将文本框与按钮添加到面板中
        jp.add(jtf);
        jp.add(jb);


        //注意：需要将滚动条与面板全部添加到窗体中
        this.add(jsp, BorderLayout.CENTER);//放在中间
        this.add(jp,BorderLayout.SOUTH);//放在最下面，上北下南

        //注意：需要设置标题，大小，位置，关闭，是否可见
        this.setTitle("QQ聊天服务端");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
	
}
