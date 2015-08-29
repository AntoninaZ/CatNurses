package cat.nurses.ua.api;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cat.nurses.ua.models.ImageSource;

/**
 * Created by antonina on 29.08.15.
 */
public class HandleXml {
    private ArrayList<ImageSource> images = new ArrayList<>() ;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXml(String url){
        this.urlString = url;
    }

    public ArrayList<ImageSource> getImages(){
        return images;
    }

    public void parseXMLAndStoreIt(XmlPullParser xpp) throws IOException, XmlPullParserException {
        ArrayList<ImageSource> results = new ArrayList<>() ;
        xpp.nextTag();
        xpp.nextTag();
        xpp.nextTag();
        xpp.require(XmlPullParser.START_TAG, null, "images");
        while (xpp.nextTag() == XmlPullParser.START_TAG) {
            xpp.require(XmlPullParser.START_TAG, null, "image");

            xpp.nextTag();
            xpp.require(XmlPullParser.START_TAG, null, "url");
            String url = xpp.nextText();
            xpp.require(XmlPullParser.END_TAG, null, "url");

            xpp.nextTag();
            xpp.require(XmlPullParser.START_TAG, null, "id");
            String id = xpp.nextText();
            xpp.require(XmlPullParser.END_TAG, null, "id");

            xpp.nextTag();
            xpp.require(XmlPullParser.START_TAG, null, "source_url");
            String sourceUrl = xpp.nextText();
            xpp.require(XmlPullParser.END_TAG, null, "source_url");

            xpp.nextTag();
            xpp.require(XmlPullParser.END_TAG, null, "image");

            ImageSource image = new ImageSource();
            image.setUrl(url);
            image.setIdImage(id);
            image.setSourceUrl(sourceUrl);
            images.add(image);
        }
        parsingComplete = false;
        xpp.require(XmlPullParser.END_TAG, null, "images");
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
