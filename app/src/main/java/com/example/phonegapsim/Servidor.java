package com.example.phonegapsim;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fi.iki.elonen.NanoHTTPD;

public class Servidor extends NanoHTTPD {
    Context ctx;
    public  Servidor(Context context){
        super(8000);
        ctx=context;
    }


    public Response serve(IHTTPSession session)
    {
        String uri = session.getUri();
        String filename = uri.substring(1);
        String type;

        if(uri.equals("/"))
            filename = "index.html";
            boolean texto=true;

        if (filename.contains(".html")) {
            type = "text/html";
            texto = true;
        } else if (filename.contains(".js")) {
            type = "text/javascript";
            texto = true;
        } else if (filename.contains(".css")) {
            type = "text/css";
            texto = true;
        } else if (filename.contains(".jpeg") || filename.contains(".jpg")) {
            type = "text/jpeg";
            texto = false;
        } else {
            filename = "index.html";
            type = "text/html";
        }

        if(texto)
        {
            String response="";
            String linea;
            BufferedReader reader;

            try{
                reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(filename)));
                while ((linea = reader.readLine()) !=null){
                    response+=linea;
                }
                reader.close();
            }
            catch (IOException ioe){

            }
            return newFixedLengthResponse(Response.Status.OK,type,response);
        }
        else {
            try {
                InputStream isr = ctx.getAssets().open(filename);
                return newFixedLengthResponse(Response.Status.OK,type,isr,isr.available());
            } catch (IOException e) {
                e.printStackTrace();
                return newFixedLengthResponse(Response.Status.OK, type, "");
            }
        }
    }
}
