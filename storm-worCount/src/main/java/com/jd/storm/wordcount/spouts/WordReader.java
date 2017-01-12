package com.jd.storm.wordcount.spouts;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.jd.storm.wordcount.utils.Constans;

/**
 * spout 读取元数据
 * */
public class WordReader implements IRichSpout {
	//文件路径 
	private String inputPath;
	//数据流发
	private SpoutOutputCollector collector;
	
	//生命周期中最先调用的方法
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.inputPath=(String)conf.get(Constans.INPUT_PATH);
		this.collector=collector;
	}

	public void close() {
		//System.out.println("WordReader close..........");
		
	}

	public void activate() {
		//System.out.println("WordReader activate..........");
		
		
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		//System.out.println("WordReader deactivate..........");
		
	}

	@SuppressWarnings("unchecked")
	public void nextTuple() {
		//System.out.println("WordReader nextTuple 哥们是被一直循环掉的，除非kill我!........");
		
		Collection<File> files = FileUtils.listFiles(new File(inputPath), FileFilterUtils.notFileFilter(FileFilterUtils.suffixFileFilter(".bak") ), null);
		for (File f:files){
			List<String> lines;
			try {
				lines = FileUtils.readLines(f, Constans.ENCODING);
				for (String line:lines){
					//发送数据到bolt
					this.collector.emit(new Values(line));
					
				}//移除文件，下载nextTuple 再次调用不在循环读取这个文件
				FileUtils.moveFile(f, new File(f.getPath()+System.currentTimeMillis()+".bak"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		
		
	}

	public void ack(Object msgId) {
		System.out.println("WordReader ack ...........");
		
	}

	public void fail(Object msgId) {
		System.out.println("WordReader fail ...........");
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("WordReader declareOutputFields ...........");
		declarer.declare(new Fields("line"));
		
	}

	public Map<String, Object> getComponentConfiguration() {
		System.out.println("WordReader getComponentConfiguration ...........");
		return null;
	}

}
