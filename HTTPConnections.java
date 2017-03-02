import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

/**
 * A utility that downloads a file from a URL.
 * @author www.codejava.net
 *
 */
public class HTTPConnections {
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) throws Exception {
        HTTPConnections http = new HTTPConnections();
        HTTPConnections.sendHTTPGetRequest("http://control.charles:/recording/stop", "8888");
        String saveCharlesUrl = "http://control.charles:/session/export-har";
        HTTPConnections.downloadFile("har.har", saveCharlesUrl, "8888");
    }

    /**
     * Downloads a file from a URL
     *
     * @throws IOException
     */
    protected static void downloadFile(String fileName, String fileURL, String proxyPort) {
        try {
            File currentDirFile = new File(".");
            String saveDir = "/Users/cen/Code/node-server/";
            System.out.println("Current path - " + saveDir);

//            String fileURL = "http://control.charles/session/export-har";


            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", Integer.parseInt(proxyPort)));
            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);

            int responseCode = 0;

                responseCode = httpConn.getResponseCode();


            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
    //                    fileName = disposition.substring(index + 9,
    //                            disposition.length() );
                    }
                } else {
                    // extracts file name from URL
    //                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
    //                        fileURL.length());
                }

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = saveDir + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded");
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void sendHTTPGetRequest(String url, String proxyPort) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", Integer.parseInt(proxyPort)));
            URL obj = new URL(url);

            HttpURLConnection con = null;

                con = (HttpURLConnection) obj.openConnection(proxy);


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Through Proxy: 127.0.0.1:" + proxyPort);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
    //        System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




//        HTTPConnections http = new HTTPConnections();



//        System.out.println("Testing 1 - Send Http GET request");
//        http.sendGet();

//        http.getSSLCert();
//        String accessToken = http.sendOAuthRequest("","");
//        http.sendMessageCentreMessage("Hello from an automated test");
//        http.openAutomatedTestMessage();
//            http.sendChangePasswordRequest("donedealuser2@gmail.com", "dealdone", "donedeal");
//        System.out.println("\nTesting 3 - Get SSL Socket");
//            http.login();
//        http.getSSLCert();
}
