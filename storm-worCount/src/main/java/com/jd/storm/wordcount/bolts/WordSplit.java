package com.jd.storm.wordcount.bolts;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mortbay.util.StringUtil;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * bolt 截取文件中的单词
 * */
public class WordSplit implements IRichBolt {
	OutputCollector collector;
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector=collector;
		//System.out.println("WordSplit prepare......................");
		
	}

	public void execute(Tuple input) {
		//System.out.println("WordSplit execute......................");
		String line=input.getString(0);
		String [] words=line.split(" ");
		for (String word:words){
			word=word.trim();
			if (StringUtils.isNotBlank(word)){
				collector.emit(new Values(word));
			}
		}
		// TODO Auto-generated method stub
		
	}

	public void cleanup() {
		//System.out.println("WordSplit cleanup......................");

		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("WordSplit declareOutputFields......................");
		declarer.declare(new Fields("word"));
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		//System.out.println("WordSplit getComponentConfiguration......................");
		// TODO Auto-generated method stub
		return null;
	}

}
