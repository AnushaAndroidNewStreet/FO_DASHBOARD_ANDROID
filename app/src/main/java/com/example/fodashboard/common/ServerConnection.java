package com.example.fodashboard.common;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerConnection implements Runnable {
    public static final int OPERATION_SUCCESS = 1001;
    public static final int OPERATION_FAILURE_NETWORK = 1002;
    public static final int OPERATION_FAILURE_GENERAL_SERVER = 1003;
    int action_code;
    Handler handler;
    String url;
    static boolean  isLongOperation = false;

    public ServerConnection(int _action_code, Handler _handler,
                            String _connectionURL) {
        this.action_code = _action_code;
        this.handler = _handler;
        this.url = _connectionURL;
    }

    public ServerConnection(int _action_code, Handler _handler,
                            String _connectionURL, boolean isLongOperation) {
        this.action_code = _action_code;
        this.handler = _handler;
        this.url = _connectionURL;
        this.isLongOperation = isLongOperation;
    }


    public static void Getdata(JSONObject data, Handler handler, int action_code, String url){
        BufferedReader buffer = null;
        int _statuscode = 0;
        try {

            HttpParams httpParameters = new BasicHttpParams();
            httpParameters.setParameter("Payload",data);


            // httpParameters.setParameter("geo",
            // "{\"$circle\":{\"$center\":[\" + x + \",\" + y + \"],\"$meters\":5000}}");
            HttpGet httpGet = new HttpGet(url);
            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 15000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 40000;
            if (isLongOperation) {
                timeoutSocket = 40000;
            }
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            httpClient
                    .setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
                            0, false));


            HttpResponse response = httpClient.execute(httpGet);

            _statuscode = response.getStatusLine().getStatusCode();
            // Log.d(ServerConnection.class.getCanonicalName(),
            // "statusCode::"+_statuscode);

            if (_statuscode != HttpStatus.SC_OK) {
                throw new IllegalStateException();
            }
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();

            StringBuffer responseBuffer = new StringBuffer();
            buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseBuffer.append(s);
            }
            buffer.close();
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_SUCCESS;
            msg.arg2 = _statuscode;
            msg.obj = responseBuffer.toString();
            handler.sendMessage(msg);
            // interfaceObject.onresponsefromserver(actioncode, stringresponse);
        } catch (IllegalStateException illegalEx) {
            // Log.d(getClass().getSimpleName(),
            // "IllegalStateHTTP Connection Error:--"+illegalEx);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_GENERAL_SERVER;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } catch (Exception eGeneral) {
            // Log.d(getClass().getSimpleName(),
            // "GeneralStateHTTP Connection Error:--"+eGeneral);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_NETWORK;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException io) {
                    // Log.e(getClass().getSimpleName(),
                    // "error shutting down connection "+io);
                }
            }
        }
    }


    public static void postData(String data, Handler handler, int action_code, String url) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost(url);
        int _statuscode = 0;
        BufferedReader buffer = null;
        try {
            // Add your data
            //	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(12);


            //CloseableHttpClient httpclient = HttpClientBuilder.create().build();

            StringEntity requestEntity = new StringEntity(data);

            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(requestEntity);

            HttpResponse rawResponse = httpclient.execute(postMethod);
            System.out.println(rawResponse);

            HttpEntity entity = rawResponse.getEntity();
            System.out.println(entity.getContentLength());
//			JSONObject json= (JSONObject) new JSONParser().parse(EntityUtils.toString(entity));


            //nameValuePairs.add(new BasicNameValuePair("", data));
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			/*HttpResponse response = httpclient.execute(postMethod);
            _statuscode = response.getStatusLine().getStatusCode();
			if (_statuscode != HttpStatus.SC_OK) {
				throw new IllegalStateException();
			}*/
            //HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();

            StringBuffer responseBuffer = new StringBuffer();
            buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseBuffer.append(s);
            }
            buffer.close();
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_SUCCESS;
            msg.arg2 = _statuscode;
            msg.obj = responseBuffer.toString();
            handler.sendMessage(msg);
            // interfaceObject.onresponsefromserver(actioncode, stringresponse);
        } catch (IllegalStateException illegalEx) {
            // Log.d(getClass().getSimpleName(),
            // "IllegalStateHTTP Connection Error:--"+illegalEx);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_GENERAL_SERVER;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } catch (Exception eGeneral) {
            // Log.d(getClass().getSimpleName(),
            // "GeneralStateHTTP Connection Error:--"+eGeneral);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_NETWORK;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException io) {
                    // Log.e(getClass().getSimpleName(),
                    // "error shutting down connection "+io);
                }
            }
        }
    }

    public static Message postData(String data, int action_code, String url) {

        JSONObject jsono = null;
        String rawresponse;
        Message msg = null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();


        HttpResponse response = null;
        try {
            StringEntity requestEntity = new StringEntity(data);
            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(requestEntity);
            response = httpclient.execute(postMethod);
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            rawresponse = EntityUtils.toString(entity);
            jsono = new JSONObject(rawresponse);

            //forming message object
            msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_SUCCESS;
            msg.obj = jsono.toString();
            //Log.i("response",""+msg.obj);
        } catch (IllegalStateException illegalEx) {
            msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_GENERAL_SERVER;
       } catch (Exception eGeneral) {
            eGeneral.printStackTrace();

            msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_NETWORK;
        }
        return msg;
    }
