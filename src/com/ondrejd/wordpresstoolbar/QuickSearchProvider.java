/**
 * @author  Ondřej Doněk, <ondrejd@gmail.com>
 * @license https://www.gnu.org/licenses/gpl-3.0.en.html GNU General Public License 3.0
 * @link https://github.com/ondrejd/od-downloads-plugin for the canonical source repository
 */

package com.ondrejd.wordpresstoolbar;

import org.netbeans.spi.quicksearch.SearchProvider;
import org.netbeans.spi.quicksearch.SearchRequest;
import org.netbeans.spi.quicksearch.SearchResponse;

/**
 * Provides quick search over <a href="#">WordPress Code Reference</a>.
 */
public class QuickSearchProvider implements SearchProvider {

    public void evaluate(SearchRequest request, SearchResponse response) {

        //sample code
        //for (SearchedItem item : getAllItemsToSearchIn()) {
        //    if (isConditionSatisfied(item, request)) {
        //        if (!response.addResult(item.getRunnable(), item.getDisplayName(),
        //	      item.getShortcut(), item.getDisplayHint())) {
        //	      break;
        //	  }
        //    }
        //}
    }

}
