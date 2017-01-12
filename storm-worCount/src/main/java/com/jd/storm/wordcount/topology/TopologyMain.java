package com.jd.storm.wordcount.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.jd.storm.wordcount.bolts.WordCounter;
import com.jd.storm.wordcount.bolts.WordSplit;
import com.jd.storm.wordcount.spouts.WordReader;
import com.jd.storm.wordcount.utils.Constans;
/**
 * hello word 可执行jar
 * 创建spout 读取数据
 * 创建bolt 解析数据
 * 创建bolt 计数
 * 
 * */
public class TopologyMain {
	public static void main (String []args){
		if (args.length!=2){
			 System.err.println("运行命令请传入正确参数:inputPath and timeOffSet ");
		     System.err.println("such as : java -jar  WordCount.jar D:/工作/技术/storm/input");
		    // System.exit(0);
		}
		TopologyBuilder builder= new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-slipt", new WordSplit()).shuffleGrouping("word-reader");
		builder.setBolt("word-count", new WordCounter()).fieldsGrouping("word-slipt", new Fields("word"));
		String inputPath=args[0];
		String timeOffset=args[1];
		Config conf = new Config();
		conf.put(Constans.INPUT_PATH, inputPath);
		conf.put(Constans.TIME_OFFSET, timeOffset);
		/*conf.put(Constans.INPUT_PATH, "D:/工作/技术/storm/input");
		conf.put(Constans.TIME_OFFSET, 4);*/
		conf.setDebug(Boolean.TRUE);
		//创建本地提交
		LocalCluster cluster=new LocalCluster();
		//提交到storm
		cluster.submitTopology("wordCount", conf, builder.createTopology());
		/*try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cluster.shutdown();*/
		
	}
	

}
