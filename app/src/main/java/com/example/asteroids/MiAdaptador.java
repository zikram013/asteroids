package com.example.asteroids;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
    private LayoutInflater inflador;
    private Vector<String>lista;
    protected View.OnClickListener onClickListener;


    public MiAdaptador(Context context,Vector<String>lista){
        this.lista=lista;
        inflador=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MiAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=inflador.inflate(R.layout.elemento_lista,parent,false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiAdaptador.ViewHolder holder, int position) {
        holder.titulo.setText(lista.get(position));
        switch (Math.round((float)Math.random()*3)){
            case 0:
                holder.icon.setImageResource(R.drawable.asteroide1);
            case 1:
                holder.icon.setImageResource(R.drawable.asteroide2);
            /*default:
                holder.icon.setImageResource(R.drawable.asteroide3); */
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo,subtitulo;
        public ImageView icon;
        ViewHolder(View itemView){
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.titulo);
            subtitulo=(TextView)itemView.findViewById(R.id.subtitulo);
            icon=(ImageView)itemView.findViewById(R.id.icono);
        }
    }
}
