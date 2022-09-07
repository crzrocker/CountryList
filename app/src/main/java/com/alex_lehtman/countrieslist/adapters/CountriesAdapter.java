package com.alex_lehtman.countrieslist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex_lehtman.countrieslist.R;
import com.alex_lehtman.countrieslist.model.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<Country> mCountryList;
    private OnItemClickListener listener;

    public CountriesAdapter(Context mContext, ArrayList<Country> countryList) {
        this.mContext = mContext;
        this.mCountryList = countryList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Country country = mCountryList.get(position);

        MyViewHolder mvh = (MyViewHolder) holder;
        mvh.tvName.setText(country.getName().getOfficialName());
        mvh.tvCapital.setText(country.getCapital().get(0));
        mvh.tvPopulation.setText(country.getPopulation());

        Picasso.get().load(country.getFlag().getPng()).into(mvh.imgFlag);
    }

    @Override
    public int getItemCount() {
        if (mCountryList == null){
            return 0;
        }
        return mCountryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFlag;
        private TextView tvName;
        private TextView tvCapital;
        private TextView tvPopulation;

        private LinearLayout countryRow;

        public MyViewHolder(final View view)
        {
            super(view);
            tvName = (TextView) view.findViewById(R.id.itemCountryOfficialName);
            tvCapital = (TextView) view.findViewById(R.id.itemCountryCapital);
            tvPopulation = (TextView) view.findViewById(R.id.itemCountryPopulation);
            imgFlag = (ImageView) view.findViewById(R.id.itemCountryFlag);

            countryRow = (LinearLayout) view.findViewById(R.id.itemCountryRow);

            countryRow.setOnClickListener(v -> {
                if (listener != null)
                    listener.onItemClick(getAbsoluteAdapterPosition());
            });
        }
    }
}


