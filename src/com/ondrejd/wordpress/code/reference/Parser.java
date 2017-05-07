/**
 * @author  Ondřej Doněk, <ondrejd@gmail.com>
 * @license https://www.gnu.org/licenses/gpl-3.0.en.html GNU General Public License 3.0
 * @link https://github.com/ondrejd/od-downloads-plugin for the canonical source repository
 */

package com.ondrejd.wordpress.code.reference;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Parser for <a href="https://developer.wordpress.org/reference/">WordPress Code Reference</a> site.
 */
public class Parser {
    public static enum TYPES { CLASSES, FUNCTIONS, HOOKS };
    public static String DEFAULT_FILE = "wordpress-api.xml";
    public static String SEARCH_URL = "https://developer.wordpress.org/reference/%s/page/%d/";

    public static String getSearchUrl(TYPES type, int page) {
        String typeStr = "";

        switch(type) {
            case FUNCTIONS: typeStr = "functions"; break;
            case HOOKS: typeStr = "hooks"; break;
            case CLASSES:
            default:
                typeStr = "classes";
                break;
        }

        String searchUrl = SEARCH_URL
                .replace("%s", typeStr)
                .replace("%d", Integer.toString(page));

        return searchUrl;
    }

    public static void parse(TYPES type) {
        try {
            String url = getSearchUrl(type, 1);
            System.out.println(url);
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.toString());
            //...
        } catch (IOException ex) {
            System.out.println(ex.toString());
            //...
        }
    }
}
