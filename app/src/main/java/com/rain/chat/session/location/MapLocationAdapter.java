package com.rain.chat.session.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;


import com.rain.chat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : duyu
 * @date : 2019/1/24 11:02
 * @filename : MapLocationAdapter
 * @describe :
 */
public class MapLocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nim_location_list_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LocationViewHolder) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                ((LocationViewHolder) holder).txv_addressDetails.setVisibility(View.VISIBLE);
                ((LocationViewHolder) holder).mAddress.setText(locationInfos.get(position).getName());
                ((LocationViewHolder) holder).txv_addressDetails.setText(locationInfos.get(position).getAddress());

            } else {
                ((LocationViewHolder) holder).mAddress.setText(String.format(locationInfo.getAddress()));
                ((LocationViewHolder) holder).txv_addressDetails.setVisibility(View.GONE);
            }

            if (selected == position)
                ((LocationViewHolder) holder).mRadioButton.setVisibility(View.VISIBLE);
            else
                ((LocationViewHolder) holder).mRadioButton.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return locationInfos.size();
    }

    private List<LocationInfo> locationInfos = new ArrayList<>();

    private LocationInfo locationInfo;

    public void setNewData(List<LocationInfo> locationInfoList, LocationInfo locationInfo) {
        locationInfos.clear();
        this.locationInfos = locationInfoList;
        this.locationInfo = locationInfo;
    }

    private int selected = 0;

    private class LocationViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAddress;
        private final TextView txv_addressDetails;
        private final AppCompatRadioButton mRadioButton;

        public LocationViewHolder(View itemView) {
            super(itemView);
            mAddress = itemView.findViewById(R.id.txv_address);
            txv_addressDetails = itemView.findViewById(R.id.txv_addressDetails);
            mRadioButton = itemView.findViewById(R.id.radioButton);
                itemView.setOnClickListener(v -> {
                    selected = getAdapterPosition();
                    onItemClick.onItemClick(locationInfos.get(getAdapterPosition()), getAdapterPosition());
                    notifyDataSetChanged();
                });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (locationInfo != null && position == 0)
            return TYPE_HEADER;
        else return TYPE_NORMAL;
    }

    interface OnItemClickListener {
        void onItemClick(LocationInfo data, int position);
    }

    OnItemClickListener onItemClick;

    void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }


}
