package yyc.demo.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final int MAX = 0x32000;
    private byte[] body;
    private String str;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String sessionStream = getBodyString(request);
        body = sessionStream.getBytes(StandardCharsets.UTF_8);
        str = sessionStream;
    }

    private String getBodyString(final ServletRequest request) throws IOException {
        if( request.getInputStream().available() > MAX) {
            throw new IllegalStateException("request message is huge:");
        }
        StringBuilder sb = new StringBuilder();
        try (
            InputStream inputStream = cloneInputStream(request.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
         ){
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch(IOException e){

        }
        return sb.toString();
    }

    public void setBody(String bodyString){
        this.body = bodyString.getBytes(StandardCharsets.UTF_8);
    }

    public InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try{
            while ((len = inputStream.read(buffer)) > -1 ){
                byteArrayOutputStream.write(buffer,0,len);
            }
            byteArrayOutputStream.flush();
        }catch (IOException e) {
            System.out.println("复制输入流异常");
        }
        return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public String getStr(){
        return  str;
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public ServletInputStream getInputStream() throws IOException{
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }


}
