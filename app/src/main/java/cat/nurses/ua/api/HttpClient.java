package cat.nurses.ua.api;

import android.content.Context;
import android.provider.DocumentsContract;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cat.nurses.ua.adapters.CatsListAdapter;

/**
 * Created by antonina on 25.08.15.
 */
public class HttpClient extends AsyncHttpClient {
    private String strUrl;

    public void getCatsList(final Context context, final ListView listView) {
        get("http://thecatapi.com/api/images/get?format=xml&size=med", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
               String r = new String(response);
                try {
                    DocumentBuilderFactory dbf =
                            DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(r));

                    Document doc = db.parse(is);
                    NodeList data = doc.getElementsByTagName("data");
                    NodeList images = ((Element) data.item(0)).getElementsByTagName("images");
                    for (int i = 0; i < images.getLength(); i++) {
                        Element element = (Element) images.item(i);
                        NodeList image = element.getElementsByTagName("image");
                        Element imageUrl = (Element) image.item(0);
                        NodeList urls = imageUrl.getElementsByTagName("url");
                        Element url = (Element) urls.item(0);
                        strUrl = getCharacterDataFromElement(url);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onRetry(int retryNo) {

            }

            @Override
            public void onFinish() {
                ArrayList<String> arr = new ArrayList<>();
                arr.add(strUrl);
                listView.setAdapter(new CatsListAdapter(context, arr));
            }
        });
    }

    private String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}
