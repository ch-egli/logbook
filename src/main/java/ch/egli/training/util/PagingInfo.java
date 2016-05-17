package ch.egli.training.util;

/**
 * Cf. https://github.com/eugenp/reddit-app/blob/master/reddit-rest/src/main/java/org/baeldung/web/controller/rest/PagingInfo.java.
 *
 * @author Christian Egli
 * @since 5/17/16.
 */
public class PagingInfo {


    private final long totalNoRecords;
    private final int totalNoPages;
    private String uriToNextPage;
    private String uriToPrevPage;

    public PagingInfo(final int page, final int size, final long totalNoRecords) {
        this.totalNoRecords = totalNoRecords;
        this.totalNoPages = Math.round(totalNoRecords / size);
        if (page > 0) {
            this.uriToPrevPage = "page=" + (page - 1) + "&size=" + size;
        }
        if (page < this.totalNoPages) {
            this.uriToNextPage = "page=" + (page + 1) + "&size=" + size;
        }
    }

    //
    public long getTotalNoRecords() {
        return totalNoRecords;
    }

    public int getTotalNoPages() {
        return totalNoPages;
    }

    public String getUriToNextPage() {
        return uriToNextPage;
    }

    public String getUriToPrevPage() {
        return uriToPrevPage;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("totalNoRecords=").append(totalNoRecords)
                .append(", totalNoPages=").append(totalNoPages)
                .append(", uriToNextPage=").append(uriToNextPage)
                .append(", uriToPrevPage=").append(uriToPrevPage);
        return builder.toString();
    }

}
