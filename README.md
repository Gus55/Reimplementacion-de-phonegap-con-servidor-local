
# Servidor local para simular phonegap


El siguiente código, genera un servidol local dentro de la aplicación, para cargar archivos html y mostrarlos en un webview, simulando el funcionamiento de PhoneGap.

Video: https://drive.google.com/file/d/1AVz5HQKPZkkQlN_Njc3rqJaOFT-_mBpJ/view?usp=sharing


 
# Herramientas
Para desarrollar la aplicación se utilizó Android Stuido y la librería NanoHTTPD para el servidor web

# Antes de
Se deberá Crear la carpeta assets en el directorio principal de la aplicación.
En la carpeta assets deberas colocar tu archivo html, css y/o js.

Colocar la siguiente dependencia en el build gradle
implementation 'org.nanohttpd:nanohttpd:2.3.1'

Colocar en el AndroidManifest.xml el permiso:
<uses-permission android:name="android.permission.INTERNET"></uses-permission>

# Implementación de servidor
# 1.-Crear el servidor y asignarle un puerto
Context ctx;
    public  Servidor(Context context){
        super(8000);
        ctx=context;
    }
# 2.- Crear el método serve para poner los archivos en el servidor
    
    public Response serve(IHTTPSession session)
    {
        String uri = session.getUri();
        String filename = uri.substring(1);
        String type;

        if(uri.equals("/"))
            filename = "index.html";
            boolean texto=true;
# 2.1- La variable type diferencia el tipo de archivos que se cargarán al servidor
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


# 3.- Usar BufferedReader e InputStream para obtener los datos de los archivos

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
        
# 4.- Inicializar el servidor en el MainActivity
     server= new Servidor(this);
        try{
            server.start();
        }
        catch (IOException ioe)
        {

        }
# 5.- Mostrar contenido en el webview
        webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://localhost:8000");

      

# 6.- Notas
La direcin local host solo funcionara en un dispositivo físico. Si se prueba desde un emulador, se tendra que poner la ip de el emulador, o de otra forma la aplicación no funcionara.

