package com.jd.storm.wordcount.bolts;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import com.jd.storm.wordcount.utils.Constans;

/**
 * bolt 统计word count
 * */
public class WordCounter implements IRichBolt  {
    private OutputCollector collector;
    private Map<String,Integer> wordMap=new HashMap<String, Integer>();
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		System.out.println("WordCounter prepare ........................");
		this.collector=collector;
		final long timeOffset= Long.parseLong(stormConf.get(Constans.TIME_OFFSET).toString());
		new Thread(new Runnable() {
			
			public void run() {
				for (;;){
				
					for (String key :wordMap.keySet()){
						System.out.println("------"+key+":"+wordMap.get(key));
					}
					System.out.println("______________________________________storm___________________________________________");
					try {
						Thread.sleep(timeOffset*1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		}).start();
		
	}

	public void execute(Tuple input) {
		//System.out.println("WordCounter execute ........................");
		String word=input.getString(0);
		if (!wordMap.containsKey(word)){
			wordMap.put(word,1);
		}
		else {
			wordMap.put(word, wordMap.get(word)+1);
		}
		
	}

	public void cleanup() {
		//System.out.println("WordCounter cleanup ........................");
		
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//System.out.println("WordCounter declareOutputFields ........................");
		
		
	}

	public Map<String, Object> getComponentConfiguration() {
		//System.out.println("WordCounter getComponentConfiguration ........................");
		
		return null;
	}

}
