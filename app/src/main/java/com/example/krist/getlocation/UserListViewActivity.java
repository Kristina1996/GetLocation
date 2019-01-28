package com.example.krist.getlocation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by krist on 27.07.2018.
 */

public class UserListViewActivity extends ArrayAdapter<String> {

    private String[] Name;
    private String[] Surname;
    private String[] Login;
    //private String[] imagepath;

    private Activity context;
    Bitmap bitmap;

    public UserListViewActivity (Activity context, String[] Name, String[] Surname, String[] Login) {
        super(context, R.layout.activity_item, Name);

        this.context = context;
        this.Name = Name;
        this.Surname = Surname;
        this.Login = Login;
        //this.imagepath=imagepath;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.activity_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)r.getTag();
        }

        viewHolder.txtName.setText(Name[position]);
        viewHolder.txtSurname.setText(Surname[position]);
        viewHolder.txtLogin.setText(Login[position]);
        //new GetImageFromURL(viewHolder.ivw).execute(imagepath[position]);

        return r;
    }

    class ViewHolder{

        TextView txtName;
        TextView txtSurname;
        TextView txtLogin;
        //ImageView image;

        ViewHolder(View v){
            txtName = (TextView)v.findViewById(R.id.txtName);
            txtSurname = (TextView)v.findViewById(R.id.txtSurname);
            txtLogin = (TextView)v.findViewById(R.id.txtLogin);
            //ivw=(ImageView)v.findViewById(R.id.imageView);
        }

    }

    /*public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
    {

        ImageView imgView;
        public GetImageFromURL(ImageView imgv)
        {
            this.imgView=imgv;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay=url[0];
            bitmap=null;

            try{

                InputStream ist=new java.net.URL(urldisplay).openStream();
                bitmap= BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }*/
}
