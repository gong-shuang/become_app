package com.gs.learn.network;

import java.util.ArrayList;

import com.gs.learn.network.adapter.MailExpandAdapter;
import com.gs.learn.network.bean.MailBox;
import com.gs.learn.network.bean.MailItem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import com.gs.learn.R;

/**
 * Created by ouyangshen on 2016/11/11.
 */
public class FoldListActivity extends AppCompatActivity {
	private final static String TAG = "FoldListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fold_list);
		initMailBox();
	}
	
	private void initMailBox() {
		ExpandableListView elv_list = (ExpandableListView) findViewById(R.id.elv_list);
		final ArrayList<MailBox> box_list = new ArrayList<MailBox>();
		box_list.add(new MailBox(R.drawable.mail_folder_inbox, "收件箱", getRecvMail()));
		box_list.add(new MailBox(R.drawable.mail_folder_outbox, "发件箱", getSentMail()));
		box_list.add(new MailBox(R.drawable.mail_folder_draft, "草稿箱", getDraftMail()));
		box_list.add(new MailBox(R.drawable.mail_folder_recycle, "废件箱", getRecycleMail()));
		MailExpandAdapter adapter = new MailExpandAdapter(this, box_list);
		elv_list.setAdapter(adapter);
		elv_list.setOnChildClickListener(adapter);
		elv_list.setOnGroupClickListener(adapter);
		elv_list.expandGroup(0);  //默认展开第一个邮件夹
	}
	
	private ArrayList<MailItem> getRecvMail() {
		ArrayList<MailItem> mail_list = new ArrayList<MailItem>();
		mail_list.add(new MailItem("这里是收件箱呀1", "2016年11月15日"));
		mail_list.add(new MailItem("这里是收件箱呀2", "2016年11月10日"));
		mail_list.add(new MailItem("这里是收件箱呀3", "2016年11月14日"));
		mail_list.add(new MailItem("这里是收件箱呀4", "2016年11月11日"));
		mail_list.add(new MailItem("这里是收件箱呀5", "2016年11月13日"));
		return mail_list;
	}

	private ArrayList<MailItem> getSentMail() {
		ArrayList<MailItem> mail_list = new ArrayList<MailItem>();
		mail_list.add(new MailItem("邮件发出去了吗1", "2016年11月15日"));
		mail_list.add(new MailItem("邮件发出去了吗2", "2016年11月14日"));
		mail_list.add(new MailItem("邮件发出去了吗3", "2016年11月11日"));
		mail_list.add(new MailItem("邮件发出去了吗4", "2016年11月13日"));
		mail_list.add(new MailItem("邮件发出去了吗5", "2016年11月10日"));
		return mail_list;
	}

	private ArrayList<MailItem> getDraftMail() {
		ArrayList<MailItem> mail_list = new ArrayList<MailItem>();
		mail_list.add(new MailItem("暂时放在草稿箱吧1", "2016年11月14日"));
		mail_list.add(new MailItem("暂时放在草稿箱吧2", "2016年11月11日"));
		mail_list.add(new MailItem("暂时放在草稿箱吧3", "2016年11月15日"));
		mail_list.add(new MailItem("暂时放在草稿箱吧4", "2016年11月10日"));
		mail_list.add(new MailItem("暂时放在草稿箱吧5", "2016年11月13日"));
		return mail_list;
	}

	private ArrayList<MailItem> getRecycleMail() {
		ArrayList<MailItem> mail_list = new ArrayList<MailItem>();
		mail_list.add(new MailItem("啊啊啊，怎么被删除了1", "2016年11月11日"));
		mail_list.add(new MailItem("啊啊啊，怎么被删除了2", "2016年11月13日"));
		mail_list.add(new MailItem("啊啊啊，怎么被删除了3", "2016年11月15日"));
		mail_list.add(new MailItem("啊啊啊，怎么被删除了4", "2016年11月10日"));
		mail_list.add(new MailItem("啊啊啊，怎么被删除了5", "2016年11月14日"));
		return mail_list;
	}

}