/*
            //HttpPost httppost = new HttpPost(url);
        int _statuscode = 0;n
        BufferedReader buffer = null;
        try {
            StringEntity requestEntity = new StringEntity(data);
            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(requestEntity);
            HttpResponse rawResponse = httpclient.execute(postMethod);
            System.out.println(rawResponse);

            HttpEntity entity = rawResponse.getEntity();
            System.out.println(entity.getContentLength());
            InputStream content = entity.getContent();

            StringBuffer responseBuffer = new StringBuffer();
            buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseBuffer.append(s);
            }
            buffer.close();
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_SUCCESS;
            msg.arg2 = _statuscode;
            msg.obj = responseBuffer.toString();
            handler.sendMessage(msg);
        } catch (IllegalStateException illegalEx) {

            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_GENERAL_SERVER;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
        } catch (Exception eGeneral) {

            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_NETWORK;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException io) {
                }
            }
        }*/


    @Override
    public void run() {
        BufferedReader buffer = null;
        int _statuscode = 0;
        try {

            HttpParams httpParameters = new BasicHttpParams();
            // httpParameters.setParameter("geo",
            // "{\"$circle\":{\"$center\":[\" + x + \",\" + y + \"],\"$meters\":5000}}");
            HttpGet httpGet = new HttpGet(url);
            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 15000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 40000;
            if (isLongOperation) {
                timeoutSocket = 40000;
            }
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            httpClient
                    .setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
                            0, false));

            HttpResponse response = httpClient.execute(httpGet);

            _statuscode = response.getStatusLine().getStatusCode();
            // Log.d(ServerConnection.class.getCanonicalName(),
            // "statusCode::"+_statuscode);

            if (_statuscode != HttpStatus.SC_OK) {
                throw new IllegalStateException();
            }
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();

            StringBuffer responseBuffer = new StringBuffer();
            buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                responseBuffer.append(s);
            }
            buffer.close();
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_SUCCESS;
            msg.arg2 = _statuscode;
            msg.obj = responseBuffer.toString();
            handler.sendMessage(msg);
            // interfaceObject.onresponsefromserver(actioncode, stringresponse);
        } catch (IllegalStateException illegalEx) {
            // Log.d(getClass().getSimpleName(),
            // "IllegalStateHTTP Connection Error:--"+illegalEx);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_GENERAL_SERVER;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } catch (Exception eGeneral) {
            // Log.d(getClass().getSimpleName(),
            // "GeneralStateHTTP Connection Error:--"+eGeneral);
            Message msg = new Message();
            msg.what = action_code;
            msg.arg1 = OPERATION_FAILURE_NETWORK;
            msg.arg2 = _statuscode;
            handler.sendMessage(msg);
            // interfaceobject.onerrorresponse(int code, int errorcode)
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException io) {
                    // Log.e(getClass().getSimpleName(),
                    // "error shutting down connection "+io);
                }
            }
        }
    }

}