package com.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrCloudTest {
	
	public static void main(String[] args) {
		CloudSolrServer server = new CloudSolrServer("101.132.183.157:2182,101.132.183.157:2183,101.132.183.157:2184");
		server.setDefaultCollection("collection2");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		try {
			server.add(document);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
