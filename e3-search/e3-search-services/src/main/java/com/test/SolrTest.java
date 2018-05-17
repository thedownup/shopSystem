//package com.test;
//
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.junit.Test;
//
//public class SolrTest {
//	
//	@Test
//	public void solrTest(){
//		SolrServer server = new HttpSolrServer("http://101.132.183.157:8080/solr");
//		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.set("q", "*:*");
//		solrQuery.setStart(0);
//		solrQuery.setRows(200);
//		solrQuery.setHighlight(true);
//		solrQuery.addHighlightField("item_title");
//		solrQuery.setHighlightSimplePre("<em>");
//		solrQuery.setHighlightSimplePost("</em>");
//		solrQuery.set("df", "item_title");
//		try {
//			QueryResponse query = server.query(solrQuery);
//			SolrDocumentList results = query.getResults();
//			for (SolrDocument solrDocument : results) {
//				System.out.println(solrDocument.get("item_title"));
//			}
//		} catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//}
