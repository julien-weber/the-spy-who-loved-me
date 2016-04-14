package com.criteo.hackathon;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;


/**
 * @author Yasser Ganjisaffar
 */
public class LinkedinCrawlController {
  private static final Logger logger = LoggerFactory.getLogger(LinkedinCrawlController.class);
  private static String inputFile;

  private static int NUM_CRAWLER = 10;

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      logger.info("Needed parameters: ");
      logger.info("\t the input file containing the linkedin urls, under the current user dir.");
      logger.info("\t the output dir for the results");
      return;
    }

    inputFile = args[0];
    LinkedinCrawler.setOutputDir(args[1]);

    /*
     * crawlStorageFolder is a folder where intermediate crawl data is
     * stored.
     */
    String crawlStorageFolder = System.getProperty("user.dir");
    System.out.println("Storage dir is " + crawlStorageFolder);

    /*
     * numberOfCrawlers shows the number of concurrent threads that should
     * be initiated for crawling.
     */
    int numberOfCrawlers = NUM_CRAWLER;

    CrawlConfig config = new CrawlConfig();

    config.setFollowRedirects(true);

    config.setIncludeHttpsPages(true);

    config.setCrawlStorageFolder(crawlStorageFolder);

    /*
     * Be polite: Make sure that we don't send more than 1 request per
     * second (1000 milliseconds between requests).
     */
    config.setPolitenessDelay(1000);

    /*
     * You can set the maximum crawl depth here. The default value is -1 for
     * unlimited depth
     */
    config.setMaxDepthOfCrawling(0);

    /*
     * You can set the maximum number of pages to crawl. The default value
     * is -1 for unlimited number of pages
     */
    config.setMaxPagesToFetch(1000);

    /**
     * Do you want crawler4j to crawl also binary data ?
     * example: the contents of pdf, or the metadata of images etc
     */
    config.setIncludeBinaryContentInCrawling(false);

    /*
     * Do you need to set a proxy? If so, you can use:
     * config.setProxyHost("proxyserver.example.com");
     * config.setProxyPort(8080);
     *
     * If your proxy also needs authentication:
     * config.setProxyUsername(username); config.getProxyPassword(password);
     */

    /*
     * This config parameter can be used to set your crawl to be resumable
     * (meaning that you can resume the crawl from a previously
     * interrupted/crashed crawl). Note: if you enable resuming feature and
     * want to start a fresh crawl, you need to delete the contents of
     * rootFolder manually.
     */
    config.setResumableCrawling(false);

    /*
     * Instantiate the controller for this crawl.
     */
    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    /*
     * For each crawl, you need to add some seed urls. These are the first
     * URLs that are fetched and then the crawler starts following links
     * which are found in these pages
     */
    String profileInput = System.getProperty("user.dir") + "/" + inputFile;
    List<String> inputs = Files.readLines(new File(profileInput), Charsets.UTF_8);
    logger.info("Crawling " + inputs.size() + " profiles");
    for (String input : inputs) {
      int pos = input.indexOf("linkedin.com");
      controller.addSeed("https://www."+input.substring(pos));
    }
    /*
     * Start the crawl. This is a blocking operation, meaning that your code
     * will reach the line after this only when crawling is finished.
     */
    controller.start(LinkedinCrawler.class, numberOfCrawlers);
  }
}
