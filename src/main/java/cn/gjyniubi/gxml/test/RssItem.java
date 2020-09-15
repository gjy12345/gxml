package cn.gjyniubi.gxml.test;

public class RssItem {
    @XmlRule("/title")
    private String title;
    @XmlRule("/link")
    private String link;
    @XmlRule("/description")
    private String description;
    @XmlRule("/dc:creator")
    private String creator;
    @XmlRule("/pubDate")
    private String pubDate;
    @XmlRule("/guid")
    private String guid;
    @XmlRule("")
    private TestInner testInner;


    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", guid='" + guid + '\'' +
                ", testInner=" + testInner +
                '}';
    }

    public static class TestInner{
        @XmlRule("/guid")
        private String test;

        @Override
        public String toString() {
            return "TestInner{" +
                    "test='" + test + '\'' +
                    '}';
        }
    }
}
